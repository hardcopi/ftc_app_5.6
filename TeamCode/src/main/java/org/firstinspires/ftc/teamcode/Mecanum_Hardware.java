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

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.JewelDetector;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 */
public class Mecanum_Hardware
{
    /* Public OpMode members. */
    public DcMotor leftFront   = null;
    public DcMotor rightFront  = null;
    public DcMotor leftBack   = null;
    public DcMotor rightBack  = null;
    public DcMotor liftMotor  = null;
    public DcMotor relicMotor = null;

    public Servo leftClaw    = null;
    public Servo rightClaw   = null;
    public Servo leftClaw2    = null;
    public Servo rightClaw2   = null;
    public Servo jewelArm   = null;
    public Servo wrist      = null;
    public Servo hand       = null;

    private JewelDetector jewelDetector = null;
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;

    public static final double MID_SERVO       =  0 ;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public Mecanum_Hardware(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;
        leftFront  = hwMap.get(DcMotor.class, "leftFront");
        rightFront = hwMap.get(DcMotor.class, "rightFront");
        leftBack  = hwMap.get(DcMotor.class, "leftBack");
        rightBack = hwMap.get(DcMotor.class, "rightBack");
        liftMotor = hwMap.get(DcMotor.class, "liftMotor");
        relicMotor = hwMap.get(DcMotor.class, "relicMotor");

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        relicMotor.setDirection(DcMotor.Direction.FORWARD);

        liftMotor.setDirection(DcMotor.Direction.REVERSE);

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);

        liftMotor.setPower(0);

        leftClaw  = hwMap.get(Servo.class, "leftClaw");
        rightClaw = hwMap.get(Servo.class, "rightClaw");
        leftClaw2  = hwMap.get(Servo.class, "leftClaw2");
        rightClaw2 = hwMap.get(Servo.class, "rightClaw2");
        jewelArm = hwMap.get(Servo.class, "jewelArm");
        wrist = hwMap.get(Servo.class, "wrist");
        hand = hwMap.get(Servo.class, "hand");

        hand.setPosition(1);

//        leftClaw.setPosition(MID_SERVO);
//        rightClaw.setPosition(MID_SERVO);
//        leftClaw2.setPosition(MID_SERVO);
//        rightClaw2.setPosition(MID_SERVO);

        jewelArm.setPosition(1);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        relicMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void driveForward(double power) {
        leftFront.setPower(-power);
        rightFront.setPower(-power);
        leftBack.setPower(-power);
        rightBack.setPower(-power);
    }

    public String jewelDetect() {
        String jewelColor;
        /** Initialize the Jewel Detector and Wait for Start **/
        jewelDetector = new JewelDetector();
        jewelDetector.init(hwMap.appContext, CameraViewDisplay.getInstance());
        jewelDetector.areaWeight = 0.02;
        jewelDetector.detectionMode = JewelDetector.JewelDetectionMode.MAX_AREA;
        jewelDetector.debugContours = true;
        jewelDetector.maxDiffrence = 15;
        jewelDetector.ratioWeight = 15;
        jewelDetector.minArea = 700;

        /** Enable Jewel Detector and send code to drive station **/
        jewelDetector.enable();
        sleep(1000);
        jewelColor = jewelDetector.getCurrentOrder().toString();
        jewelDetector.disable();
        return jewelColor;
    }

    public String vuMarkDetect() {
        String vuMarkLocation;
        /** Enable Vuforia **/
        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "Abh5GtX/////AAAAGU4NACwqzktehPb5VWgSwz2AjhTTfzNOC7Ciqyt5D89oGI437qoF33JZdyt7GE62AqzqCBkVfIajxpJrTYwgxdVrPSMpFUd3TkkYpzwCKKKeRS4JziYmfmix5qzjLvphfWwvFvdSq4LtBVQ7VlXOAzRSX2aSZGGUb+X/926ZWmbpTqwkMPaFnYOchlv/pwolE9UXjqDBdU+xw8XVsuZxILg+4sDsskgXLJljck2qfqTPJRbCabMM22gSup4ZrPO53XFVqXh/Klzgck2dWvKX0Y5nUIPmLrgxAQKlfvMVZP01B91HqUV8SccYYZ0sz5XELshuugDUIRFj70qoLLsFDy69vkVmW31UfSRWL7Altksa\n";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        relicTrackables.activate();
        sleep(1000);

        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
            if (pose != null) {
                VectorF trans = pose.getTranslation();
                Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                double tX = trans.get(0);
                double tY = trans.get(1);
                double tZ = trans.get(2);
                double rX = rot.firstAngle;
                double rY = rot.secondAngle;
                double rZ = rot.thirdAngle;
            }
            vuMarkLocation = vuMark.toString();
        }
        else {
            vuMarkLocation = "Not Visible";
        }

        relicTrackables.deactivate();
        return vuMarkLocation;
    }

    public void sleep(long duration) {
        try {
            Thread.sleep(duration);
        }
        catch(InterruptedException e) {
        }
    }
}

