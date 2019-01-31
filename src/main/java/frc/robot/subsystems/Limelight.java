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
public class Limelight extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  
  /**
   * Gets the horizontal angle of the current target in relation to robot's
   * heading
   * 
   * The angle can be in the range [-x, x]
   * 
   * @return The horizontal angle, in degrees
   */
  public double getHorizontalAngle() {
    return 0;
  }
  
  /**
   * Gets the vertical angle of the current target in relation to a flat plane
   * extending out from the limelight, and parallel to the robot's base
   * 
   * The angle can be in the range [-x, x]
   * 
   * @return The vertical angle, in degrees
   */
  public double getVerticalAngle() {
    return 0;
  }
  
  /**
   * Gets an approxomate distance to the target, from the front of the robot
   * 
   * @return The distance in inches.\
   */
  public double getTargetDistance() {
    return 0;
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
