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
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.util.Console;
import frc.robot.util.MotorUtil;
import frc.robot.util.SimpleSendable;

/**
 * Add your docs here.
 */
public class FrontJacks extends Subsystem {
  
  TalonSRX leftMotor = MotorUtil.initAbsoluteTalon(RobotMap.CLIMBER_FRONT_LEFT, false, false);
  TalonSRX rightMotor = MotorUtil.initAbsoluteTalon(RobotMap.CLIMBER_FRONT_RIGHT, false, true);
  
  private String status = "";
  
  private static final int LEFT_OFFSET = 3857;
  private static final int RIGHT_OFFSET = 1876;
  private static final int INCH = 620;
  private static final int LOW = (int) (9 * INCH);
  private static final int LOWTOHIGH = (int) (14.5 * INCH);
  private static final int HIGH = (int) (22.5 * INCH);
  private static final int DEADZONE = (int) (((double) INCH) / 2.0);
  public static final int cruiseVelocity = 1200;
  public static final int acceleration = (int) (cruiseVelocity / 0.5);
  
  public FrontJacks() {
    leftMotor.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    leftMotor.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    leftMotor.config_kP(0, 1.4);
    leftMotor.config_kI(0, 0);
    leftMotor.config_kD(0, 0);
    leftMotor.config_kF(0, 0.000667544342359);
    leftMotor.configMotionCruiseVelocity(cruiseVelocity);
    leftMotor.configMotionAcceleration(acceleration);
    leftMotor.configClosedLoopPeakOutput(0, 0.9);
    
    rightMotor.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    rightMotor.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    rightMotor.config_kP(0, 1.4);
    rightMotor.config_kI(0, 0);
    rightMotor.config_kD(0, 0);
    rightMotor.config_kF(0, 0.000667519968892);
    rightMotor.configMotionCruiseVelocity(cruiseVelocity);
    rightMotor.configMotionAcceleration(acceleration);
    rightMotor.configClosedLoopPeakOutput(0, 0.9);
    
    SmartDashboard.putData("frontJacks", new SimpleSendable(this::initSendable));
  }
  
  public void RaiseJacks() {
    status = "RaiseJacks";
    leftMotor.set(ControlMode.Position, LEFT_OFFSET);
    rightMotor.set(ControlMode.Position, RIGHT_OFFSET);
  }
  
  public void LowerJacks() {
    status = "LowerJacks";
    leftMotor.set(ControlMode.MotionMagic, HIGH + LEFT_OFFSET);
    rightMotor.set(ControlMode.MotionMagic, HIGH + RIGHT_OFFSET);
  }

  public void LowerJacksMedium() {
    status  = "LowerJacksMedium";
    leftMotor.set(ControlMode.MotionMagic, LOWTOHIGH + LEFT_OFFSET);
    rightMotor.set(ControlMode.MotionMagic, LOWTOHIGH + RIGHT_OFFSET);
  }
  
  public void LowerJacksALittle() {
    status = "LowerJacksALittle";
    leftMotor.set(ControlMode.MotionMagic, LOW + LEFT_OFFSET);
    rightMotor.set(ControlMode.MotionMagic, LOW + RIGHT_OFFSET);
  }
  
  public void RestJacks() {
    status = "RestJacks";
    leftMotor.set(ControlMode.PercentOutput, 0);
    rightMotor.set(ControlMode.PercentOutput, 0);
  }
  
  public boolean AtHighTarget() {
    return (Math.abs((HIGH + LEFT_OFFSET) - leftMotor.getSelectedSensorPosition()) < DEADZONE)
        && (Math.abs((HIGH + RIGHT_OFFSET) - rightMotor.getSelectedSensorPosition()) < DEADZONE);
  }

  public boolean AtLowToHighTarget() {
      return (Math.abs((LOWTOHIGH + LEFT_OFFSET)- leftMotor.getSelectedSensorPosition()) < DEADZONE)
          && (Math.abs((LOWTOHIGH + RIGHT_OFFSET)- leftMotor.getSelectedSensorPosition()) < DEADZONE);
  }
  
  public boolean AtLowTarget() {
    return Math.abs((LOW + LEFT_OFFSET) - leftMotor.getSelectedSensorPosition()) < DEADZONE
        && Math.abs((LOW + RIGHT_OFFSET) - rightMotor.getSelectedSensorPosition()) < DEADZONE;
  }
  
  public boolean AtHomeTarget() {
    return Math.abs(LEFT_OFFSET - leftMotor.getSelectedSensorPosition()) < DEADZONE
        && Math.abs(RIGHT_OFFSET - rightMotor.getSelectedSensorPosition()) < DEADZONE;
  }
  
  public String getStatus() {
    return status;
  }
  
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(null);
  }
  
  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("frontJacks");
    builder.addStringProperty("FrontStatus", this::getStatus, null);
    builder.addDoubleProperty("left", leftMotor::getSelectedSensorPosition, null);
    builder.addDoubleProperty("right", rightMotor::getSelectedSensorPosition, null);
    builder.addBooleanProperty("atHigh", this::AtHighTarget, null);
  }
  
  @Override
  public void periodic() {
    Console.debug("Left %", leftMotor.getMotorOutputPercent());
    Console.debug("Right %", rightMotor.getMotorOutputPercent());
  }
}