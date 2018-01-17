package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Mecanum_Hardware;


@Autonomous(name="Blue 2 - Jewel Detector", group="Fire Wires: Test")
//@Disabled
public class Blue_2_Jewel_Vision extends LinearOpMode  {
    private ElapsedTime runtime = new ElapsedTime();

    /* Declare OpMode members. */
    Mecanum_Hardware robot       = new Mecanum_Hardware();

    static final double     FORWARD_SPEED = 0.3;
    static final double     TURN_SPEED    = 0.5;
    static final double     MID_SERVO     = 0.5;

    @Override
    public void runOpMode() {
        String jewelColor;

        telemetry.addData("Status", "Initialized.");
        telemetry.update();
        robot.init(hardwareMap);

        waitForStart();

        jewelColor = robot.jewelDetect();
//        vuMarkDirection = vuMarkDetect();
        sleep(1000);

        telemetry.addData("Vision", jewelColor);
        telemetry.update();

        robot.rightClaw.setPosition(1);
        robot.rightClaw2.setPosition(1);

        robot.leftClaw.setPosition(0);
        robot.leftClaw2.setPosition(0);
        sleep(1000);
        robot.liftMotor.setPower(-1);
        sleep(500);
        robot.liftMotor.setPower(0);

        /* If Red is the first color then drive forward */
        if (jewelColor == "BLUE_RED") {
            robot.jewelArm.setPosition(0);
            sleep(1000);
            robot.driveForward(FORWARD_SPEED);
            sleep(250);
            robot.driveForward(0);
            sleep(250);
            robot.driveForward(-FORWARD_SPEED);
            sleep(250);
            robot.driveForward(0);
            sleep(1000);

            robot.jewelArm.setPosition(1);
        }
        /* If Red is the second color then drive backward */
        if (jewelColor == "RED_BLUE") {
            robot.jewelArm.setPosition(0);
            sleep(1000);
            robot.driveForward(-FORWARD_SPEED);
            sleep(350);
            robot.driveForward(0);
            sleep(250);
            robot.driveForward(FORWARD_SPEED);
            sleep(350);
            robot.driveForward(0);
            sleep(1000);

            robot.jewelArm.setPosition(1);
        }

        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        robot.rightClaw.setPosition(1);
        robot.rightClaw2.setPosition(1);
        robot.leftClaw.setPosition(0);
        robot.leftClaw2.setPosition(0);
        sleep(1000);

        robot.liftMotor.setPower(-1);
        sleep(500);
        robot.liftMotor.setPower(0);

        /* Drive Backwards */
        robot.leftFront.setPower(FORWARD_SPEED);
        robot.rightFront.setPower(FORWARD_SPEED);
        robot.leftBack.setPower(FORWARD_SPEED);
        robot.rightBack.setPower(FORWARD_SPEED);
        sleep(1250);
        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightBack.setPower(0);

        robot.leftFront.setPower(FORWARD_SPEED);
        robot.rightFront.setPower(-FORWARD_SPEED);
        robot.leftBack.setPower(-FORWARD_SPEED);
        robot.rightBack.setPower(FORWARD_SPEED);
        sleep(1250);
        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightBack.setPower(0);

        /* Drive Forward */
        robot.leftFront.setPower(FORWARD_SPEED);
        robot.rightFront.setPower(FORWARD_SPEED);
        robot.leftBack.setPower(FORWARD_SPEED);
        robot.rightBack.setPower(FORWARD_SPEED);
        sleep(350);

        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightBack.setPower(0);

        robot.rightClaw.setPosition(0);
        robot.rightClaw2.setPosition(0);
        robot.leftClaw.setPosition(1);
        robot.leftClaw2.setPosition(1);
        sleep(1000);

        /* Drive Backwards */
        robot.leftFront.setPower(-FORWARD_SPEED);
        robot.rightFront.setPower(-FORWARD_SPEED);
        robot.leftBack.setPower(-FORWARD_SPEED);
        robot.rightBack.setPower(-FORWARD_SPEED);
        sleep(150);

        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightBack.setPower(0);
        sleep(1000);

        /* Drive Forward */
        robot.leftFront.setPower(FORWARD_SPEED);
        robot.rightFront.setPower(FORWARD_SPEED);
        robot.leftBack.setPower(FORWARD_SPEED);
        robot.rightBack.setPower(FORWARD_SPEED);
        sleep(500);

        /* Drive Backwards */
        robot.leftFront.setPower(-FORWARD_SPEED);
        robot.rightFront.setPower(-FORWARD_SPEED);
        robot.leftBack.setPower(-FORWARD_SPEED);
        robot.rightBack.setPower(-FORWARD_SPEED);
        sleep(300);

        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightBack.setPower(0);
        robot.wrist.setPosition(.9);

        telemetry.addData("Auto", "Complete");
        telemetry.update();
        sleep(1000);
        sleep(1000);
    }
}