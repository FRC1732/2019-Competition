/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * A command to score Cargo when aligned at a port for cargo
 */
public class PlaceCargo extends InstantCommand {
  /**
   * A command to intake a Cargo. Stops when a cargo has been collected
   */
  public PlaceCargo() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.cargoIntake);
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.cargoIntake.setEngaged(false);
  }
  
}
