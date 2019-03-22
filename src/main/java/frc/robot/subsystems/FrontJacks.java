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
import frc.robot.util.MotorUtil;
import frc.robot.util.SimpleSendable;

/**
 * Add your docs here.
 */
public class FrontJacks extends Subsystem {
  
  TalonSRX LeftMotor = MotorUtil.initAbsoluteTalon(RobotMap.CLIMBER_FRONT_LEFT, false, false);
  TalonSRX RightMotor = MotorUtil.initAbsoluteTalon(RobotMap.CLIMBER_FRONT_RIGHT, false, true);

  private String status = "";

  private static final int LEFT_OFFSET = 450;
  private static final int RIGHT_OFFSET = 575;
  private static final int INCH = 620;
  private static final int LOW = (int)(9 * INCH);
  private static final int HIGH = (int)(22 * INCH);
  private static final int DEADZONE = (int)(((double)INCH) / 2.0);

  public FrontJacks(){
    LeftMotor.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    LeftMotor.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    LeftMotor.config_kP(0, 1.4);
    LeftMotor.configClosedLoopPeakOutput(0, 0.55);

    RightMotor.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    RightMotor.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    RightMotor.config_kP(0, 1.4);
    RightMotor.configClosedLoopPeakOutput(0, 0.625);

    SmartDashboard.putData("frontJacks", new SimpleSendable(this::initSendable));
  }

  public void RaiseJacks(){
    status = "RaiseJacks";
    LeftMotor.set(ControlMode.Position, LEFT_OFFSET);
    RightMotor.set(ControlMode.Position, RIGHT_OFFSET);
  }

  public void LowerJacks(){
    status = "LowerJacks";
    LeftMotor.set(ControlMode.Position, HIGH + LEFT_OFFSET);
    RightMotor.set(ControlMode.Position, HIGH + RIGHT_OFFSET);
  }

  public void LowerJacksALittle(){
    status = "LowerJacksALittle";
    LeftMotor.set(ControlMode.Position, LOW + LEFT_OFFSET);
    RightMotor.set(ControlMode.Position, LOW + RIGHT_OFFSET);
  }

  public void RestJacks(){
    status = "RestJacks";
    LeftMotor.set(ControlMode.PercentOutput, 0);
    RightMotor.set(ControlMode.PercentOutput, 0);
  }

  public boolean AtHighTarget(){
    return (Math.abs((HIGH + LEFT_OFFSET) - LeftMotor.getSelectedSensorPosition()) < DEADZONE)
      && (Math.abs((HIGH + RIGHT_OFFSET) - RightMotor.getSelectedSensorPosition()) < DEADZONE);
  }

  public boolean AtLowTarget(){
    return Math.abs((LOW + LEFT_OFFSET) - LeftMotor.getSelectedSensorPosition()) < DEADZONE
      && Math.abs((LOW + RIGHT_OFFSET) - RightMotor.getSelectedSensorPosition()) < DEADZONE;
  }

  public boolean AtHomeTarget(){
    return Math.abs(LEFT_OFFSET - LeftMotor.getSelectedSensorPosition()) < DEADZONE
      && Math.abs(RIGHT_OFFSET - RightMotor.getSelectedSensorPosition()) < DEADZONE;
  }

  public String getStatus(){
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
    builder.addDoubleProperty("left", LeftMotor::getSelectedSensorPosition, null);
    builder.addDoubleProperty("right", RightMotor::getSelectedSensorPosition, null);
    builder.addBooleanProperty("atHigh", this::AtHighTarget, null);
  }
}