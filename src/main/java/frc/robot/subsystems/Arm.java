// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
//simport com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

//import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants; 

public class Arm extends SubsystemBase {
  private final WPI_TalonFX armMotor;
  
  /** Creates a new Arm. */
  public Arm() {
    armMotor = new WPI_TalonFX(Constants.ARM_MOTOR);
    configmotor(); 

  }

  private void configmotor(){

    armMotor.configFactoryDefault();
    armMotor.setNeutralMode(NeutralMode.Brake);
    armMotor.configNeutralDeadband(0.001, 30);

    armMotor.configClosedloopRamp(1.0);  //is this supposed to match the spreadsheet?
    armMotor.configOpenloopRamp(0.5);

    armMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, Constants.ARM_pidLoopTimeout);
    armMotor.selectProfileSlot(0, 0);

    armMotor.config_kF(0, .046, 30); //set to zero first
    armMotor.config_kP(0,  0.049, 30); //needs Pheonix Tuner
    armMotor.config_kI(0, 0, 30);
    armMotor.config_kD(0, 0.0, 30);

    armMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 30);
    armMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 30);

    armMotor.setInverted(true);  //??
    armMotor.setSensorPhase(false);  //??
    
    armMotor.configNominalOutputForward(0, 30);
    armMotor.configNominalOutputReverse(0, 30);
    armMotor.configPeakOutputForward(1, 30);
    armMotor.configPeakOutputReverse(-1, 30);

    armMotor.configMotionCruiseVelocity(Constants.armCruiseVelocity, Constants.ARM_pidLoopTimeout);
    armMotor.configMotionAcceleration(Constants.armAcceleration, Constants.ARM_pidLoopTimeout);

    //Zero the encoder
    armMotor.setSelectedSensorPosition(0, Constants.ARM_kpIDLoopIDx, Constants.ARM_pidLoopTimeout);

  }


  public void testArm(double power) {
    armMotor.set(ControlMode.PercentOutput, power);
    double motorOutput = armMotor.getMotorOutputPercent();
    //Display motor power, velocity, and sensor position   
    StringBuilder info = new StringBuilder();
    info.append("\tOutput Power: ");
    info.append(motorOutput);
    info.append("\tMotor Velocity: ");
    info.append(armMotor.getSelectedSensorVelocity(Constants.ARM_kpIDLoopIDx));
    info.append("\tSENSOR POSITION:  ");
    info.append(armMotor.getSelectedSensorPosition(Constants.ARM_kpIDLoopIDx));
   
    System.out.println(info.toString());
  }

  public void setArmMotionMagic(double targetRotations){
    double targetTicks = targetRotations * 20 * 2048;
    //double retractHoldOutput = 0.13;
    armMotor.set(TalonFXControlMode.MotionMagic, targetTicks);
   // armMotor.set(TalonFXControlMode.MotionMagic, targetTicks, DemandType.ArbitraryFeedForward, retractHoldOutput);

    // Display PID Commanded Target and Resulting Error
    StringBuilder moreinfo = new StringBuilder();
    moreinfo.append("\tCommanded Target: ");
    moreinfo.append(targetTicks);
    moreinfo.append("\tPID Error: ");
    moreinfo.append(armMotor.getClosedLoopError());
    moreinfo.append("\tSENSOR POSITON:  ");
    moreinfo.append(armMotor.getSelectedSensorPosition());

    System.out.println(moreinfo.toString());

  }


  public void stop(){
    armMotor.set(ControlMode.PercentOutput, 0);  
  }
  

  public double getArmPosition(){
    return armMotor.getSelectedSensorPosition(0);
  }


  public void zeroArmSensor() {
    armMotor.setSelectedSensorPosition(0);
  }


  @Override
  public void periodic() {
  }
}