package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Mecanum + Outtake", group = "StarterBot")
public class MecanumDriveTest extends OpMode {

    private DcMotor leftFrontDrive;
    private DcMotor rightFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightBackDrive;
    private DcMotor outtake;
    private static final double OUTTAKE_POWER = 0.55;

    @Override
    public void init() {

        // Initialize drivetrain motors
        leftFrontDrive = hardwareMap.get(
                DcMotor.class, "left_front_drive");

        rightFrontDrive = hardwareMap.get(
                DcMotor.class, "right_front_drive");

        leftBackDrive = hardwareMap.get(
                DcMotor.class, "left_back_drive");

        rightBackDrive = hardwareMap.get(
                DcMotor.class, "right_back_drive");

        // Set motor directions
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);

        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        // Enable braking
        leftFrontDrive.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE);

        rightFrontDrive.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE);

        leftBackDrive.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE);

        rightBackDrive.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE);

        // Configure the motor on its hub as "outtake".
        outtake = hardwareMap.get(DcMotor.class, "outtake");
        outtake.setPower(0);
        outtake.setDirection(DcMotor.Direction.FORWARD);
        outtake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outtake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized");
        telemetry.addLine("Sticks: drive | Hold A: outtake | Hold B: reverse");
        telemetry.update();
    }

    @Override
    public void loop() {

        // Gamepad controls
        double forward = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;

        // Mecanum drive calculations
        double leftFrontPower = forward + strafe + rotate;
        double rightFrontPower = forward - strafe - rotate;
        double leftBackPower = forward - strafe + rotate;
        double rightBackPower = forward + strafe - rotate;

        // Normalize motor powers
        double max = Math.max(
                Math.abs(leftFrontPower),
                Math.abs(rightFrontPower));

        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        // Set motor powers (limited for initial testing)
        double speedLimit = 0.3;

        leftFrontDrive.setPower(leftFrontPower * speedLimit);
        rightFrontDrive.setPower(rightFrontPower * speedLimit);
        leftBackDrive.setPower(leftBackPower * speedLimit);
        rightBackDrive.setPower(rightBackPower * speedLimit);

        // Release both buttons, or press both together, to stop the outtake.
        double outtakePower = 0;
        if (gamepad1.a && !gamepad1.b) {
            outtakePower = OUTTAKE_POWER;
        } else if (gamepad1.b && !gamepad1.a) {
            outtakePower = -OUTTAKE_POWER;
        }
        outtake.setPower(outtakePower);

        // Telemetry
        telemetry.addData("Outtake power", "%.2f", outtakePower);
        telemetry.addData("Forward", forward);
        telemetry.addData("Strafe", strafe);
        telemetry.addData("Rotate", rotate);
        telemetry.update();
    }

    @Override
    public void stop() {
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
        outtake.setPower(0);
    }
}
