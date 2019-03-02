/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.input;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Elevator.Position;

/**
 * Add your docs here.
 */
public class PlaceRocketCargo extends InstantCommand {
  /**
   * Add your docs here.
   */
  private Elevator.Position position = Position.BaseHeight;
  
  public PlaceRocketCargo(Elevator.Position pos) {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    position = pos;
  }
  
  // Called once when the command executes
  @Override
  protected void initialize() {
  }
  
}
