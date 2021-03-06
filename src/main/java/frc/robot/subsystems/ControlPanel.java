// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.subsystems;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import com.revrobotics.ColorSensorV3;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

/**
 *
 */
public class ControlPanel extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
private WPI_TalonSRX controlPanelDrive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public ControlPanel() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
controlPanelDrive = new WPI_TalonSRX(6);


        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
        Color detectedColor = m_colorSensor.getColor();
        double IR = m_colorSensor.getIR();
        
        SmartDashboard.putNumber("Control Panel sensor Red", detectedColor.red);
        SmartDashboard.putNumber("Control Panel sensor Green", detectedColor.green);
        SmartDashboard.putNumber("Control Panel sensor Blue", detectedColor.blue);
        SmartDashboard.putNumber("Control Panel sensor IR", IR);

        int proximity = m_colorSensor.getProximity();
        SmartDashboard.putNumber("Control Panel sensor Proximity", proximity);
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void rotateControlPanel(double speed) {
        controlPanelDrive.set(speed);
    }

    public Color getColor() {
        return m_colorSensor.getColor();
    }

    public boolean isRed(Color color) {
        if (color.red >= .4) {
            return true;
        } else {
            return false;
        }

    }
    public boolean isYellow(Color color) {
        if (color.green >= .52 && color.red >= .3) {
            return true;
        } else {
            return false;
        }

    }
    public boolean isBlue(Color color) {
        if (color.blue >= .4) {
            return true;
        } else {
            return false;
        }

    }
    public boolean isGreen(Color color) {
        if (color.green >= .53 && color.red <= .2) {
            return true;
        } else {
            return false;
        }

    }

    public void stopRotation() {
        rotateControlPanel(0);
    }

}
