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

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
  
  private TalonSRX frontLeft = new TalonSRX(RobotMap.CLIMBER_FRONT_LEFT);
  private TalonSRX frontRight = new TalonSRX(RobotMap.CLIMBER_FRONT_RIGHT);
  private TalonSRX back = new TalonSRX(RobotMap.CLIMBER_BACK);
  private VictorSPX driver = MotorUtil.createVictor(RobotMap.CLIMBER_DRIVER, false);
  
  /**
   * Various positions for the jacks
   * 
   * 1" ~= 620 ticks
   * 
   * Robot and Jacks on ground == 0
   */
  private final double BACK_OFFSET = 4009;
  private final double FRIGHT_OFFSET = 2673;
  private final double FLEFT_OFFSET = 4024;

  private final double BOTTOM = 0;
  private final double LVL2 = 620 * 7;
  private final double LVL3 = 620 * 21;
  
  private double bottom = BOTTOM;
  private double top = LVL2;
  private final double ALLOWED_ERROR = 600;
  
  private double frontTarget = bottom;
  private double backTarget = bottom;
  private double drive = 0;
  
  /**
   * Stage on: 0: disabled 1: lift jacks 2: push forward 3: lower front 4: push
   * forward 5: lower back 6: climbed
   */
  private int stage = 0;
  
  private double kP = 1.4;
  private double kI = 0.0;
  private double kD = 0.0;
  
  public Climber() {
    frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    back.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

    frontLeft.setSensorPhase(false);
    frontRight.setSensorPhase(false);
    back.setSensorPhase(false);

    frontLeft.setInverted(true);
    frontRight.setInverted(false);
    back.setInverted(true);

    frontLeft.configNominalOutputForward(0, 30);
    frontRight.configNominalOutputForward(0, 30);
    back.configNominalOutputForward(0, 30);

    frontLeft.configNominalOutputReverse(0, 30);
    frontRight.configNominalOutputReverse(0, 30);
    back.configNominalOutputReverse(0, 30);
    
    frontLeft.configPeakOutputForward(1, 30);
    frontRight.configPeakOutputForward(1, 30);
    back.configPeakOutputForward(1, 30);

    frontLeft.configPeakOutputReverse(-1, 30);
    frontRight.configPeakOutputReverse(-1, 30);
    back.configPeakOutputReverse(-1, 30);

    frontLeft.configAllowableClosedloopError(0, 0, 30);
    frontRight.configAllowableClosedloopError(0, 0, 30);
    back.configAllowableClosedloopError(0, 0, 30);

    int frontLeftAbsolutePosition = frontLeft.getSensorCollection().getPulseWidthPosition();
    int frontRightAbsolutePosition = frontRight.getSensorCollection().getPulseWidthPosition();
    int backAbsolutePosition = back.getSensorCollection().getPulseWidthPosition();

    frontLeftAbsolutePosition &= 0xFFF;
    frontRightAbsolutePosition &= 0xFFF;
    backAbsolutePosition &= 0xFFF;

    frontLeftAbsolutePosition *= -1;
    backAbsolutePosition *= -1;

    frontLeft.setSelectedSensorPosition(frontLeftAbsolutePosition, 0, 30);
    frontRight.setSelectedSensorPosition(frontRightAbsolutePosition, 0, 30);
    back.setSelectedSensorPosition(backAbsolutePosition, 0, 30);
    
    frontLeft.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    frontLeft.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    frontRight.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    frontRight.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    back.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    back.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
    
    frontLeft.config_kP(0, kP);
    frontRight.config_kP(0, kP);
    back.config_kP(0, kP);
    
    frontLeft.config_kI(0, kI);
    frontRight.config_kI(0, kI);
    back.config_kI(0, kI);
    
    frontLeft.config_kD(0, kD);
    frontRight.config_kD(0, kD);
    back.config_kD(0, kD);
    
    frontLeft.configClosedLoopPeakOutput(0, 0.7);
    frontRight.configClosedLoopPeakOutput(0, 0.7);
    back.configClosedLoopPeakOutput(0, 0.7);
    
    frontLeft.setNeutralMode(NeutralMode.Brake);
    frontRight.setNeutralMode(NeutralMode.Brake);
    back.setNeutralMode(NeutralMode.Brake);

    SmartDashboard.putData("Climber ", this);
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
    if (stage == 0) {
      stage = 1;
    }
  }
  
  public void stage2() {
    if (stage == 2) {
      stage = 3;
    }
  }
  
  public void stage3() {
    if (stage == 4) {
      stage = 5;
    }
  }
  
  @Override
  public void periodic() {
    updateTargets();
    Console.graph("Climber", frontLeft.getSensorCollection().getQuadraturePosition(), frontRight.getSensorCollection().getQuadraturePosition(),
        back.getSensorCollection().getQuadraturePosition());
  }
  
  /**
   * Checks the position of the motors, and sets a new target for them
   */
  private void updateTargets() {
    if (Robot.oi.manual.get()) {
      if (isErrorAllowed()) {
        if (Robot.oi.operator1.getY() < -0.9 && frontTarget > BOTTOM) {
          frontTarget -= ALLOWED_ERROR;
        } else if (Robot.oi.operator1.getY() > 0.9 && frontTarget < LVL3) {
          frontTarget += ALLOWED_ERROR;
        }
        if (Robot.oi.operator1.getX() > 0.9 && backTarget > BOTTOM) {
          backTarget -= ALLOWED_ERROR;
        } else if (Robot.oi.operator1.getX() < -0.9 && backTarget < LVL3) {
          backTarget += ALLOWED_ERROR;
        }
      }
      if (Robot.oi.operator2.getX() > 0.9) {
        drive = 0.2;
      } else if (Robot.oi.operator2.getX() < -0.9) {
        drive = -0.2;
      } else {
        drive = 0.0;
      }
      back.set(ControlMode.Position, backTarget);
      frontLeft.set(ControlMode.Position, frontTarget);
      frontRight.set(ControlMode.Position, frontTarget);
      driver.set(ControlMode.PercentOutput, drive);
    } else {
      switch (stage) {
      case 1:
        // lift jacks
        drive(false, false);
        if (moveTo(top, top)) {

          // stage = 0;
        }
        break;
      case 2:
        // push forward
        drive(true, false);
        moveTo(top, top);
        break;
      case 3:
        // lower front
        drive(false, false);
        if (moveTo(bottom, top)) {
          stage = 4;
        }
        break;
      case 4:
        // push forward
        drive(true, true);
        moveTo(bottom, top);
        break;
      case 5:
        // lower back
        drive(false, false);
        if (moveTo(bottom, bottom)) {
          stage = 6;
        }
        break;
      }
      setMotors();
      //testJack();
    }
  }
  
  private boolean moveTo(double front, double back) {
    frontTarget = front;
    backTarget = back;
    return Math.abs(frontTarget - this.frontLeft.getSensorCollection().getQuadraturePosition() - FLEFT_OFFSET) <= 100 && 
    (frontTarget - this.frontRight.getSensorCollection().getQuadraturePosition() - FRIGHT_OFFSET) <= 100 && Math.abs(backTarget - this.back.getSensorCollection().getQuadraturePosition() - BACK_OFFSET) <= 100;
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
    if (frontLeft.getSensorCollection().getQuadraturePosition() > 400) {
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
      // holdJacks();
      frontTarget = BOTTOM;
      backTarget = BOTTOM;
    } else {
      frontLeft.set(ControlMode.Position, frontTarget + FLEFT_OFFSET);
      frontRight.set(ControlMode.Position, frontTarget + FRIGHT_OFFSET);
      back.set(ControlMode.Position, backTarget + BACK_OFFSET);
      driver.set(ControlMode.PercentOutput, drive);
    }
  }

  private void testJack() {
    if (stage <= 0) {
      //holdJacks();
      // frontTarget = 0;
      // backTarget = 0;
    } else {
      // Switch out whichever jack you want to test here
      back.set(ControlMode.Position, 620 * 14);
    }
  }
  
  private boolean isErrorAllowed() {
    return Math.abs(frontLeft.getSelectedSensorPosition(0) - frontTarget) < ALLOWED_ERROR
        && Math.abs(frontRight.getSelectedSensorPosition(0) - frontTarget) < ALLOWED_ERROR
        && Math.abs(back.getSelectedSensorPosition(0) - backTarget) < ALLOWED_ERROR;
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
    builder.addDoubleProperty("back", back.getSensorCollection()::getQuadraturePosition, null);
    builder.addDoubleProperty("frontLeft", frontLeft.getSensorCollection()::getQuadraturePosition, null);
    builder.addDoubleProperty("frontRight", frontRight.getSensorCollection()::getQuadraturePosition, null);
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
