/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

/**
 * A class for writing better print statements
 */
public class Console {
    /**
     * A Level to log a message at
     */
    public static enum Level {
        None(0), Warn(1), Info(2), Detail(3), Debug(4), Graph(-1);
        public final int level;
        
        private Level(int num) {
            this.level = num;
        }
    }
    
    private static Level currentLevel = Level.Graph;
    
    /**
     * Sets the current output level - should only be done in Robot.java
     * 
     * @param level
     */
    public static void setLevel(Level level) {
        currentLevel = level;
    }
    
    /**
     * Warnings will be printed in red, and should display some kind of warning,
     * e.g. trying to take an action that the programming shouldn't allow, like
     * picking up a cargo when holding a hatch panel
     * 
     * @param printString
     */
    public static void warn(Object printString) {
        if (Level.Warn.level <= currentLevel.level) {
            System.err.println(printString);
        }
    }
    
    /**
     * An info print should provide info about what is running, e.g. CollectCargo
     * should print that it collected a cargo once is has, and any command that
     * takes control of the drivetrain should also print when done
     * 
     * @param printString
     */
    public static void info(Object printString) {
        if (Level.Info.level <= currentLevel.level) {
            System.out.println(printString);
        }
    }
    
    /**
     * A detail print should provide detailed information, e.g. a response when a
     * command has started, has taken control, or has taken other common actions
     * 
     * @param printString
     */
    public static void detail(Object printString) {
        if (Level.Detail.level <= currentLevel.level) {
            System.out.println(printString);
        }
    }
    
    /**
     * A debug print should never be needed on the FRC field, but should be used to
     * print debug information that needs to be tracked while tracking down issues
     * in the code
     * 
     * @param printString
     */
    public static void debug(Object printString) {
        if (Level.Debug.level <= currentLevel.level) {
            System.out.println(printString);
        }
    }
    
    /**
     * A convience method for printing information in a csv type format (a
     * spreadsheet format). When the Console is at Graph level, all other prints are
     * suspended to allow easier copying and pasting of logged output
     * 
     * @param objects
     *                    a complete row of the spreadsheet
     */
    public static void graph(Object... objects) {
        if (Level.Graph.level == currentLevel.level) {
            System.out.print(objects[0]);
            for (int i = 1; i < objects.length; i++) {
                System.out.print("\t");
                System.out.print(objects[i]);
            }
            System.out.println();
        }
    }
}
