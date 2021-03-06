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
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Autonomous extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    static double degToRad = Math.PI/180.0;

    // static enum AutonState{
    //     BEGIN, DISMOUNT, ADVANCE, TURN, APPROACH, END
    // }
    int curStage = 0;
    final double distancePerPulse = 6*Math.PI/360;
    private double distR=0, distL=0;
    private double invert = -1;

    private int m_Position;
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public Autonomous(int Position) {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        this.m_Position = Position;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.driveSub.leftPairEncoder.setDistancePerPulse(distancePerPulse);
        Robot.driveSub.rightPairEncoder.setDistancePerPulse(distancePerPulse); //6 inch wheel, one pulse per inch
        Robot.driveSub.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        distR = Robot.driveSub.rightPairEncoder.getDistance();
        distL = Robot.driveSub.leftPairEncoder.getDistance();

        SmartDashboard.putString("Dist L", Double.toString(distL));
        SmartDashboard.putString("Dist R", Double.toString(distR));
        switch(this.curStage){
            case 0://begin
                new limelight();
                nextStage();
                break;
            case 1:
                AS_Advance(0.5, 6);
                break;
            case 2:
                AS_Turn('R', 0.90, 0.35, 410);
                // newTurn('R', 0.9, 0.6, 410);
                break;
            case 3:
                AS_Advance(0.5, 1.3);
                break;
            case 4:
                AS_Turn('L', 0.35, 0.90, 280);
                // newTurn('L', 0.9, 0.6, 280);
                break;
            case 5:
                AS_Advance(0.5, 4.0);
                break;
            case 6:
                AS_Turn('L', 0.3, 0.85, 270);
                // newTurn('L', 0.85, 0.36, 270);
                break;
            case 7:
                AS_Advance(0.5, 8.5);
                break;
            case 8:
                AS_Turn('R', -1,-0.45, 410);
                break;
            default:
                Robot.driveSub.resetEncoders();
                Robot.driveSub.stop();
        }
    }

    private void nextStage(){
        Robot.driveSub.resetEncoders();
        this.curStage++;
    }

    private void AS_Advance(double advanceSpeed, double distanceToTravel){//26.67 inches = 1 unit of distance 
        double distanceTraveled = 0.5*(distR+distL);
        Robot.driveSub.driveStraight(advanceSpeed * invert);
        if (distanceTraveled >= distanceToTravel){
            nextStage();
        }
    }

    private void AS_Turn(char direction, double powerL, double powerR, double outerDistanceToTravel){
        double outerDistanceTraveled = (direction=='L') ? Math.abs(distR) : Math.abs(distL);
        SmartDashboard.putString("Outer distance traveled", Double.toString(Math.abs(outerDistanceTraveled)));
        Robot.driveSub.swivel(direction, powerL, powerR);
        if (outerDistanceTraveled >= outerDistanceToTravel){
            nextStage();
        }
    }

    /* 
        direction: R = turn right, L = turn left
        power: amount of power given to robot. -1 = max power backwards -> 1 = max power forwards
        steepness: how steep the turn is. 0 = straight -> 1 = very steep
        outerDistanceToTravel: distance the outer wheel has to travel
    */
    private void newTurn(char direction, double power, double steepness, double outerDistanceToTravel){
        double outerDistanceTraveled = (direction=='L') ? Math.abs(distR) : Math.abs(distL);
        // SmartDashboard.putString("Outer distance traveled", Double.toString(Math.abs(outerDistanceTraveled)));
        Robot.driveSub.swivel(direction, 
            direction=='R' ? power : power*(1-steepness), 
            direction=='L' ? power : power*(1-steepness));
        if (outerDistanceTraveled >= outerDistanceToTravel){
            nextStage();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
