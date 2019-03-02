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

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.DriveWithJoysticks;
import frc.robot.util.Console;
import frc.robot.util.MotorUtil;
import frc.robot.util.SimpleSendable;

/**
 * Add your docs here.
 * 
 * 3 motors per side, standard encoders
 */
public class Drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  
  private TalonSRX leftMaster = MotorUtil.createTalon(RobotMap.DRIVETRAIN_LEFTMASTER_ID, true);
  private VictorSPX left1 = MotorUtil.createVictor(RobotMap.DRIVETRAIN_LEFT1_ID, true);
  private VictorSPX left2 = MotorUtil.createVictor(RobotMap.DRIVETRAIN_LEFT2_ID, true);
  
  private TalonSRX rightMaster = MotorUtil.createTalon(RobotMap.DRIVETRAIN_RIGHTMASTER_ID, false);
  private VictorSPX right1 = MotorUtil.createVictor(RobotMap.DRIVETRAIN_RIGHT1_ID, false);
  private VictorSPX right2 = MotorUtil.createVictor(RobotMap.DRIVETRAIN_RIGHT2_ID, false);
  
  private static final double INCHES_PER_TICK = 1;
  
  private double kP = 1.0;
  private double kI = 0.0;
  private double kD = 0.0;
  private double kF = 0.0;
  
  public Drivetrain() {
    left1.follow(leftMaster);
    left2.follow(leftMaster);
    right1.follow(rightMaster);
    right2.follow(rightMaster);
    
    leftMaster.configNeutralDeadband(0.02);
    rightMaster.configNeutralDeadband(0.02);
    
    leftMaster.config_kP(0, kP);
    leftMaster.config_kI(0, kI);
    leftMaster.config_kD(0, kD);
    leftMaster.config_kF(0, kF);
    
    rightMaster.config_kP(0, kP);
    rightMaster.config_kI(0, kI);
    rightMaster.config_kD(0, kD);
    rightMaster.config_kF(0, kF);
    
    SmartDashboard.putData("Left Encoder", new SimpleSendable(this::leftSendable));
    SmartDashboard.putData("Right Encoder", new SimpleSendable(this::rightSendable));
  }
  
  /**
   * Sets the speed of the left and right side of the drivetrain using percent
   * output
   * 
   * @param left
   *                the left speed, in the range of [-1, 1]
   * @param right
   *                the right speed, in the range of [-1, 1]
   */
  public void set(double left, double right) {
    leftMaster.set(ControlMode.PercentOutput, left);
    rightMaster.set(ControlMode.PercentOutput, right);
  }
  
  /**
   * Sets the speed of the left and right side of the drivetrain using velocity
   * closed loop
   * 
   * @param left
   *                the left velocity, in Inches Per Second
   * @param right
   *                the right speed, in Inches Per Second
   */
  public void setVelocity(double left, double right) {
    leftMaster.set(ControlMode.Velocity, left / INCHES_PER_TICK * 10);
    rightMaster.set(ControlMode.Velocity, right / INCHES_PER_TICK * 10);
  }
  
  private double totKF = 0;
  private double numKF = 0;
  private int num = 0;
  
  @Override
  public void periodic() {
    num++;
    if (num >= 100) {
      num = 0;
      if (leftMaster.getSelectedSensorVelocity(0) != 0) {
        totKF += leftMaster.getMotorOutputPercent() / leftMaster.getSelectedSensorVelocity(0);
      }
      if (rightMaster.getSelectedSensorVelocity(0) != 0) {
        totKF += rightMaster.getMotorOutputPercent() / rightMaster.getSelectedSensorVelocity(0);
      }
      numKF += 2;
      // Console.debug("kF: " + (totKF / numKF));
    }
  }
  
  /**
   * Gets the total rotation of the left side of the drivetrain
   * 
   * @return
   */
  public double getLeftPos() {
    return leftMaster.getSelectedSensorPosition(0) * INCHES_PER_TICK;
  }
  
  public PIDSource getLeftPIDSource() {
    return new PIDSource() {
      
      @Override
      public void setPIDSourceType(PIDSourceType pidSource) {
      }
      
      @Override
      public double pidGet() {
        return Robot.drivetrain.getLeftPos();
      }
      
      @Override
      public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
      }
    };
  }
  
  /**
   * Gets the total rotation of the right side of the drivetrain
   * 
   * @return
   */
  public double getRightPos() {
    return rightMaster.getSelectedSensorPosition(0) * INCHES_PER_TICK;
  }
  
  public PIDSource getRightPIDSource() {
    return new PIDSource() {
      
      @Override
      public void setPIDSourceType(PIDSourceType pidSource) {
      }
      
      @Override
      public double pidGet() {
        return Robot.drivetrain.getRightPos();
      }
      
      @Override
      public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
      }
    };
  }
  
  /**
   * Gets the total rotation of the left side of the drivetrain
   * 
   * @return
   */
  public double getLeftRate() {
    return leftMaster.getSelectedSensorVelocity(0);
  }
  
  public PIDSource getLeftRatePIDSource() {
    return new PIDSource() {
      
      @Override
      public void setPIDSourceType(PIDSourceType pidSource) {
      }
      
      @Override
      public double pidGet() {
        return Robot.drivetrain.getLeftRate();
      }
      
      @Override
      public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kRate;
      }
    };
  }
  
  /**
   * Gets the total rotation of the right side of the drivetrain
   * 
   * @return
   */
  public double getRightRate() {
    return rightMaster.getSelectedSensorVelocity(0);
  }
  
  public PIDSource getRightRatePIDSource() {
    return new PIDSource() {
      
      @Override
      public void setPIDSourceType(PIDSourceType pidSource) {
      }
      
      @Override
      public double pidGet() {
        return Robot.drivetrain.getRightRate();
      }
      
      @Override
      public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kRate;
      }
    };
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    
    // whenever the robot has no other command to control the drivetrain, the
    // DriveWithJoysticks command runs.
    setDefaultCommand(new DriveWithJoysticks());
  }
  
  /**
   * Resets this subsystem to a known state
   */
  public void stop() {
    leftMaster.set(ControlMode.PercentOutput, 0);
    rightMaster.set(ControlMode.PercentOutput, 0);
  }
  
  /**
   * Resets all sensors
   */
  public void zero() {
    leftMaster.setSelectedSensorPosition(0);
    rightMaster.setSelectedSensorPosition(0);
  }
  
  private void leftSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("Encoder");
    builder.addDoubleProperty("Speed", this::getLeftRate, null);
    builder.addDoubleProperty("Distance", this::getLeftPos, null);
    builder.addDoubleProperty("Distance per Tick", this::getDistancePerPulse, null);
  }
  
  private void rightSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("Encoder");
    builder.addDoubleProperty("Speed", this::getRightRate, null);
    builder.addDoubleProperty("Distance", this::getRightPos, null);
    builder.addDoubleProperty("Distance per Tick", this::getDistancePerPulse, null);
  }
  
  private double getDistancePerPulse() {
    return INCHES_PER_TICK;
  }
}
