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
import frc.robot.commands.DriveWithJoysticks;
import frc.robot.util.MotorUtil;

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
  
  public Drivetrain() {
    left1.follow(leftMaster);
    left2.follow(leftMaster);
    right1.follow(rightMaster);
    right2.follow(rightMaster);
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
   * Gets the total rotation of the left side of the drivetrain
   * 
   * @return
   */
  public double getLeftPos() {
    return 0;
  }
  
  /**
   * Gets the total rotation of the right side of the drivetrain
   * 
   * @return
   */
  public double getRightPos() {
    return 0;
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
    
  }
}
