package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name="MPAutoBlueOLD", group="MetalPieces")
public class AutoOpModeBlue extends LinearOpMode {
    //
    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;
    Servo   foundationleft;
    Servo   foundationright;
    DcMotor intakeleft = null;
    DcMotor intakeright = null;
    //Calculate encoder conversion
    Double width = 18.0; //inches
    int cpr = 28; //counts per rotation
    Double gearratio = 19.2;
    Double diameter = 4.0;
    Double cpi = (cpr * gearratio)/(Math.PI * diameter); //counts per inch -> counts per rotation / circumference
    Double bias = 1.0;
    Double arcBias = 0.0;//Not recommended
    //
    Double conversion = cpi * bias;
    Boolean exit = false;
    //
    BNO055IMU imu;
    Orientation angles;
    Acceleration gravity;
    //

    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode(){
        //
        initGyro();
        //
        frontleft = hardwareMap.dcMotor.get("frontLeft");
        frontright = hardwareMap.dcMotor.get("frontRight");
        backleft = hardwareMap.dcMotor.get("backLeft");
        backright = hardwareMap.dcMotor.get("backRight");
        intakeleft = hardwareMap.dcMotor.get("intakeL");
        intakeright = hardwareMap.dcMotor.get("intakeR");
        frontleft.setDirection(DcMotorSimple.Direction.REVERSE);//If your robot goes backward, switch this from right to left
        backleft.setDirection(DcMotorSimple.Direction.REVERSE);//If your robot goes backward, switch this from right to left
        intakeright.setDirection(DcMotorSimple.Direction.FORWARD);//If Both motors don't spin in, switch this from right to left
        foundationleft = hardwareMap.servo.get("foundationL");
        foundationright = hardwareMap.servo.get("foundationR");
        frontleft.setPower(0);
        frontright.setPower(0);
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        //
        waitForStart();
        startIntaking(1);
        //longitudinal(6,0.2);
        driveForward(1, -.6);
        turnWithGyro(-30, .1);
        startIntaking(0);

        driveForward(1,.3);
        startIntaking(-1);
    }
    //
    /*
    This function's purpose is simply to drive forward or backward.
    To drive backward, simply make the inches input negative.
     */

