package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "Intake Test", group = "Test")
public class IntakeTest extends LinearOpMode{
    private static final double MOTOR_POWER = 0.8;

    @Override
    public void runOpMode() {
        DcMotor intake = hardwareMap.get(DcMotor.class, "intake");
        intake.setPower(0);
        intake.setDirection(DcMotor.Direction.FORWARD);

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addLine("Press X for intake");
        telemetry.update();

        waitForStart();

        try {
            while(opModeIsActive()){
                double power = 0.0;
                if(gamepad1.x){
                    power = MOTOR_POWER;
                }
                intake.setPower(power);
                telemetry.addData("Intake power", "%.2f", power);
                telemetry.addLine("Hold X and release to stop");
                telemetry.update();
                idle();
            }

        } finally {
            intake.setPower(0);
        }

    }
}
