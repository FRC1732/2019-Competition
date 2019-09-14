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
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

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
public class Backjack extends Subsystem {
  
  TalonSRX jackMotor = MotorUtil.initAbsoluteTalon(RobotMap.CLIMBER_BACK, false, false);
  VictorSPX driveMotor = MotorUtil.createVictor(RobotMap.CLIMBER_DRIVER, false);
  
  private String status = "";
  
  private static int OFFSET = 250; //3293
  private static final int INCH = 620;
  private static final int LOW = (int) (9 * INCH);
  private static final int LOWTOHIGH = (int) (13.75 * INCH);
  private static final int HIGH = (int) (21.75 * INCH);
  private static final int DEADZONE = (int) (((double) INCH) / 2.0);
  
  public Backjack() {
    OFFSET = jackMotor.getSelectedSensorPosition();
    jackMotor.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    jackMotor.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    jackMotor.config_kP(0, 1.4);
    jackMotor.config_kI(0, 0);
    jackMotor.config_kD(0, 0);
    jackMotor.config_kF(0, 0.000667544342359);
    jackMotor.configMotionCruiseVelocity(FrontJacks.cruiseVelocity);
    jackMotor.configMotionAcceleration(FrontJacks.acceleration);
    jackMotor.configClosedLoopPeakOutput(0, 0.9);
    
    SmartDashboard.putData("backJack", new SimpleSendable(this::initSendable));
  }
  
  public void RaiseJack() {
    status = "Raise";
    jackMotor.set(ControlMode.Position, OFFSET);
  }
  
  public void LowerJack() {
    status = "Lower";
    jackMotor.set(ControlMode.MotionMagic, HIGH + OFFSET);
  }

  public void LowerJackMedium() {
    status = "LowerMedium";
    jackMotor.set(ControlMode.MotionMagic, LOWTOHIGH + OFFSET);
  }
  
  public void LowerJackALittle() {
    status = "LowerJackALittle";
    jackMotor.set(ControlMode.MotionMagic, LOW + OFFSET);
  }
  
  public void RestJack() {
    status = "RestJack";
    jackMotor.set(ControlMode.PercentOutput, 0);
  }
  
  public void Drive() {
    status = "Drive";
    driveMotor.set(ControlMode.PercentOutput, 0.4);
  }
  
  public void Stop() {
    status = "Stop";
    driveMotor.set(ControlMode.PercentOutput, 0.0);
  }
  
  public boolean AtHighTarget() {
    return Math.abs((HIGH + OFFSET) - jackMotor.getSelectedSensorPosition()) < DEADZONE;
  }

  public boolean AtLowToHighTarget() {
      return Math.abs((LOWTOHIGH + OFFSET)- jackMotor.getSelectedSensorPosition()) < DEADZONE;
  }
  
  public boolean AtLowTarget() {
    return Math.abs((LOW + OFFSET) - jackMotor.getSelectedSensorPosition()) < DEADZONE;
  }
  
  public boolean AtHomeTarget() {
    return Math.abs(OFFSET - jackMotor.getSelectedSensorPosition()) < DEADZONE;
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
    builder.setSmartDashboardType("backJack");
    builder.addStringProperty("backStatus", this::getStatus, null);
    builder.addDoubleProperty("back", jackMotor::getSelectedSensorPosition, null);
    builder.addBooleanProperty("atHigh", this::AtHighTarget, null);
  }
  
  @Override
  public void periodic() {
    Console.debug("Back %", jackMotor.getMotorOutputPercent());
  }
}