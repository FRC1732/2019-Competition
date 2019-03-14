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
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
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

public class Climber extends Subsystem implements Sendable {
  
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  
  /**
   * Various positions for the jacks
   * 
   * 1" ~= 620 ticks
   * 
   * Robot and Jacks on ground == 0
   */
  
  private TalonSRX frontLeft = MotorUtil.createTalon(RobotMap.CLIMBER_FRONT_LEFT, false);
  private TalonSRX frontRight = MotorUtil.createTalon(RobotMap.CLIMBER_FRONT_RIGHT, true);
  private TalonSRX back = MotorUtil.createTalon(RobotMap.CLIMBER_BACK, false);
  private VictorSPX driver = MotorUtil.createVictor(RobotMap.CLIMBER_DRIVER, false);
  
  private final double BOTTOM = 0;
  private final double LVL2 = 620 * 7;
  private final double LVL3 = 620 * 21;
  
  private double bottom = BOTTOM;
  private double top = LVL2;
  
  private double frontTarget = bottom;
  private double backTarget = bottom;
  private double drive = 0;
  private int stage = 0;
  
  private static final double kP = 1.4;
  private static final double kI = 0.0;
  private static final double kD = 0.0;
  private static final double kF = 1.0;
  private static final double minimumOutput = .01;
  private static final double motionCruiseVelocity = 300;
  private static final double motionAcceleration = motionCruiseVelocity;
  
  /**
   * Stage on: 0: disabled 1: put down jacks 2: push forward 3: raise front 4:
   * push forward 5: raise back 6: climbed
   */
  
  public Climber() {
    frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    back.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    
    frontLeft.setSensorPhase(false);
    frontRight.setSensorPhase(false);
    back.setSensorPhase(false);
    
    frontLeft.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    frontLeft.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    frontRight.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    frontRight.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    back.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    back.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    
    frontLeft.setSelectedSensorPosition(0, 0, 0);
    frontRight.setSelectedSensorPosition(0, 0, 0);
    back.setSelectedSensorPosition(0, 0, 0);
    
    frontLeft.config_kP(0, kP);
    frontRight.config_kP(0, kP);
    back.config_kP(0, kP);
    
    frontLeft.config_kI(0, kI);
    frontRight.config_kI(0, kI);
    back.config_kI(0, kI);
    
    frontLeft.config_kD(0, kD);
    frontRight.config_kD(0, kD);
    back.config_kD(0, kD);
    
    frontLeft.config_kF(0, kF);
    frontRight.config_kF(0, kF);
    back.config_kF(0, kF);
    
    frontLeft.configMotionCruiseVelocity((int) motionCruiseVelocity);
    frontRight.configMotionCruiseVelocity((int) motionCruiseVelocity);
    back.configMotionCruiseVelocity((int) motionCruiseVelocity);
    
    frontLeft.configMotionAcceleration((int) motionAcceleration);
    frontRight.configMotionAcceleration((int) motionAcceleration);
    back.configMotionAcceleration((int) motionAcceleration);
    
    frontLeft.configClosedLoopPeakOutput(0, 0.7);
    frontRight.configClosedLoopPeakOutput(0, 0.7);
    back.configClosedLoopPeakOutput(0, 0.7);
    
    frontLeft.setNeutralMode(NeutralMode.Brake);
    frontRight.setNeutralMode(NeutralMode.Brake);
    back.setNeutralMode(NeutralMode.Brake);
  }
  
  public void setClimbHeight(int platform) {
    switch (platform) {
    case 1:
      Console.warn("HAB 1 doesn't need climber");
      break;
    case 2:
      top = LVL2;
      break;
    case 3:
      top = LVL3;
      break;
    default:
      Console.warn("HAB " + platform + " doesn't exist");
    }
  }
  
  public void stage1() {
  }
  
  public void stage2() {
    
  }
  
  public void stage3() {
  }
  
  @Override
  public void periodic() {
    if (Robot.oi.manual.get()) {
    updateManualTargets();
    } else {
    climbProcess();
    }
    setMotors();
    Console.graph("Climber", frontLeft.getSelectedSensorPosition(0), frontRight.getSelectedSensorPosition(0),
        back.getSelectedSensorPosition(0));
  }
  
  /**
   * Checks the position of the motors, and sets a new target for them
   */
  
  private void jacksOne() {
    // lower jacks
    frontTarget = top;
    backTarget = top;
    if (isOnTarget(frontTarget, backTarget)) {
      stage = 2;
    }
  }  
  private void climberForward() {
    // move climber/robot forward
    // should this be true, true?
    drive(true, true);
    if (back.getSelectedSensorPosition(0) >= 0) {
      stage += 1;
    }
  }  
  private void jacksTwo() {
    // raise front jack
    frontTarget = bottom;
    backTarget = top;
    if (isOnTarget(frontTarget, backTarget)) {
      stage = 4;
    }
  }  
  private void jacksThree() {
    // raise both jacks
    frontTarget = bottom;
    backTarget = bottom;
    if (isOnTarget(frontTarget, backTarget)) {
      stage = 5;
    }
  }  
  private void climbProcess() {
    switch (stage) {
    case (0):
      jacksOne(); break;
    case (1):
      climberForward(); break;
    case (2):
      jacksTwo(); break;
    case (3):
      climberForward(); break;
    case (4):
      jacksThree(); break;
    case (5):
      Console.info("climb completed sucessfully"); break;
    default:
      Console.warn("climb error: auto stage does not exist"); break;
    }    
  }
  
