/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.MotorUtil;

/**
 * Add your docs here.
 * 
 * One motor, one solenoid to move the entire assembly
 */
public class CargoIntake extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private TalonSRX cargoIntakeMotor = MotorUtil.createTalon(RobotMap.CARGO_INTAKE_MOTOR_ID, true);
  private Solenoid cargoIntakeSolenoid = new Solenoid(RobotMap.CARGO_INTAKE_SOLENOID_ID);
  
  /**
   * Sets whether the intake should be actively intaking cargo, unless the robot
   * already has a hatch panel
   * 
   * @param intaking
   *                   when true, enable the intake
   */
  public void setEngaged(boolean intaking) {
    if (intaking) {
      cargoIntakeMotor.set(ControlMode.PercentOutput, 1);
      cargoIntakeSolenoid.set(true);
    } else {
      cargoIntakeMotor.set(ControlMode.PercentOutput, 0);
      cargoIntakeSolenoid.set(false);
    }
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
    cargoIntakeMotor.set(ControlMode.PercentOutput, 0);
    cargoIntakeSolenoid.set(false);
  }
  
  /**
   * Resets all sensors
   */
  public void zero() {
    
  }
}
