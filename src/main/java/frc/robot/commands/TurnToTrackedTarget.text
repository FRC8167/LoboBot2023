// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import org.photonvision.PhotonUtils;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class TurnToTrackedTarget extends CommandBase {
  Drivetrain drivetrain;
  Vision vision;
  //double visionTargetAngle;  //is this used?
  double visionKP;  //error correction

  /** Creates a new TurnToTrackedTarget. */
  public TurnToTrackedTarget(Drivetrain drivetrain, Vision vision, double visionKP) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.vision = vision;
    visionKP = Constants.TRACK_TAG_ROTATION_KP;  //THIS NEEDS ADJUSTING
    addRequirements(drivetrain, vision);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (vision.getHasTarget())  {
      double error = vision.getBestTarget().getYaw();
      double rotationSpeed = -Math.min(error * visionKP, 1);
      double forwardSpeed = 0.4;  //SHOULD THIS BE ZERO SO THERE IS NO FORWARD MOTION AND ONLY ROTATION??
      drivetrain.arcadeDrive(forwardSpeed, rotationSpeed);

      double range = PhotonUtils.calculateDistanceToTargetMeters(
        Constants.CAMERA_HEIGHT_METERS,
        Constants.TARGET_HEIGHT_METERS,
        Constants.CAMERA_PITCH_RADIANS,
        Units.degreesToRadians(vision.getBestTarget().getPitch()));
        System.out.println("Range = "+range);
        drivetrain.arcadeDrive(forwardSpeed, rotationSpeed); //replace with motion magic and use range


      
    }  else  {
      System.out.println("EPIC FAIL");
      drivetrain.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("TurnToTrackedTarget ENDED");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
