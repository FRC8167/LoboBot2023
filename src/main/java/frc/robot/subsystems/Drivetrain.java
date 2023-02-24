// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  /** Creates a new Drivetrain. */
//left side motors
PWMVictorSPX leftFront = new PWMVictorSPX(Constants.LEFT_FRONT);
PWMVictorSPX leftBack = new PWMVictorSPX(Constants.LEFT_BACK);

//right side motors
PWMVictorSPX rightFront = new PWMVictorSPX(Constants.RIGHT_FRONT);
PWMVictorSPX rightBack = new PWMVictorSPX(Constants.RIGHT_BACK);  

//Create MotorControllerGroup objects to use the defined in a DifferentialDrive class.

MotorControllerGroup leftMotors = new MotorControllerGroup(leftFront, leftBack);
MotorControllerGroup rightMotors = new MotorControllerGroup(rightFront, rightBack);

//Create a Differential Drive object with the defined motors.
DifferentialDrive drivetrain = new DifferentialDrive(leftMotors, rightMotors);

private Pigeon2 pigeon = new Pigeon2(Constants.PIGEON_CANID);



  public Drivetrain() {
    //invert the right side motors since they are installed facing the opposite way as the left side motors
    rightMotors.setInverted(true);
    leftMotors.setInverted(false);
    pigeon.configFactoryDefault();
    pigeon.setYaw(0.0);
  }


  //if drivetrain still backwards try changing the -throttle to +throttle
  public void arcadeDrive(double throttle, double rotation){
   // System.out.println("drive arcade");
    drivetrain.arcadeDrive(throttle, rotation);
  }

 
  public void tankDrive(double leftSpeed, double rightSpeed){
    drivetrain.tankDrive(leftSpeed, rightSpeed);
  }
    

  public void driveForward(double speed){
    drivetrain.tankDrive(speed, speed);
  }

  
  public void stop(){
    drivetrain.stopMotor();
  }

  public void zeroPigeon(double reset) {
    pigeon.setYaw(reset);
  }

  public double getYaw() {
    return pigeon.getYaw();
  }

  public double getPitch() {
    return pigeon.getPitch();
  }

  public double getRoll() {
    return pigeon.getRoll();
  }

  public PIDController turnPID = new PIDController(Constants.TURN_PID_kP, Constants.TURN_PID_KI, Constants.TURN_PID_kD);
 
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
