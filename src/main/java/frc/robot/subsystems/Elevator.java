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
  
  private static final double kMaxSensorVelocity = 588;// fastest we feel measured
  private static final double kF = ((0.319648093841642) * 1023) / kMaxSensorVelocity - 0.07;// .28% of max (1023),
                                                                                            // when speed is maxed
  private static final double kCruiseVelocity = 1000;
  private static final double kAcceleration = kCruiseVelocity * 2;// acceleate to full speed in one seconds
  private static final double kP = 0.48;
  private static final double kI = 0;
  private static final double kD = 0;
  
  public Elevator() {
    elevator.config_kP(0, kP);
    elevator.config_kI(0, kI);
    elevator.config_kD(0, kD);
    elevator.config_kF(0, kF);
    elevator.configMotionCruiseVelocity((int) kCruiseVelocity);
    elevator.configMotionAcceleration((int) kAcceleration);
    elevator.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    // elevator.config_IntegralZone(0, 1000);
    // elevator.
    // Correct the LimitSwitchSource.FeedbackConnector when you know better
    // Remember to declare sensor type and sensor phase
    // config soft limit, config soft limit override on the actual limit
    // config current limit
    elevator.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    elevator.configClearPositionOnLimitR(true, 0);
    
    SmartDashboard.putData("Elevator", new SimpleSendable(this::sendHeight));
  }
  
  // 19800 is max encoder reading
  public static enum Position {
    BaseHeight(0), CargoShipCargo(13600), CargoShipHatch(22000), RocketLevel1Cargo(1), RocketLevel1Hatch(
        1), RocketLevel2Cargo(
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
  
  private int position = Position.BaseHeight.position;
  
  /**
   * Sets the position of the elevator, using raw encoder ticks for the positions.
   * Commands should use {@code setHeight(Position)} except for debugging
   * 
   * @param pos
   *              the position to move the elevator to
   */
  private void setHeight(int pos) {
    position = pos;
  }
  
  public void increment() {
    position += 100;
  }
  
  public void decrement() {
    position -= 100;
  }
  
  private int getHeight() {
    return elevator.getSelectedSensorPosition(0);
  }
  
  @Override
  public void periodic() {
    // if (position != Position.BaseHeight.position) {
    // elevator.set(ControlMode.PercentOutput, demand);
    // } else if (position == 0) {
    // demand = 0;
    // elevator.set(ControlMode.PercentOutput, demand);
    // }
    if (elevator.getSelectedSensorPosition(0) < 150 && position < 150) {
      elevator.set(ControlMode.PercentOutput, 0);
    } else {
      elevator.set(ControlMode.MotionMagic, position, DemandType.ArbitraryFeedForward, calcF());
    }
    // Console.graph(position, elevator.getMotorOutputPercent(),
    // elevator.getMotorOutputVoltage(),
    // elevator.getOutputCurrent(), elevator.getSelectedSensorPosition(0),
    // elevator.getSelectedSensorVelocity(0),
    // elevator.getTemperature());
    
  }
  
  private double calcF() {
    return 0.07;
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
    position = elevator.getSelectedSensorPosition(0);
  }
  
  /**
   * Resets all sensors
   */
  public void zero() {
    // elevator.setSelectedSensorPosition(0, 0, 0);
  }
  
  private void sendHeight(SendableBuilder builder) {
    builder.setSmartDashboardType("Elevator");
    builder.addDoubleProperty("Height", this::getHeight, null);
  }
}
