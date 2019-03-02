/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Console;

/**
 * A command to intake a Cargo. Stops when a cargo has been collected
 */
public class IntakeCargo extends Command {
  /**
   * A command to intake a Cargo. Stops when a cargo has been collected
   */
  public IntakeCargo() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.cargoIntake);
    requires(Robot.cargoScorer);
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.cargoIntake.setEngaged(true);
    Robot.cargoIntake.setExtended(true);
    Robot.cargoScorer.rollIn();
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }
  
  @Override
  protected void interrupted() {
    Robot.cargoIntake.setExtended(false);
    new Notifier(() -> {
      Robot.cargoIntake.stopIntake();
      Robot.cargoScorer.stop();
    }).startSingle(0.5);
    Console.debug("Ending intake");
  }
  
  @Override
  protected void end() {
    interrupted();
  }
}