  private void updateManualTargets() {    
      if (Robot.oi.operator1.getY() < -0.9 && frontTarget > BOTTOM) {
        frontLeft.set(ControlMode.PercentOutput, 1);
        frontRight.set(ControlMode.PercentOutput, 1);
      } else if (Robot.oi.operator1.getY() > 0.9 && frontTarget < LVL3) {
        frontLeft.set(ControlMode.PercentOutput, -1);
        frontRight.set(ControlMode.PercentOutput, -1);
      } else {
        frontLeft.set(ControlMode.PercentOutput, 0);
        frontRight.set(ControlMode.PercentOutput, 0);
      }
      if (Robot.oi.operator1.getX() > 0.9 && backTarget > BOTTOM) {
        back.set(ControlMode.PercentOutput, 1);
      } else if (Robot.oi.operator1.getX() < -0.9 && backTarget < LVL3) {
        back.set(ControlMode.PercentOutput, -1);
      } else {
        back.set(ControlMode.PercentOutput, 0);
      }
    if (Robot.oi.operator2.getX() > 0.9) {
      drive = 0.2;
    } else if (Robot.oi.operator2.getX() < -0.9) {
      drive = -0.2;
    } else {
      drive = 0.0;
    }
    back.set(ControlMode.PercentOutput, backTarget);
    frontLeft.set(ControlMode.PercentOutput, frontTarget);
    frontRight.set(ControlMode.PercentOutput, frontTarget);
    driver.set(ControlMode.PercentOutput, drive);
  }
  
  private void drive(boolean back, boolean drivetrian) {
    if (back || Robot.oi.manual.get()) {
      drive = 0.2;
    } else {
      drive = 0.0;
    }
    if (drivetrian) {
      Robot.drivetrain.set(0.1, 0.1);
    } else {
      Robot.drivetrain.set(0.0, 0.0);
    }
  }
  
  private void holdJacks() {
    if (frontLeft.getSelectedSensorPosition(0) > 400) {
      frontLeft.set(ControlMode.PercentOutput, -.1);
    } else {
      frontLeft.set(ControlMode.PercentOutput, 0);
    }
    if (frontRight.getSelectedSensorPosition(0) > 400) {
      frontRight.set(ControlMode.PercentOutput, -.1);
    } else {
      frontRight.set(ControlMode.PercentOutput, 0);
    }
    if (back.getSelectedSensorPosition(0) > 400) {
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
    if (stage <= 0) {
      holdJacks();
      frontTarget = 0;
      backTarget = 0;
    } else {
      frontLeft.set(ControlMode.MotionMagic, frontTarget, DemandType.ArbitraryFeedForward, minimumOutput);
      frontRight.set(ControlMode.MotionMagic, frontTarget, DemandType.ArbitraryFeedForward, minimumOutput);
      back.set(ControlMode.MotionMagic, backTarget, DemandType.ArbitraryFeedForward, minimumOutput);
      driver.set(ControlMode.PercentOutput, drive);
    }
  }
  
  private boolean isOnTarget(double tempFrontTarget, double tempBackTarget) {
    return Math.abs(frontLeft.getSelectedSensorPosition(0) - tempFrontTarget) < 10
        && Math.abs(frontRight.getSelectedSensorPosition(0) - tempFrontTarget) < 10
        && Math.abs(back.getSelectedSensorPosition(0) - tempBackTarget) < 10;
  }
  
  @Override
  public void initDefaultCommand() {
  }
  
  public void stop() {
    stage = 0;
  }
  
  public void zero() {
  }
  
  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("Climber");
    builder.addDoubleProperty("stage", this::getStage, null);
    builder.addDoubleProperty("frontTarget", this::getFrontTarget, null);
    builder.addDoubleProperty("backTarget", this::getBackTarget, null);
    builder.addDoubleProperty("drive", this::getDrive, null);
    builder.addDoubleProperty("back", back::getSelectedSensorPosition, null);
    builder.addDoubleProperty("frontLeft", frontLeft::getSelectedSensorPosition, null);
    builder.addDoubleProperty("frontRight", frontRight::getSelectedSensorPosition, null);
  }
  
  private double getStage() {
    return stage;
  }
  
  private double getFrontTarget() {
    return frontTarget;
  }
  
  private double getBackTarget() {
    return backTarget;
  }
  
  private double getDrive() {
    return drive;
  }
}
