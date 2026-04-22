package com.pavan.measurecore;

public class MeasureCoreApp {

    // ---------------- ENUM ----------------
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CM(0.0328084);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double toBaseUnit(double value) {
            return value * conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }

    // ---------------- QUANTITY CLASS ----------------
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid numeric value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBaseUnit() {
            return unit.toBaseUnit(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toBaseUnit());
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ---------------- CONVERSION METHOD ----------------
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }

        if (source == null || target == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        // Convert to base unit (FEET)
        double baseValue = source.toBaseUnit(value);

        // Convert base → target
        return baseValue / target.getConversionFactor();
    }

    // ---------------- MAIN METHOD ----------------
    public static void main(String[] args) {

        // -------- UC3 & UC4 (Equality) --------
        System.out.println("1 ft == 12 inch → " +
                new Quantity(1.0, LengthUnit.FEET)
                        .equals(new Quantity(12.0, LengthUnit.INCH))); // true

        System.out.println("1 yard == 3 ft → " +
                new Quantity(1.0, LengthUnit.YARD)
                        .equals(new Quantity(3.0, LengthUnit.FEET))); // true

        System.out.println("1 cm == 0.393701 inch → " +
                new Quantity(1.0, LengthUnit.CM)
                        .equals(new Quantity(0.393701, LengthUnit.INCH))); // true

        // -------- UC5 (Conversion) --------
        System.out.println("\n--- Conversion ---");

        System.out.println("Feet → Inches: " +
                convert(1.0, LengthUnit.FEET, LengthUnit.INCH)); // 12

        System.out.println("Yards → Feet: " +
                convert(3.0, LengthUnit.YARD, LengthUnit.FEET)); // 9

        System.out.println("Inches → Yards: " +
                convert(36.0, LengthUnit.INCH, LengthUnit.YARD)); // 1

        System.out.println("CM → Inches: " +
                convert(1.0, LengthUnit.CM, LengthUnit.INCH)); // ~0.393701

        System.out.println("Same Unit (Feet → Feet): " +
                convert(5.0, LengthUnit.FEET, LengthUnit.FEET)); // 5

        System.out.println("Negative Value: " +
                convert(-1.0, LengthUnit.FEET, LengthUnit.INCH)); // -12

        System.out.println("Zero Value: " +
                convert(0.0, LengthUnit.FEET, LengthUnit.INCH)); // 0
    }
}