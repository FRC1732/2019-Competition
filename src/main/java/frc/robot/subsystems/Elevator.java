/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.Console;
import frc.robot.util.MotorUtil;

/**
 * Add your docs here.
 * 
 * One motor, with encoder
 */
public class Elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  /**
   * Defines a set of constants for the height of the elevator
   */
  private TalonSRX elevator = MotorUtil.createTalon(RobotMap.ELEVATOR_ELEVATOR_ID, true);
  
  public Elevator() {
    elevator.config_kP(0, 0);
    elevator.config_kI(0, 0);
    elevator.config_kD(0, 0);
    elevator.config_kF(0, 0);
    //Correct the LimitSwitchSource.FeedbackConnector when you know better
    //Remember to declare sensor type and sensor phase
    //config soft limit, config soft limit override on the actual limit
    //config current limit
    elevator.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    elevator.configClearPositionOnLimitF(true, 0);
  }
  
  public static enum Position {
    BaseHeight(0), CargoShipCargo(0), CargoShipHatch(0), RocketLevel1Cargo(1), RocketLevel1Hatch(1), RocketLevel2Cargo(
        2), RocketLevel2Hatch(2), RocketLevel3Cargo(3), RocketLevel3Hatch(3), HumanPlayerStation(0);
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
  private void setHeight(int pos) {
    elevator.set(ControlMode.Position, pos);
  }
  private int getHeight() {
    return elevator.getSelectedSensorPosition(0);
  }
  @Override
  public void periodic() {
     
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
    elevator.set(ControlMode.PercentOutput, 0);
  }
  
  /**
   * Resets all sensors
   */
  public void zero() {
    
  }
}
