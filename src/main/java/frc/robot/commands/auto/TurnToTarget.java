/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.Limelight;

/**
 * A command to turn the robot towards the nearest vision target
 */
public class TurnToTarget extends Command {
  private PIDController anglePID;
  private PIDController forwardPID;
  
  /**
   * A command to turn the robot towards the nearest vision target
   */
  public TurnToTarget() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivetrain);
    anglePID = new PIDController(0.02, 0.0, 0.0, Robot.limelight.getHorizontalPidSource(), this::output);
    anglePID.setOutputRange(-1, 1);
    anglePID.setSetpoint(0);
    SmartDashboard.putData("VisionTurning", anglePID);
    forwardPID = new PIDController(0.02, 0.0, 0.0, Robot.limelight.getDistancePidSource(Limelight.Target.AnyOther),
        this::output);
    forwardPID.setOutputRange(-1, 1);
    forwardPID.setSetpoint(20);
    SmartDashboard.putData("VisionForward", forwardPID);
  }
  
  private void output(double d) {
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    anglePID.reset();
    anglePID.enable();
  }
  
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double speed = (Robot.oi.getLeftJoystick() + Robot.oi.getRightJoystick()) * 0.5;
    double turn = anglePID.get();
    // System.out.printf("%1.3f /\\, > %1.3f\n", speed, turn);
    Robot.drivetrain.set(speed - turn, speed + turn);
  }
  
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }
  
  // Called once after isFinished returns true
  @Override
  protected void end() {
    anglePID.disable();
  }
  
  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    anglePID.disable();
  }
}
