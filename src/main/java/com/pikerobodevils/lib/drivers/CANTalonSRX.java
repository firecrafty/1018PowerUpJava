package com.pikerobodevils.lib.drivers;

import com.ctre.phoenix.motorcontrol.ControlFrame;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * @author Ryan Blue
 */
public class CANTalonSRX extends TalonSRX implements SpeedController {
    private double mLastSetValue = Double.NaN;

    public CANTalonSRX(int deviceNumber) {
        super(deviceNumber);
        set(ControlMode.Disabled, 0);
    }

    public static CANTalonSRX fromConfiguration(int id, Configuration config) {
        CANTalonSRX talon = new CANTalonSRX(id);
        talon.applyConfiguration(config);
        return talon;
    }

    public static CANTalonSRX newDefaultTalon(int id) {
        return fromConfiguration(id, kDefaultConfiguration);
    }

    public static CANTalonSRX newPermanentSlaveTalon(int id, IMotorController master) {
        CANTalonSRX talon = fromConfiguration(id, kSlaveConfiguration);
        talon.follow(master);
        return talon;
    }

    public static class Configuration {
        public Configuration() {
        }

        public boolean invert = false;
        public double openLoopRampRate = 0;
        public double closedLoopRampRate = 0;
        public double peakOutputForward = 1;
        public double peakOutputReverse = -1;
        public double nominalOutputForward = 0;
        public double nominalOutputReverse = 0;
        public double neutralDeadband = 0.04;
        public double voltageCompSaturation = 0;
        public boolean enableVoltageComp = false;
        public int voltageMeasurementFilterWindow = 32;
        public FeedbackDevice selectedSensorSlotZero = FeedbackDevice.QuadEncoder;
        public FeedbackDevice selectedSensorSlotOne = FeedbackDevice.QuadEncoder;
        public double selectedFeedbackCoefficientSlotZero = 1;
        public double selectedFeedbackCoefficientSlotOne = 1;
        public FeedbackDevice sensorTermDiffZero = FeedbackDevice.QuadEncoder;
        public FeedbackDevice sensorTermDiffOne = FeedbackDevice.QuadEncoder;
        public FeedbackDevice sensorTermSumZero = FeedbackDevice.QuadEncoder;
        public FeedbackDevice sensorTermSumOne = FeedbackDevice.QuadEncoder;
        public VelocityMeasPeriod velocityMeasurementPeriod = VelocityMeasPeriod.Period_100Ms;
        public int velocityWindowSize = 64;
        public LimitSwitchSource forwardLimitSwitchSource = LimitSwitchSource.FeedbackConnector;
        public LimitSwitchSource reverseLimitSwitchSource = LimitSwitchSource.FeedbackConnector;
        public LimitSwitchNormal forwardLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
        public LimitSwitchNormal reverseLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
        public int forwardSoftLimitThreshold = 0;
        public int reverseSoftLimitThreshold = 0;
        public boolean forwardSoftLimitEnable = false;
        public boolean reverseSoftLimitEnable = false;
        public PIDSlotValues pidSlotZero = new PIDSlotValues();
        public PIDSlotValues pidSlotOne = new PIDSlotValues();
        public PIDSlotValues pidSlotTwo = new PIDSlotValues();
        public PIDSlotValues pidSlotThree = new PIDSlotValues();
        public boolean auxPIDPolarity = false;
        public int motionCruiseVelocity = 0;
        public int motionAcceleration = 0;
        public int motionProfileTrajectoryPeriod = 0;
        public int peakCurrentLimit = 0;
        public int peakCurrentDuration = 0;
        public int continuousCurrentLimit = 0;
        public boolean enableCurrentLimit = false;

        public NeutralMode neutralMode = NeutralMode.EEPROMSetting;

        public int mainControlFramePeriodMs = 10;
        public int motionControlFramePeriodMs = 20;
        public int generalStatusFrameRateMs = 10;
        public int feedbackStatusFrameRateMs = 20;
        public int quadEncoderStatusFrameRateMs = 160;
        public int analogTempVbatStatusFrameRateMs = 160;
        public int pulseWidthStatusFrameRateMs = 160;
        public int targetsStatusFrameRateMs = 160;
        public int pidZeroStatusFrameRateMs = 160;
    }

    public static class PIDSlotValues {
        public double kP = 0;
        public double kI = 0;
        public double kD = 0;
        public double kF = 0;
        public int integralZone = 0;
        public double maxIntegralAccumulator = 0;
        public double closedLoopPeakOutput = 1;
        public int closedLoopPeriodMs = 1;
    }

    public static Configuration kDefaultConfiguration = new Configuration();
    public static Configuration kSlaveConfiguration = new Configuration();

    static {
        kSlaveConfiguration.generalStatusFrameRateMs = 1000;
        kSlaveConfiguration.feedbackStatusFrameRateMs = 1000;
        kSlaveConfiguration.quadEncoderStatusFrameRateMs = 1000;
        kSlaveConfiguration.analogTempVbatStatusFrameRateMs = 1000;
        kSlaveConfiguration.pulseWidthStatusFrameRateMs = 1000;
        kSlaveConfiguration.targetsStatusFrameRateMs = 1000;
    }

