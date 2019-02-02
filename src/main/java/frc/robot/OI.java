/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.IntakeCargo;
import frc.robot.commands.PlaceCargo;
import frc.robot.commands.PlaceHatch;
import frc.robot.commands.SetElevator;
import frc.robot.subsystems.Elevator;
import frc.robot.util.Console;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  private Joystick left = new Joystick(RobotMap.OI_LEFT_ID);
  private Joystick right = new Joystick(RobotMap.OI_RIGHT_ID);
  private Joystick operator1 = new Joystick(RobotMap.OI_OPERATOR_1_ID);
  private Joystick operator2 = new Joystick(RobotMap.OI_OPERATOR_2_ID);
  
  /**
   * Gets the left joystick's position, as a percent of fully pushed
   * 
   * @return the position, in the range of [-1, 1]
   */
  public double getLeftJoystick() {
    return -left.getY();
  }
  
  /**
   * Gets the right joystick's position, as a percent of fully pushed
   * 
   * @return the position, in the range of [-1, 1]
   */
  public double getRightJoystick() {
    return -right.getY();
  }

  // Auto Routines
  private JoystickButton intakeCargo = new JoystickButton(left, RobotMap.OI_INTAKE_ID);
  private JoystickButton outtakeCargo = new JoystickButton(right, RobotMap.OI_OUTTAKE_ID);
  private JoystickButton cargoRocketLevel1 = new JoystickButton(left, RobotMap.OI_ROCKET_LEVEL_1_ID);
  private JoystickButton cargoRocketLevel2 = new JoystickButton(left, RobotMap.OI_ROCKET_LEVEL_2_ID);
  private JoystickButton cargoRocketLevel3 = new JoystickButton(left, RobotMap.OI_ROCKET_LEVEL_3_ID);
  private JoystickButton cargoShip = new JoystickButton(left, RobotMap.OI_CARGO_SHIP_ID);
  private JoystickButton climb = new JoystickButton(left, RobotMap.OI_CLIMB_ID);

  private JoystickButton rocketOverride = new JoystickButton(left, RobotMap.OI_ROCKET_OVERRIDE_ID);
  private JoystickButton cargoOverride = new JoystickButton(left, RobotMap.OI_CARGO_SHIP_OVERRIDE_ID);
  private JoystickButton climbOverride = new JoystickButton(left, RobotMap.OI_CLIMB_OVERRIDE_ID);


  /** Three Variables: Cargo vs. Hatch ( bool ), 
   *  elevatorPosition (for rest of the buttons),
   *  Methods: intake, outtake: called to check the above variables, then start the appropriate method
   *  i.e: IF CARGO --> start a 'place cargo in ship command'  */


  // Buttons and their associated commands
  public OI() {
    intakeCargo.whenPressed(new IntakeCargo());
    // scoreCargo.whenPressed(new PlaceCargo());
    // elevator.whenPressed(new SetElevator(Elevator.Position.BaseHeight));
    // placeHatch.whenPressed(new PlaceHatch());
  }
}
