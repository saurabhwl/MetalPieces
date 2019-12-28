//import com.qual.comm.joey_is_so_gangsta
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="MPTeleOpCopy", group="MetalPieces")
public class CopyOfTeleopOpMode extends OpMode {
    DcMotor frontLeft, frontRight, backLeft, backRight, intakeL, intakeR, lift, horizontal;
    Servo claw, foundationLeft, foundationRight;
    public float x, y, z, leftTrigger, rightTrigger, pwr;
    public boolean leftBumper, rightBumper;
    public static double deadzone = 0.2;

    final static double CLAMP_MIN  = 172.0/255.0;
    final static double CLAMP_MAX  = 232.0/255.0;
    final static double StartPost  = 0/255.0;
    final static double ClampPost  = 15/255.0;
    final static double MainPost  = 127.5/255.0;



    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        lift = hardwareMap.dcMotor.get("lift");
        horizontal = hardwareMap.dcMotor.get("horizontal");
        intakeL = hardwareMap.dcMotor.get("intakeL");
        intakeR = hardwareMap.dcMotor.get("intakeR");
        foundationLeft = hardwareMap.servo.get("foundationL");
        foundationRight = hardwareMap.servo.get("foundationR");


        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        intakeL.setDirection(DcMotor.Direction.REVERSE);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        horizontal.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        claw = hardwareMap.servo.get("claw");
        claw.setPosition(CLAMP_MIN);
        foundationLeft.setPosition(StartPost);
        foundationRight.setPosition(StartPost);

    }

    @Override
    public void loop() {
        getJoyVals();

        pwr = y;

        z = z * 1.50f;
        frontRight.setPower( Range.clip(pwr - x + z, -1, 1));
        backRight.setPower(Range.clip(pwr + x + z, -1, 1));
        backLeft.setPower(Range.clip(pwr - x - z, -1, 1));
        frontLeft.setPower(Range.clip(pwr + x - z, -1, 1));

        //allows control of drive train
        intakeL.setPower(leftTrigger);
        intakeR.setPower(leftTrigger);
        intakeL.setPower(rightTrigger);
        intakeR.setPower(rightTrigger);

        Extras();
        telemetry.addData("Motors", "Running");
        telemetry.update();
    }


    public void getJoyVals()
    {
        y = gamepad1.left_stick_y;
        // y = mx+b
        x = gamepad1.left_stick_x;
        z = gamepad1.right_stick_x;

        leftTrigger = gamepad2.left_trigger;
        rightTrigger = -gamepad2.right_trigger;
        leftBumper = gamepad2.left_bumper;
        rightBumper = gamepad2.right_bumper;
      //updates joystick values

     if(Math.abs(x)<deadzone) x = 0;
     if(Math.abs(y)<deadzone) y = 0;
     if(Math.abs(z)<deadzone) z = 0;
        //checks deadzones
    }

    private void Extras() {

        if (gamepad2.y) {
            horizontal.setPower(1);
        } else if (gamepad2.a) {
            horizontal.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else {
            horizontal.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        if (gamepad2.dpad_up) {
            lift.setPower(-.40);
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else {
            lift.setPower(0);
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        if (gamepad2.right_bumper) {
            lift.setPower(.8);
        } else {
            lift.setPower(0);
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            //YEET
        }
        if (gamepad2.left_bumper) {
            lift.setPower(0.3);
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            //YEET
        }
        if (gamepad2.x){
            claw.setPosition(0);
            //YEET
        }
        if (gamepad2.b){
            claw.setPosition(1);
            //YEET
        }
        if(gamepad1.a){
            foundationLeft.setPosition(ClampPost);
        }
        if(gamepad1.a){
            foundationRight.setPosition(ClampPost);
        }
        if(gamepad1.y){
            foundationLeft.setPosition(MainPost);
        }
        if(gamepad1.y){
            foundationRight.setPosition(ClampPost);
        }
    }
//da robot go beep beep boop boop beep beep boop -Joey 2019

    @Override
    public void stop() {
        //nothing here? probably gotta call garbage collection at some point

        //YEET
    }
}