/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * A command to turn the robot towards the nearest vision target
 */
public class LockTurning extends Command {
  public double turnConstant = 0.05;
  public double applied;
  /**
   * A command to turn the robot towards the nearest vision target
   */
  public LockTurning() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivetrain);
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }
  
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double speed = (Robot.oi.getLeftJoystick() + Robot.oi.getRightJoystick()) * 0.5;
    // System.out.printf("%1.3f /\\, > %1.3f\n", speed, turn);
    // System.out.println(Robot.limelight.getHorizontalAngle());
    if(speed < 0) {
      Robot.drivetrain.set(speed, speed + turnConstant);
    }
    else {
      Robot.drivetrain.set(speed, speed - turnConstant);
    }
  }
  
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
