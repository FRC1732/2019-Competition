/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto.Climb;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoClimb extends CommandGroup {
  /**
   * Performs a climb
   */
  public AutoClimb() {
    addSequential(new LowerJacks(), 4);
    addSequential(new JackDrive(), 2);
    addSequential(new RaiseFrontJacks(), 3);
    addSequential(new DoubleDrive(), 1);
    addSequential(new RaiseBackjack(), 3);
  }
}
