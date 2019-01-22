# 2019-Competition

A repository for FRC Team 1732's offical 2019 code, for Destination Deep Space

## Repository

Before editing files in this repository, read [CONTRIBUTING.md](CONTRIBUTING.md).

## Code

The code herein follows the WPILIB Command Robot pattern, and uses a single code formatting. Please follow these patterns, and practicies when creating commits, otherwise they will not be accepted into the main branch.

### Formatting

The offical formatting is defined in eclipse-formatter.xml. Just use `Shift-Alt-F` or `Format Document` from the command palette to automatically format an open .java file.

## Specific Files

* Main.java should not be modified in any way. If Main.java is modified, the code may not run when deployed
* build.gradle is the gradle build script. Do not modify
* settings.gradle sets settings for the gradle build system. Do not modify
* .gitignore controls what files are pushed and pulled using git. Do not modify
* eclipse-formatter.xml define the defualt style for java code. Do not modify

### Constants

Constants can be found in RobotMap.java. The RobotMap is intended to provide constants in a single place, e.g. Can ids, and other identifying information.

### Subsystems

Subsystems interface with either hardware, either through the wpilib library, or other similar methods. Another way to think of subsystems it that they store and change the physical state of the robot.

Subsystems may also just read from sensors. In these cases, the subsystem is just converting the signals read in from sensors into a more useful format (e.g. encoder ticks to inches).

### Commands

Commands read data from subsystems, and use logic and math to decide on a target state for the subsystem. 

### Operator Input

OI.java has all of the joystick and button inputs. Commands shouldn't use or access Joystick objects directly, but instead just call the appropriate method in OI.java.

### Util

The util package has several useful classes:
* MotorUtil, which configures and applies a bunch of defualt settings on motor controllers, for subsystems.
* Console, which provides better printing methods, e.g. logging w/ levels instead of just always printing everything.
* MathUtil, which has several Mathematical functions to make code more readable.

