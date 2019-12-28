package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


public class Extras extends LinearOpMode {
    DcMotor leadScrew;
    DcMotor lift;
    DcMotor intakeL;
    DcMotor intakeR;

    Servo claw;


    public void runOpMode() {

        leadScrew = hardwareMap.dcMotor.get("Lead Screw");
        intakeL = hardwareMap.dcMotor.get("Intake Left");
        intakeR = hardwareMap.dcMotor.get("Intake Right");

        leadScrew.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }

    public void startIntakeing(long time, double power) {
        intakeL.setPower(-power);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intakeR.setPower(power);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void moveClaw ( int ticks, double power){
        leadScrew.setMode(DcMotor.RunMode.RESET_ENCODERS);
        leadScrew.setTargetPosition(ticks);
        leadScrew.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leadScrew.setPower(power);
        while (leadScrew.isBusy()) {

        }
        leadScrew.setPower(0);
        leadScrew.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    public void raiseLift ( int ticks, double power){
        lift.setMode(DcMotor.RunMode.RESET_ENCODERS);
        lift.setTargetPosition(ticks);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(power);
        while (lift.isBusy()) {

        }
        lift.setPower(0);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    public void grabSkystone () {
        claw.setPosition(0);
    }
    public void ungrabSkystone () {
        claw.setPosition(1);
    }
}