/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.commands.auto.GrabPanel;
import frc.robot.commands.auto.ScorePanel;
import frc.robot.commands.auto.StickPanel;
import frc.robot.commands.auto.TurnToTarget;
import frc.robot.commands.cargo.ExtendIntakeTime;
import frc.robot.commands.cargo.IntakeCargo;
import frc.robot.commands.cargo.IntakeCargoHigh;
import frc.robot.commands.cargo.PlaceCargo;
import frc.robot.commands.cargo.SpitCargo;
import frc.robot.commands.climb.AutoClimbHigh;
import frc.robot.commands.climb.AutoClimbLow;
import frc.robot.commands.climb.AutoClimbLowToHigh;
import frc.robot.commands.climb.LowerJacksHigh;
import frc.robot.commands.climb.LowerJacksLow;
import frc.robot.commands.elevator.HomeElevator;
import frc.robot.commands.elevator.ManualElevator;
import frc.robot.commands.elevator.SetElevator;
import frc.robot.commands.hatch.CloseFinger;
import frc.robot.commands.hatch.OpenFinger;
import frc.robot.commands.hatch.SliderIn;
import frc.robot.commands.hatch.SliderOut;
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
  public boolean manualMode = false;
  
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
  
  private JoystickButton sliderInOut = new JoystickButton(operator1, 1);
  private JoystickButton fingerOpenClose = new JoystickButton(operator1, 2);
  public JoystickButton manual = new JoystickButton(operator2, 9);
  
  // Buttons and their associated commands
  @SuppressWarnings("resource")
  public OI() {
    new JoystickButton(left, 1).whileActive(new IntakeCargo());
    new JoystickButton(left, 1).whenReleased(new ExtendIntakeTime());
    new JoystickButton(left, 2).whenPressed(new StickPanel());
    new JoystickButton(left, 3).whenPressed(new GrabPanel());
    new JoystickButton(left, 4).whileActive(new IntakeCargoHigh());
    new JoystickButton(left, 5);

    new JoystickButton(right, 1).whileActive(new TurnToTarget());
    new JoystickButton(right, 2).whileActive(new PlaceCargo());
    new JoystickButton(right, 3).whenPressed(new ScorePanel());
    new JoystickButton(right, 4);
    new JoystickButton(right, 5).whileActive(new SpitCargo());

    sliderInOut.whenActive(new SliderIn());
    sliderInOut.whenInactive(new SliderOut());
    fingerOpenClose.whenActive(new CloseFinger());
    fingerOpenClose.whenInactive(new OpenFinger());
    new JoystickButton(operator1, 5).whenPressed(new SetElevator(Position.RocketLevel3Hatch));
    new JoystickButton(operator1, 6).whenPressed(new SetElevator(Position.RocketLevel2Hatch));
    new JoystickButton(operator1, 7).whenPressed(new HomeElevator());

    new JoystickButton(operator2, 1).whenPressed(new SetElevator(Position.HumanPlayerStation));
    new JoystickButton(operator2, 2).whenPressed(new SetElevator(Position.RocketLevel3Cargo));
    new JoystickButton(operator2, 3).whenPressed(new SetElevator(Position.RocketLevel2Cargo));
    new JoystickButton(operator2, 4).whenPressed(new SetElevator(Position.RocketLevel1Cargo));
    new JoystickButton(operator2, 5).whenPressed(new SetElevator(Position.CargoShipCargo));
    new JoystickButton(operator2, 6).whenActive(new AutoClimbLow());// Climb 3
    new JoystickButton(operator2, 9); // manual override
    new JoystickButton(operator2, 7).whenActive(new AutoClimbLowToHigh());// Climb 2
    new JoystickButton(operator2, 11).whenActive(new AutoClimbHigh());// Climb 1

    new Notifier(this::run).startPeriodic(0.2);
  }
  private Command minusY = new ManualElevator(true);
  private Command plusY = new ManualElevator(false);
  private void run() {
    if(operator2.getY() > 0.9) {
      plusY.start();
      minusY.cancel();
    }else if(operator2.getY() < -0.9) {
      minusY.start();
      plusY.cancel();
    }else {
      plusY.cancel();
      minusY.cancel();
    }
  }

  public boolean getManual() {
    return manualMode;
  }

  public void setManual(boolean cond) {
    manualMode = cond;
  }
  
}
