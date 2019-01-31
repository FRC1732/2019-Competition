/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.DriveWithJoysticks;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  
  /**
   * Sets the speed of the left and right side of the drivetrain using percent
   * output
   * 
   * @param left
   *                the left speed, in the range of [-1, 1]
   * @param right
   *                the right speed, in the range of [-1, 1]
   */
  public void set(double left, double right) {
    
  }
  
  /**
   * Gets the total rotation of the left side of the drivetrain
   * 
   * @return
   */
  public double getLeftPos() {
    return 0;
  }
  
  /**
   * Gets the total rotation of the right side of the drivetrain
   * 
   * @return
   */
  public double getRightPos() {
    return 0;
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    
    // whenever the robot has no other command to control the drivetrain, the
    // DriveWithJoysticks command runs.
    setDefaultCommand(new DriveWithJoysticks());
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
