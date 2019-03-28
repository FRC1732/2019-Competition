/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
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
  private TalonSRX elevator = MotorUtil.initAbsoluteTalon(RobotMap.ELEVATOR_ELEVATOR_ID, false, true);
  private DigitalInput limit = new DigitalInput(0);
  
  private final static int OFFSET = 2524;
  
  private static final double minimumOutput = .065;
  private static final double speed = 588;
  private static final double percent = 0.319648093841642;
  private static final double kF = (percent * 1023) / speed - minimumOutput;
  private static final double motionCruiseVelocity = 1000;
  private static final double motionAcceleration = motionCruiseVelocity * 2;
  
  public Elevator() {
    elevator.config_kF(0, kF);
    elevator.configMotionCruiseVelocity((int) motionCruiseVelocity);
    elevator.configMotionAcceleration((int) motionAcceleration);
    // config current limit
    // elevator.configForwardSoftLimitEnable(true);
    // elevator.configForwardSoftLimitThreshold(19800);
    elevator.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    elevator.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    
    SmartDashboard.putData("Elevator", new SimpleSendable(this::sendHeight));
  }
  
  public static enum Position {// 8329
    BaseHeight(OFFSET), CargoShipCargo(10100 + OFFSET), CargoShipHatch(22000 + OFFSET), RocketLevel1Cargo(
        5800 + OFFSET), RocketLevel1Hatch(0 + OFFSET), RocketLevel2Cargo(16150 + OFFSET), RocketLevel2Hatch(
            10000 + OFFSET), RocketLevel3Cargo(
                19100 + OFFSET), RocketLevel3Hatch(19100 + OFFSET), HumanPlayerStation(4600 + OFFSET);
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
    elevator.set(ControlMode.MotionMagic, pos.position, DemandType.ArbitraryFeedForward, minimumOutput);
    Console.debug("set elevator height to: " + position);
  }
  
  public int getHeight() {
    return elevator.getSelectedSensorPosition();
  }
  
  public void manualUp() {
    elevator.set(ControlMode.PercentOutput, 0.2);
  }
  
  public void manualDown() {
    elevator.set(ControlMode.PercentOutput, -0.2);
  }
  
  @Override
  public void periodic() {
    // if (elevator.getSelectedSensorPosition(0) < OFFSET + 150 && position < OFFSET + 150) {
    //   elevator.set(ControlMode.PercentOutput, 0);
    // } else {
    //   Console.debug("elevator: " + elevator.getSelectedSensorPosition() + ", set to " + (position + OFFSET));
    //   elevator.set(ControlMode.MotionMagic, position, DemandType.ArbitraryFeedForward, 0);// minimumOutput);
    // }
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
    builder.addBooleanProperty("Limit", limit::get, null);
  }
}