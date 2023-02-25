// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class ChargingStationAutoBalance extends CommandBase {
  private Drivetrain drivetrain;
    private double error;
    private double currentPitchAngle;
    private double drivePower;
  /** Creates a new ChargingStationAutoBalance. */
  public ChargingStationAutoBalance(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
    
    
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.currentPitchAngle = -drivetrain.getPitch();
    error = currentPitchAngle - Constants.BALANCE_GOAL_DEGREES;
    drivePower = Math.min(0.025 * error, 1);  //Constants.BALANCE_KP
    System.out.println("Current Pitch Angle: " + currentPitchAngle);
    System.out.println("Pitch Error: " + error);
    System.out.println("Balancing Drive Power: " + drivePower);

    //Just in case more or less power is needed for a backwards approach due to weight imbalance back vs front
    if (drivePower < 0) {
      drivePower *= Constants.BALANCE_REVERSE_POWER;
    }

    //Limit max drivePower to minimize overshooting
    // if (Math.abs(drivePower) > 0.6) {
    //   drivePower = Math.copySign(0.6, drivePower);
    // }
    drivetrain.arcadeDrive(-drivePower, 0);

    //Check error conditions
   
  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(error) < 1.0;  //Constants.BALANCE_PITCH_THRESHOLD;  //this ends the auto command
  }
}
