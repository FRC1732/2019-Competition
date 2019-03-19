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
import frc.robot.RobotMap;
import frc.robot.util.MotorUtil;

/**
 * Add your docs here.
 */
public class Backjack extends Subsystem {

  TalonSRX jackMotor = MotorUtil.initAbsoluteTalon(RobotMap.CLIMBER_BACK, false, false);
  TalonSRX driveMotor = MotorUtil.createTalon(RobotMap.CLIMBER_DRIVER, false);

  private String status = "";

  private static final int OFFSET = 3000;
  private static final int INCH = 620;
  private static final int LOW = 6 * INCH;
  private static final int HIGH = 19 * INCH;
  private static final int DEADZONE = (int)(0.5 * INCH);

  public Backjack(){
    jackMotor.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    jackMotor.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    jackMotor.config_kP(0, 1.4);
    jackMotor.configClosedLoopPeakOutput(0, 0.7);
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
    driveMotor.set(ControlMode.PercentOutput, 0.2);
  }

  public void Stop(){
    status = "Stop";
    driveMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public boolean AtTarget(){
    return jackMotor.getClosedLoopError() < DEADZONE;
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
    builder.setSmartDashboardType("Climber");
    builder.addStringProperty("backStatus", this::getStatus, null);
    builder.addDoubleProperty("back", jackMotor::getSelectedSensorPosition, null);
  }
}
