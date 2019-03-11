/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;
  
  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
  
  // names of constants should be subsystem_specificPart
  
  public static final int DRIVETRAIN_LEFTMASTER_ID = 32;
  public static final int DRIVETRAIN_LEFT1_ID = 33;
  public static final int DRIVETRAIN_LEFT2_ID = 34;
  public static final int DRIVETRAIN_RIGHTMASTER_ID = 11;
  public static final int DRIVETRAIN_RIGHT1_ID = 12;
  public static final int DRIVETRAIN_RIGHT2_ID = 13;
  
  // Start of OI Variables
  public static final int OI_LEFT_ID = 1;
  public static final int OI_RIGHT_ID = 2;
  public static final int OI_OPERATOR_1_ID = 0;
  public static final int OI_OPERATOR_2_ID = 3;
  
  // Operator 1 and 2
  public static final int OI_CARGO_SHIP_ID = 5;
  public static final int OI_CARGO_STATION_ID = 1;
  public static final int OI_ROCKET_PANEL_LEVEL_1_ID = 5;
  public static final int OI_ROCKET_PANEL_LEVEL_2_ID = 4;
  public static final int OI_ROCKET_PANEL_LEVEL_3_ID = 3;
  public static final int OI_ROCKET_LEVEL_1_ID = 4;
  public static final int OI_ROCKET_LEVEL_2_ID = 3;
  public static final int OI_ROCKET_LEVEL_3_ID = 2;
  public static final int OI_CLIMB1_ID = 11;
  public static final int OI_CLIMB2_ID = 7;
  public static final int OI_CLIMB3_ID = 6;
  
  // Joystick 1 Buttons
  public static final int OI_CLAW_IN_ID = 3;
  public static final int OI_CLAW_OUT_ID = 2;
  public static final int OI_CLAW_GRAB_ID = 2;
  public static final int OI_CLAW_RELEASE_ID = 3;
  
  // Joystick 2 Buttons
  public static final int OI_VISION_ALIGNMENT_ID = 1;
  public static final int OI_INTAKE_CARGO_ID = 1;
  public static final int OI_PLACE_CARGO_ID = 2;
  public static final int OI_GRAB_PANEL_ID = 1;
  public static final int OI_PLACE_PANEL_ID = 1;
  // End of OI Variables
  
  public static final int CARGO_INTAKE_MOTOR_ID = 16;
  public static final int CARGO_INTAKE_SOLENOID_ID = 2;
  
  public static final int ELEVATOR_ELEVATOR_ID = 29;
  
  public static final int SCORER_MOTOR_LEFT = 28; // 30
  public static final int SCORER_MOTOR_RIGHT = 30; // 17
  
  public static final int CLIMBER_FRONT_LEFT = 35;
  public static final int CLIMBER_FRONT_RIGHT = 10;
  public static final int CLIMBER_BACK = 15;
  public static final int CLIMBER_DRIVER = 17;
  
  public static final int HATCH_CLAW_GRABBER = 1;
  public static final int HATCH_CLAW_MOVER = 0;
}
