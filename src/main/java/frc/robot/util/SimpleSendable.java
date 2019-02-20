/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * A class for creating arbitrary sendables with just an initSendable method
 */
public class SimpleSendable implements Sendable {
    private String name = null;
    private String subsystem = null;
    private Consumer<SendableBuilder> init;
    
    /**
     * Creates a sendable with just an initSendable a method
     * 
     * @param init
     */
    public SimpleSendable(Consumer<SendableBuilder> init) {
        this.init = init;
    }
    
    /**
     * Creates a named sendable
     * 
     * @param name
     * @param init
     */
    public SimpleSendable(String name, Consumer<SendableBuilder> init) {
        this.init = init;
        this.name = name;
    }
    
    /**
     * Creates a named sendable associated with a subsystem
     * 
     * @param name
     * @param subsystem
     * @param init
     */
    public SimpleSendable(String name, String subsystem, Consumer<SendableBuilder> init) {
        this.init = init;
        this.name = name;
        this.subsystem = subsystem;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getSubsystem() {
        return subsystem;
    }
    
    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }
    
    @Override
    public void initSendable(SendableBuilder builder) {
        init.accept(builder);
    }
}
