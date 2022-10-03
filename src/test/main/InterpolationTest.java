import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import tubes.matrix.Interpolation;
import tubes.matrix.LinearEquation;
import tubes.matrix.Matrix;
import tubes.util.Util;

import java.io.File;
import java.io.IOException;

public class InterpolationTest {
    @Test
    void testInterpolation1() throws IOException {
        double[][] sample = Util.readFromFile(new File("src/test/cases/interpolation/interpolation1.txt").getCanonicalPath());
        double[] expected = {
                -0.18, 10.28, -163.92, 1220.85, -4346.32, 7102.4, -4212.43
        };
        Matrix matrix = new Matrix(sample);
        Matrix result = Interpolation.polynomialInterpolation(matrix);

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result.data[i][0], 0.01);
        }
    }

    @Test
    void testApprox1() throws IOException {
        double[][] sample = Util.readFromFile(new File("src/test/cases/interpolation/interpolation1.txt").getCanonicalPath());
        double[][] cases = {{0.2, 0.13}, {0.55, 2.13757}, {0.85, -66.2696}, {1.28, -3485.14}};
        Matrix matrix = new Matrix(sample);

        for (int i = 0; i < cases.length; i++) {
            double result = Interpolation.approximateFunction(matrix, cases[i][0]);
            assertEquals(cases[i][1], result, 1e-2);
        }
    }

    @Test
    void testInterpolation2() throws IOException {
        double[][] sample = Util.readFromFile(new File("src/test/cases/interpolation/interpolation2.txt").getCanonicalPath());
        String[] expected = {
                "7.2e+12", "-9.3e+12", "5.3e+12", "-1.8e+12", "3.7e+11", "-5.1e+10", "4.7e+09", "-2.8e+08", "9.4e+06", "-1.4e+05"
        };
        Matrix matrix = new Matrix(sample);
        Matrix result = Interpolation.polynomialInterpolation(matrix);

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], String.format("%.1e", result.data[i][0]));
        }
    }

    @Test
    void testApprox2() throws IOException {
        double[][] sample = Util.readFromFile(new File("src/test/cases/interpolation/interpolation2.txt").getCanonicalPath());
        double[][] cases = {{7.51613, 53532.6}, {8.3226, 36315}, {9.166667, -664933}, {8.93528, 48603}};
        Matrix matrix = new Matrix(sample);

        for (int i = 0; i < cases.length; i++) {
            double result = Interpolation.approximateFunction(matrix, cases[i][0]);
            assertEquals(cases[i][1], result, 1000);
        }
    }

    @Test
    void testInterpolation3() throws IOException {
        double[][] sample = Util.readFromFile(new File("src/test/cases/interpolation/interpolation3.txt").getCanonicalPath());
        double[] expected = {
                0, 1.9, -2.8, 1.91, -0.48
        };
        Matrix matrix = new Matrix(sample);
        Matrix result = Interpolation.polynomialInterpolation(matrix);

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result.data[i][0], 0.01);
        }
    }

    @Test
    void testApprox3() throws IOException {
        double[][] sample = Util.readFromFile(new File("src/test/cases/interpolation/interpolation3.txt").getCanonicalPath());
        double expected = 0.53128;
        Matrix matrix = new Matrix(sample);
        double result = Interpolation.approximateFunction(matrix, 1);

        assertEquals(expected, result, 1e-3);
    }
}
