/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.Console;
import frc.robot.util.MotorUtil;
import frc.robot.util.SimpleSendable;

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
  private static final double kP = 2.0;
  private static final double kI = 0;
  private static final double kD = 0;
  private static final double minimumOutput = .07;
  private static final double speed = 588;
  private static final double percent = 0.319648093841642;
  private static final double kF = (percent * 1023) / speed - minimumOutput;
  private static final double motionCruiseVelocity = 1000;
  private static final double motionAcceleration = motionCruiseVelocity * 2;
  
  public Elevator() {
    elevator.config_kP(0, kP);
    elevator.config_kI(0, kI);
    elevator.config_kD(0, kD);
    elevator.config_kF(0, kF);
    elevator.configMotionCruiseVelocity((int) motionCruiseVelocity);
    elevator.configMotionAcceleration((int) motionAcceleration);
    // config current limit
    elevator.configForwardSoftLimitEnable(true);
    elevator.configForwardSoftLimitThreshold(19800);
    elevator.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
    elevator.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    elevator.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    elevator.configClearPositionOnLimitF(true, 0);
    elevator.configClearPositionOnLimitR(true, 0);
    
    elevator.setSelectedSensorPosition(0, 0, 0);
    
    SmartDashboard.putData("Elevator", new SimpleSendable(this::sendHeight));
  }
  
  public static enum Position {
    BaseHeight(0), CargoShipCargo(13200), CargoShipHatch(22000), RocketLevel1Cargo(5700), RocketLevel1Hatch(
        1), RocketLevel2Cargo(
            16100), RocketLevel2Hatch(10000), RocketLevel3Cargo(19100), RocketLevel3Hatch(19100), HumanPlayerStation(0);
    public final int position;
    
    private Position(int position) {
      this.position = position;
    }
  }
  
  private int position = Position.BaseHeight.position;
  
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
    position = pos;
    Console.debug("set elevator height to: " + position);
  }
  
  private int getHeight() {
    return elevator.getSelectedSensorPosition(0);
  }
  
  public void increment() {
    if (position < 19800) {
      position += 100;
    } else {
      position = 19800;
    }
    Console.debug("set elevator height to: " + position);
  }
  
  public void decrement() {
    if (position > 100) {
      position -= 100;
    } else {
      position = 0;
    }
    Console.debug("set elevator height to: " + position);
  }
  
  @Override
  public void periodic() {
    if (Robot.oi.operator2.getY() > 0.9) {
      decrement();
    } else if (Robot.oi.operator2.getY() < -0.9) {
      increment();
    }
    if (elevator.getSelectedSensorPosition(0) < 150 && position < 150) {
      elevator.set(ControlMode.PercentOutput, 0);
    } else {
      elevator.set(ControlMode.MotionMagic, position, DemandType.ArbitraryFeedForward, minimumOutput);
    }
    // System.out.println(elevator.getSelectedSensorPosition());
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
  
  private void sendHeight(SendableBuilder builder) {
    builder.setSmartDashboardType("Elevator");
    builder.addDoubleProperty("Height", this::getHeight, null);
  }
}
