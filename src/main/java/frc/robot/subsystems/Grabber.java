// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Grabber extends SubsystemBase {
  /** Creates a new Grabber. */
private boolean isOpen;
//private boolean grabberRightIsExtended;

private DoubleSolenoid leftGrabberPiston;
//private DoubleSolenoid grabberRightPiston;

  public Grabber() {
    leftGrabberPiston = 
    new DoubleSolenoid(0,PneumaticsModuleType.CTREPCM, Constants.LEFT_SOLENOID_ID1, Constants.LEFT_SOLENOID_ID2);
    // grabberRightPiston = 
    // new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.RIGHT_SOLENOID_ID1, Constants.RIGHT_SOLENOID_ID2);
    //These true/false values might be reversed depending on how the double solenoid is connected??
    isOpen = false;
    //grabberRightIsExtended = true;
    //The grabber pistons will be initially extended to hold a pre-loaded game piece
  }

//Methods for controlling the state of the double solenoid
public void openGrabber(){
  leftGrabberPiston.set(Value.kReverse);
  isOpen = true;
  // grabberRightPiston.set(Value.kForward);
  // grabberRightIsExtended = true;
}

public void closeGrabber(){
  leftGrabberPiston.set(Value.kForward);
  isOpen = false;
  // grberRightPiston.set(Value.kReverse);
  // grabberRightIsExtended = false;
}

public void toggle(){
  //Toggle the state of both solenoids and gripper
  if (isOpen){
  //if(grabberLeftIsExtended && grabberRightIsExtended){
    closeGrabber();
  }else {
    openGrabber();
  }
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
