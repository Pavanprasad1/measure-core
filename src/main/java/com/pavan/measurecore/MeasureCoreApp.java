package com.pavan.measurecore;

public class MeasureCoreApp {

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

        // UC6
        public Quantity add(Quantity other) {
            if (other == null) {
                throw new IllegalArgumentException("Other quantity cannot be null");
            }

            double sumInBase = this.toBaseUnit() + other.toBaseUnit();
            double resultValue = sumInBase / this.unit.getConversionFactor();

            return new Quantity(resultValue, this.unit);
        }

        // UC7 (NEW)
        public Quantity add(Quantity other, LengthUnit targetUnit) {

            if (other == null) {
                throw new IllegalArgumentException("Other quantity cannot be null");
            }

            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double sumInBase = this.toBaseUnit() + other.toBaseUnit();
            double resultValue = sumInBase / targetUnit.getConversionFactor();

            return new Quantity(resultValue, targetUnit);
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

    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }

        if (source == null || target == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        double baseValue = source.toBaseUnit(value);
        return baseValue / target.getConversionFactor();
    }

    public static void main(String[] args) {

        // UC6
        System.out.println(
                new Quantity(1.0, LengthUnit.FEET)
                        .add(new Quantity(12.0, LengthUnit.INCH))
        ); // 2 FEET

        // UC7 examples
        System.out.println(
                new Quantity(1.0, LengthUnit.FEET)
                        .add(new Quantity(12.0, LengthUnit.INCH), LengthUnit.INCH)
        ); // 24 INCH

        System.out.println(
                new Quantity(1.0, LengthUnit.FEET)
                        .add(new Quantity(12.0, LengthUnit.INCH), LengthUnit.YARD)
        ); // ~0.667 YARD

        System.out.println(
                new Quantity(1.0, LengthUnit.YARD)
                        .add(new Quantity(3.0, LengthUnit.FEET), LengthUnit.FEET)
        ); // 6 FEET

        System.out.println(
                new Quantity(2.54, LengthUnit.CM)
                        .add(new Quantity(1.0, LengthUnit.INCH), LengthUnit.CM)
        ); // ~5.08 CM
    }
}