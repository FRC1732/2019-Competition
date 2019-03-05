/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.commands.CollectHatchPanel;
import frc.robot.commands.ExtendPanel;
import frc.robot.commands.IntakeCargo;
import frc.robot.commands.PlaceCargo;
import frc.robot.commands.PlaceHatch;
import frc.robot.commands.RetractPanel;
import frc.robot.commands.SetElevator;
import frc.robot.commands.SpitCargo;
import frc.robot.commands.auto.ClimbStage1;
import frc.robot.commands.auto.ClimbStage2;
import frc.robot.commands.auto.ClimbStage3;
import frc.robot.commands.auto.GrabPanel;
import frc.robot.commands.auto.ScorePanel;
import frc.robot.commands.auto.TurnToTarget;
import frc.robot.subsystems.Elevator.Position;

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
    return -Math.signum(left.getY()) * left.getY() * left.getY();
  }
  
  /**
   * Gets the right joystick's position, as a percent of fully pushed
   * 
   * @return the position, in the range of [-1, 1]
   */
  public double getRightJoystick() {
    return -Math.signum(right.getY()) * right.getY() * right.getY();
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
    // cargoRocketLevel1.whenPressed(new
    // PlaceRocketCargo(Elevator.Position.RocketLevel1Cargo));
    // cargoRocketLevel2.whenPressed(new
    // PlaceRocketCargo(Elevator.Position.RocketLevel2Cargo));
    // cargoRocketLevel3.whenPressed(new
    // PlaceRocketCargo(Elevator.Position.RocketLevel3Cargo));
    // rocketOverride.whenPressed(new RocketOverrideOn());
    // rocketOverride.whenReleased(new RocketOverrideOff());
    
    // cargoShip.whenPressed(new PlaceShipCargo());
    // cargoOverride.whenPressed(new CargoOverrideOn());
    // cargoOverride.whenReleased(new CargoOverrideOff());
    
    // climbOverride.whenPressed(new ClimbOverrideOn());
    // climbOverride.whenReleased(new ClimbOverrideOff());
    
    panelExtend.whenPressed(new ExtendPanel());
    panelRetract.whenPressed(new RetractPanel());
    panelGrab.whenPressed(new CollectHatchPanel());
    panelRelease.whenPressed(new PlaceHatch());
    
    intakeExtendIn.whileActive(new IntakeCargo());
    intakeExtendOut.whileActive(new PlaceCargo());
    intakeIn.whileActive(new ScorePanel()); // temporary command - change this later.
    intakeOut.whileActive(new GrabPanel());
    
    visionAlignment.whileActive(new TurnToTarget());
    
    new JoystickButton(left, 5).whileActive(new SpitCargo());
    
    new JoystickButton(operator1, 5).whileActive(new SetElevator(Position.RocketLevel1Hatch));
    new JoystickButton(operator1, 4).whileActive(new SetElevator(Position.RocketLevel2Hatch));
    new JoystickButton(operator1, 3).whileActive(new SetElevator(Position.RocketLevel3Hatch));
    new JoystickButton(operator2, 4).whileActive(new SetElevator(Position.RocketLevel1Cargo));
    new JoystickButton(operator2, 3).whileActive(new SetElevator(Position.RocketLevel2Cargo));
    new JoystickButton(operator2, 2).whileActive(new SetElevator(Position.CargoShipCargo));
    new JoystickButton(operator1, 6).whileActive(new InstantCommand() {
      @Override
      protected void initialize() {
        Robot.elevator.increment();
      }
    });
    new JoystickButton(operator1, 7).whileActive(new InstantCommand() {
      @Override
      protected void initialize() {
        Robot.elevator.decrement();
      }
    });
    climb1.whileActive(new ClimbStage1());
    climb1.whenInactive(new ClimbStage1(true));
    climb2.whileActive(new ClimbStage2());
    climb3.whileActive(new ClimbStage3());
  }
  
  public JoystickButton climb1 = new JoystickButton(operator2, 11);
  public JoystickButton climb2 = new JoystickButton(operator2, 7);
  public JoystickButton climb3 = new JoystickButton(operator2, 6);
  
  public void setRocketManual(boolean manual) {
  }
  
  public void setShipManual(boolean manual) {
  }
  
  public void setClimbManual(boolean manual) {
  }
}
