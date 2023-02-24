// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;

//import frc.robot.Constants;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Arm;

public class SetArmDistance extends CommandBase {
  /** Creates a new SetArmDistance. */
  private final Arm arm;
  private final double armTarget;
  private double armStartTime;

  public SetArmDistance(Arm arm, double armTarget) {
    this.arm = arm;
    this.armTarget = armTarget;  
    //this.argTarget = armTarget/(0.787*Math.PI)  check
    addRequirements(arm);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    armStartTime = Timer.getFPGATimestamp();
    arm.zeroArmSensor();
    arm.setArmMotionMagic(armTarget);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    arm.stop();
  }

   private boolean isArmMotionMagicDone() {
     double armSensorDistance = arm.getArmPosition();
     double error = armSensorDistance - armTarget*20*2048;
     System.out.println("sensor = "+armSensorDistance + ", armTarget = "+(armTarget * 20 *2048));
     double percentErr =  Math.abs(error)/Math.abs(armTarget*20*2048);
     if( percentErr < 0.01) {
      return true;
     }

     double timepassed = Timer.getFPGATimestamp() - armStartTime ;
     if( timepassed > 7) {
       return true;
     }
    
     System.out.println("percent error = "+percentErr +", timpassed = "+timepassed);

     return false;

    }
  

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean isDone = isArmMotionMagicDone();
    return isDone;
  }
}
