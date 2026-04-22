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
    }

    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
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
    }

    public static void main(String[] args) {

        // Yard vs Feet
        System.out.println(new Quantity(1.0, LengthUnit.YARD)
                .equals(new Quantity(3.0, LengthUnit.FEET))); // true

        // Yard vs Inch
        System.out.println(new Quantity(1.0, LengthUnit.YARD)
                .equals(new Quantity(36.0, LengthUnit.INCH))); // true

        // CM vs Inch
        System.out.println(new Quantity(1.0, LengthUnit.CM)
                .equals(new Quantity(0.393701, LengthUnit.INCH))); // true

        // Same unit
        System.out.println(new Quantity(2.0, LengthUnit.YARD)
                .equals(new Quantity(2.0, LengthUnit.YARD))); // true
    }
}