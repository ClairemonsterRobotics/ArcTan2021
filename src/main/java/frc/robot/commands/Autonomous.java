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

/**
 *
 */
public class Autonomous extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    static double degToRad = Math.PI/180.0;

    // static enum AutonState{
    //     BEGIN, DISMOUNT, ADVANCE, TURN, APPROACH, END
    // }
    int curState = 0;
    final double distancePerPulse = 6*Math.PI/360;

    private double distR=0, distL=0;

    private final double dismountDistance = 24; //need to measure so that we aren't going off platform
    private final double dismountSpeed = 0.3;
    private final double advanceDistance = 56;
    private final double advanceSpeed = 0.1;
    private double turnDegrees = 30;
    private double turnSpeed = 0.1;
    private final double approachDistance = 24;
    private final double approachSpeed = 0.1;
    private double invert = -1;

    private int m_Position;
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public Autonomous(int Position) {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        m_Position = Position;

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

        switch(curState){
            case 0://begin
                new limelight();
                nextState();
                break;
            case 1:
                if(AS_Advance(this.dismountSpeed,this.dismountDistance)){
                    nextState();
                }
                break;
            case 2:
                if(AS_Advance(this.advanceSpeed,this.advanceDistance)){
                    nextState();
                }
                break;
            case 3:
                if(AS_Turn(this.turnSpeed,this.turnDegrees)){
                    nextState();
                }
                break;
            case 4:
                if(AS_Advance(this.approachDistance,this.approachSpeed)){
                    nextState();
                }
                break;
            case -1:
                Robot.driveSub.resetEncoders();
                Robot.driveSub.stop();
                // new arcadeDrive();
                break;
            default:
                Robot.driveSub.resetEncoders();
                Robot.driveSub.stop();
        }
    }

    private void nextState(){
        Robot.driveSub.resetEncoders();
        curState++;
    }
    private boolean AS_Advance(double advanceSpeed, double advanceDistance){
        double avg = 0.5*(distR+distL);
        Robot.driveSub.driveStraight(advanceSpeed * invert);
        return (avg >= advanceDistance);
    }
    private boolean AS_Turn(double turnSpeed, double turnDegrees){
        double dist = -0.5*(distR-distL);
        double degreesTurned=degToRad*dist/16.0; // (arclength / robot radius)
        Robot.driveSub.swivelRight(turnSpeed);
        return (degreesTurned >= turnDegrees);
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
