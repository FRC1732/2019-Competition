/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
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
  private TalonSRX frontRight = MotorUtil.createTalon(RobotMap.CLIMBER_FRONT_RIGHT, false);
  private TalonSRX back = MotorUtil.createTalon(RobotMap.CLIMBER_BACK, false);
  private VictorSPX driver = MotorUtil.createVictor(RobotMap.CLIMBER_DRIVER, false);
  
  /**
   * Various positions for the jacks
   */
  private final double TOP = 1;
  private final double BOTTOM = 0;
  private final double ALLOWED_ERROR = 1;
  
  private double frontTarget = BOTTOM;
  private double backTarget = BOTTOM;
  
  /**
   * Stage on: 0: disabled 1: lift jacks 2: push forward 3: lower front 4: push
   * forward 5: lower back 6: climbed
   */
  private int stage = 0;
  
  public Climber() {
    
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
    if (stage == 0) {
      stage = 1;
    }
  }
  
  public void stage3() {
    if (stage == 0) {
      stage = 1;
    }
  }
  
  @Override
  public void periodic() {
    updateTargets();
    setMotors();
  }
  
  /**
   * Checks the position of the motors, and sets a new target for them
   */
  private void updateTargets() {
    switch (stage) {
    case 1:
      // lift jacks
      if (isErrorAllowed()) {
        frontTarget++;
        backTarget++;
        if (frontTarget >= TOP && backTarget >= TOP) {
          stage = 2;
        }
      }
    case 2:
      // push forward
    case 3:
      // lower front
      if (isErrorAllowed()) {
        frontTarget--;
        if (frontTarget <= BOTTOM && backTarget >= TOP) {
          stage = 4;
        }
      }
    case 4:
      // push forward
    case 5:
      // lower back
      if (isErrorAllowed()) {
        backTarget--;
        if (frontTarget <= BOTTOM && backTarget <= BOTTOM) {
          stage = 6;
        }
      }
    }
  }
  
  /**
   * Sets the motors to their current target
   */
  private void setMotors() {
    frontLeft.set(ControlMode.Position, frontTarget);
    frontRight.set(ControlMode.Position, frontTarget);
    back.set(ControlMode.Position, backTarget);
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
}
