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
 * One motor, Current to check for ball?
 */
public class CargoScorer extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  
  /**
   * Sets the Scorer to outtake the cargo
   * 
   * @param scoring
   *                  when true, outtake the cargo
   */
  public void setEngaged(boolean scoring) {
    
  }
  
  /**
   * Check if the robot has a cargo
   * 
   * @return whether the robot has a cargo
   */
  public boolean hasCargo() {
    return false;
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
