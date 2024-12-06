Below is the full updated code integrating the NavX2-MXP setup with your swerve drive configuration. The implementation uses the [Studica Robotics NavX library](https://github.com/Studica-Robotics/NavX) and replaces the Pigeon2 setup in your original code:

```java
package frc.robot.generated;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;
import com.ctre.phoenix6.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.swerve.SwerveModuleConstants.ClosedLoopOutputType;
import com.ctre.phoenix6.swerve.SwerveModuleConstants.SteerFeedbackType;
import com.ctre.phoenix6.swerve.SwerveModuleConstantsFactory;

import edu.wpi.first.units.measure.*;
import com.studica.frc.AHRS; // Import NavX class
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class TunerConstants {
    // PID and feedforward gains
    private static final Slot0Configs steerGains = new Slot0Configs()
        .withKP(100).withKI(0).withKD(0.5)
        .withKS(0.2).withKV(1.59).withKA(0)
        .withStaticFeedforwardSign(StaticFeedforwardSignValue.UseClosedLoopSign);

    private static final Slot0Configs driveGains = new Slot0Configs()
        .withKP(0.1).withKI(0).withKD(0)
        .withKS(0).withKV(0.124);

    private static final ClosedLoopOutputType kSteerClosedLoopOutput = ClosedLoopOutputType.Voltage;
    private static final ClosedLoopOutputType kDriveClosedLoopOutput = ClosedLoopOutputType.Voltage;

    private static final SteerFeedbackType kSteerFeedbackType = SteerFeedbackType.FusedCANcoder;

    private static final Current kSlipCurrent = Amps.of(120.0);

    private static final TalonFXConfiguration driveInitialConfigs = new TalonFXConfiguration();
    private static final TalonFXConfiguration steerInitialConfigs = new TalonFXConfiguration()
        .withCurrentLimits(
            new CurrentLimitsConfigs()
                .withStatorCurrentLimit(Amps.of(60))
                .withStatorCurrentLimitEnable(true)
        );
    private static final CANcoderConfiguration cancoderInitialConfigs = new CANcoderConfiguration();

    // Replace Pigeon2 with NavX2-MXP
    private static final AHRS navx = new AHRS(AHRS.NavXComType.kMXP_SPI);

    // CAN bus configuration
    public static final CANBus kCANBus = new CANBus("rio", "./logs/example.hoot");

    public static final LinearVelocity kSpeedAt12Volts = MetersPerSecond.of(4.55);

    private static final double kCoupleRatio = 3.5;

    private static final double kDriveGearRatio = 7.363636364;
    private static final double kSteerGearRatio = 12.8;
    private static final Distance kWheelRadius = Inches.of(2.167);

    private static final boolean kInvertLeftSide = false;
    private static final boolean kInvertRightSide = true;

    private static final double kNavXOffset = 0; // Adjust as needed

    private static final double kSteerInertia = 0.01;
    private static final double kDriveInertia = 0.01;
    private static final Voltage kSteerFrictionVoltage = Volts.of(0.25);
    private static final Voltage kDriveFrictionVoltage = Volts.of(0.25);

    public static final SwerveDrivetrainConstants DrivetrainConstants = new SwerveDrivetrainConstants()
            .withCANBusName(kCANBus.getName())
            .withNavX(navx) // Configure NavX instead of Pigeon
            .withNavXOffset(kNavXOffset);

    private static final SwerveModuleConstantsFactory ConstantCreator = new SwerveModuleConstantsFactory()
            .withDriveMotorGearRatio(kDriveGearRatio)
            .withSteerMotorGearRatio(kSteerGearRatio)
            .withCouplingGearRatio(kCoupleRatio)
            .withWheelRadius(kWheelRadius)
            .withSteerMotorGains(steerGains)
            .withDriveMotorGains(driveGains)
            .withSteerMotorClosedLoopOutput(kSteerClosedLoopOutput)
            .withDriveMotorClosedLoopOutput(kDriveClosedLoopOutput)
            .withSlipCurrent(kSlipCurrent)
            .withSpeedAt12Volts(kSpeedAt12Volts)
            .withFeedbackSource(kSteerFeedbackType)
            .withDriveMotorInitialConfigs(driveInitialConfigs)
            .withSteerMotorInitialConfigs(steerInitialConfigs)
            .withCANcoderInitialConfigs(cancoderInitialConfigs)
            .withSteerInertia(kSteerInertia)
            .withDriveInertia(kDriveInertia)
            .withSteerFrictionVoltage(kSteerFrictionVoltage)
            .withDriveFrictionVoltage(kDriveFrictionVoltage);

    // Module constants
    private static final int kFrontLeftDriveMotorId = 30;
    private static final int kFrontLeftSteerMotorId = 31;
    private static final int kFrontLeftEncoderId = 32;
    private static final Angle kFrontLeftEncoderOffset = Rotations.of(-0.83544921875);
    private static final boolean kFrontLeftSteerMotorInverted = true;
    private static final boolean kFrontLeftCANcoderInverted = false;

    private static final Distance kFrontLeftXPos = Inches.of(10.5);
    private static final Distance kFrontLeftYPos = Inches.of(10.5);

    private static final int kFrontRightDriveMotorId = 40;
    private static final int kFrontRightSteerMotorId = 41;
    private static final int kFrontRightEncoderId = 42;
    private static final Angle kFrontRightEncoderOffset = Rotations.of(-0.15234375);
    private static final boolean kFrontRightSteerMotorInverted = true;
    private static final boolean kFrontRightCANcoderInverted = false;

    private static final Distance kFrontRightXPos = Inches.of(10.5);
    private static final Distance kFrontRightYPos = Inches.of(-10.5);

    private static final int kBackLeftDriveMotorId = 10;
    private static final int kBackLeftSteerMotorId = 11;
    private static final int kBackLeftEncoderId = 12;
    private static final Angle kBackLeftEncoderOffset = Rotations.of(-0.4794921875);
    private static final boolean kBackLeftSteerMotorInverted = true;
    private static final boolean kBackLeftCANcoderInverted = false;

    private static final Distance kBackLeftXPos = Inches.of(-10.5);
    private static final Distance kBackLeftYPos = Inches.of(10.5);

    private static final int kBackRightDriveMotorId = 20;
    private static final int kBackRightSteerMotorId = 21;
    private static final int kBackRightEncoderId = 22;
    private static final Angle kBackRightEncoderOffset = Rotations.of(-0.84130859375);
    private static final boolean kBackRightSteerMotorInverted = true;
    private static final boolean kBackRightCANcoderInverted = false;

    private static final Distance kBackRightXPos = Inches.of(-10.5);
    private static final Distance kBackRightYPos = Inches.of(-10.5);

    public static final SwerveModuleConstants FrontLeft = ConstantCreator.createModuleConstants(
            kFrontLeftSteerMotorId, kFrontLeftDriveMotorId, kFrontLeftEncoderId, kFrontLeftEncoderOffset,
            kFrontLeftXPos, kFrontLeftYPos, kInvertLeftSide, kFrontLeftSteerMotorInverted, kFrontLeftCANcoderInverted);
    public static final SwerveModuleConstants FrontRight = ConstantCreator.createModuleConstants(
            kFrontRightSteerMotorId, kFrontRightDriveMotorId, kFrontRightEncoderId, kFrontRightEncoderOffset,
            kFrontRightXPos, kFrontRightYPos, kInvertRightSide, kFrontRightSteerMotorInverted, kFrontRightCANcoderInverted);
    public static final SwerveModuleConstants BackLeft = ConstantCreator.createModuleConstants(
            kBackLeftSteerMotorId, kBackLeftDriveMotorId, kBackLeftEncoderId, kBackLeftEncoderOffset,
            kBackLeftXPos, kBackLeftYPos, kInvertLeftSide, kBackLeftSteerMotorInverted, kBackLeftCANcoderInverted);
    public static final SwerveModuleConstants BackRight = ConstantCreator.createModuleConstants(
            kBackRightSteerMotorId, kBackRightDriveMotorId, kBackRightEncoderId, kBackRightEncoderOffset,
            kBackRightXPos, kBackRightYPos, kInvertRightSide, kBackRightSteerMotorInverted, kBackRight
        
    /**
     * Creates a CommandSwerveDrivetrain instance.
     * This should only be called once in your robot program,.
     */
    public static CommandSwerveDrivetrain createDrivetrain() {
        return new CommandSwerveDrivetrain(
            DrivetrainConstants, FrontLeft, FrontRight, BackLeft, BackRight
        );
    }
}
