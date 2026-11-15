package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Outtake Test", group = "Test")
public class OuttakeTest extends LinearOpMode {
    private static final double MOTOR_POWER = 0.55;

    @Override
    public void runOpMode() {
        // Use this exact name for the motor in the robot configuration.
        DcMotor outtake = hardwareMap.get(DcMotor.class, "outtake");
        outtake.setPower(0);
        outtake.setDirection(DcMotor.Direction.FORWARD);
        // This simple power test does not require an encoder cable.
        outtake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outtake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addLine("Ready: press START, then hold A to run or B to reverse.");
        telemetry.addLine("Release buttons to stop. A + B also stops.");
        telemetry.update();

        waitForStart();

        try {
            while (opModeIsActive()) {
                double power = 0;
                if (gamepad1.a && !gamepad1.b) {
                    power = MOTOR_POWER;
                } else if (gamepad1.b && !gamepad1.a) {
                    power = -MOTOR_POWER;
                }

                outtake.setPower(power);
                telemetry.addData("Outtake power", "%.2f", power);
                telemetry.addLine("Hold A: run | Hold B: reverse | Release: stop");
                telemetry.update();
                idle();
            }
        } finally {
            outtake.setPower(0);
        }
    }
}
