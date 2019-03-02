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
  
  private static final double kF = 0.03153099;
  private static final double kMinOutput = 0.01;
  private static final double kCruiseVelocity = 1900;
  private static final double kAcceleration = kCruiseVelocity / 0.5;// acceleate to full speed in one seconds
  private static final double kP = 0.48;
  private static final double kI = 0;
  private static final double kD = -4.80;
  
  public Elevator() {
    elevator.config_kP(0, kP);
    elevator.config_kI(0, kI);
    elevator.config_kD(0, kD);
    elevator.config_kF(0, kF);
    elevator.configMotionCruiseVelocity((int) kCruiseVelocity);
    elevator.configMotionAcceleration((int) kAcceleration);
    elevator.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
    // elevator.config_IntegralZone(0, 1000);
    // elevator.
    // Correct the LimitSwitchSource.FeedbackConnector when you know better
    // Remember to declare sensor type and sensor phase
    // config soft limit, config soft limit override on the actual limit
    // config current limit
    elevator.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    elevator.configClearPositionOnLimitR(true, 0);
    elevator.configForwardSoftLimitEnable(true);
    elevator.configForwardSoftLimitThreshold(19300);
    
    elevator.configClosedLoopPeakOutput(0, 0.9);
    
    SmartDashboard.putData("Elevator", new SimpleSendable(this::sendHeight));
  }
  
  // 19800 is max encoder reading
  public static enum Position {
      BaseHeight(0), CargoShipCargo(13600), CargoShipHatch(22000), RocketLevel1Cargo(5700), RocketLevel1Hatch(0),
      RocketLevel2Cargo(16100), RocketLevel2Hatch(10000), RocketLevel3Cargo(19100), RocketLevel3Hatch(19100),
      HumanPlayerStation(0);
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
    position = pos.position;
  }
  
  private int position = Position.BaseHeight.position;
  private boolean debugging = false;
  
  public void setDebugging(boolean debugging) {
    this.debugging = debugging;
    System.out.printf("Min: %10.8f, kF: %10.8f, Max: %10.8f\n", minOutput, totConv / numConv, maxSpeed);
    demand = 0;
    minOutput = 0;
    totConv = 0;
    numConv = 0;
    maxSpeed = 0;
    elevator.setSelectedSensorPosition(0, 0, 0);
  }
  
  public boolean doneDebugging() {
    return elevator.getSelectedSensorPosition(0) > 1000;
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
  
  private double demand = 0;
  private double minOutput = 0;
  private double maxSpeed = 0;
  private double totConv = 0;
  private double numConv = 0;
  
  @Override
  public void periodic() {
    if (debugging) {
      if (elevator.getSelectedSensorPosition(0) <= 1) {
        demand += 0.01;
      } else if (minOutput == 0) {
        minOutput = demand;
      } else if (elevator.getSelectedSensorPosition(0) <= 5000) {
        demand = 0.8;
        numConv++;
        totConv += (elevator.getMotorOutputPercent() - minOutput) / elevator.getSelectedSensorVelocity(0);
        if (maxSpeed < elevator.getSelectedSensorVelocity(0) && elevator.getMotorOutputPercent() <= 0.8) {
          maxSpeed = elevator.getSelectedSensorVelocity(0);
        }
      } else {
        demand = 0;
      }
      elevator.set(ControlMode.PercentOutput, demand);
      Console.graph(position, elevator.getMotorOutputPercent(), elevator.getMotorOutputVoltage(),
          elevator.getOutputCurrent(), elevator.getSelectedSensorPosition(0), elevator.getSelectedSensorVelocity(0),
          elevator.getTemperature());
    } else {
      // Actual Normal Code
      if (elevator.getSelectedSensorPosition(0) <= 2 && position == 0) {
        elevator.set(ControlMode.PercentOutput, 0);
      } else {
        elevator.set(ControlMode.MotionMagic, position, DemandType.ArbitraryFeedForward, calcF());
      }
    }
  }
  
  private double calcF() {
    if (elevator.getSelectedSensorPosition(0) - position > 1000) {
      // return kMinOutput / 2;
    }
    // temporary increase to make the elevator work
    if (elevator.getSelectedSensorPosition(0) < 200) {
      return kMinOutput + 0.1;
    }
    return kMinOutput;
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
