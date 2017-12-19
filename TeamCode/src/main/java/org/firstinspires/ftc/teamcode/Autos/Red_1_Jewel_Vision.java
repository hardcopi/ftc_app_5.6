package org.firstinspires.ftc.teamcode.Autos;

        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;

        import com.disnodeteam.dogecv.CameraViewDisplay;
        import com.disnodeteam.dogecv.detectors.*;
        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.util.ElapsedTime;
        import java.io.IOException;
        import org.firstinspires.ftc.robotcore.external.ClassFactory;
        import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
        import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
        import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
        import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
        import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
        import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
        import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
        import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
        import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
        import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
        import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
        import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


@Autonomous(name="Red 1 - Jewel Detector", group="Fire Wires: Test")

public class Red_1_Jewel_Vision extends LinearOpMode  {
    private ElapsedTime runtime = new ElapsedTime();
    private JewelDetector jewelDetector = null;
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() {
        String jewelColor;
        String vuMarkDirection;
        String vuMarkLocation;

        telemetry.addData("Status", "Initialized.");
        telemetry.update();

        waitForStart();
        jewelColor = jewelDetect();
        vuMarkDirection = vuMarkDetect();

        telemetry.addData("VuMark", "%s visible", vuMarkDirection);
        telemetry.addData("Jewel Order: ", jewelColor);
        telemetry.update();
        sleep(3000000);
    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    private String jewelDetect() {
        String jewelColor;
        /** Initialize the Jewel Detector and Wait for Start **/
        jewelDetector = new JewelDetector();
        jewelDetector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        jewelDetector.areaWeight = 0.02;
        jewelDetector.detectionMode = JewelDetector.JewelDetectionMode.MAX_AREA;
        jewelDetector.debugContours = true;
        jewelDetector.maxDiffrence = 15;
        jewelDetector.ratioWeight = 15;
        jewelDetector.minArea = 700;

        /** Enable Jewel Detector and send code to drive station **/
        jewelDetector.enable();
        sleep(2000);
        jewelColor = jewelDetector.getCurrentOrder().toString();
        jewelDetector.disable();
        return jewelColor;
    }

    private String vuMarkDetect() {
        String vuMarkLocation;
        /** Enable Vuforia **/
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
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
}