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
 */
public class HatchClaw extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  
  /**
   * Sets the claw to grabbing or released, when grabbing is true, the claw is
   * closed arround the panel
   * 
   * @param grabbing
   *                   whether the claw should be grabbing the panel
   */
  public void setEngaged(boolean grabbing) {
    
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