    public void driveForward(int driveTimeInSec, double speed){

        frontleft.setPower(speed);
        frontright.setPower(speed);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < driveTimeInSec)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        frontleft.setPower(0);
        frontright.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }

    public void driveSideWay(int driveTimeInSec, double speed){

        frontleft.setPower(speed);
        frontright.setPower(-speed);
        backleft.setPower(-speed);
        backright.setPower(speed);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < driveTimeInSec)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        frontleft.setPower(0);
        frontright.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }

    public void longitudinal(double inches, double speed){
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //
        if (inches < 5) {
            int move = (int) (Math.round(inches * conversion));
            //
            frontleft.setTargetPosition(frontleft.getCurrentPosition() + move);
            frontright.setTargetPosition(frontright.getCurrentPosition() + move);
            backleft.setTargetPosition(backleft.getCurrentPosition() + move);
            backright.setTargetPosition(backright.getCurrentPosition() + move);
            //
            frontleft.setPower(speed);
            frontright.setPower(speed);
            backleft.setPower(speed);
            backright.setPower(speed);
            sleep(5000);
            //
            //while (frontleft.isBusy() && frontright.isBusy() && backleft.isBusy() && backright.isBusy()) {}
            frontleft.setPower(0);
            frontright.setPower(0);
            backleft.setPower(0);
            backright.setPower(0);
        }else{
            int move1 = (int)(Math.round((inches - 5) * conversion));
            int movefl2 = frontleft.getCurrentPosition() + (int)(Math.round(inches * conversion));
            int movefr2 = frontright.getCurrentPosition() + (int)(Math.round(inches * conversion));
            int movebl2 = backleft.getCurrentPosition() + (int)(Math.round(inches * conversion));
            int movebr2 = backright.getCurrentPosition() + (int)(Math.round(inches * conversion));
            //
            frontleft.setTargetPosition(frontleft.getCurrentPosition() + move1);
            frontright.setTargetPosition(frontright.getCurrentPosition() + move1);
            backleft.setTargetPosition(backleft.getCurrentPosition() + move1);
            backright.setTargetPosition(backright.getCurrentPosition() + move1);
            //
            frontleft.setPower(speed);
            frontright.setPower(speed);
            backleft.setPower(speed);
            backright.setPower(speed);
            //
            while (frontleft.isBusy() && frontright.isBusy() && backleft.isBusy() && backright.isBusy()) {}
            //
            frontleft.setTargetPosition(movefl2);
            frontright.setTargetPosition(movefr2);
            backleft.setTargetPosition(movebl2);
            backright.setTargetPosition(movebr2);
            //
            frontleft.setPower(.1);
            frontright.setPower(.1);
            backleft.setPower(.1);
            backright.setPower(.1);
            //
            while (frontleft.isBusy() && frontright.isBusy() && backleft.isBusy() && backright.isBusy()) {}
            frontleft.setPower(0);
            frontright.setPower(0);
            backleft.setPower(0);
            backright.setPower(0);
        }
        return;
    }
    public void strafe (double inches, double speed){
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //
        if (inches < 5) {
            int move = (int) (Math.round(inches * conversion));
            //
            frontleft.setTargetPosition(frontleft.getCurrentPosition() - move);
            frontright.setTargetPosition(frontright.getCurrentPosition() - move);
            backleft.setTargetPosition(backleft.getCurrentPosition() + move);
            backright.setTargetPosition(backright.getCurrentPosition() + move);
            //
            frontleft.setPower(speed);
            frontright.setPower(speed);
            backleft.setPower(speed);
            backright.setPower(speed);
            //
            while (frontleft.isBusy() && frontright.isBusy() && backleft.isBusy() && backright.isBusy()) {}
            frontleft.setPower(0);
            frontright.setPower(0);
            backleft.setPower(0);
            backright.setPower(0);
        }else{
            int move1 = (int)(Math.round((inches - 5) * conversion));
            int movefl2 = frontleft.getCurrentPosition() - (int)(Math.round(inches * conversion));
            int movefr2 = frontright.getCurrentPosition() - (int)(Math.round(inches * conversion));
            int movebl2 = backleft.getCurrentPosition() + (int)(Math.round(inches * conversion));
            int movebr2 = backright.getCurrentPosition() + (int)(Math.round(inches * conversion));
            //
            frontleft.setTargetPosition(frontleft.getCurrentPosition() + move1);
            frontright.setTargetPosition(frontright.getCurrentPosition() + move1);
            backleft.setTargetPosition(backleft.getCurrentPosition() + move1);
            backright.setTargetPosition(backright.getCurrentPosition() + move1);
            //
            frontleft.setPower(speed);
            frontright.setPower(speed);
            backleft.setPower(speed);
            backright.setPower(speed);
            //
            while (frontleft.isBusy() && frontright.isBusy() && backleft.isBusy() && backright.isBusy()) {}
            //
            frontleft.setTargetPosition(movefl2);
            frontright.setTargetPosition(movefr2);
            backleft.setTargetPosition(movebl2);
            backright.setTargetPosition(movebr2);
            //
            frontleft.setPower(.1);
            frontright.setPower(.1);
            backleft.setPower(.1);
            backright.setPower(.1);
            //
            while (frontleft.isBusy() && frontright.isBusy() && backleft.isBusy() && backright.isBusy()) {}
            frontleft.setPower(0);
            frontright.setPower(0);
            backleft.setPower(0);
            backright.setPower(0);
        }
        return;
    }
    //
    /*
    This function uses the Expansion Hub IMU Integrated Gyro to turn a precise number of degrees (+/- 5).
    Degrees should always be positive, make speedDirection negative to turn left.
     */
    public void turnWithGyro(double degrees, double speedDirection){
        //<editor-fold desc="Initialize">
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double yaw = -angles.firstAngle;//make this negative
        telemetry.addData("Speed Direction", speedDirection);
        telemetry.addData("Yaw", yaw);
        telemetry.update();
        //
        telemetry.addData("stuff", speedDirection);
        telemetry.update();
        //
        double first;
        double second;
        //</editor-fold>
        //
        if (speedDirection > 0){//set target positions
            //<editor-fold desc="turn right">
            if (degrees > 20){
                first = (degrees - 20) + devertify(yaw);
                second = degrees + devertify(yaw);
            }else{
                first = devertify(yaw);
                second = degrees + devertify(yaw);
            }
            //</editor-fold>
        }else{
            //<editor-fold desc="turn left">
            if (degrees > 20){
                first = devertify(-(degrees - 20) + devertify(yaw));
                second = devertify(-degrees + devertify(yaw));
            }else{
                first = devertify(yaw);
                second = devertify(-degrees + devertify(yaw));
            }
            //
            //</editor-fold>
        }
        //
        //<editor-fold desc="Go to position">
        Double firsta = convertify(first - 5);//175
        Double firstb = convertify(first + 5);//-175
        //
        turnWithEncoder(speedDirection);
        //
        if (Math.abs(firsta - firstb) < 11) {
            while (!(firsta < yaw && yaw < firstb) && opModeIsActive()) {//within range?
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity = imu.getGravity();
                yaw = -angles.firstAngle;
                telemetry.addData("Position", yaw);
                telemetry.addData("first before", first);
                telemetry.addData("first after", convertify(first));
                telemetry.update();
            }
        }else{
            //
            while (!((firsta < yaw && yaw < 180) || (-180 < yaw && yaw < firstb)) && opModeIsActive()) {//within range?
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity = imu.getGravity();
                yaw = -angles.firstAngle;
                telemetry.addData("Position", yaw);
                telemetry.addData("first before", first);
                telemetry.addData("first after", convertify(first));
                telemetry.update();
            }
        }
        //
        Double seconda = convertify(second - 5);//175
        Double secondb = convertify(second + 5);//-175
        //
        turnWithEncoder(speedDirection / 3);
        //
        if (Math.abs(seconda - secondb) < 11) {
            while (!(seconda < yaw && yaw < secondb) && opModeIsActive()) {//within range?
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity = imu.getGravity();
                yaw = -angles.firstAngle;
                telemetry.addData("Position", yaw);
                telemetry.addData("second before", second);
                telemetry.addData("second after", convertify(second));
                telemetry.update();
            }
        }else {
            while (!((seconda < yaw && yaw < 180) || (-180 < yaw && yaw < secondb)) && opModeIsActive()) {//within range?
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity = imu.getGravity();
                yaw = -angles.firstAngle;
                telemetry.addData("Position", yaw);
                telemetry.addData("second before", second);
                telemetry.addData("second after", convertify(second));
                telemetry.update();
            }
        }
        //</editor-fold>
        //
        frontleft.setPower(0);
        frontright.setPower(0);
        backleft.setPower(0);
        backright.setPower(0);
    }
    //
    /*
    WARNING: This Movement type is INCOMPATIBLE with your chassis, use is NOT RECOMMENDED
    This is our function for arcing, a special type of movement that allows for turning while moving.
    Use the angle and length to determine where the robot will end up.
     */
    public void arc(Double angle, Double length, Double speed){
        //\frac{c*sin*(90-b)}{\sin2b}
        Double radius = ((length + arcBias) * Math.sin(Math.toRadians(90-angle)))/(Math.sin(Math.toRadians(2 * angle)));
        telemetry.addData("radius", radius);
        telemetry.update();
        //2\pi\left(r+a\right)\left(\frac{b}{180}\right)
        //2\pi\left(r-a\right)\left(\frac{b}{180}\right)
        //
        Double rightMotor;
        Double leftMotor;
        rightMotor = 2 * Math.PI * (radius - (width / 2)) * (angle / 180);
        leftMotor = 2 * Math.PI * (radius + (width / 2)) * (angle / 180);
        //
        int rightd = (int) (Math.round(rightMotor * conversion));
        int leftd = (int) (Math.round(leftMotor * conversion));
        //
        telemetry.addData("left motor", leftMotor + ", " + leftd);
        telemetry.addData("right motor", rightMotor + ", " + rightd);
        telemetry.update();
        //
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //
        frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //
        backright.setTargetPosition(backright.getCurrentPosition() + rightd);
        backleft.setTargetPosition(backleft.getCurrentPosition() + leftd);
        //
        frontleft.setPower(0);
        frontright.setPower(0);
        backleft.setPower(speed);
        backright.setPower((rightMotor / leftMotor) * speed);
        //
        while (backleft.isBusy() && backright.isBusy()){}
        //
        frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backright.setPower(0);
        return;
    }
    //
    public void waitForStartify(){
        waitForStart();
    }
    //
    /*
    These functions are used in the turnWithGyro function to ensure angle
    inputs are interpreted properly.
     */
    public double devertify(double degrees){
        if (degrees < 0){
            degrees = degrees + 360;
        }
        return degrees;
    }
    public double convertify(double degrees){
        if (degrees > 179){
            degrees = -(360 - degrees);
        } else if(degrees < -180){
            degrees = 360 + degrees;
        } else if(degrees > 360){
            degrees = degrees - 360;
        }
        return degrees;
    }
    //
    /*
    This function is called at the beginning of the program to activate
    the IMU Integrated Gyro.
     */
    public void initGyro(){
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        //parameters.calibrationDataFile = "GyroCal.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        //
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }
    //
    /*
    This function is used in the turnWithGyro function to set the
    encoder mode and begin turning.
     */
    public void turnWithEncoder(double input){
        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //
        frontleft.setPower(input);
        frontright.setPower(-input);
        backleft.setPower(input);
        backright.setPower(-input);
    }
    public void startIntaking(double power) {
        intakeleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeright.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeleft.setPower(power);
        intakeright.setPower(power);
        //
    }
    public void stopIntakeing(double power) {
        startIntaking(0);
        //
    }
}
