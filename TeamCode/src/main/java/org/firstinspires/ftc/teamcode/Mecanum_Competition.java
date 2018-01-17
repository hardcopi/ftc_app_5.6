/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Mecanum Competition", group="Fire Wires")
//@Disabled
public class Mecanum_Competition extends OpMode {

    /* Declare OpMode members. */
    Mecanum_Hardware robot       = new Mecanum_Hardware();

    double          clawOffset  = 0.0;                  // Servo mid position
    double          clawOffset2  = 0.2;
    final double    CLAW_SPEED  = 0.02 ;                 // sets rate to move servo
    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Mecanum Initialized");    //
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        /*  This is the mecanum drive loop.
            The math functions and basic code are based on code found on the FTC Forums.
            The left joystick will move the robot while the right joystick will
            rotate the robot in place.

            https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example
         */

        double vD = Math.sqrt(Math.pow(-gamepad1.left_stick_y, 2) +
                Math.pow(gamepad1.left_stick_x, 2));
        double thetaD = Math.atan2(gamepad1.left_stick_x, -gamepad1.left_stick_y);
        double vTheta = gamepad1.right_stick_x;

        /* Convert desired motion to wheel powers, with power clamping. */
        Mecanum.Wheels wheels = Mecanum.motionToWheels(vD, thetaD, vTheta);
        robot.leftFront.setPower(wheels.frontLeft);
        robot.rightFront.setPower(wheels.frontRight);
        robot.leftBack.setPower(wheels.backLeft);
        robot.rightBack.setPower(wheels.backRight);
        /* End Mecanum Section */


        robot.liftMotor.setPower(gamepad2.left_stick_y * 1);

        if (gamepad2.right_trigger > 0) {
            robot.rightClaw.setPosition(0.5);
            robot.rightClaw2.setPosition(0.5);

            robot.leftClaw.setPosition(.5);
            robot.leftClaw2.setPosition(.5);

        }
        if (gamepad2.left_trigger > 0) {
            robot.rightClaw.setPosition(1);
            robot.rightClaw2.setPosition(1);

            robot.leftClaw.setPosition(0);
            robot.leftClaw2.setPosition(0);

        }

        // Move both servos to new position.  Assume servos are mirror image of each other.
        clawOffset = Range.clip(clawOffset, -0.75, 0);

        // Move both servos to new position.  Assume servos are mirror image of each other.
        clawOffset2 = Range.clip(clawOffset2, -0.75, 0);


        // relicArm
        robot.relicMotor.setPower(-gamepad2.right_stick_y * 1);

        if (gamepad2.right_stick_y == 0) {
            robot.relicMotor.setPower(0);
        }

        if (gamepad2.right_bumper) {
            robot.wrist.setPosition(.9);
        }
        if (gamepad2.left_bumper) {
            robot.wrist.setPosition(.5);
        }


        if (gamepad2.x) {
            robot.hand.setPosition(1);
        }
        if (gamepad2.y) {
            robot.hand.setPosition(0);
        }



    }


    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