    public void applyConfiguration(Configuration config) {
        setInverted(config.invert);
        configOpenloopRamp(config.openLoopRampRate, 0);
        configClosedloopRamp(config.closedLoopRampRate, 0);
        configPeakOutputForward(config.peakOutputForward, 0);
        configPeakOutputReverse(config.peakOutputReverse, 0);
        configNominalOutputForward(config.nominalOutputForward, 0);
        configNominalOutputReverse(config.nominalOutputReverse, 0);
        configNeutralDeadband(config.neutralDeadband, 0);
        configVoltageCompSaturation(config.voltageCompSaturation, 0);
        enableVoltageCompensation(config.enableVoltageComp);
        configVoltageMeasurementFilter(config.voltageMeasurementFilterWindow, 0);
        configSelectedFeedbackSensor(config.selectedSensorSlotZero, 0, 0);
        configSelectedFeedbackSensor(config.selectedSensorSlotOne, 1, 0);
        configSelectedFeedbackCoefficient(config.selectedFeedbackCoefficientSlotZero, 0, 0);
        configSelectedFeedbackCoefficient(config.selectedFeedbackCoefficientSlotOne, 1, 0);
        configSensorTerm(SensorTerm.Diff0, config.sensorTermDiffZero, 0);
        configSensorTerm(SensorTerm.Diff1, config.sensorTermDiffOne, 0);
        configSensorTerm(SensorTerm.Sum0, config.sensorTermSumZero, 0);
        configSensorTerm(SensorTerm.Sum1, config.sensorTermSumOne, 0);
        configVelocityMeasurementPeriod(config.velocityMeasurementPeriod, 0);
        configVelocityMeasurementWindow(config.velocityWindowSize, 0);
        configForwardLimitSwitchSource(config.forwardLimitSwitchSource, config.forwardLimitSwitchNormal, 0);
        configReverseLimitSwitchSource(config.reverseLimitSwitchSource, config.reverseLimitSwitchNormal, 0);
        configForwardSoftLimitThreshold(config.forwardSoftLimitThreshold, 0);
        configReverseSoftLimitThreshold(config.reverseSoftLimitThreshold, 0);
        configForwardSoftLimitEnable(config.forwardSoftLimitEnable, 0);
        configReverseSoftLimitEnable(config.reverseSoftLimitEnable, 0);
        configPIDSlot(0, config.pidSlotZero);
        configPIDSlot(1, config.pidSlotOne);
        configPIDSlot(2, config.pidSlotTwo);
        configPIDSlot(3, config.pidSlotThree);
        configAuxPIDPolarity(config.auxPIDPolarity, 0);
        configMotionCruiseVelocity(config.motionCruiseVelocity, 0);
        configMotionAcceleration(config.motionAcceleration, 0);
        configMotionProfileTrajectoryPeriod(config.motionProfileTrajectoryPeriod, 0);
        configPeakCurrentLimit(config.peakCurrentLimit, 0);
        configPeakCurrentDuration(config.peakCurrentDuration, 0);
        configContinuousCurrentLimit(config.continuousCurrentLimit, 0);
        enableCurrentLimit(config.enableCurrentLimit);

        setNeutralMode(config.neutralMode);

        changeMotionControlFramePeriod(config.motionControlFramePeriodMs);
        setControlFramePeriod(ControlFrame.Control_3_General, config.mainControlFramePeriodMs);
        setControlFramePeriod(ControlFrame.Control_6_MotProfAddTrajPoint, config.motionControlFramePeriodMs);
        setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, config.generalStatusFrameRateMs, 0);
        setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, config.feedbackStatusFrameRateMs, 0);
        setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, config.quadEncoderStatusFrameRateMs, 0);
        setStatusFramePeriod(StatusFrameEnhanced.Status_4_AinTempVbat, config.analogTempVbatStatusFrameRateMs, 0);
        setStatusFramePeriod(StatusFrameEnhanced.Status_8_PulseWidth, config.pulseWidthStatusFrameRateMs, 0);
        setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, config.targetsStatusFrameRateMs, 0);
        setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, config.pidZeroStatusFrameRateMs, 0);
    }

    public void configPIDSlot(int slotIdx, PIDSlotValues values) {
        config_kP(slotIdx, values.kP, 0);
        config_kI(slotIdx, values.kI, 0);
        config_kD(slotIdx, values.kD, 0);
        config_kF(slotIdx, values.kF, 0);
        config_IntegralZone(slotIdx, values.integralZone, 0);
        configMaxIntegralAccumulator(slotIdx, values.maxIntegralAccumulator, 0);
        configClosedLoopPeakOutput(slotIdx, values.closedLoopPeakOutput, 0);
        configClosedLoopPeriod(slotIdx, values.closedLoopPeriodMs, 0);
    }

    @Override
    public void set(ControlMode mode, double outputValue) {
        if (outputValue != mLastSetValue || mode != getControlMode()) {
            super.set(mode, outputValue);
        }
    }

    @Override
    public void set(double speed) {
        set(ControlMode.PercentOutput, speed);
    }

    @Override
    public double get() {
        return getMotorOutputPercent();
    }

    @Override
    public void disable() {
        stopMotor();
    }

    @Override
    public void stopMotor() {
        set(ControlMode.Disabled, 0);
    }

    @Override
    public void pidWrite(double output) {
        System.out.println("why would you do this");
    }
}
