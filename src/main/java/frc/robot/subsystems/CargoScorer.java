/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.MotorUtil;

/**
 * Add your docs here.
 * 
 * One motor, Current to check for ball?
 */
public class CargoScorer extends Subsystem {
  private final VictorSPX left = MotorUtil.createVictor(RobotMap.SCORER_MOTOR_LEFT, false);
  private final VictorSPX right = MotorUtil.createVictor(RobotMap.SCORER_MOTOR_RIGHT, true);
  
  public void rollIn() {
    left.set(ControlMode.PercentOutput, 1);
    right.set(ControlMode.PercentOutput, 1);
  }

  public void hold() {
    left.set(ControlMode.PercentOutput, 0.2);
    right.set(ControlMode.PercentOutput, 0.2);
    System.out.println("Hold");
  }

  
  public void rollOut() {
    left.set(ControlMode.PercentOutput, -0.9);
    right.set(ControlMode.PercentOutput, -0.9);
    
  }
  
  public void stop() {
    left.set(ControlMode.PercentOutput, 0);
    right.set(ControlMode.PercentOutput, 0);
    System.out.println("Stop");
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
   * Resets all sensors
   */
  public void zero() {
    
  }
}
