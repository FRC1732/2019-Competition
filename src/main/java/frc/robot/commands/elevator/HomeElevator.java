/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.commands.hatch.CloseFinger;
import frc.robot.commands.hatch.RetractPanel;
import frc.robot.subsystems.Elevator.Position;

public class HomeElevator extends Command {
  private Command also1 = new RetractPanel();
  private Command also2 = new CloseFinger();

  public HomeElevator() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.elevator);
  }

  private long start;
  private static final long length = 10000;

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.elevator.setHeight(Position.BaseHeight);
    start = System.currentTimeMillis();
    also1.start();
    also2.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return start + length < System.currentTimeMillis() || Robot.elevator.getHeight() <= Position.BaseHeight.position + 150;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.elevator.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
