// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.command.WaitUntilCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class shootBall extends Command {

    boolean isShoot = false;

    int desiredVelocity = 1300;

    int minimumVeloctiy = 1000;

    double currentVelocity;

    int firstTime = 0;


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    Timer timer1 = new Timer();
    Timer timer2 = new Timer();

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public shootBall() {

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        firstTime = 0;

        // Robot.conveyorSub.pullBack();
        timer1.start();
        if (timer1.hasPeriodPassed(1)) {
            // Robot.conveyorSub.stopReverse();
            timer1.stop();
            timer1.reset();
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        currentVelocity = Robot.shootSub.testVelocity();
        SmartDashboard.putNumber("shoot encoder value", Robot.shootSub.testVelocity());

        if (currentVelocity < desiredVelocity) {
            Robot.shootSub.runMotor(0.5);
            if (currentVelocity < minimumVeloctiy) {
                // Robot.conveyorSub.stopFeeding();
            }
        }
        while (currentVelocity > desiredVelocity) {
            Robot.shootSub.runMotor(0.1);
            firstTime += 1;
            SmartDashboard.putNumber("Countup Checker", firstTime);
            if (firstTime == 1) {
                timer2.start();
            }
            // timer2.start(); ******WOULD START TIMER HERE BUT CANT CAUSE EXECUTE******
            // insert feeding here
            SmartDashboard.putNumber("2nd part timer", timer2.get());
            if (timer2.hasPeriodPassed(4)) {
                // SmartDashboard.putString("in home stretch", "yes");
                Robot.shootSub.runMotor(0);
                timer2.stop();
                timer2.reset();
                //Robot.shootSub.resetVelCount();
                end();
            }
        }

        /*
         * if(isShoot = false){ Robot.shootSub.enable(); isShoot = true; }
         * 
         * Robot.shootSub.keepSpeed(desiredVelocity);
         * 
         * if (Robot.shootSub.atSetPoint(desiredVelocity)){ timer.start();
         * //Robot.conveyorSub.feedForShooting();
         * SmartDashboard.putBoolean("executeIsAtSetpoint", Robot.executeIsAtSetpoint);
         * if (timer.hasPeriodPassed(4)){ timer.stop(); cancel(); } }
         */
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        firstTime = 0;
        currentVelocity = 0;
        cancel();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
