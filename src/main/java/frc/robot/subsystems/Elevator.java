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
public class Elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  /**
   * Defines a set of constants for the height of the elevator
   */
  public static enum Position {
    BaseHeight(0), CargoShip(0), RocketLevel2(1), RocketLevel3(2);
    public final int position;
    
    private Position(int position) {
      this.position = position;
    }
  }
  
  /**
   * Sets the position of the elevator, using constants declared in
   * {@link Position}. e.g. {@code Position.BaseHeight}
   * 
   * @param pos
   *              the position to move the elevator to
   */
  public void setHeight(Position pos) {
    // passes the constant int for the height requested
    setHeight(pos.position);
  }
  
  /**
   * Sets the position of the elevator, using raw encoder ticks for the positions.
   * Commands should use {@code setHeight(Position)} except for debugging
   * 
   * @param pos
   *              the position to move the elevator to
   */
  public void setHeight(int pos) {
    
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
