package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mecanum_Hardware;

@Autonomous(name="Red 2 - Away Relic Zone", group="Fire Wires")

public class Red_2 extends LinearOpMode {

    /* Declare OpMode members. */
    Mecanum_Hardware robot       = new Mecanum_Hardware();
    private ElapsedTime     runtime = new ElapsedTime();

    static final double     FORWARD_SPEED = 0.4;
    static final double     TURN_SPEED    = 0.5;
    static final double     MID_SERVO     = 0.5;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();
        waitForStart();

        robot.rightClaw.setPosition(1);
        robot.rightClaw2.setPosition(1);

        robot.leftClaw.setPosition(0);
        robot.leftClaw2.setPosition(0);

        sleep(1000);
        
        robot.liftMotor.setPower(-1);
        sleep(500);
        robot.liftMotor.setPower(0);
        
        /* Drive Backwards */
        robot.leftFront.setPower(-FORWARD_SPEED);
        robot.rightFront.setPower(-FORWARD_SPEED);
        robot.leftBack.setPower(-FORWARD_SPEED);
        robot.rightBack.setPower(-FORWARD_SPEED);
        sleep(1000);
        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightBack.setPower(0);

        /* Turn */
        robot.leftFront.setPower(-.5);
        robot.rightFront.setPower(.5);
        robot.leftBack.setPower(-.5);
        robot.rightBack.setPower(.5);
        sleep(2300);
        
        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightBack.setPower(0);

        //Strafe
        robot.leftFront.setPower(-FORWARD_SPEED);
        robot.rightFront.setPower(FORWARD_SPEED);
        robot.leftBack.setPower(FORWARD_SPEED);
        robot.rightBack.setPower(-FORWARD_SPEED);
        sleep(2550);
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
    }
}
