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
import frc.robot.commands.input.CargoOverrideOff;
import frc.robot.commands.input.CargoOverrideOn;
import frc.robot.commands.input.ClimbOverrideOff;
import frc.robot.commands.input.ClimbOverrideOn;
import frc.robot.commands.input.RocketOverrideOff;
import frc.robot.commands.input.RocketOverrideOn;

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

  // Auto Routines - need to review button mappings since there are two different intake commands - hatch, cargo.
  private JoystickButton intakeCargo = new JoystickButton(left, RobotMap.OI_INTAKE_ID);
  private JoystickButton outtakeCargo = new JoystickButton(right, RobotMap.OI_OUTTAKE_ID);
  public JoystickButton cargoRocketLevel1 = new JoystickButton(left, RobotMap.OI_ROCKET_LEVEL_1_ID);
  public JoystickButton cargoRocketLevel2 = new JoystickButton(left, RobotMap.OI_ROCKET_LEVEL_2_ID);
  public JoystickButton cargoRocketLevel3 = new JoystickButton(left, RobotMap.OI_ROCKET_LEVEL_3_ID);
  public JoystickButton cargoShip = new JoystickButton(left, RobotMap.OI_CARGO_SHIP_ID);
  public JoystickButton climb = new JoystickButton(left, RobotMap.OI_CLIMB_ID);

  // Manual Routines
  private JoystickButton rocketOverride = new JoystickButton(left, RobotMap.OI_ROCKET_OVERRIDE_ID);
  private JoystickButton cargoOverride = new JoystickButton(left, RobotMap.OI_CARGO_SHIP_OVERRIDE_ID);
  private JoystickButton climbOverride = new JoystickButton(left, RobotMap.OI_CLIMB_OVERRIDE_ID);

  // Buttons and their associated commands
  public OI() {
    intakeCargo.whenPressed(new IntakeCargo());
    outtakeCargo.whenPressed(new OuttakeCargo()); // placeholder, need a command

    // The OI assigns these buttons by default.
    cargoRocketLevel1.whenPressed(new AutoRocketLevel1()); // placeholder, need a command
    cargoRocketLevel2.whenPressed(new AutoRocketLevel2());
    cargoRocketLevel3.whenPressed(new AutoRocketLevel3());

    cargoShip.whenPressed(new AutoCargo()); // placeholder, need a command
    climb.whenPressed(new AutoClimb());

    // The rocket, cargoship, and climb buttons are overridden by these commands. When the switch is turned off, the commands revert to the above.
    rocketOverride.whenPressed(new RocketOverrideOn());
    rocketOverride.whenReleased(new RocketOverrideOff());

    cargoOverride.whenPressed(new CargoOverrideOn());
    cargoOverride.whenReleased(new CargoOverrideOff());

    climbOverride.whenPressed(new ClimbOverrideOn());
    climbOverride.whenReleased(new ClimbOverrideOff());
  }
}
