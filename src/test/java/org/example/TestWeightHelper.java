package org.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestWeightHelper {

    @Test
    public void testCalculateBMI_ValidInputs() {
        assertEquals(22.15, WeightHelper.calculateBMI(170, 64), 0.01); // Adding delta for floating-point
    }

    @Test
    public void testCalculateBMI_BoundaryValues() {
        // BMI boundary tests with more variety
        assertEquals(18.5, WeightHelper.calculateBMI(170, 53.46), 0.01); // Lower bound of normal weight
        assertEquals(25.0, WeightHelper.calculateBMI(170, 72.25), 0.01); // Upper bound of normal weight
        assertEquals(30.0, WeightHelper.calculateBMI(170, 86.7), 0.01); // Lower bound of overweight
        assertEquals(36.26, WeightHelper.calculateBMI(170, 104.8), 0.01); // Upper bound of overweight
    }

    @Test
    public void testGetBMICategory() {
        // Normal weight categories
        assertEquals("Underweight", WeightHelper.getBMICategory(170, 50));
        assertEquals("Normal weight", WeightHelper.getBMICategory(170, 64));
        assertEquals("Overweight", WeightHelper.getBMICategory(170, 75));
        assertEquals("Obese", WeightHelper.getBMICategory(170, 90));
    }

    @Test
    public void testGetBMICategory_BoundaryCases() {
        // Testing boundary values for BMI category transition
        assertEquals("Underweight", WeightHelper.getBMICategory(170, 53.4)); // Just below normal weight
        assertEquals("Normal weight", WeightHelper.getBMICategory(170, 53.5)); // Exactly at boundary
        assertEquals("Overweight", WeightHelper.getBMICategory(170, 72.3)); // Just below obese
        assertEquals("Obese", WeightHelper.getBMICategory(170, 86.8)); // Just into obese
    }

    @Test
    public void testInvalidInputs() {
        // Invalid inputs for height and weight
        try {
            WeightHelper.calculateBMI(0, 70);
            fail("Expected IllegalArgumentException for height <= 0");
        } catch (IllegalArgumentException e) {
            // Expected behavior
        }

        try {
            WeightHelper.calculateBMI(170, -10);
            fail("Expected IllegalArgumentException for weight < 0");
        } catch (IllegalArgumentException e) {
            // Expected behavior
        }

        try {
            WeightHelper.calculateBMI(0, 0);
            fail("Expected IllegalArgumentException for height and weight <= 0");
        } catch (IllegalArgumentException e) {
            // Expected behavior
        }
    }

    @Test
    public void testCalculateBMI_ExtremeValues() {
        // Extreme BMI values with larger variances
        assertEquals(10.0, WeightHelper.calculateBMI(200, 40), 0.01); // Extremely low BMI
        assertEquals(50.0, WeightHelper.calculateBMI(150, 112.5), 0.01); // Extremely high BMI
        assertEquals(0.0, WeightHelper.calculateBMI(100, 0), 0.01); // Zero weight with extreme height
    }

    @Test
    public void testCalculateBMI_SameBMIWithDifferentHeights() {
        // Same BMI for different heights and weights
        assertEquals(21.99, WeightHelper.calculateBMI(160, 56.3), 0.01);
        assertEquals(22.07, WeightHelper.calculateBMI(180, 71.5), 0.01);
        assertEquals(22.77, WeightHelper.calculateBMI(165, 62.0), 0.01); // Additional case for same BMI
    }

    @Test
    public void testCalculateBMI_ExtremeHeights() {
        // Extreme height cases
        assertEquals(50.0, WeightHelper.calculateBMI(100, 50), 0.01); // Extremely short height
        assertEquals(10.0, WeightHelper.calculateBMI(250, 62.5), 0.01); // Extremely tall height
        assertEquals(20.0, WeightHelper.calculateBMI(250, 125), 0.01); // Edge of very tall people
    }

    @Test
    public void testCalculateBMI_ZeroWeight() {
        assertEquals(0.0, WeightHelper.calculateBMI(180, 0), 0.01); // Edge case for zero weight
    }

    @Test
    public void testCalculateBMI_WithDecimals() {
        assertEquals(22.08, WeightHelper.calculateBMI(175.5, 68.0), 0.01); // Tolerance for decimals
        assertEquals(22.14, WeightHelper.calculateBMI(175.5, 68.2), 0.01); // Slight variation with decimals
    }

    @Test
    public void testCalculateBMI_HeightZero() {
        try {
            WeightHelper.calculateBMI(0, 70); // Invalid case: height can't be zero
            fail("Expected IllegalArgumentException for height = 0");
        } catch (IllegalArgumentException e) {
            // Expected behavior
        }
    }

    @Test
    public void testCalculateBMI_HeightExtreme() {
        assertEquals(0.5, WeightHelper.calculateBMI(1000, 50), 0.01); // Unreasonably high height
    }

    @Test
    public void testCalculateBMI_WeightExtreme() {
        assertEquals(55.56, WeightHelper.calculateBMI(180, 180), 0.01); // Extremely heavy person with reasonable height
    }
}
