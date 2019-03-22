/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator.Position;

/**
 * A command to set the elevator position
 */
public class SetElevator extends InstantCommand {
  private Position pos;
  
  /**
   * A command to set the elevator position
   *
   * @param pos
   *              The position to set
   */
  public SetElevator(Position pos) {
    this.pos = pos;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.elevator);
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.elevator.setHeight(pos);
  }
}