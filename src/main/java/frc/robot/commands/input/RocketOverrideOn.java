/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.input;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.commands.SetElevator;
import frc.robot.subsystems.Elevator;

/**
 * Add your docs here.
 */
public class RocketOverrideOn extends InstantCommand {
  /**
   * Add your docs here.
   */
  public RocketOverrideOn() {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Robot.oi.cargoRocketLevel1.whenPressed(new SetElevator(Elevator.Position.RocketLevel1Cargo));
    Robot.oi.cargoRocketLevel2.whenPressed(new SetElevator(Elevator.Position.RocketLevel2Cargo));
    Robot.oi.cargoRocketLevel3.whenPressed(new SetElevator(Elevator.Position.RocketLevel3Cargo));
  }

}
