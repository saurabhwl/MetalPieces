package org.firstinspires.ftc.teamcode.Landon;

/**
 * Created by joshuasmith on 11/12/17.
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TeleOpDrive {
    public HardwareMap mHardwareMap;
    public DcMotor mFrontLeftMotor;
    public DcMotor mFrontRightMotor;
    public DcMotor mBackRightMotor;
    public DcMotor mBackLeftMotor;


    public TeleOpDrive(HardwareMap input){
        mHardwareMap = input;
    }


    public void init(){

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        mFrontLeftMotor = mHardwareMap.get(DcMotor.class, "frontLeft");
        mFrontRightMotor = mHardwareMap.get(DcMotor.class, "frontRight");
        mBackLeftMotor = mHardwareMap.get(DcMotor.class, "backLeft");
        mBackRightMotor = mHardwareMap.get(DcMotor.class, "backRight");

        mFrontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mFrontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mBackLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mBackRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        mFrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mFrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mBackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mBackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        mBackRightMotor.setDirection(DcMotor.Direction.REVERSE);
        mFrontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        
    }
}