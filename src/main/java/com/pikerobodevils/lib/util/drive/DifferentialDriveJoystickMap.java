package com.pikerobodevils.lib.util.drive;

/**
 * @author Ryan Blue
 */
public class DifferentialDriveJoystickMap {
    public static final double kDefaultDeadband = 0.02;
    public static final double kDefaultMaxOutput = 1.0;

    protected double m_deadband = kDefaultDeadband;
    protected double m_maxOutput = kDefaultMaxOutput;

    public static final double kDefaultQuickStopThreshold = 0.2;
    public static final double kDefaultQuickStopAlpha = 0.1;

    private double m_quickStopThreshold = kDefaultQuickStopThreshold;
    private double m_quickStopAlpha = kDefaultQuickStopAlpha;
    private double m_quickStopAccumulator = 0.0;

    public void setDeadband(double deadband) {
        m_deadband = deadband;
    }

    public void setMaxOutput(double maxOutput) {
        m_maxOutput = maxOutput;
    }

    /**
     * Limit motor values to the -1.0 to +1.0 range.
     */
    protected double limit(double value) {
        if (value > 1.0) {
            return 1.0;
        }
        if (value < -1.0) {
            return -1.0;
        }
        return value;
    }

    /**
     * Returns 0.0 if the given value is within the specified range around zero. The remaining range
     * between the deadband and 1.0 is scaled from 0.0 to 1.0.
     *
     * @param value    value to clip
     * @param deadband range around zero
     */
    protected double applyDeadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            } else {
                return (value + deadband) / (1.0 - deadband);
            }
        } else {
            return 0.0;
        }
    }

    /**
     * Normalize all wheel speeds if the magnitude of any wheel is greater than 1.0.
     */
    protected void normalize(double[] wheelSpeeds) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);
        for (int i = 1; i < wheelSpeeds.length; i++) {
            double temp = Math.abs(wheelSpeeds[i]);
            if (maxMagnitude < temp) {
                maxMagnitude = temp;
            }
        }
        if (maxMagnitude > 1.0) {
            for (int i = 0; i < wheelSpeeds.length; i++) {
                wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
            }
        }
    }

    /**
     * Arcade drive method for differential drive platform. The calculated values will be squared to
     * decrease sensitivity at low speeds.
     *
     * @param xSpeed    The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
     * @param zRotation The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is
     *                  positive.
     */
    @SuppressWarnings("ParameterName")
    public DriveSignal arcadeDrive(double xSpeed, double zRotation) {
        return arcadeDrive(xSpeed, zRotation, false);
    }

    /**
     * Arcade drive method for differential drive platform.
     *
     * @param xSpeed        The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
     * @param zRotation     The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is
     *                      positive.
     * @param squaredInputs If set, decreases the input sensitivity at low speeds.
     */
    public DriveSignal arcadeDrive(double xSpeed, double zRotation, boolean squaredInputs) {
        xSpeed = limit(xSpeed);
        xSpeed = applyDeadband(xSpeed, m_deadband);

        zRotation = limit(zRotation);
        zRotation = applyDeadband(zRotation, m_deadband);

        // Square the inputs (while preserving the sign) to increase fine control
        // while permitting full power.
        if (squaredInputs) {
            xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);
            zRotation = Math.copySign(zRotation * zRotation, zRotation);
        }

        double leftMotorOutput;
        double rightMotorOutput;

        double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);

        if (xSpeed >= 0.0) {
            // First quadrant, else second quadrant
            if (zRotation >= 0.0) {
                leftMotorOutput = maxInput;
                rightMotorOutput = xSpeed - zRotation;
            } else {
                leftMotorOutput = xSpeed + zRotation;
                rightMotorOutput = maxInput;
            }
        } else {
            // Third quadrant, else fourth quadrant
            if (zRotation >= 0.0) {
                leftMotorOutput = xSpeed + zRotation;
                rightMotorOutput = maxInput;
            } else {
                leftMotorOutput = maxInput;
                rightMotorOutput = xSpeed - zRotation;
            }
        }
        leftMotorOutput = limit(leftMotorOutput) * m_maxOutput;
        rightMotorOutput = -limit(rightMotorOutput) * m_maxOutput;
        return new DriveSignal(leftMotorOutput, rightMotorOutput);
    }

    /**
     * Curvature drive method for differential drive platform.
     * <p>
     * <p>The rotation argument controls the curvature of the robot's path rather than its rate of
     * heading change. This makes the robot more controllable at high speeds. Also handles the
     * robot's quick turn functionality - "quick turn" overrides constant-curvature turning for
     * turn-in-place maneuvers.
     *
     * @param xSpeed      The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
     * @param zRotation   The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is
     *                    positive.
     * @param isQuickTurn If set, overrides constant-curvature turning for turn-in-place maneuvers.
     */
    public DriveSignal curvatureDrive(double xSpeed, double zRotation, boolean isQuickTurn) {

        xSpeed = limit(xSpeed);
        xSpeed = applyDeadband(xSpeed, m_deadband);

        zRotation = limit(zRotation);
        zRotation = applyDeadband(zRotation, m_deadband);

        double angularPower;
        boolean overPower;

        if (isQuickTurn) {
            if (Math.abs(xSpeed) < m_quickStopThreshold) {
                m_quickStopAccumulator = (1 - m_quickStopAlpha) * m_quickStopAccumulator
                        + m_quickStopAlpha * limit(zRotation) * 2;
            }
            overPower = true;
            angularPower = zRotation;
        } else {
            overPower = false;
            angularPower = Math.abs(xSpeed) * zRotation - m_quickStopAccumulator;

            if (m_quickStopAccumulator > 1) {
                m_quickStopAccumulator -= 1;
            } else if (m_quickStopAccumulator < -1) {
                m_quickStopAccumulator += 1;
            } else {
                m_quickStopAccumulator = 0.0;
            }
        }

        double leftMotorOutput = xSpeed + angularPower;
        double rightMotorOutput = xSpeed - angularPower;

        // If rotation is overpowered, reduce both outputs to within acceptable range
        if (overPower) {
            if (leftMotorOutput > 1.0) {
                rightMotorOutput -= leftMotorOutput - 1.0;
                leftMotorOutput = 1.0;
            } else if (rightMotorOutput > 1.0) {
                leftMotorOutput -= rightMotorOutput - 1.0;
                rightMotorOutput = 1.0;
            } else if (leftMotorOutput < -1.0) {
                rightMotorOutput -= leftMotorOutput + 1.0;
                leftMotorOutput = -1.0;
            } else if (rightMotorOutput < -1.0) {
                leftMotorOutput -= rightMotorOutput + 1.0;
                rightMotorOutput = -1.0;
            }
        }

        // Normalize the wheel speeds
        double maxMagnitude = Math.max(Math.abs(leftMotorOutput), Math.abs(rightMotorOutput));
        if (maxMagnitude > 1.0) {
            leftMotorOutput /= maxMagnitude;
            rightMotorOutput /= maxMagnitude;
        }

        leftMotorOutput = leftMotorOutput * m_maxOutput;
        rightMotorOutput = rightMotorOutput * m_maxOutput;

        return new DriveSignal(leftMotorOutput, rightMotorOutput);
    }

    /**
     * Tank drive method for differential drive platform. The calculated values will be squared to
     * decrease sensitivity at low speeds.
     *
     * @param leftSpeed  The robot's left side speed along the X axis [-1.0..1.0]. Forward is
     *                   positive.
     * @param rightSpeed The robot's right side speed along the X axis [-1.0..1.0]. Forward is
     *                   positive.
     */
    public DriveSignal tankDrive(double leftSpeed, double rightSpeed) {
        return tankDrive(leftSpeed, rightSpeed, false);
    }

    /**
     * Tank drive method for differential drive platform.
     *
     * @param leftSpeed     The robot left side's speed along the X axis [-1.0..1.0]. Forward is
     *                      positive.
     * @param rightSpeed    The robot right side's speed along the X axis [-1.0..1.0]. Forward is
     *                      positive.
     * @param squaredInputs If set, decreases the input sensitivity at low speeds.
     */
    public DriveSignal tankDrive(double leftSpeed, double rightSpeed, boolean squaredInputs) {

        leftSpeed = limit(leftSpeed);
        leftSpeed = applyDeadband(leftSpeed, m_deadband);

        rightSpeed = limit(rightSpeed);
        rightSpeed = applyDeadband(rightSpeed, m_deadband);

        // Square the inputs (while preserving the sign) to increase fine control
        // while permitting full power.
        if (squaredInputs) {
            leftSpeed = Math.copySign(leftSpeed * leftSpeed, leftSpeed);
            rightSpeed = Math.copySign(rightSpeed * rightSpeed, rightSpeed);
        }
        leftSpeed = leftSpeed * m_maxOutput;
        rightSpeed = rightSpeed * m_maxOutput;
        return new DriveSignal(leftSpeed, rightSpeed);
    }

    /**
     * Sets the QuickStop speed threshold in curvature drive.
     * <p>
     * <p>QuickStop compensates for the robot's moment of inertia when stopping after a QuickTurn.
     * <p>
     * <p>While QuickTurn is enabled, the QuickStop accumulator takes on the rotation rate value
     * outputted by the low-pass filter when the robot's speed along the X axis is below the
     * threshold. When QuickTurn is disabled, the accumulator's value is applied against the
     * computed angular power request to slow the robot's rotation.
     *
     * @param threshold X speed below which quick stop accumulator will receive rotation rate values
     *                  [0..1.0].
     */
    public void setQuickStopThreshold(double threshold) {
        m_quickStopThreshold = threshold;
    }

    /**
     * Sets the low-pass filter gain for QuickStop in curvature drive.
     * <p>
     * <p>The low-pass filter filters incoming rotation rate commands to smooth out high frequency
     * changes.
     *
     * @param alpha Low-pass filter gain [0.0..2.0]. Smaller values result in slower output changes.
     *              Values between 1.0 and 2.0 result in output oscillation. Values below 0.0 and
     *              above 2.0 are unstable.
     */
    public void setQuickStopAlpha(double alpha) {
        m_quickStopAlpha = alpha;
    }
}
