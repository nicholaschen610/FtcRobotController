package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Mecanum Drive Test", group = "StarterBot")
public class MecanumDriveTest extends OpMode {

    private DcMotor leftFrontDrive;
    private DcMotor rightFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightBackDrive;

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

        telemetry.addData("Status", "Initialized");
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

        // Telemetry
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
    }
}
