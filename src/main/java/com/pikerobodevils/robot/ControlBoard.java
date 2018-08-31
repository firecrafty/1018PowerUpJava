package com.pikerobodevils.robot;

import com.pikerobodevils.robot.commands.claw.EjectCubeManual;
import com.pikerobodevils.robot.commands.claw.IntakeCubeManual;
import com.pikerobodevils.robot.commands.claw.SetGripperCommand;
import com.pikerobodevils.robot.commands.elevator.ElevatorBumpDownCommand;
import com.pikerobodevils.robot.commands.elevator.ElevatorBumpUpCommand;
import com.pikerobodevils.robot.commands.superstructure.SuperstructurePrepForIntakeCommand;
import com.pikerobodevils.robot.commands.superstructure.SuperstructureSetScoreHeightCommand;
import com.pikerobodevils.robot.commands.superstructure.SuperstructureStowCommand;
import com.pikerobodevils.robot.commands.wrist.WristSetAngleCommand;
import com.pikerobodevils.robot.subsystems.Elevator;
import com.pikerobodevils.robot.subsystems.IntakeGripper;
import com.pikerobodevils.robot.subsystems.Wrist;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

public class ControlBoard {
    private static final int LEFT_STICK_PORT = 0;
    private static final int RIGHT_STICK_PORT = 1;
    private static final int BUTTON_PANEL_PORT = 2;

    private static final int SWITCH_BUTTON = 6;
    private static final int SWITCH_TWO_BUTTON = 1;
    private static final int SCALE_LOW_BUTTON = 4;
    private static final int SCALE_MID_BUTTON = 5;
    private static final int SCALE_HIGH_BUTTON = 2;
    private static final int SCALE_LOW_TWO_BUTTON = 3;
    private static final int SCALE_MID_TWO_BUTTON = 17;
    private static final int SCALE_HIGH_TWO_BUTTON = 18;
    private static final int EXCHANGE_IN_BUTTON = 12;
    private static final int EXCHANGE_OUT_BUTTON = 13;
    private static final int STOW_BUTTON = 16;
    private static final int ELEVATOR_BUMP_UP_BUTTON = 20;
    private static final int ELEVATOR_BUMP_DOWN_BUTTON = 19;
    private static final int WRIST_OUT_BUTTON = 15;
    private static final int WRIST_HALF_OUT_BUTTON = 14;
    private static final int WRIST_STOW_BUTTON = 7;
    private static final int ROLLERS_IN_BUTTON = 9;
    private static final int ROLLERS_OUT_BUTTON = 8;
    private static final int CLAW_OPEN_BUTTON = 10;
    private static final int CLAW_CLOSE_BUTTON = 11;

    private Joystick leftStick = new Joystick(LEFT_STICK_PORT);
    private Joystick rightStick = new Joystick(RIGHT_STICK_PORT);
    private Joystick buttonPanel = new Joystick(BUTTON_PANEL_PORT);

