package org.firstinspires.ftc.teamcode.Landon;

/**
 * Created by aaronAgarwal on 11/12/17.
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TeleOpExtras {
    public HardwareMap mHardwareMap;
    public DcMotor intakeL;
    public DcMotor intakeR;
    public DcMotor lift;
    public DcMotor spinny;
    public Servo claw;
    public Servo foundationL;
    public Servo foundationR;
    public TeleOpExtras(HardwareMap input){
        mHardwareMap = input;
    }
    public void init(){

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        intakeL = mHardwareMap.get(DcMotor.class, "intakeL");
        intakeR = mHardwareMap.get(DcMotor.class, "intakeR");
        lift = mHardwareMap.get(DcMotor.class, "lift");
        spinny = mHardwareMap.get(DcMotor.class, "spinny");
        claw = mHardwareMap.servo.get("claw");
        foundationL = mHardwareMap.servo.get("foundationL");
        foundationR = mHardwareMap.servo.get("foundationR");


        claw.setPosition(0);
        foundationL.setPosition(0);
        foundationR.setPosition(1);
        spinny.setTargetPosition(0);
        intakeL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        spinny.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        spinny.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeL.setDirection(DcMotor.Direction.REVERSE);
        spinny.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spinny.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }
}