/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.Robot;

/**
 * A command to intake a Cargo. Stops when a cargo has been collected
 */
public class SpitCargo extends Command {
  /**
   * A command to intake a Cargo. Stops when a cargo has been collected
   */
  public SpitCargo() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.cargoIntake);
    requires(Robot.cargoScorer);
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.cargoIntake.setEngaged(false);
    Robot.cargoScorer.rollOut();
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }
  
  @Override
  protected void interrupted() {
    Robot.cargoIntake.stop();
    Robot.cargoScorer.stop();
  }
  
}
