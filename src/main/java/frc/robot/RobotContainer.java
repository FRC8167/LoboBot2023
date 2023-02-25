// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


//import edu.wpi.first.cameraserver.CameraServer;
//import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.ChargingStationAutoBalance;
import frc.robot.commands.DriveForwardTimed;
import frc.robot.commands.QuickTurn;
import frc.robot.commands.SetArmDistance;
import frc.robot.commands.TurnPID;
//import frc.robot.commands.TurnToTrackedTarget;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.Pivot;
//import frc.robot.subsystems.Vision;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  
  private final Drivetrain drivetrain = new Drivetrain();
 // private final Grabber grabber = new Grabber();
  //private final Arm arm = new Arm();
  //private final Vision vision  = new Vision();
 // private final Pivot pivot = new Pivot();

  private final SendableChooser<Command> autoCommandSelector = new SendableChooser<>();

  private final CommandXboxController driverController = new CommandXboxController(Constants.DRIVER_CONTROLLER);
  private final CommandXboxController operatorController = new CommandXboxController(Constants.OPERATOR_CONTROLLER);



  

  //create an autonomous command
 
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    addAutoCommands();
    SmartDashboard.putData(autoCommandSelector);
    //CameraServer.startAutomaticCapture();  //not sure this goes here

    drivetrain.setDefaultCommand(new ArcadeDrive(
      drivetrain,
      () -> driverController.getLeftY()*0.75,
      () -> driverController.getRightX()*-0.5
     )
      );
      
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    
  //driverController.leftBumper().onTrue(new InstantCommand(() -> grabber.toggle()));
  operatorController.rightBumper().whileTrue(new ChargingStationAutoBalance(drivetrain));


  driverController.x().onTrue(new TurnPID(drivetrain, 90));
  driverController.y().onTrue(new TurnPID(drivetrain, 180));
  //driverController.a().whileTrue(new TurnToTrackedTarget(drivetrain, vision, Constants.TRACK_TAG_ROTATION_KP));


  //operatorController.a().onTrue(new SetArmDistance(arm, 5.0));  //extend  
  //operatorController.b().onTrue(new SetArmDistance(arm, 0));  //retract

  // operatorController.x().onTrue(new InstantCommand(() -> arm.zeroArmSensor()));
  // operatorController.leftTrigger().whileTrue(new StartEndCommand(() -> arm.testArm(Constants.ARM_POWER ), () -> arm.stop()));
  // operatorController.rightTrigger().whileTrue(new StartEndCommand(() -> arm.testArm(-Constants.ARM_POWER ), () -> arm.stop())); 
  // operatorController.povUp().whileTrue(new StartEndCommand(() -> pivot.testPivot(Constants.PIVOT_POWER), () -> pivot.stop()));
  // operatorController.povDown().whileTrue(new StartEndCommand(() -> pivot.testPivot(-Constants.PIVOT_POWER), () -> pivot.stop()));
}

private void addAutoCommands()  {

  autoCommandSelector.addOption(
    "Fancy",
    new SequentialCommandGroup(
      
      //new SetArmDistance(arm, 6.0),
      new DriveForwardTimed(drivetrain, 2, 0.5),
      new QuickTurn(drivetrain, 90),
      new DriveForwardTimed(drivetrain, 2, 0.5),
      new QuickTurn(drivetrain, 90)
      //new InstantCommand(() -> grabber.openGrabber(), grabber))
    ));

    autoCommandSelector.setDefaultOption(
      "Drive Forward 2 Sec",
      new DriveForwardTimed(drivetrain, 2, Constants.AUTONOMOUS_SPEED)
      );

    autoCommandSelector.addOption(
      "Drive Forward 4 Sec",
      new DriveForwardTimed(drivetrain, 4, Constants.AUTONOMOUS_SPEED)
    );

}
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoCommandSelector.getSelected();
    //return driveForwardTimed;
  }
}
