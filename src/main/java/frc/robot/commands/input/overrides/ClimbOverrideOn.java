/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.input.overrides;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.util.Console;

/**
 * Add your docs here.
 */
public class ClimbOverrideOn extends InstantCommand {
  /**
   * Add your docs here.
   */
  public ClimbOverrideOn() {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Console.warn("Overriding auto climb routines.");
    Robot.oi.setClimbManual(true);
  }

}
