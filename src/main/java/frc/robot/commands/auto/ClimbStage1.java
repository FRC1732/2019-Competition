/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ClimbStage1 extends Command {
  public static ClimbStage1 command;
  private static boolean ready = true;
  private boolean readySet = false;
  public ClimbStage1() {
    if (command == null) {
      command = this;
    } else {
      throw new IllegalStateException("Only 1 of each Climber Stage can Exist");
    }
    requires(Robot.climber);
    requires(Robot.drivetrain);
    requires(Robot.elevator);
    requires(Robot.hatchClaw);
    requires(Robot.cargoIntake);
    requires(Robot.cargoScorer);
    setInterruptible(false);
  }

  public ClimbStage1(boolean ready) {
    readySet = ready;
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(readySet) {
      ready = true;
    }else if (ready && Robot.robot.isEnabled() && !ClimbStage2.command.isRunning() && !ClimbStage3.command.isRunning()) {
      Robot.climber.stage1();
    } else {
      cancel();
      ready = false;
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
