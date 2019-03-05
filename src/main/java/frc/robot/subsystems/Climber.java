/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.Console;
import frc.robot.util.MotorUtil;

/**
 * 
 * 3 motors for jacks - limit switches
 * 
 * 1 motor to creep forward
 * 
 * Method:
 * 
 * * Raise jacks
 * 
 * * Creep forward
 * 
 * * Front jacks go up
 * 
 * * Creep forward, with drivetrain
 * 
 * * Raise back jack until match end/ at top
 */
public class Climber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  
  private TalonSRX frontLeft = MotorUtil.createTalon(RobotMap.CLIMBER_FRONT_LEFT, false);
  private TalonSRX frontRight = MotorUtil.createTalon(RobotMap.CLIMBER_FRONT_RIGHT, true);
  private TalonSRX back = MotorUtil.createTalon(RobotMap.CLIMBER_BACK, false);
  private VictorSPX driver = MotorUtil.createVictor(RobotMap.CLIMBER_DRIVER, false);
  
  /**
   * Various positions for the jacks
   * 
   * 1" ~= 620 ticks
   * 
   * Robot and Jacks on ground == 0
   */
  private final double TOP = 620 * 20;// 6; //LV 2 = 7 //LV 3 = 20
  private final double BOTTOM = 0;
  private final double ALLOWED_ERROR = 200;
  private final double NOMINAL_CURRENT = 30;
  
  private double frontTarget = BOTTOM;
  private double backTarget = BOTTOM;
  private double drive = 0.0;
  
  /**
   * Stage on: 0: disabled, 1: drop and zero encoders, 2: lift jacks, 3: push
   * forward, 4: lower front, 5: push forward, 6: lower back, 7: climbed
   */
  private int stage = 0;
  
  private double kP = 2.0;
  private double kI = 0.0;
  private double kD = 0.0;
  
  public Climber() {
    frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    back.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    frontLeft.setSensorPhase(false);
    frontRight.setSensorPhase(false);
    back.setSensorPhase(false);
    
    frontRight.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
    frontRight.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
    
    frontLeft.setSelectedSensorPosition(0, 0, 0);
    frontRight.setSelectedSensorPosition(0, 0, 0);
    back.setSelectedSensorPosition(0, 0, 0);
    
    frontLeft.config_kP(0, kP * 0.9);
    frontLeft.config_kI(0, kI);
    frontLeft.config_kD(0, kD);
    
    frontRight.config_kP(0, kP * 0.9);
    frontRight.config_kI(0, kI);
    frontRight.config_kD(0, kD);
    
    back.config_kP(0, kP * 0.7);
    back.config_kI(0, kI);
    back.config_kD(0, kD);
    
    frontLeft.configClosedLoopPeakOutput(0, 0.6);
    frontRight.configClosedLoopPeakOutput(0, 0.6);
    back.configClosedLoopPeakOutput(0, 0.6);
    
    frontLeft.setNeutralMode(NeutralMode.Brake);
    frontRight.setNeutralMode(NeutralMode.Brake);
    back.setNeutralMode(NeutralMode.Brake);
  }
  
  /**
   * Starts the Jacks raising
   */
  public void raiseJacks() {
    if (stage == 0) {
      stage = 1;
    }
  }
  
  public void stage1() {
    if (stage == 0) {
      stage = 1;
    }
  }
  
  public void stage2() {
    if (stage == 3) {
      stage = 4;
    }
  }
  
  public void stage3() {
    if (stage == 5) {
      stage = 6;
    }
  }
  
  @Override
  public void periodic() {
    updateTargets();
    setMotors();
    Console.graph("Climber", frontLeft.getSelectedSensorPosition(0), frontRight.getSelectedSensorPosition(0),
        back.getSelectedSensorPosition(0));
  }
  
  /**
   * Checks the position of the motors, and sets a new target for them
   */
  private void updateTargets() {
    switch (stage) {
    case 1:
      // drop and zero encoders
      drive(false, false);
      if(lowerJacks()) {
        stage = 2;
      }
    case 2:
      // lift jacks
      drive(false, false);
      if (moveTo(TOP, TOP)) {
        stage = 3;
      }
      break;
    case 3:
      // push forward
      drive(true, false);
      moveTo(TOP, TOP);
      break;
    case 4:
      // lower front
      drive(false, false);
      if (moveTo(BOTTOM, TOP)) {
        stage = 5;
      }
      break;
    case 5:
      if (Robot.oi.climb3.get()) {
        stage = 6;
      }
      // push forward
      drive(true, true);
      moveTo(BOTTOM, TOP);
      break;
    case 6:
      // lower back
      drive(false, false);
      if (moveTo(BOTTOM, BOTTOM)) {
        stage = 0;
      }
      break;
    }
  }
  
  private boolean moveTo(double front, double back) {
    if (isErrorAllowed()) {
      if (front > frontTarget) {
        frontTarget += ALLOWED_ERROR;
      } else if (front < frontTarget) {
        frontTarget -= ALLOWED_ERROR;
      }
      if (back > backTarget) {
        frontTarget += ALLOWED_ERROR;
      } else if (back < frontTarget) {
        frontTarget -= ALLOWED_ERROR;
      }
      return Math.abs(frontTarget - front) < ALLOWED_ERROR / 2 && Math.abs(backTarget - back) < ALLOWED_ERROR / 2;
    } else {
      return false;
    }
  }
  
  private void drive(boolean back, boolean drivetrian) {
    if (back) {
      drive = 0.2;
    }
    if (drivetrian) {
      Robot.drivetrain.set(0.1, 0.1);
    }
  }
  
  private boolean lowerJacks() {
    if (frontLeft.getControlMode() == ControlMode.PercentOutput) {
      frontLeft.set(ControlMode.PercentOutput, .1);
      if (frontLeft.getOutputCurrent() > NOMINAL_CURRENT) {
        frontLeft.setSelectedSensorPosition(0, 0, 0);
        frontLeft.set(ControlMode.Position, 0);
      }
    }
    if (frontRight.getControlMode() == ControlMode.PercentOutput) {
      frontRight.set(ControlMode.PercentOutput, .1);
      if (frontRight.getOutputCurrent() > NOMINAL_CURRENT) {
        frontRight.setSelectedSensorPosition(0, 0, 0);
        frontRight.set(ControlMode.Position, 0);
      }
    }
    if (back.getControlMode() == ControlMode.PercentOutput) {
      back.set(ControlMode.PercentOutput, .1);
      if (back.getOutputCurrent() > NOMINAL_CURRENT) {
        back.setSelectedSensorPosition(0, 0, 0);
        back.set(ControlMode.Position, 0);
      }
    }
    return frontLeft.getControlMode() == ControlMode.Position && frontRight.getControlMode() == ControlMode.Position
        && back.getControlMode() == ControlMode.Position;
  }
  
  private void holdJacks() {
    if (frontLeft.getSelectedSensorPosition(0) > 1000) {
      frontLeft.set(ControlMode.PercentOutput, -.1);
    } else {
      frontLeft.set(ControlMode.PercentOutput, 0);
    }
    if (frontRight.getSelectedSensorPosition(0) > 1000) {
      frontRight.set(ControlMode.PercentOutput, -.1);
    } else {
      frontRight.set(ControlMode.PercentOutput, 0);
    }
    if (back.getSelectedSensorPosition(0) > 1000) {
      back.set(ControlMode.PercentOutput, -.1);
    } else {
      back.set(ControlMode.PercentOutput, 0);
    }
    driver.set(ControlMode.PercentOutput, 0.0);
  }
  
  /**
   * Sets the motors to their current target
   */
  private void setMotors() {
    if (stage <= 1) {
      // frontLeft.set(ControlMode.PercentOutput, .1);
      // frontRight.set(ControlMode.PercentOutput, -.1);
      // back.set(ControlMode.PercentOutput, .1);
      holdJacks();
    } else {
      frontLeft.set(ControlMode.Position, frontTarget);
      frontRight.set(ControlMode.Position, frontTarget);
      back.set(ControlMode.Position, backTarget);
      driver.set(ControlMode.PercentOutput, drive);
    }
  }
  
  private boolean isErrorAllowed() {
    return Math.abs(frontLeft.getSelectedSensorPosition(0) - frontTarget) < ALLOWED_ERROR
        && Math.abs(frontRight.getSelectedSensorPosition(0) - frontTarget) < ALLOWED_ERROR
        && Math.abs(back.getSelectedSensorPosition(0) - backTarget) < ALLOWED_ERROR;
  }
  
  public void stop() {
    stage = 0;
  }
  
  public void zero() {
    
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
