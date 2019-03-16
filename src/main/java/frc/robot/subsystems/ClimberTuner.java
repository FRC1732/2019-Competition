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

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
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

public class ClimberTuner extends Subsystem implements Sendable {
  
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
 
  private static final double kP = 1.4;
  private static final double kI = 0.0;
  private static final double kD = 0.0;

  private boolean ClimberTest = false;
  
  /**
   * Stage on: 0: disabled 1: put down jacks 2: push forward 3: raise front 4:
   * push forward 5: raise back 6: climbed
   */
  
  public ClimberTuner() {
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
    
    frontLeft.configClosedLoopPeakOutput(0, 0.7);
    frontRight.configClosedLoopPeakOutput(0, 0.7);
    back.configClosedLoopPeakOutput(0, 0.7);
    
    frontLeft.setNeutralMode(NeutralMode.Brake);
    frontRight.setNeutralMode(NeutralMode.Brake);
    back.setNeutralMode(NeutralMode.Brake);
  }


  
  @Override
  public void periodic() {
    if (ClimberTest == false) testJacks();
    else holdJacks();  
    
    Console.graph("Climber", frontLeft.getSelectedSensorPosition(0), frontRight.getSelectedSensorPosition(0),
        back.getSelectedSensorPosition(0));
  }

  private void testJacks() {
    frontLeft.set(ControlMode.PercentOutput, 1);
    frontRight.set(ControlMode.PercentOutput, 1);
    back.set(ControlMode.PercentOutput, 1);
    if (frontLeft.getSelectedSensorPosition(0) > 620 || frontRight.getSelectedSensorPosition(0) >620
     || back.getSelectedSensorPosition(0) >620){
       ClimberTest = true;
     }
  }

  private void holdJacks() {
    frontLeft.set(ControlMode.Position, frontLeft.getSelectedSensorPosition(0));
    frontRight.set(ControlMode.Position, frontRight.getSelectedSensorPosition(0));
    back.set(ControlMode.Position, back.getSelectedSensorPosition(0));
  }

  
  @Override
  public void initDefaultCommand() {
  }
  
  public void stop() {
  }
  
  public void zero() {
  }
  
  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("ClimberTuner");
    builder.addDoubleProperty("back", back::getSelectedSensorPosition, null);
    builder.addDoubleProperty("frontLeft", frontLeft::getSelectedSensorPosition, null);
    builder.addDoubleProperty("frontRight", frontRight::getSelectedSensorPosition, null);
  }
  
  
}
