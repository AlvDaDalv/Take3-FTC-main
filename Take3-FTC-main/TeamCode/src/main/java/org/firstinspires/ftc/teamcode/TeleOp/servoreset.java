package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "Goon TeleOp")
public class servoreset extends LinearOpMode {
    // Hardware Components
    private DcMotor leftFront, leftBack, rightFront, rightBack;
    private DcMotorEx rightShooter, leftShooter;
    private DcMotor intake;
    private Servo skibidiServo;
    private CRServo intakeServo;
    private Limelight3A limelight;
    // Constants
    private static final int FAR_SHOT_VELOCITY = 1500;
    private static final int CLOSE_SHOT_VELOCITY = 1118;
    private static final double OUTAKE_POSITION = 0.188;
    private static final double OUTAKE_FAR_POSITION = 0.17;
    private static final double MIN_TURN_POWER = 0.25;
    private static final double MAX_TURN_POWER = 0.5;
    private static final double DISTANCE_THRESHOLD = 80.0;
    private static final double ALIGNMENT_TOLERANCE = 1.0; // degrees
    // Variables
    private boolean intakePowerToggle = false;
    private double targetArea = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        initializeHardware();
        configureLimelight();
        telemetry.addData("Status", "Initialized - Ready to Start");
        telemetry.update();
        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {
            updateLimelightTelemetry();
            handleGamepad2Controls();
            handleGamepad1Controls();
        }
    }

    /**
     * Initialize all hardware components
     */
    private void initializeHardware() {
        // Drive Motors
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        // Shooter Motors
        rightShooter = hardwareMap.get(DcMotorEx.class, "rightShooter");
        leftShooter = hardwareMap.get(DcMotorEx.class, "leftShooter");
        // Intake System
        intake = hardwareMap.dcMotor.get("intake");
        skibidiServo = hardwareMap.servo.get("skibidiS");
        intakeServo = hardwareMap.get(CRServo.class, "intakeS");
        // Configure Motor Directions
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotor.Direction.REVERSE);
        leftShooter.setDirection(DcMotorSimple.Direction.REVERSE);
        // Configure Encoders
        rightShooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftShooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Set Zero Power Behavior
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Initialize Powers
        intake.setPower(0);
        skibidiServo.setPosition(0.65);

        servoreset();
    }

    public class servoreset() {
        skibidiServo.setPosition(0.0);
    }

}