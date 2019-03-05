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
  
  private TalonSRX frontLeft = MotorUtil.createTalon(RobotMap.CLIMBER_FRONT_LEFT, true);
  private TalonSRX frontRight = MotorUtil.createTalon(RobotMap.CLIMBER_FRONT_RIGHT, true);
  private TalonSRX back = MotorUtil.createTalon(RobotMap.CLIMBER_BACK, false);
  private VictorSPX driver = MotorUtil.createVictor(RobotMap.CLIMBER_DRIVER, false);
  
  /**
   * Various positions for the jacks
   * 
   * 1" ~= 620 ticks
   */
  private final double TOP = 620 * 12;// 6;
  private final double BOTTOM = 0;
  private final double ALLOWED_ERROR = 200;
  
  private double frontTarget = BOTTOM;
  private double backTarget = BOTTOM;
  
  /**
   * Stage on: 0: disabled 1: lift jacks 2: push forward 3: lower front 4: push
   * forward 5: lower back 6: climbed
   */
  private int stage = 0;
  
  private double kP = 2.0;
  private double kI = 0.0;
  private double kD = 0.0;
  
  public Climber() {
    frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    back.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    frontLeft.setSensorPhase(true);
    frontRight.setSensorPhase(false);
    back.setSensorPhase(false);
    
    frontRight.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
    frontRight.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
    
    // frontLeft.setSelectedSensorPosition(0, 0, 0);
    // frontRight.setSelectedSensorPosition(0, 0, 0);
    // back.setSelectedSensorPosition(0, 0, 0);
    
    frontLeft.config_kP(0, kP);
    frontLeft.config_kI(0, kI);
    frontLeft.config_kD(0, kD);
    
    frontRight.config_kP(0, kP);
    frontRight.config_kI(0, kI);
    frontRight.config_kD(0, kD);
    
    back.config_kP(0, kP);
    back.config_kI(0, kI);
    back.config_kD(0, kD);
    
    frontLeft.configClosedLoopPeakOutput(0, 0.6);
    frontRight.configClosedLoopPeakOutput(0, 0.6);
    back.configClosedLoopPeakOutput(0, 0.6);
    
    frontLeft.setNeutralMode(NeutralMode.Coast);
    frontRight.setNeutralMode(NeutralMode.Coast);
    back.setNeutralMode(NeutralMode.Coast);
  }
  
  /**
   * Starts the Jacks raising
   */
  public void raiseJacks() {
    if (stage == 0) {
      stage = 1;
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
      // lift jacks
      if (isErrorAllowed()) {
        frontTarget += ALLOWED_ERROR;
        backTarget += ALLOWED_ERROR;
        if (frontTarget >= TOP && backTarget >= TOP) {
          stage = 2;
        }
      }
      break;
    case 2:
      // push forward
      // stage = 3;
      break;
    case 3:
      // lower front
      if (isErrorAllowed()) {
        frontTarget -= ALLOWED_ERROR;
        if (frontTarget <= BOTTOM && backTarget >= TOP) {
          stage = 4;
        }
      }
      break;
    case 4:
      // push forward
      break;
    case 5:
      // lower back
      if (isErrorAllowed()) {
        backTarget--;
        if (frontTarget <= BOTTOM && backTarget <= BOTTOM) {
          stage = 0;
        }
      }
      break;
    }
  }
  
  /**
   * Sets the motors to their current target
   */
  private void setMotors() {
    if (stage == 0) {
      // frontLeft.set(ControlMode.PercentOutput, .1);
      // frontRight.set(ControlMode.PercentOutput, -.1);
      // back.set(ControlMode.PercentOutput, .1);
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
    } else {
      frontLeft.set(ControlMode.Position, frontTarget);
      frontRight.set(ControlMode.Position, frontTarget);
      back.set(ControlMode.Position, backTarget);
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
