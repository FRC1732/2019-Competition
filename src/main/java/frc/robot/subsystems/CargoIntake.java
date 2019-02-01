/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 * 
 * One motor, one solenoid to move the entire assembly
 */
public class CargoIntake extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  
  /**
   * Sets whether the intake should be actively intaking cargo, unless the robot
   * already has a hatch panel
   * 
   * @param intaking
   *                   when true, enable the intake
   */
  public void setEngaged(boolean intaking) {
    
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  
  /**
   * Resets this subsystem to a known state
   */
  public void stop() {
    
  }
  
  /**
   * Resets all sensors
   */
  public void zero() {
    
  }
}
