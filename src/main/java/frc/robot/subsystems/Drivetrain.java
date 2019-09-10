/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;



import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.DriveWithJoysticks;
import frc.robot.util.SimpleSendable;

/**
 * Add your docs here.
 * 
 * 3 motors per side, standard encoders
 */
public class Drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  
  private CANSparkMax leftMaster = new CANSparkMax(RobotMap.DRIVETRAIN_LEFTMASTER_ID, MotorType.kBrushless);
  private CANSparkMax left1 = new CANSparkMax(RobotMap.DRIVETRAIN_LEFT1_ID, MotorType.kBrushless);
 // private CANSparkMax left2 = new CANSparkMax(RobotMap.DRIVETRAIN_LEFT2_ID, MotorType.kBrushless);
  
  private CANSparkMax rightMaster = new CANSparkMax(RobotMap.DRIVETRAIN_RIGHTMASTER_ID, MotorType.kBrushless);
  private CANSparkMax right1 = new CANSparkMax(RobotMap.DRIVETRAIN_RIGHT1_ID, MotorType.kBrushless);
 // private CANSparkMax right2 = new CANSparkMax(RobotMap.DRIVETRAIN_RIGHT2_ID, MotorType.kBrushless);
  
  private static final double INCHES_PER_TICK = 1;
  
  public Drivetrain() {
    left1.follow(leftMaster);
   // left2.follow(leftMaster);
    right1.follow(rightMaster);
  //  right2.follow(rightMaster);
    
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
    leftMaster.set(left);
    rightMaster.set(right);
  }
  
  /**
   * Gets the total rotation of the left side of the drivetrain
   * 
   * @return
   */
  public Double getLeftPos() {
    return leftMaster.getEncoder().getPosition();
  }
  
  /**
   * Gets the total rotation of the right side of the drivetrain
   * 
   * @return
   */
  public Double getRightPos() {
    return rightMaster.getEncoder().getPosition();
  }
  
  /**
   * Gets the total rotation of the left side of the drivetrain
   * 
   * @return
   */
  public double getLeftRate() {
    return leftMaster.getEncoder().getVelocity();
  }
  
  /**
   * Gets the total rotation of the right side of the drivetrain
   * 
   * @return
   */
  public double getRightRate() {
    return rightMaster.getEncoder().getVelocity();
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
    leftMaster.set(0);
    rightMaster.set(0);
  }
  
  /**
   * Resets all sensors
   */
  public void zero() {
    leftMaster.getEncoder().getPosition();
    rightMaster.getEncoder().getPosition();
  }
  
  private void leftSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("Encoder");
    builder.addDoubleProperty("Speed", this::getLeftRate, null);
    builder.addDoubleProperty("Distance", this::getLeftPos, null);
  }
  
  private void rightSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("Encoder");
    builder.addDoubleProperty("Speed", this::getLeftRate, null);
     builder.addDoubleProperty("Distance", this::getLeftPos, null);
  }
  
}
