package org.firstinspires.ftc.teamcode.Landon;

import android.media.MediaPlayer;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//Joey was here

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@TeleOp(name="FullDrive")

public class MainTeleop extends OpMode {
    // Declare OpMode members.
    private TeleOpDrive myDrive;
    private TeleOpExtras myExtras;
    private ElapsedTime runtime;
    private int position;
    public int poe;

//    private int poe;
    Servo stopper;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        myDrive = new TeleOpDrive(hardwareMap);
        myExtras = new TeleOpExtras(hardwareMap);

        myDrive.init();
        myExtras.init();

        telemetry.addData("initialized", null);
        runtime = new ElapsedTime();
        runtime.reset();

    }

    /*
     * Code to run REPEATEDLY after the driver hAits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        // Setup a variable for each drive wheel to save power level for telemetry
        telemetry.addData("FR Val: ", myDrive.mFrontRightMotor.getCurrentPosition());
        telemetry.addData("FL Val: ", myDrive.mFrontLeftMotor.getCurrentPosition());
        telemetry.addData("BR Val: ", myDrive.mBackRightMotor.getCurrentPosition());
        telemetry.addData("BL Val: ", myDrive.mBackLeftMotor.getCurrentPosition());

//        if (poe < -11 || poe > 160) {
//           if(poe < -11) {
//               poe = -11;
//           }
//           if(poe > 160){
//               poe = 160;
//           }
//        }

        MediaPlayer ny;
        telemetry.addData("SpinnyL Current Val: ", myExtras.spinnyL.getCurrentPosition());
        telemetry.addData("SpinnyR Current Val: ", myExtras.spinnyR.getCurrentPosition());
        telemetry.addData("SpinnyL Target Val: ", myExtras.spinnyL.getTargetPosition());
        telemetry.addData("SpinnyR Target Val: ", myExtras.spinnyR.getTargetPosition());
        telemetry.addData("Poe value: ", poe);
        telemetry.addData("TEST FORCE DRIVE", poe);

        driveMotors();
        Extras();
        // Show the elapsed game time and wheel power.
    }

    private void Extras() {

        if (Math.abs(gamepad2.left_trigger) > 0.4) {
            myExtras.intakeL.setPower(-.7);
        } else if (Math.abs(gamepad2.right_trigger) > 0.4) {
            myExtras.intakeL.setPower(.7);
        } else {
            myExtras.intakeL.setPower(0);
        }
        if (Math.abs(gamepad2.left_trigger) > 0.4) {
            myExtras.intakeR.setPower(-.7);
        } else if (Math.abs(gamepad2.right_trigger) > 0.4) {
            myExtras.intakeR.setPower(.7);
        } else {
            myExtras.intakeR.setPower(0);
        }
        if (gamepad2.x) {
            myExtras.intakeL.setPower(-1);
            myExtras.intakeR.setPower(-1);
        }
//        if (gamepad2.left_bumper) {
//            myExtras.lift.setPower(.9);
//        } else if (gamepad2.right_bumper) {
//            myExtras.lift.setPower(-0.48);
//        } else {
//            myExtras.lift.setPower(0);
//            myExtras.lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        }
//        if (gamepad2.b){
////            if (myExtras.spinny.getCurrentPosition() < 120) {
////                myExtras.spinny.setPower(1);
////            else {
////                myExtras.spinny.setPower(-0.1);
////            }
////            myExtras.spinny.setTargetPosition(20);
////            myExtras.spinny.setPower(0.4);
//
//        }
//        else if (gamepad2.y) {
////            if (myExtras.spinny.getCurrentPosition() > 90) {
////                myExtras.spinny.setPower(-1);
//            // }
//            //else {
////                if (myExtras.spinny.getCurrentPosition() > 105) {
////                    myExtras.spinny.setPower(-0.2);
////                }
////                else {
////                    myExtras.spinny.setPower(0.2);
//            myExtras.spinny.setTargetPosition(40);
//            myExtras.spinny.setPower(0.5);

//        }
//        else if (gamepad2.x){
//            if (myExtras.spinny.getCurrentPosition() < 120) {
//                myExtras.spinny.setPower(1);
//            else {
//                myExtras.spinny.setPower(-0.1);
//            }
//            myExtras.spinny.setTargetPosition(179);
//            myExtras.spinny.setPower(0.4);
//        }
//        else {
//            myExtras.spinny.setPower(0);
//            myExtras.spinny.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        }

        if (gamepad2.y){
            myExtras.claw.setPosition(0);
        } else if (gamepad2.a){
            myExtras.claw.setPosition(1);
        }
        if (gamepad1.left_bumper) {
            myExtras.foundationL.setPosition(1);
            myExtras.foundationR.setPosition(0);
        } else if (gamepad1.right_bumper) {
            myExtras.foundationL.setPosition(0);
            myExtras.foundationR.setPosition(1);
        }
        if (gamepad2.dpad_up) {
            poe = poe + 1;
            myExtras.spinnyL.setTargetPosition(poe);
            myExtras.spinnyR.setTargetPosition(poe);
            myExtras.spinnyL.setPower(1);
            myExtras.spinnyR.setPower(1);
        }
        else if (gamepad2.dpad_down) {
            poe = poe - 1;
            myExtras.spinnyL.setTargetPosition(poe);
            myExtras.spinnyR.setTargetPosition(poe);
            myExtras.spinnyL.setPower(1);
            myExtras.spinnyR.setPower(1);
        }
//        else {
//            if (poe < -11 || poe > 160) {
//                if(poe < -11) {
//                    poe = -11;
//                }
//                if(poe > 160){
//                    poe = 160;
//                }
//            }
            else {
                poe = myExtras.spinnyL.getCurrentPosition();

                myExtras.spinnyL.setTargetPosition(poe);
                myExtras.spinnyR.setTargetPosition(poe);
                myExtras.spinnyL.setPower(1);
                myExtras.spinnyR.setPower(1);
            }

        }

    private void driveMotors() {
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double r = gamepad1.right_stick_x;

        if (gamepad1.dpad_up) {
            y = 0.250;
        } else if (gamepad1.dpad_down) {
            y = -0.250;
        }

        if (gamepad1.dpad_left) {
            x = -0.250;
        } else if (gamepad1.dpad_right) {
            x = 0.250;
        }

        if (Math.abs(y) <= 0.10) {
            y = 0;
        }
        if (Math.abs(x) <= 0.10) {
            x = 0;
        }
        if (Math.abs(r) <= 0.10) {
            r = 0;
        }

        double FRPower = y - x - r;
        double FLPower = y + x + r;
        double BLPower = y - x + r;
        double BRPower = y + x - r;

        double max = Math.abs(FLPower);
        if (Math.abs(FRPower) > max) max = Math.abs(FRPower);
        if (Math.abs(BRPower) > max) max = Math.abs(BRPower);
        if (Math.abs(BLPower) > max) max = Math.abs(BLPower);

        if (max > 1) {
            FLPower /= max;
            FRPower /= max;
            BRPower /= max;
            BLPower /= max;
        }

        myDrive.mFrontLeftMotor.setPower(1 *FLPower);
        myDrive.mFrontRightMotor.setPower(1 *FRPower);
        myDrive.mBackRightMotor.setPower(1 *BRPower);
        myDrive.mBackLeftMotor.setPower(1 *BLPower);

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}