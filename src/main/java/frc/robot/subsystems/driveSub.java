package frc.robot.subsystems;

// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commands.driveCmd;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class driveSub extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public double compensationMult = 1;
    public double deltaDistance = 0;
    boolean compesationError = false;

    public Spark rightMotorPair;
    public Spark leftMotorPair;
    private DifferentialDrive diffDrive;
    public Encoder rightPairEncoder;
    public Encoder leftPairEncoder;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public driveSub() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        rightMotorPair = new Spark(1);
        addChild("rightMotorPair", rightMotorPair);
        rightMotorPair.setInverted(false);

        leftMotorPair = new Spark(0);
        addChild("leftMotorPair", leftMotorPair);
        leftMotorPair.setInverted(false);

        diffDrive = new DifferentialDrive(leftMotorPair, rightMotorPair);
        addChild("diffDrive", diffDrive);
        diffDrive.setSafetyEnabled(true);
        diffDrive.setExpiration(0.1);
        diffDrive.setMaxOutput(1.0);

        double encoderTickConstant = 1 / (60 / Math.PI); // gives approx 1in/1 encoder tick (CHECK YOUR MATH!)

        rightPairEncoder = new Encoder(2, 3, false);
        addChild("rightPairEncoder", rightPairEncoder);
        rightPairEncoder.setDistancePerPulse(1.0); // set twice?
        // rightPairEncoder.setIndexSource(11, IndexingType.kResetOnRisingEdge);
        rightPairEncoder.setDistancePerPulse(encoderTickConstant);

        leftPairEncoder = new Encoder(0, 1, false);
        addChild("leftPairEncoder", leftPairEncoder);
        leftPairEncoder.setDistancePerPulse(1.0); // set twice?
        // leftPairEncoder.setIndexSource(14, IndexingType.kResetOnRisingEdge);
        leftPairEncoder.setDistancePerPulse(encoderTickConstant);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new driveCmd());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
    }

    public void subArcadeDrive(Joystick stick) {
        diffDrive.arcadeDrive(-stick.getY(), stick.getRawAxis(4), true);
        // =====================================================================================
    }
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    ///////// replaced with 2019 version
    // public void resetEncoders() {
    //     rightPairEncoder.reset();
    //     leftPairEncoder.reset();
    // }

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    public void driveStraight(double power) { // reset endoders before calling this
        // used for autonomous mode
        SmartDashboard.putBoolean("Compensation Error!!!", compesationError);
        deltaDistance = leftPairEncoder.getDistance() / -rightPairEncoder.getDistance();
        // deltaDistance = currentLeftDistance/currentRightDistance;
        compensationMult = 1;
        // if((leftPairEncoder.getDistance() + rightPairEncoder.getDistance()) /2 > 10){
        compensationMult = (4 * (deltaDistance - 1)) + 1;
        if (compensationMult > 1.5 || compensationMult < .5) {
            compesationError = true;
            compensationMult = 1;
        } else {
            compesationError = false;
        }
        // }
        SmartDashboard.putNumber("Compensation Multiplier (Left)", compensationMult);
        // compensationMult = deltaDistance + 1;
        // diffDrive.arcadeDrive(power, 0, true); //not sure what this line does

        // leftMotorPair.set(power);
        // rightMotorPair.set(-power * compensationMult * .80);
        leftMotorPair.set(-power * compensationMult);
        rightMotorPair.set(power);
    }

    public void turnRight(double power) {
        rightMotorPair.set(0);
        leftMotorPair.set(power);
    }

    public void turnLeft(double power) {
        leftMotorPair.set(0);
        rightMotorPair.set(power);
    }

    public void swivel(char direction, double powerL, double powerR) {//powers go opposite directions
        leftMotorPair.set(direction=='R' ? powerL : -powerL);
        rightMotorPair.set(direction=='R' ? powerR : -powerR);
    }

    public void stop() {
        // used for autonomous mode
        leftMotorPair.set(0);
        rightMotorPair.set(0);

    }

    public void resetEncoders() {
        Robot.driveSub.rightPairEncoder.reset();
        Robot.driveSub.leftPairEncoder.reset();

    }

    public void limelightDrive(double m_LimelightDriveCommand, double m_LimelightSteerCommand) {

        diffDrive.arcadeDrive(m_LimelightDriveCommand, m_LimelightSteerCommand, true);
        SmartDashboard.putNumber("limelight drive cmd", m_LimelightDriveCommand);
        SmartDashboard.putNumber("limelight steer cmd", m_LimelightSteerCommand);

        // leftMotorPair.set(power);
        // rightMotorPair.set(-power * compensationMult * .80);
    }

}