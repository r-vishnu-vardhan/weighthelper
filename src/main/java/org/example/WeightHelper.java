package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WeightHelper {

    public static double calculateBMI(double height, double weight) {
        if (!(height <= 0 || weight < 0)) {
            BigDecimal heightInMeters = BigDecimal.valueOf(height).divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
            BigDecimal bmi = BigDecimal.valueOf(weight)
                    .divide(heightInMeters.pow(2), 10, RoundingMode.HALF_UP);
            return bmi.doubleValue();
        } else {
            throw new IllegalArgumentException("Height must be > 0 and weight >= 0");
        }
    }

    public static String getBMICategory(double height, double weight) {
        double bmi = calculateBMI(height, weight);
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi >= 18.5 && bmi < 25.0) {
            return "Normal weight";
        } else if (bmi >= 25.0 && bmi < 30.0) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }
}
