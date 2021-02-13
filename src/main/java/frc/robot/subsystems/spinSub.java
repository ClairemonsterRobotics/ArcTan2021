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


import frc.robot.Constants;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Spark;

import java.lang.module.ModuleDescriptor.Requires;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class spinSub extends Subsystem {

    private TalonFX spinMotor;

    public boolean atTarget;

    private double spinInitSensorValue;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public spinSub() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        spinMotor = new TalonFX(4); //naturally address 4
        spinMotor.setSelectedSensorPosition(0);
        spinMotor.configIntegratedSensorInitializationStrategy(SensorInitializationStrategy.BootToZero);

        spinMotor.configFactoryDefault();
        spinMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
        spinMotor.setSensorPhase(Constants.kSensorPhase);
        spinMotor.setInverted(Constants.kMotorInvert);

        spinMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
        spinMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
        spinMotor.configPeakOutputForward(0.3, Constants.kTimeoutMs);
        spinMotor.configPeakOutputReverse(-0.3, Constants.kTimeoutMs);

        spinMotor.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

        spinMotor.config_kF(Constants.kPIDLoopIdx, Constants.kGains.kF, Constants.kTimeoutMs);
        spinMotor.config_kP(Constants.kPIDLoopIdx, Constants.kGains.kP, Constants.kTimeoutMs);
        spinMotor.config_kI(Constants.kPIDLoopIdx, Constants.kGains.kI, Constants.kTimeoutMs);
        spinMotor.config_kD(Constants.kPIDLoopIdx, Constants.kGains.kD, Constants.kTimeoutMs);

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

    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void beginSpin(){
        //spinMotor.set(0.55);
        //spinMotor.setSelectedSensorPosition(0);
        checkEncoders();
        SmartDashboard.putString("begin", "");
        spinMotor.set(TalonFXControlMode.PercentOutput, 0.5);
    }

    public void stopSpin(){
        //spinMotor.set(0);
        SmartDashboard.putString("end", "");
        spinMotor.set(TalonFXControlMode.PercentOutput, 0);
    }

    public void goToPos(int goPos, String targetPos){
        double currentReadPos = spinMotor.getSelectedSensorPosition(0);
        spinMotor.set(TalonFXControlMode.Position, goPos + spinInitSensorValue);
        SmartDashboard.putNumber("Current pos", currentReadPos);
        SmartDashboard.putNumber("Desired pos", goPos);
        
    }

    public double checkEncoders(){
        double encoderRead = spinMotor.getSelectedSensorPosition();
        SmartDashboard.putNumber("WOF Encoder Read", encoderRead);
        return encoderRead;
    }

    public void setPosToZero() {
        spinMotor.setSelectedSensorPosition(0);
    }

    public void sendInitSensorValue(double valueRecieved) {
        spinInitSensorValue = valueRecieved;
    }
}

