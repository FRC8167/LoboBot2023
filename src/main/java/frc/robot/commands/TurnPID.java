// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TurnPID extends CommandBase {
  private final Drivetrain drivetrain;
  private final double turnAngle;
  double targetHeading;
  double initialAngle; 
  double startTurnTime;
  
  /** Creates a new TurnPID. */
  public TurnPID (Drivetrain drivetrain, double turnAngle ) {
    drivetrain.turnPID.enableContinuousInput(-180, 180);
    drivetrain.turnPID.setTolerance(3, 5);  //how close is close enough?
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.turnAngle = turnAngle;
    addRequirements(drivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTurnTime = Timer.getFPGATimestamp();
    initialAngle = drivetrain.getYaw();
  }
  

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    targetHeading = (initialAngle + turnAngle);
    drivetrain.turnPID.calculate(drivetrain.getYaw(), targetHeading);
    if(!drivetrain.turnPID.atSetpoint()) {
      drivetrain.arcadeDrive(0, drivetrain.turnPID.calculate(drivetrain.getYaw(), targetHeading));
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return drivetrain.turnPID.atSetpoint()||Timer.getFPGATimestamp() - startTurnTime > 2;
  }
}
