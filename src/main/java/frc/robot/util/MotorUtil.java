package frc.robot.util;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * A class to configure Motor controllers with a defualt configuration
 */
public class MotorUtil {
  public static void printError(ErrorCode e) {
    if(e != ErrorCode.OK) {
      System.out.println(e.name());
    }
  }

  /**
   * Creates a TalonSRX object with the specified parameters, along with other
   * default settings
   * 
   * @param id
   *                   the CAN id of the talon
   * @param reversed
   *                   whether the motor should be reversed
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
   *                   the CAN id of the talon
   * @param reversed
   *                   whether the motor should be reversed
   * @return the VictorSPX object constructed
   */
  public static VictorSPX createVictor(int id, boolean reversed) {
    VictorSPX motor = new VictorSPX(id);
    motor.setInverted(reversed);
    motor.setNeutralMode(NeutralMode.Brake);
    return motor;
  }

  /**
   * Creates a TalonSRX object that is initialized by its absolute position.
   * 
   * @param id the CAN id of the talon
   * @param phase indicates whether to invert the phase of the sensor
   * @param inverted invert state to set
   * @return the TalonSRX object initialized
   */
  public static TalonSRX initAbsoluteTalon(int id, boolean phase, boolean inverted){
    int kTimeoutMs = 30;
    TalonSRX motor = new TalonSRX(id);
    motor.configFactoryDefault(kTimeoutMs);
    motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kTimeoutMs);
    motor.setSensorPhase(phase);
    motor.setInverted(inverted);
    motor.configNominalOutputForward(0, kTimeoutMs);
    motor.configNominalOutputReverse(0, kTimeoutMs);
    motor.configAllowableClosedloopError(0, 0, kTimeoutMs);
    motor.config_kF(0, 0);
    motor.config_kP(0, 0.2);
    motor.config_kI(0, 0);
    motor.config_kD(0, 0);
    int absolutePosition = motor.getSensorCollection().getPulseWidthPosition();
    absolutePosition &= 0xFFF;
    //if (phase ^ inverted) absolutePosition *= -1;
    motor.setSelectedSensorPosition(absolutePosition, 0, kTimeoutMs);
    motor.setNeutralMode(NeutralMode.Brake);
    return motor;
  }
}
