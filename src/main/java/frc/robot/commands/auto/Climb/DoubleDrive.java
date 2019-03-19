/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto.climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DoubleDrive extends Command {
  private long startTime;
  private static final long DRIVE_MILLISECONDS = 1000;

  public DoubleDrive() {
    requires(Robot.backjack);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    startTime = System.currentTimeMillis();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.backjack.Drive();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return System.currentTimeMillis() - startTime > DRIVE_MILLISECONDS;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.backjack.Stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.backjack.Stop();
  }
}
