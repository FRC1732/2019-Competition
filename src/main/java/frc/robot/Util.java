package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Util {
    /**
     * Creates a TalonSRX object with the specified parameters, along with other
     * default settings
     * 
     * @param id
     *                     the CAN id of the talon
     * @param reversed
     *                     whether the motor should be reversed
     * @return the TalonSRX object constructed
     */
    public static TalonSRX createTalon(int id, boolean reversed) {
        TalonSRX motor = new TalonSRX(id);
        motor.setInverted(reversed);
        motor.setNeutralMode(NeutralMode.Brake);
        return motor;
    }
    
    /**
     * Creates a VictorSPX object with the specified parameters, along with other
     * default settings
     * 
     * @param id
     *                     the CAN id of the talon
     * @param reversed
     *                     whether the motor should be reversed
     * @return the VictorSPX object constructed
     */
    public static VictorSPX createVictor(int id, boolean reversed) {
        VictorSPX motor = new VictorSPX(id);
        motor.setInverted(reversed);
        motor.setNeutralMode(NeutralMode.Brake);
        return motor;
    }
}
