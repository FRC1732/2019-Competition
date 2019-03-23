/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TogglePanel extends Command {
  private Notifier drop = new Notifier(() -> {
    Robot.hatchClaw.setEngaged(!Robot.hatchClaw.getEngaged());
  });
  private Notifier retract = new Notifier(() -> {
    Robot.hatchClaw.setExtended(false);
    done = true;
  });
  
  private boolean done = false;
  
  public TogglePanel() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.hatchClaw);
    setInterruptible(false);
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.hatchClaw.setExtended(true);
    drop.startSingle(0.1);
    retract.startSingle(0.3);
    // lift.startSingle(1.0);
    done = false;
  }
  
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }
  
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return done;
  }
  
  // Called once after isFinished returns true
  @Override
  protected void end() {
  }
  
  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
