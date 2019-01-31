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
public class NavX extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  
  /**
   * Gets the heading of the robot in degrees
   * 
   * @return The robot's heading
   */
  public double getHeading() {
    return 0;
  }
  
  /**
   * Gets the heading of the robot in radians
   * 
   * @return The robot's heading, in radians
   */
  public double getHeadingRad() {
    return Math.toRadians(0);
  }
  
  /**
   * Resets this subsystem to a known state
   */
  public void stop() {
    
  }
  
  /**
   * Resets all sensors to a known state
   */
  public void zero() {
    
  }
}
