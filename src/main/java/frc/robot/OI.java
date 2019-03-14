/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.CloseFinger;
import frc.robot.commands.ExtendToggle;
import frc.robot.commands.FingerDown;
import frc.robot.commands.FingerUp;
import frc.robot.commands.IntakeCargo;
import frc.robot.commands.OpenFinger;
import frc.robot.commands.PlaceCargo;
import frc.robot.commands.PlaceHatch;
import frc.robot.commands.SetElevator;
import frc.robot.commands.SliderIn;
import frc.robot.commands.SliderOut;
import frc.robot.commands.SpitCargo;
import frc.robot.commands.auto.ClimbStage1;
import frc.robot.commands.auto.ClimbStage2;
import frc.robot.commands.auto.ClimbStage3;
import frc.robot.commands.auto.GrabPanel;
import frc.robot.commands.auto.StickPanel;
import frc.robot.commands.auto.TurnToTarget;
import frc.robot.subsystems.Elevator.Position;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  // Variable Declarations
  private Joystick left = new Joystick(RobotMap.OI_LEFT_ID);
  private Joystick right = new Joystick(RobotMap.OI_RIGHT_ID);
  public Joystick operator1 = new Joystick(RobotMap.OI_OPERATOR_1_ID);
  public Joystick operator2 = new Joystick(RobotMap.OI_OPERATOR_2_ID);

  public boolean hatchExtended = false;
  
  /**
   * Gets the left joystick's position, as a percent of fully pushed
   * 
   * @return the position, in the range of [-1, 1]
   */
  public double getLeftJoystick() {
    return -1 * left.getY() * left.getY() * Math.signum(left.getY());
  }
  
  /**
   * Gets the right joystick's position, as a percent of fully pushed
   * 
   * @return the position, in the range of [-1, 1]
   */
  public double getRightJoystick() {
    return -1 * right.getY() * right.getY() * Math.signum(right.getY());
  }
  
  // private JoystickButton cargoRocketLevel1 = new JoystickButton(operator2, RobotMap.OI_ROCKET_LEVEL_1_ID);
  // private JoystickButton cargoRocketLevel2 = new JoystickButton(operator2, RobotMap.OI_ROCKET_LEVEL_2_ID);
  // private JoystickButton cargoRocketLevel3 = new JoystickButton(operator2, RobotMap.OI_ROCKET_LEVEL_3_ID);

  // private JoystickButton panelRocketLevel1 = new JoystickButton(operator1, RobotMap.OI_ROCKET_PANEL_LEVEL_1_ID);
  // private JoystickButton panelRocketLevel2 = new JoystickButton(operator1, RobotMap.OI_ROCKET_PANEL_LEVEL_2_ID);
  // private JoystickButton panelRocketLevel3 = new JoystickButton(operator1, RobotMap.OI_ROCKET_PANEL_LEVEL_3_ID);
  
  // private JoystickButton cargoShip = new JoystickButton(operator2, RobotMap.OI_CARGO_SHIP_ID);
  // private JoystickButton cargoStation = new JoystickButton(operator2, RobotMap.OI_CARGO_STATION_ID);
  
  // private JoystickButton climb1 = new JoystickButton(operator2, RobotMap.OI_CLIMB1_ID);
  // private JoystickButton climb2 = new JoystickButton(operator2, RobotMap.OI_CLIMB2_ID);
  // private JoystickButton climb3 = new JoystickButton(operator2, RobotMap.OI_CLIMB3_ID);
  
  // private JoystickButton clawIn = new JoystickButton(left, RobotMap.OI_CLAW_IN_ID);
  // private JoystickButton clawOut = new JoystickButton(left, RobotMap.OI_CLAW_OUT_ID);
  // private JoystickButton clawGrab = new JoystickButton(left, RobotMap.OI_CLAW_GRAB_ID);
  // private JoystickButton clawRelease = new JoystickButton(right, RobotMap.OI_CLAW_RELEASE_ID);

  // private JoystickButton reverseIntake = new JoystickButton(right, RobotMap.OI_REVERSE_INTAKE_ID);
  // private JoystickButton placePanel = new JoystickButton(right, RobotMap.OI_PLACE_PANEL_ID);
  
  // private JoystickButton visionAlignment = new JoystickButton(right, RobotMap.OI_VISION_ALIGNMENT_ID);
  // private JoystickButton intakeCargo = new JoystickButton(left, RobotMap.OI_INTAKE_CARGO_ID);
  // private JoystickButton placeCargo = new JoystickButton(right, RobotMap.OI_PLACE_CARGO_ID);

  // private JoystickButton grabPanel = new JoystickButton(right,
  // RobotMap.OI_GRAB_PANEL_ID);
  // private JoystickButton placePanel = new JoystickButton(right,
  // RobotMap.OI_PLACE_PANEL_ID);
  
  private JoystickButton sliderInOut = new JoystickButton(operator1, 2);
  private JoystickButton fingerOpenClose = new JoystickButton(operator1, 2);
  public JoystickButton manual = new JoystickButton(operator2, 9);
  
  // Buttons and their associated commands
  @SuppressWarnings("resource")
  public OI() {
    new JoystickButton(left, 1).whenActive(new IntakeCargo());
    new JoystickButton(left, 2).whenPressed(new StickPanel());
    new JoystickButton(left, 3).whenPressed(new GrabPanel());
    new JoystickButton(left, 4).whenPressed(new FingerDown());
    new JoystickButton(left, 5).whenPressed(new FingerUp());

    new JoystickButton(right, 1).whenActive(new TurnToTarget());
    new JoystickButton(right, 2).whenActive(new PlaceCargo());
    new JoystickButton(right, 3).whenPressed(new PlaceHatch());
    new JoystickButton(right, 4).whenPressed(new ExtendToggle());
    new JoystickButton(right, 5).whenActive(new SpitCargo());

    sliderInOut.whenActive(new SliderIn());
    sliderInOut.whenInactive(new SliderOut());
    fingerOpenClose.whenActive(new OpenFinger());
    fingerOpenClose.whenInactive(new CloseFinger());
    new JoystickButton(operator1, 5).whenPressed(new SetElevator(Position.RocketLevel3Hatch));
    new JoystickButton(operator1, 6).whenPressed(new SetElevator(Position.RocketLevel2Hatch));
    new JoystickButton(operator1, 7).whenPressed(new SetElevator(Position.RocketLevel1Hatch));

    new JoystickButton(operator2, 1).whenPressed(new SetElevator(Position.HumanPlayerStation));
    new JoystickButton(operator2, 2).whenPressed(new SetElevator(Position.RocketLevel3Cargo));
    new JoystickButton(operator2, 3).whenPressed(new SetElevator(Position.RocketLevel2Cargo));
    new JoystickButton(operator2, 4).whenPressed(new SetElevator(Position.RocketLevel1Cargo));
    new JoystickButton(operator2, 5).whenPressed(new SetElevator(Position.CargoShipCargo));
    new JoystickButton(operator2, 6).whenActive(new ClimbStage3());
    new JoystickButton(operator2, 9); // manual override
    new JoystickButton(operator2, 7).whenActive(new ClimbStage2());
    new JoystickButton(operator2, 11).whenActive(new ClimbStage1());
  }
}
