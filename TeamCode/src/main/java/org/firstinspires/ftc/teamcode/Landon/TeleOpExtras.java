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
//    public DcMotor lift;
    public DcMotor spinnyL;
    public DcMotor spinnyR;
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
//        lift = mHardwareMap.get(DcMotor.class, "lift");
        spinnyL = mHardwareMap.get(DcMotor.class, "spinnyL");
        spinnyR = mHardwareMap.get(DcMotor.class, "spinnyR");
        claw = mHardwareMap.servo.get("claw");
        foundationL = mHardwareMap.servo.get("foundationL");
        foundationR = mHardwareMap.servo.get("foundationR");


        claw.setPosition(0);
        foundationL.setPosition(0);
        foundationR.setPosition(1);
        spinnyL.setTargetPosition(0);
        spinnyR.setTargetPosition(0);
        intakeL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        spinnyL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        spinnyR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        spinnyL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        spinnyR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeL.setDirection(DcMotor.Direction.REVERSE);
        spinnyL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spinnyR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spinnyL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spinnyR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }
}