// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

//import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants; 

public class Pivot extends SubsystemBase {
  private final WPI_TalonFX pivotMotor;
  
  /** Creates a new Pivot. */
  public Pivot() {
    pivotMotor = new WPI_TalonFX(Constants.PIVOT_MOTOR);

    configmotor(); 

  }

  private void configmotor(){

    pivotMotor.configFactoryDefault();
    pivotMotor.setNeutralMode(NeutralMode.Brake);
    pivotMotor.configNeutralDeadband(0.001, 30);

    pivotMotor.configClosedloopRamp(1.0);  //is this supposed to match the spreadsheet?
    pivotMotor.configOpenloopRamp(0.5);

    pivotMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, Constants.PIVOT_pidLoopTimeout);
    pivotMotor.selectProfileSlot(0, 0);

    pivotMotor.config_kF(0, .046, 30); //set to zero first
    pivotMotor.config_kP(0,  0.049, 30); //needs Pheonix Tuner
    pivotMotor.config_kI(0, 0, 30);
    pivotMotor.config_kD(0, 0.0, 30);

    pivotMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 30);
    pivotMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 30);

    pivotMotor.setInverted(true);  //??
    pivotMotor.setSensorPhase(false);  //??
    
    pivotMotor.configNominalOutputForward(0, 30);
    pivotMotor.configNominalOutputReverse(0, 30);
    pivotMotor.configPeakOutputForward(1, 30);
    pivotMotor.configPeakOutputReverse(-1, 30);

    pivotMotor.configMotionCruiseVelocity(Constants.pivotCruiseVelocity, Constants.PIVOT_pidLoopTimeout);
    pivotMotor.configMotionAcceleration(Constants.pivotAcceleration, Constants.PIVOT_pidLoopTimeout);

    //Zero the encoder
    pivotMotor.setSelectedSensorPosition(0, Constants.PIVOT_kpIDLoopIDx, Constants.PIVOT_pidLoopTimeout);

  }


  public void testPivot(double power) {
    pivotMotor.set(ControlMode.PercentOutput, power);
    double motorOutput = pivotMotor.getMotorOutputPercent();
    //Display motor power, velocity, and sensor position   
    StringBuilder pivotinfo = new StringBuilder();
    pivotinfo.append("\tPIVOT Output Power: ");
    pivotinfo.append(motorOutput);
    pivotinfo.append("\tPIVOT Motor Velocity: ");
    pivotinfo.append(pivotMotor.getSelectedSensorVelocity(Constants.PIVOT_kpIDLoopIDx));
    pivotinfo.append("\tPIVOT SENSOR POSITION:  ");
    pivotinfo.append(pivotMotor.getSelectedSensorPosition(Constants.PIVOT_kpIDLoopIDx));
   
    System.out.println(pivotinfo.toString());
  }

  public void setPivotMotionMagic(double targetRotations){  
    double targetTicks = targetRotations * 80 * 2048;  //gearbox for pivot is 80:1
    double horizontaltHoldOutput = 0.13;
    double arbFeedFwdTerm = getFeedForward(horizontaltHoldOutput);
    //pivotMotor.set(TalonFXControlMode.MotionMagic, targetTicks);
    pivotMotor.set(TalonFXControlMode.MotionMagic, targetTicks, DemandType.ArbitraryFeedForward, arbFeedFwdTerm);
    // Display PID Commanded Target and Resulting Error
    StringBuilder morepivotinfo = new StringBuilder();
    morepivotinfo.append("\tPIVOT Commanded Target: ");
    morepivotinfo.append(targetTicks);
    morepivotinfo.append("\tPIVOT PID Error: ");
    morepivotinfo.append(pivotMotor.getClosedLoopError());
    morepivotinfo.append("\tPIVOT SENSOR POSITON:  ");
    morepivotinfo.append(pivotMotor.getSelectedSensorPosition());

    System.out.println(morepivotinfo.toString());

  }


  public void stop(){
    //armMotor.stopMotor();
    pivotMotor.set(ControlMode.PercentOutput, 0);  
  }
  

  public double getPivotPosition(){
    return pivotMotor.getSelectedSensorPosition(0);
  }


  public void zeroPivotSensor() {
    pivotMotor.setSelectedSensorPosition(0);
  }



  //**********The 45-degree offset is just a guess from sketches.  Change this angle according to actual build******************8

  private double getFeedForward(double horizontalHoldOutput) {
    double pivotSensorPosition = pivotMotor.getSelectedSensorPosition(0);
    double pivotCurrentAngle = Math.toRadians(pivotSensorPosition*360/2048);  //lowest position is 0 via sensor which is really offset 45
    double theta = Math.toRadians(90 - (pivotCurrentAngle + 45.0));  //angle between pivot arm and horizontal where hold output is max
    double gravityCompensation = Math.cos(theta);
    double arbFeedFwd = gravityCompensation * horizontalHoldOutput;
    return arbFeedFwd;
  }


  @Override
  public void periodic() {
    // StringBuilder info = new StringBuilder();
    // StringBuilder moreinfo = new StringBuilder();

    // // This method will be called once per scheduler run
  }
}