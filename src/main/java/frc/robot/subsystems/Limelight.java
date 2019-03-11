/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

/**
 * Add your docs here.
 * 
 * Limelight camera
 */
public class Limelight extends Subsystem implements Sendable {
  /**
   * Various Target heights to pass to the limelight methods
   * 
   * The limelight can't tell them apart, so you need to tell it which kind of
   * target should be near the center of it's vision
   *
   */
  public static enum Target {
    RocketCargo(0), AnyOther(28.0 + (5.0 / 16.0));
    public final double height;
    
    private Target(double height) {
      this.height = height;
    }
  }
  
  private static final double cameraHeight = 37.0 + (3.0 / 16.0);
  private static final double angle = 1.9;
  
  private double horiz = 0;
  private NetworkTableEntry horizNet;
  private double vert = 0;
  private NetworkTableEntry vertNet;
  private double num = 0;
  private NetworkTableEntry numNet;
  
  private int pipeline = 1;
  private NetworkTableEntry pipeNet;
  
  public Limelight() {
    final NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    horizNet = table.getEntry("tx");
    vertNet = table.getEntry("ty");
    numNet = table.getEntry("tv");
    pipeNet = table.getEntry("pipeline");
    
    SmartDashboard.putData("Limelight", this);
  }
  
  @Override
  public void periodic() {
    horiz = horizNet.getDouble(0);
    vert = vertNet.getDouble(0);
    num = numNet.getDouble(0);
    if (pipeNet.getDouble(-1) != pipeline) {
      pipeNet.setDouble(pipeline);
    }
    // if (vert > 1) {
    // horiz = 0;
    // }
  }
  
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  
  /**
   * Gets the horizontal angle of the current target in relation to robot's
   * heading
   * 
   * The angle can be in the range [-x, x]
   * 
   * @return The horizontal angle, in degrees
   */
  public double getHorizontalAngle() {
    return horiz;
  }
  
  public PIDSource getHorizontalPidSource() {
    return new PIDSource() {
      
      @Override
      public void setPIDSourceType(PIDSourceType pidSource) {
      }
      
      @Override
      public double pidGet() {
        return Robot.limelight.getHorizontalAngle();
      }
      
      @Override
      public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
      }
    };
  }
  
  /**
   * Gets the vertical angle of the current target in relation to a flat plane
   * extending out from the limelight, and parallel to the robot's base
   * 
   * The angle can be in the range [-x, x]
   * 
   * @return The vertical angle, in degrees
   */
  public double getVerticalAngle() {
    return vert;
  }
  
  public PIDSource getVerticalPidSource() {
    return new PIDSource() {
      
      @Override
      public void setPIDSourceType(PIDSourceType pidSource) {
      }
      
      @Override
      public double pidGet() {
        return getVerticalAngle();
      }
      
      @Override
      public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
      }
    };
  }
  
  public boolean hasTarget() {
    return num > 0;
  }
  
  /**
   * Gets an approxomate distance to the target, from the front of the robot
   *
   * @param target
   *                 the target type to track
   * @return The distance in inches.
   */
  public double getTargetDistance(Target target) {
    /*
     * Target distance calculation is dependant on whether the robot has a cargo,
     * hatch panel or neither, as well as whether we are trying to score on the
     * cargo ship, or the rocket.
     */
    return (target.height - cameraHeight) / Math.tan(Math.toRadians(vert + angle));
  }
  
  public PIDSource getDistancePidSource(Target target) {
    return new PIDSource() {
      
      @Override
      public void setPIDSourceType(PIDSourceType pidSource) {
      }
      
      @Override
      public double pidGet() {
        return Robot.limelight.getTargetDistance(target);
      }
      
      @Override
      public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
      }
    };
  }
  
  /**
   * Resets this subsystem to a known state
   */
  public void stop() {
    
  }
  
  /**
   * Resets all sensors to a known state
   */
  public void zero() {
    
  }
  
  private double getRange() {
    return getTargetDistance(Target.AnyOther);
  }
  
  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("Limelight");
    builder.addBooleanProperty("Has Target", this::hasTarget, null);
    builder.addDoubleProperty("Range", this::getRange, null);
  }
}
