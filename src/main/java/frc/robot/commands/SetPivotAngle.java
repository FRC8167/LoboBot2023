// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pivot;
import edu.wpi.first.wpilibj.Timer;


public class SetPivotAngle extends CommandBase {
  /** Creates a new SetPivotAngle. */
  private final Pivot pivot;
  private final double pivotTarget;
  private double pivotStartTime;

  public SetPivotAngle(Pivot pivot, double pivotTarget) {  //desired angle in degrees with 0 really at 45 from vertical down
    this.pivot = pivot;
    this.pivotTarget = pivotTarget/360;  //pivotTarget/360 = number of motor revolutions
    addRequirements(pivot);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pivotStartTime = Timer.getFPGATimestamp();
    pivot.zeroPivotSensor();
    pivot.setPivotMotionMagic(pivotTarget);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    pivot.stop();
  }

   private boolean isPivotMotionMagicDone() {
     double pivotSensorAngle = pivot.getPivotPosition();
     double error = pivotSensorAngle - pivotTarget*80*2048;
     System.out.println("Pivot sensor = "+pivotSensorAngle + ", pivotTarget = "+(pivotTarget * 80 *2048));
     double percentErr =  Math.abs(error)/Math.abs(pivotTarget*80*2048);
     if( percentErr < 0.01) {
      return true;
     }

     double timepassed = Timer.getFPGATimestamp() - pivotStartTime ;
     if( timepassed > 7) {
       return true;
     }
    
     System.out.println("percent error = "+percentErr +", timpassed = "+timepassed);

     return false;

    }
  

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean isDone = isPivotMotionMagicDone();
    return isDone;
  }
}
