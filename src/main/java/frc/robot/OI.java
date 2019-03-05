/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.CollectHatchPanel;
import frc.robot.commands.ExtendPanel;
import frc.robot.commands.IntakeCargo;
import frc.robot.commands.PlaceCargo;
import frc.robot.commands.PlaceHatch;
import frc.robot.commands.RetractPanel;
import frc.robot.commands.SetElevator;
import frc.robot.commands.auto.TurnToTarget;
import frc.robot.subsystems.Elevator;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  // Variable Declarations
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
  
  private JoystickButton panelRocketLevel1 = new JoystickButton(operator1, RobotMap.OI_ROCKET_PANEL_LEVEL_1_ID);
  private JoystickButton panelRocketLevel2 = new JoystickButton(operator1, RobotMap.OI_ROCKET_PANEL_LEVEL_2_ID);
  private JoystickButton panelRocketLevel3 = new JoystickButton(operator1, RobotMap.OI_ROCKET_PANEL_LEVEL_3_ID);

  private JoystickButton cargoRocketLevel1 = new JoystickButton(operator2, RobotMap.OI_ROCKET_LEVEL_1_ID);
  private JoystickButton cargoRocketLevel2 = new JoystickButton(operator2, RobotMap.OI_ROCKET_LEVEL_2_ID);
  private JoystickButton cargoRocketLevel3 = new JoystickButton(operator2, RobotMap.OI_ROCKET_LEVEL_3_ID);

  private JoystickButton cargoShip = new JoystickButton(operator2, RobotMap.OI_CARGO_SHIP_ID);
  private JoystickButton cargoStation = new JoystickButton(operator2, RobotMap.OI_CARGO_STATION_ID);

  private JoystickButton climb1 = new JoystickButton(operator2, RobotMap.OI_CLIMB1_ID);
  private JoystickButton climb2 = new JoystickButton(operator2, RobotMap.OI_CLIMB2_ID);
  private JoystickButton climb3 = new JoystickButton(operator2, RobotMap.OI_CLIMB3_ID);
  
  private JoystickButton intakeExtendIn = new JoystickButton(left, RobotMap.OI_INTAKE_EXTEND_HOLDER_IN_ID);
  private JoystickButton intakeExtendOut = new JoystickButton(left, RobotMap.OI_INTAKE_EXTEND_HOLDER_OUT_ID);
  private JoystickButton panelRetract = new JoystickButton(left, RobotMap.OI_PANEL_RETRACT_ID);
  private JoystickButton panelExtend = new JoystickButton(left, RobotMap.OI_PANEL_EXTEND_ID);
  
  private JoystickButton visionAlignment = new JoystickButton(right, RobotMap.OI_VISION_ALIGNMENT_ID);
  private JoystickButton panelGrab = new JoystickButton(right, RobotMap.OI_PANEL_GRAB_ID);
  private JoystickButton panelRelease = new JoystickButton(right, RobotMap.OI_PANEL_RELEASE_ID);
  private JoystickButton intakeIn = new JoystickButton(right, RobotMap.OI_INTAKE_HOLDER_IN_ID);
  private JoystickButton intakeOut = new JoystickButton(right, RobotMap.OI_INTAKE_HOLDER_OUT_ID);
  
  // Buttons and their associated commands
  public OI() {
    panelRocketLevel1.whenPressed(new SetElevator(Elevator.Position.RocketLevel1Hatch));
    panelRocketLevel2.whenPressed(new SetElevator(Elevator.Position.RocketLevel2Hatch));
    panelRocketLevel3.whenPressed(new SetElevator(Elevator.Position.RocketLevel3Hatch));
    
    cargoRocketLevel1.whenPressed(new SetElevator(Elevator.Position.RocketLevel1Cargo));
    cargoRocketLevel2.whenPressed(new SetElevator(Elevator.Position.RocketLevel2Cargo));
    cargoRocketLevel3.whenPressed(new SetElevator(Elevator.Position.RocketLevel3Cargo));

    cargoShip.whenPressed(new SetElevator(Elevator.Position.CargoShipCargo));
    cargoStation.whenPressed(new SetElevator(Elevator.Position.HumanPlayerStation));
    
    panelExtend.whenPressed(new ExtendPanel());
    panelRetract.whenPressed(new RetractPanel());
    panelGrab.whenPressed(new CollectHatchPanel());
    panelRelease.whenPressed(new PlaceHatch());
    
    intakeExtendIn.whenActive(new IntakeCargo());
    intakeExtendOut.whenActive(new PlaceCargo());
    intakeIn.whenActive(new IntakeCargo());
    intakeOut.whenActive(new PlaceCargo());
    
    climb1.whenActive(new ClimbStage1());
    climb2.whenActive(new ClimbStage2());
    climb3.whenActive(new ClimbStage3());
    
    visionAlignment.whenActive(new TurnToTarget());
  }
}
