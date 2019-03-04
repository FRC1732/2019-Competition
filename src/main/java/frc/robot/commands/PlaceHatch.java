/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * Command to score a hatch panel when lined up at a hatch
 */
public class PlaceHatch extends InstantCommand {
  /**
   * Command to score a hatch panel when lined up at a hatch
   */
  public PlaceHatch() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.hatchClaw);
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.hatchClaw.setEngaged(false);
  }
}
