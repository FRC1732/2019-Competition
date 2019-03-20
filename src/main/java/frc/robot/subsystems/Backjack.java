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
import frc.robot.util.MotorUtil;
import frc.robot.util.SimpleSendable;

/**
 * Add your docs here.
 */
public class Backjack extends Subsystem {

  TalonSRX jackMotor = MotorUtil.initAbsoluteTalon(RobotMap.CLIMBER_BACK, true, true);
  VictorSPX driveMotor = MotorUtil.createVictor(RobotMap.CLIMBER_DRIVER, false);

  private String status = "";

  private static final int OFFSET = 3200;
  private static final int INCH = 620;
  private static final int LOW = 6 * INCH;
  private static final int HIGH = (int)(22 * INCH);
  private static final int DEADZONE = (int)(((double)INCH) / 2.0);

  public Backjack(){
    jackMotor.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    jackMotor.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    jackMotor.config_kP(0, .8);
    jackMotor.configClosedLoopPeakOutput(0, 0.52);

    SmartDashboard.putData("backJack", new SimpleSendable(this::initSendable));
  }

  public void RaiseJack(){
    status = "Raise";
    jackMotor.set(ControlMode.Position, OFFSET);
  }

  public void LowerJack(){
    status = "Lower";
    jackMotor.set(ControlMode.Position, HIGH + OFFSET);
  }

  public void LowerJackALittle(){
    status = "LowerJackALittle";
    jackMotor.set(ControlMode.Position, LOW + OFFSET);
  }

  public void RestJack(){
    status = "RestJack";
    jackMotor.set(ControlMode.PercentOutput, 0);
  }

  public void Drive(){
    status = "Drive";
    driveMotor.set(ControlMode.PercentOutput, 0.4);
  }

  public void Stop(){
    status = "Stop";
    driveMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public boolean AtHighTarget(){
    return Math.abs((HIGH + OFFSET) - jackMotor.getSelectedSensorPosition()) < DEADZONE;
  }

  public boolean AtLowTarget(){
    return Math.abs((LOW + OFFSET) - jackMotor.getSelectedSensorPosition()) < DEADZONE;
  }

  public boolean AtHomeTarget(){
    return Math.abs(OFFSET - jackMotor.getSelectedSensorPosition()) < DEADZONE;
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
    builder.setSmartDashboardType("backJack");
    builder.addStringProperty("backStatus", this::getStatus, null);
    builder.addDoubleProperty("back", jackMotor::getSelectedSensorPosition, null);
    builder.addBooleanProperty("atHigh", this::AtHighTarget, null);
  }
}
