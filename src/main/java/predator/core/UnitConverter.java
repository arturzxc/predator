package predator.core;

public class UnitConverter {
    public static final double METERS_TO_HAMMER_UNIT = 39.37007874;
    public static final double HAMMER_UNIT_TO_METERS = 0.0254;

    public static double convertMetersToHammerUnits(double meters) {
        return meters * METERS_TO_HAMMER_UNIT;
    }

    public static double convertHammerUnitsToMeters(double hammerUnits) {
        return hammerUnits * HAMMER_UNIT_TO_METERS;
    }

}
