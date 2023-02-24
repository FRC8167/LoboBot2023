// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class QuickTurn extends CommandBase {
  private final Drivetrain drivetrain;
  private final double degreesToTurn;  //the amount the robot should turn
  private double initialAngle;   //the robots current heading (can be more than 360)
  double targetHeading;  //the initialAngle + degreesToTurn
  private final int reversed;  //reverses the turning direction depending on -/+ angles
  double error;  // How incorrect the current angle is as it is turning
  private double startTurnTime;  //start the clock
  /** Creates a new QuickTurn. */

  public QuickTurn(Drivetrain drivetrain, double degreesToTurn) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.degreesToTurn = degreesToTurn;
    this.reversed = ((this.degreesToTurn < 0)?-1:1);
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //drivetrain.zeroPigeon(0);  //not needed if we use mod 360
    startTurnTime = Timer.getFPGATimestamp();
    this.initialAngle = drivetrain.getYaw();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    targetHeading = (initialAngle + degreesToTurn);
    error = (Math.abs((drivetrain.getYaw() - (initialAngle - degreesToTurn))%360));
    double power = error * .113;//Constants.PIGEON_TURN_KP;  
    if (Math.abs(power) > 0.5) {  //maximum power value desired
      power = Math.copySign(0.5, power);
    }
    if (Math.abs(power) < 0.15) {
      power = Math.copySign(0.15, power);  //minimum power value desired
    }
    System.out.println("QuickTurn Error: " + error);
    System.out.println("Power:  " +  power);
    drivetrain.tankDrive(power*reversed, -power*reversed);  //drive with the calculated speed
    System.out.println("Current Heading = "+ drivetrain.getYaw());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
    //drivetrain.zeroPigeon(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (Math.abs(error)) < Constants.QUICKTURN_THRESHOLD_DEGREES || Timer.getFPGATimestamp() - startTurnTime > 2;
  }
}
