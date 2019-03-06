/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ClimbStage2 extends Command {
  public static ClimbStage2 command;
  
  public ClimbStage2() {
    if (command == null) {
      command = this;
    } else {
      throw new IllegalStateException("Only 1 of each Climber Stage can Exist");
    }
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (Robot.robot.isEnabled() && ClimbStage1.command.isRunning() && !ClimbStage3.command.isRunning()) {
      Robot.climber.stage2();
    } else {
      cancel();
    }
  }
  
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }
  
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }
  
  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.climber.stop();
  }
  
  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.climber.stop();
  }
}
