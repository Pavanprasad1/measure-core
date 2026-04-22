package com.pavan.measurecore;

public class MeasureCoreApp {

    // ---------------- LENGTH QUANTITY ----------------
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
            return unit.convertToBaseUnit(value);
        }

        public Quantity convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }
            double base = this.toBaseUnit();
            double converted = targetUnit.convertFromBaseUnit(base);
            return new Quantity(converted, targetUnit);
        }

        public Quantity add(Quantity other) {
            if (other == null) {
                throw new IllegalArgumentException("Other cannot be null");
            }
            double sumBase = this.toBaseUnit() + other.toBaseUnit();
            double result = unit.convertFromBaseUnit(sumBase);
            return new Quantity(result, unit);
        }

        public Quantity add(Quantity other, LengthUnit targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }
            double sumBase = this.toBaseUnit() + other.toBaseUnit();
            double result = targetUnit.convertFromBaseUnit(sumBase);
            return new Quantity(result, targetUnit);
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
            return "Length(" + value + ", " + unit + ")";
        }
    }

    // ---------------- WEIGHT QUANTITY ----------------
    static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
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
            return unit.convertToBaseUnit(value);
        }

        public QuantityWeight convertTo(WeightUnit targetUnit) {
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }
            double base = this.toBaseUnit();
            double converted = targetUnit.convertFromBaseUnit(base);
            return new QuantityWeight(converted, targetUnit);
        }

        public QuantityWeight add(QuantityWeight other) {
            if (other == null) {
                throw new IllegalArgumentException("Other cannot be null");
            }
            double sumBase = this.toBaseUnit() + other.toBaseUnit();
            double result = unit.convertFromBaseUnit(sumBase);
            return new QuantityWeight(result, unit);
        }

        public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }
            double sumBase = this.toBaseUnit() + other.toBaseUnit();
            double result = targetUnit.convertFromBaseUnit(sumBase);
            return new QuantityWeight(result, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityWeight other = (QuantityWeight) obj;
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toBaseUnit());
        }

        @Override
        public String toString() {
            return "Weight(" + value + ", " + unit + ")";
        }
    }

    // ---------------- MAIN METHOD ----------------
    public static void main(String[] args) {

        // -------- LENGTH TESTS --------
        System.out.println("---- LENGTH ----");

        System.out.println(
                new Quantity(1.0, LengthUnit.FEET)
                        .equals(new Quantity(12.0, LengthUnit.INCH))
        ); // true

        System.out.println(
                new Quantity(1.0, LengthUnit.FEET)
                        .convertTo(LengthUnit.INCH)
        ); // 12 inch

        System.out.println(
                new Quantity(1.0, LengthUnit.FEET)
                        .add(new Quantity(12.0, LengthUnit.INCH))
        ); // 2 feet

        System.out.println(
                new Quantity(1.0, LengthUnit.FEET)
                        .add(new Quantity(12.0, LengthUnit.INCH), LengthUnit.YARD)
        ); // ~0.667 yard


        // -------- WEIGHT TESTS --------
        System.out.println("\n---- WEIGHT ----");

        System.out.println(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .equals(new QuantityWeight(1000.0, WeightUnit.GRAM))
        ); // true

        System.out.println(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .convertTo(WeightUnit.POUND)
        ); // ~2.20462 lb

        System.out.println(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(1000.0, WeightUnit.GRAM))
        ); // 2 kg

        System.out.println(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(1000.0, WeightUnit.GRAM), WeightUnit.GRAM)
        ); // 2000 g
    }
}