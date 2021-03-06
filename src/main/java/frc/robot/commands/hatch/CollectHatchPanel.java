/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.hatch;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * A command to collect a hatch panel when aligned at the human player station
 */
public class CollectHatchPanel extends InstantCommand {
  /**
   * A command to collect a hatch panel when aligned at the human player station
   */
  public CollectHatchPanel() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.hatchClaw);
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.hatchClaw.setEngaged(true);
  }
}
