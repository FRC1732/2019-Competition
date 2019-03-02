/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

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
  private VictorSPX cargoIntakeMotor = MotorUtil.createVictor(RobotMap.CARGO_INTAKE_MOTOR_ID, false);
  private Solenoid cargoIntakeSolenoid = new Solenoid(1, RobotMap.CARGO_INTAKE_SOLENOID_ID);
  
  /**
   * Sets whether the intake should be actively intaking cargo, unless the robot
   * already has a hatch panel
   * 
   * @param direction
   *                    when true, draw balls in
   */
  public void setEngaged(boolean direction) {
    if (direction) {
      cargoIntakeMotor.set(ControlMode.PercentOutput, 0.5);
    } else {
      cargoIntakeMotor.set(ControlMode.PercentOutput, -0.5);
    }
  }
  
  public void stopIntake() {
    cargoIntakeMotor.set(ControlMode.PercentOutput, 0);
  }
  
  public void setExtended(boolean extended) {
    cargoIntakeSolenoid.set(extended);
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
