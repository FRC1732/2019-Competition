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
 * 3 motors for jacks - limit switches
 * 
 * 1 motor to creep forward
 * 
 * Method:
 * 
 * * Raise jacks
 * 
 * * Creep forward
 * 
 * * Front jacks go up
 * 
 * * Creep forward, with drivetrain
 * 
 * * Raise back jack until match end/ at top
 */
public class Climber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  
  /**
   * Starts the Jacks raising
   */
  public void raiseJacks() {
    
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
