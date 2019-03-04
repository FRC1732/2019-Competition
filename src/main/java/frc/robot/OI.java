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
import frc.robot.commands.auto.AutoClimb;
import frc.robot.commands.auto.ScoreCargo;
import frc.robot.commands.auto.TurnToTarget;
import frc.robot.commands.input.PlaceRocketCargo;
import frc.robot.commands.input.PlaceShipCargo;
import frc.robot.commands.input.StartClimb;
import frc.robot.commands.input.overrides.CargoOverrideOff;
import frc.robot.commands.input.overrides.CargoOverrideOn;
import frc.robot.commands.input.overrides.ClimbOverrideOff;
import frc.robot.commands.input.overrides.ClimbOverrideOn;
import frc.robot.commands.input.overrides.RocketOverrideOff;
import frc.robot.commands.input.overrides.RocketOverrideOn;
import frc.robot.subsystems.Elevator;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  // Variable Declarations
  private boolean rocketManual = false;
  private boolean shipManual = false;
  private boolean climbManual = false;
  
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
  
  private JoystickButton cargoRocketLevel1 = new JoystickButton(operator1, RobotMap.OI_ROCKET_LEVEL_1_ID);
  private JoystickButton cargoRocketLevel2 = new JoystickButton(operator1, RobotMap.OI_ROCKET_LEVEL_2_ID);
  private JoystickButton cargoRocketLevel3 = new JoystickButton(operator1, RobotMap.OI_ROCKET_LEVEL_3_ID);
  private JoystickButton cargoShip = new JoystickButton(operator1, RobotMap.OI_CARGO_SHIP_ID);
  private JoystickButton climb = new JoystickButton(operator1, RobotMap.OI_CLIMB_ID);
  
  private JoystickButton rocketOverride = new JoystickButton(operator1, RobotMap.OI_ROCKET_OVERRIDE_ID);
  private JoystickButton cargoOverride = new JoystickButton(operator1, RobotMap.OI_CARGO_SHIP_OVERRIDE_ID);
  private JoystickButton climbOverride = new JoystickButton(operator1, RobotMap.OI_CLIMB_OVERRIDE_ID);
  
  // Left Joystick
  private JoystickButton intakeExtendIn = new JoystickButton(left, RobotMap.OI_INTAKE_EXTEND_HOLDER_IN_ID);
  private JoystickButton intakeExtendOut = new JoystickButton(left, RobotMap.OI_INTAKE_EXTEND_HOLDER_OUT_ID);
  private JoystickButton panelRetract = new JoystickButton(left, RobotMap.OI_PANEL_RETRACT_ID);
  private JoystickButton panelExtend = new JoystickButton(left, RobotMap.OI_PANEL_EXTEND_ID);
  
  // Right Joystick
  private JoystickButton visionAlignment = new JoystickButton(right, RobotMap.OI_VISION_ALIGNMENT_ID);
  private JoystickButton panelGrab = new JoystickButton(right, RobotMap.OI_PANEL_GRAB_ID);
  private JoystickButton panelRelease = new JoystickButton(right, RobotMap.OI_PANEL_RELEASE_ID);
  private JoystickButton intakeIn = new JoystickButton(right, RobotMap.OI_INTAKE_HOLDER_IN_ID);
  private JoystickButton intakeOut = new JoystickButton(right, RobotMap.OI_INTAKE_HOLDER_OUT_ID);
  
  // Buttons and their associated commands
  public OI() {
    cargoRocketLevel1.whenPressed(new PlaceRocketCargo(Elevator.Position.RocketLevel1Cargo));
    cargoRocketLevel2.whenPressed(new PlaceRocketCargo(Elevator.Position.RocketLevel2Cargo));
    cargoRocketLevel3.whenPressed(new PlaceRocketCargo(Elevator.Position.RocketLevel3Cargo));
    rocketOverride.whenPressed(new RocketOverrideOn());
    rocketOverride.whenReleased(new RocketOverrideOff());
    
    cargoShip.whenPressed(new PlaceShipCargo());
    cargoOverride.whenPressed(new CargoOverrideOn());
    cargoOverride.whenReleased(new CargoOverrideOff());
    
    climbOverride.whenPressed(new ClimbOverrideOn());
    climbOverride.whenReleased(new ClimbOverrideOff());
    
    panelExtend.whenPressed(new ExtendPanel());
    panelRetract.whenPressed(new RetractPanel());
    panelGrab.whenPressed(new CollectHatchPanel());
    panelRelease.whenPressed(new PlaceHatch());
    
    intakeExtendIn.whenActive(new IntakeCargo());
    intakeExtendOut.whenActive(new PlaceCargo());
    intakeIn.whenActive(new IntakeCargo()); // temporary command - change this later.
    intakeOut.whenActive(new PlaceCargo());
    
    climb.whenPressed(new StartClimb());
    
    visionAlignment.whenActive(new TurnToTarget()); // temporary command - probably need to change?
  }

  // public void setRocketManual(boolean manual) {
  //   rocketManual = manual;
  // }

  // public void setShipManual(boolean manual) {
  //   shipManual = manual;
  // }

  public void setClimbManual(boolean manual) {
    climbManual = manual;
  }
  
  public void placeRocketCargo(Elevator.Position pos) {
    if (rocketManual) {
      new SetElevator(pos).start();
    } else {
      new ScoreCargo(pos).start();
    }
  }
  
  public void placeShipCargo() {
    if (shipManual) {
      new SetElevator(Elevator.Position.CargoShipCargo).start();
    } else {
      new ScoreCargo(Elevator.Position.CargoShipCargo).start();
    }
  }
  
  public void climb() {
    if (climbManual) {
      // do either nothing or have some commands that prep something to make climbing
      // easier
    } else {
      new AutoClimb(); // placeholder
    }
  }
  
  // public void bindCargoShip(boolean isCargoShipManual) {
  // if (isCargoShipManual == true) {
  // cargoShip.whenPressed(new SetElevator(Elevator.Position.CargoShipCargo));
  // }
  // else {
  // cargoShip.whenPressed(new StoreCargoParam()); // placeholder, need a command
  // }
  // }
  
  // public void bindCargoRocket(boolean isRocketCargoManual) {
  // if (isRocketCargoManual == true) {
  // cargoRocketLevel1.whenPressed(new
  // SetElevator(Elevator.Position.RocketLevel1Cargo));
  // cargoRocketLevel2.whenPressed(new
  // SetElevator(Elevator.Position.RocketLevel2Cargo));
  // cargoRocketLevel3.whenPressed(new
  // SetElevator(Elevator.Position.RocketLevel3Cargo));
  // }
  // else {
  // cargoRocketLevel1.whenPressed(new AutoRocketLevel1()); // placeholder, need a
  // command
  // cargoRocketLevel2.whenPressed(new AutoRocketLevel2());
  // cargoRocketLevel3.whenPressed(new AutoRocketLevel3());
  // }
  // }
  
  // public void bindClimb(boolean isClimbManual) {
  // if (isClimbManual == true) {
  // climb.whenPressed(new ManualClimb()); // placeholder command
  // }
  // else {
  // climb.whenPressed(new AutoClimb());
  // }
  // }
}