    private JoystickButton switchButton = new JoystickButton(buttonPanel, SWITCH_BUTTON);
    private JoystickButton switchTwoButton = new JoystickButton(buttonPanel, SWITCH_TWO_BUTTON);
    private JoystickButton scaleLowButton = new JoystickButton(buttonPanel, SCALE_LOW_BUTTON);
    private JoystickButton scaleMidButton = new JoystickButton(buttonPanel, SCALE_MID_BUTTON);
    private JoystickButton scaleHighButton = new JoystickButton(buttonPanel, SCALE_HIGH_BUTTON);
    private JoystickButton scaleLowTwoButton = new JoystickButton(buttonPanel, SCALE_LOW_TWO_BUTTON);
    private JoystickButton scaleMidTwoButton = new JoystickButton(buttonPanel, SCALE_MID_TWO_BUTTON);
    private JoystickButton scaleHighTwoButton = new JoystickButton(buttonPanel, SCALE_HIGH_TWO_BUTTON);
    private JoystickButton exchangeInButton = new JoystickButton(buttonPanel, EXCHANGE_IN_BUTTON);
    private JoystickButton exchangeOutButton = new JoystickButton(buttonPanel, EXCHANGE_OUT_BUTTON);
    private JoystickButton stowButton = new JoystickButton(buttonPanel, STOW_BUTTON);
    private JoystickButton elevatorBumpUpButton = new JoystickButton(buttonPanel, ELEVATOR_BUMP_UP_BUTTON);
    private JoystickButton elevatorBumpDownButton = new JoystickButton(buttonPanel, ELEVATOR_BUMP_DOWN_BUTTON);
    private JoystickButton wristOutButton = new JoystickButton(buttonPanel, WRIST_OUT_BUTTON);
    private JoystickButton wristHalfOutButton = new JoystickButton(buttonPanel, WRIST_HALF_OUT_BUTTON);
    private JoystickButton wristStowButton = new JoystickButton(buttonPanel, WRIST_STOW_BUTTON);
    private JoystickButton rollersInButton = new JoystickButton(buttonPanel, ROLLERS_IN_BUTTON);
    private JoystickButton rollersOutButton = new JoystickButton(buttonPanel, ROLLERS_OUT_BUTTON);
    private JoystickButton clawOpenButton = new JoystickButton(buttonPanel, CLAW_OPEN_BUTTON);
    private JoystickButton clawCloseButton = new JoystickButton(buttonPanel, CLAW_CLOSE_BUTTON);


    private ControlBoard() {
        registerCommands();
    }

    public double getThrottle() {
        return leftStick.getY();
    }

    public double getTurn() {
        return rightStick.getX();
    }

    private void registerCommands() {
        Command switchCommand = new SuperstructureSetScoreHeightCommand(Elevator.ElevatorSetpoint.SWITCH);
        switchButton.whenPressed(switchCommand);
        switchTwoButton.whenPressed(switchCommand);
        scaleLowButton.whenPressed(new SuperstructureSetScoreHeightCommand(Elevator.ElevatorSetpoint.SCALE_LOW));
        scaleMidButton.whenPressed(new SuperstructureSetScoreHeightCommand(Elevator.ElevatorSetpoint.SCALE_MID));
        scaleHighButton.whenPressed(new SuperstructureSetScoreHeightCommand(Elevator.ElevatorSetpoint.SCALE_HIGH));
        scaleLowTwoButton.whenPressed(new SuperstructureSetScoreHeightCommand(Elevator.ElevatorSetpoint.SCALE_LOW_TWO));
        scaleMidTwoButton.whenPressed(new SuperstructureSetScoreHeightCommand(Elevator.ElevatorSetpoint.SCALE_MID_TWO));
        scaleHighTwoButton.whenPressed(new SuperstructureSetScoreHeightCommand(Elevator.ElevatorSetpoint.SCALE_HIGH_TWO));
        exchangeInButton.whenPressed(new SuperstructurePrepForIntakeCommand());
        exchangeOutButton.whenPressed(new SuperstructureSetScoreHeightCommand(Elevator.ElevatorSetpoint.EXCHANGE_PORTAL));
        stowButton.whenPressed(new SuperstructureStowCommand());
        elevatorBumpUpButton.whenPressed(new ElevatorBumpUpCommand());
        elevatorBumpDownButton.whenPressed(new ElevatorBumpDownCommand());
        wristOutButton.whenPressed(new WristSetAngleCommand(Wrist.WristSetpoint.SCORE_INTAKE));
        wristHalfOutButton.whenPressed(new WristSetAngleCommand(Wrist.WristSetpoint.HALF_OUT));
        wristStowButton.whenPressed(new WristSetAngleCommand(Wrist.WristSetpoint.STOW));
        rollersInButton.whileHeld(new IntakeCubeManual());
        rollersOutButton.whileHeld(new EjectCubeManual());
        clawOpenButton.whenPressed(new SetGripperCommand(IntakeGripper.State.OPEN));
        clawCloseButton.whenPressed(new SetGripperCommand(IntakeGripper.State.CLOSE));
    }

    private static ControlBoard mInstance = new ControlBoard();

    public static ControlBoard getInstance() {
        return mInstance;
    }

}
