/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.input;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.commands.PlaceCargo;

public class CheckRocketOverride extends Command {
  public CheckRocketOverride() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    // switch to manual routines
    Robot.oi.cargoRocketLevel1.whenPressed(new ManualRocketLevel1()); // placeholder commands
    Robot.oi.cargoRocketLevel2.whenPressed(new ManualRocketLevel2());
    Robot.oi.cargoRocketLevel3.whenPressed(new ManualRocketLevel3());
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
    // go back to auto routines
    Robot.oi.cargoRocketLevel1.whenPressed(new AutoRocketLevel1()); // placeholder commands
    Robot.oi.cargoRocketLevel2.whenPressed(new AutoRocketLevel2());
    Robot.oi.cargoRocketLevel3.whenPressed(new AutoRocketLevel3());
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
