/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ExtendPanel extends Command {
  public ExtendPanel() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.hatchClaw);
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.hatchClaw.setExtended(true);
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }
  
  @Override
  protected void interrupted() {
    Robot.hatchClaw.setExtended(false);
  }
  
}
