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

import frc.robot.Limelight;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class Shooter extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
private WPI_TalonSRX shooterDriveTop;
private WPI_TalonSRX shooterDriveBottom;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // TODO: Tune PIDF's on actual robot to meet desired speed
    private final static double SPEED_P_CONSTANT_TOP = 0.001; //0.0035
    private final static double SPEED_I_CONSTANT_TOP = 0.000;//-5.0;
    private final static double SPEED_D_CONSTANT_TOP = 0.0;
    private final static double SPEED_F_CONSTANT_TOP = -0.00;

    private final static double SPEED_P_CONSTANT_BOTTOM = 0.001;//.005;//5.0;
    private final static double SPEED_I_CONSTANT_BOTTOM = 0.000;//0.0025;
    private final static double SPEED_D_CONSTANT_BOTTOM = 0.0;
    private final static double SPEED_F_CONSTANT_BOTTOM = 0.00;

    private final static double speedP_top = SPEED_P_CONSTANT_TOP;
    private final static double speedI_top = SPEED_I_CONSTANT_TOP;
    private final static double speedD_top = SPEED_D_CONSTANT_TOP;
    private final static double speedF_top = SPEED_F_CONSTANT_TOP;

    private final static double speedP_bottom = SPEED_P_CONSTANT_BOTTOM;
    private final static double speedI_bottom = SPEED_I_CONSTANT_BOTTOM;
    private final static double speedD_bottom = SPEED_D_CONSTANT_BOTTOM;
    private final static double speedF_bottom = SPEED_F_CONSTANT_BOTTOM;

    private final static double angleOfCameraRadians = 0.20200; // (must be in radians)
    private final static double angleBallLeavesRadians = 0.733; //Fake value ~45 degrees (must be in radians)
    private final static double heightOfCameraFeet = 1.208; 
    private final static double heightOfGoalFeet = 7.010416666666666667; 
    private final static double deltaHeightFeet = (heightOfGoalFeet - heightOfCameraFeet);
    private final static double g = 9.81;

    private final static int PID_SLOT_SPEED_MODE = 0;
    private final int TIMEOUT_MS = 10;

    private static final int MAX_TICKS_PER_SEC_TOP = 120000;
    private static final int MAX_TICKS_PER_SEC_BOTTOM = 120000;

    public Shooter() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        shooterDriveTop = new WPI_TalonSRX(9);
        shooterDriveBottom = new WPI_TalonSRX(8);
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    shooterDriveTop.setNeutralMode(NeutralMode.Coast);
    shooterDriveBottom.setNeutralMode(NeutralMode.Coast);

    shooterDriveTop.setInverted(false);
        shooterDriveTop.configSelectedFeedbackCoefficient(1);
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new Shooting());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());

    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
        SmartDashboard.putNumber("Shooter Top Motor Velocity SetPoint",shooterDriveTop.getClosedLoopTarget());
        SmartDashboard.putNumber("Shooter Top Motor Encoder Values Of Shooter",shooterDriveTop.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Shooter Top get", shooterDriveTop.get());
        SmartDashboard.putNumber("Shooter Top Error", shooterDriveTop.getClosedLoopError());
        SmartDashboard.putNumber("Shooter Bottom Motor Velocity SetPoint",shooterDriveBottom.getClosedLoopTarget());
        SmartDashboard.putNumber("Shooter Bottom Motor Encoder Values Of Shooter",shooterDriveBottom.getSelectedSensorVelocity());
        SmartDashboard.putNumber("shooter bottom sensor position",shooterDriveBottom.getSelectedSensorPosition());

    }
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void shoot(double rotation) {
        this.setPercentSpeedPIDTop(rotation);
        this.setPercentSpeedPIDBottom(rotation);
    }


    /*
        a1 = arctan((h2 - h1) / d - tan(a2)). This equation, with a known distance input, helps find the 
        mounted camera angle.
    */
    public double getCameraMountingAngle(double measuredDistanceFeet){
        Double ty = Limelight.getTy();
        double radiansToTarget = ty * (Math.PI/180.0);

        //find the result of (h2-h1)/d
        double heightOverDistance = deltaHeightFeet/measuredDistanceFeet;

        //find the result of tan(a2)
        double tangentOfAngle = Math.tan(radiansToTarget);

        // (h2-h1)/d - tan(a2) subtract two results for the tangent of the two sides
        double TangentOfSides = heightOverDistance - tangentOfAngle;

        //inverse of the tan to get the camera mounting angle in radians
        double cameraMountingAngleRadians = Math.atan(TangentOfSides);

        return cameraMountingAngleRadians;
    }
   
    public double getHorizontalDistance() {
        Double ty = Limelight.getTy();
        double angleToTargetRadians = Math.toRadians(ty);
        double distanceInFeet = deltaHeightFeet / (Math.tan(angleOfCameraRadians + angleToTargetRadians));
        
        return distanceInFeet;
    }

    // Calculates the speed needed
    public void shootAuto() {
        //See what the camera mounting angle is.
        //SmartDashboard.putNumber("ShootMounting Angle of Camera", getCameraMountingAngle(15.04166666667));

        double distanceFromTargetFeet = getHorizontalDistance();
        SmartDashboard.putNumber("distanceFeet", distanceFromTargetFeet);

        //Constants
        double g = -9.81;
        double theta = 40;
        double distanceFromTargetMeters = 0.3048 * distanceFromTargetFeet;
        double secTicksPerFeetCow = 1779.624;

        double vi = Math.sqrt((0.5 * g * distanceFromTargetMeters) / ((1.5 - distanceFromTargetFeet) * Math.cos(theta) * Math.cos(theta)));
        SmartDashboard.putNumber("initialVelocity", vi);

        double tickCows = vi * secTicksPerFeetCow;
        SmartDashboard.putNumber("tickCows", tickCows);
        
        shooterDriveTop.set(ControlMode.Velocity, tickCows);
        shooterDriveBottom.set(ControlMode.Velocity, tickCows);
        double currentShootError = shooterDriveTop.getClosedLoopError();
        double currentShootTarget = shooterDriveTop.getClosedLoopTarget();
        SmartDashboard.putNumber("shootError", currentShootError);
        SmartDashboard.putNumber("shootTarget", currentShootTarget);
    }

    public void initSpeedMode() {
        shooterDriveTop.setSensorPhase(true);
        shooterDriveTop.set(ControlMode.Velocity, 0);

        shooterDriveTop.config_kP(PID_SLOT_SPEED_MODE, speedP_top, TIMEOUT_MS);
        shooterDriveTop.config_kI(PID_SLOT_SPEED_MODE, speedI_top, TIMEOUT_MS);
        shooterDriveTop.config_kD(PID_SLOT_SPEED_MODE, speedD_top, TIMEOUT_MS);
        shooterDriveTop.config_kF(PID_SLOT_SPEED_MODE, speedF_top, TIMEOUT_MS);
        shooterDriveTop.selectProfileSlot(PID_SLOT_SPEED_MODE, 0);

        shooterDriveBottom.set(ControlMode.Velocity, 0);
        shooterDriveBottom.config_kP(PID_SLOT_SPEED_MODE, speedP_bottom, TIMEOUT_MS);
        shooterDriveBottom.config_kI(PID_SLOT_SPEED_MODE, speedI_bottom, TIMEOUT_MS);
        shooterDriveBottom.config_kD(PID_SLOT_SPEED_MODE, speedD_bottom, TIMEOUT_MS);
        shooterDriveBottom.config_kF(PID_SLOT_SPEED_MODE, speedF_bottom, TIMEOUT_MS);
        shooterDriveBottom.selectProfileSlot(PID_SLOT_SPEED_MODE, 0);
    }

    public void stop() {
        shooterDriveTop.set(0);
        shooterDriveBottom.set(0);
    }

    public void setPercentSpeedPIDTop(double setSpeed) {
        shooterDriveTop.set(ControlMode.Velocity, MAX_TICKS_PER_SEC_TOP * setSpeed);
    }

    public void setPercentSpeedPIDBottom(double setSpeed) {
        shooterDriveBottom.set(ControlMode.Velocity, MAX_TICKS_PER_SEC_BOTTOM * -setSpeed);

    }
}
