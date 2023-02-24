// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  // public static class OperatorConstants {
  //   public static final int kDriverControllerPort = 1;
  // }


//PWM ports for the drivetrain motors
public static final int LEFT_FRONT = 1;
public static final int LEFT_BACK = 2;
public static final int RIGHT_FRONT = 3;
public static final int RIGHT_BACK = 4;

//CAN
public static final int ARM_MOTOR = 5;
public static final int PIGEON_CANID = 6;
public static final int PIVOT_MOTOR = 7;
//Other useful constants

public static final double DRIVE_FORWARD_TIME = 20;
public static final double AUTONOMOUS_SPEED = 0.4;
public static final double TANK_SPEED = 0.75;
public static final double SHOOTER_SPINUP_SPEED = 0.7;
public static final double SHOOTER_SPINDOWN_SPEED = 0.7;

//Pneumatics constants
public static final int LEFT_SOLENOID_ID1 = 0;
public static final int LEFT_SOLENOID_ID2 = 1;
// public static final int RIGHT_SOLENOID_ID1 = 3;
// public static final int RIGHT_SOLENOID_ID2 = 4;

//robot constants
public static final double WHEEL_DIAMETER = Units.inchesToMeters(6);
public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
public static final double TRACK_WIDTH = Units.inchesToMeters(21.75);

//Operator Controller  Constants
public static final int DRIVER_CONTROLLER = 0;
public static final int OPERATOR_CONTROLLER = 1;
public static final int kA = 1;
public static final int kB = 2;
public static final int kLeftBumper = 5;

//ARM PID
//public static final double maxArmExtensionTicks = 0;
public static final int ARM_kpIDLoopIDx = 0;
public static final int ARM_pidLoopTimeout = 30;
//public static final int kSlotIDx = 0;
//public static int kpidLoopIDx = 0;
public static final double armCruiseVelocity = 4450.0;
public static final double armAcceleration = 4450.0;
public static final double ARM_POWER = 0.2;

//PIVOT PID
public static final int PIVOT_kpIDLoopIDx = 0;
public static final int PIVOT_pidLoopTimeout = 30;
public static final double pivotCruiseVelocity = 4450.0;
public static final double pivotAcceleration = 4450.0;
public static final double PIVOT_HORIZONTAL_HOLD_OUTPUT = 0.13;
public static final double PIVOT_POWER = 0.2;




//Quick Turn constants
//public static final double timeoutQuickTurn = 15.0;  //can change this
public static final String USB_CAMERA_NAME = "Microsoft_LifeCam_HD-3000";
public static final double TRACK_TAG_ROTATION_KP = 0.0175;
public static final double PIGEON_TURN_KP = 0.115;
public static final double QUICKTURN_THRESHOLD_DEGREES = 1.0;

//VISION
public static final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(7);
public static final double TARGET_HEIGHT_METERS = Units.inchesToMeters(18.5);
public static final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(18);

//BALANCE ON CHARGING STATION
public static final double BALANCE_GOAL_DEGREES = 0.0;
public static final double BALANCE_KP = 0.02;
public static final double BALANCE_PITCH_THRESHOLD = 3.0;
public static final double BALANCE_REVERSE_POWER = 1.3;  //change as needed


public static final double QUICKTURN_TIMEOUT = 5;
public static final double TURN_PID_kP = .01;
public static final double TURN_PID_KI = 0;
public static final double TURN_PID_kD = 0.0025;



  
}



