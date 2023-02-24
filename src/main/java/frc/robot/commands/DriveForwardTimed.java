// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveForwardTimed extends CommandBase {
  /** Creates a new DriveForwardTimed. */
  Drivetrain drivetrain;
  final double timeToDrive;
  final double throttle;
  private double startDriveTime;


  


  public DriveForwardTimed(Drivetrain drivetrain, double timeToDrive, double throttle) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.timeToDrive = timeToDrive;
    this.throttle = throttle;
    
    addRequirements(this.drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startDriveTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("in DriverForward:  speed = "+throttle);
    //drivetrain.tankDrive(speed, speed);
    //drivetrain.arcadeDrive(speed, speed);
    // drivetrain.splitArcadeDrive(startTimeTurn, speed);
     drivetrain.arcadeDrive(-throttle, 0.0);
  }

  // Called once the command ends or is interrupt
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return false;
    return startDriveTime + timeToDrive < Timer.getFPGATimestamp();

  }
}
