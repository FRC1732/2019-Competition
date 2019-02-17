/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.Console;

/**
 * Add your docs here.
 * 
 * One solenoid to grab, One solenoid to move the panels in/out
 */
public class HatchClaw extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private Solenoid grabber = new Solenoid(1, RobotMap.HATCH_CLAW_GRABBER);
  private Solenoid mover = new Solenoid(1, RobotMap.HATCH_CLAW_MOVER);
  
  public HatchClaw() {
  }
  
  /**
   * Sets the claw to grabbing or released, when grabbing is true, the claw is
   * closed arround the panel, unless the robot already has a cargo
   * 
   * @param grabbing
   *                   whether the claw should be grabbing the panel
   */
  
  public void setEngaged(boolean grabbing) {
    if (!Robot.cargoScorer.hasCargo()) {
      grabber.set(grabbing);
      Console.debug("Grabbing: " + grabbing);
    } else {
      Console.warn("Cannot grab: already has cargo");
    }
  }
  
  /**
   * Sets the claw to extended or retracted, when extending is true, the claw is
   * exted from the robot
   * 
   * @param extending
   *                    whether the claw should be extended
   */
  public void setExtended(boolean extending) {
    mover.set(extending);
    Console.debug("Extending: " + extending);
  }
  
  /**
   * Check if the robot has a hatch panel
   * 
   * @return whether the robot has a hatch panel
   */
  public boolean hasHatchPanel() {
    return grabber.get();
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
    // grabber.set(false);
    // mover.set(false);
    
  }
  
  /**
   * Resets all sensors
   */
  public void zero() {
    
  }
}
