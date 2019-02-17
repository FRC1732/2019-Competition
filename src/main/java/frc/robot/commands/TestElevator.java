/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator.Position;

public class TestElevator extends Command {
  
  private JoystickButton button;
  
  public TestElevator(JoystickButton button) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.elevator);
    this.button = button;
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.elevator.setHeight(Position.CargoShipCargo);
  }
  
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (button.get()) {
      Robot.elevator.increment();
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
    Robot.elevator.setHeight(Position.BaseHeight);
  }
  
  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.elevator.setHeight(Position.BaseHeight);
  }
}
