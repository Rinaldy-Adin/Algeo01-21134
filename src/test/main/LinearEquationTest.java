import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import tubes.matrix.LinearEquation;
import tubes.matrix.Matrix;
import tubes.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class LinearEquationTest {
    Matrix counting3x3;
    Matrix counting4x4;
    Matrix identity3x3;
    Matrix identity4x4;

    @BeforeEach
    void setupMatrix() throws IOException {
        double[][] c3 = Util.readFromFile(new File("src/test/cases/general/counting3x3.txt").getCanonicalPath());
        double[][] c4 = Util.readFromFile(new File("src/test/cases/general/counting4x4.txt").getCanonicalPath());
        double[][] i3 = Util.readFromFile(new File("src/test/cases/general/identity3x3.txt").getCanonicalPath());
        double[][] i4 = Util.readFromFile(new File("src/test/cases/general/identity4x4.txt").getCanonicalPath());

        counting3x3 = new Matrix(c3);
        counting4x4 = new Matrix(c4);
        identity3x3 = new Matrix(i3);
        identity4x4 = new Matrix(i4);
    }

    Matrix hilbertMatrix(int n) {
        Matrix matrix = new Matrix(n, n + 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix.data[i][j] = 1d / (j + i * n + 1);
            }
        }

        matrix.data[0][matrix.getNCols() - 1] = 1;
        for (int i = 1; i < n; i++) {
            matrix.data[i][matrix.getNCols() - 1] = 0;
        }

        return matrix;
    }

    // TODO: Add handling for cramer and handling for no solutions
    // TODO: Fix for gauss solve for Hilbert Matrix
    @Test
    void testElimination() throws IOException {
        double[][] sample1 = Util.readFromFile(new File("src/test/cases/linearequation/gauss-sample1.txt").getCanonicalPath());
        String[] expected1 = {"Tidak ada solusi"};
        Matrix matrix1 = new Matrix(sample1);
        String[] resultgauss1 = LinearEquation.solveGauss(matrix1);
        String[] resultgaussjordan1 = LinearEquation.solveGaussJordan(matrix1);

//        assertArrayEquals(expected1, resultgauss1);

        double[][] sample2 = Util.readFromFile(new File("src/test/cases/linearequation/gauss-sample2.txt").getCanonicalPath());
        String[] expected2 =
                {
                        "3.0 + e",
                        "2.0e",
                        "c",
                        "-1.0 + e",
                        "e"
                };
        Matrix matrix2 = new Matrix(sample2);
        String[] resultgauss2 = LinearEquation.solveGauss(matrix2);
        String[] resultgaussjordan2 = LinearEquation.solveGaussJordan(matrix2);

        assertArrayEquals(expected2, resultgauss2);
        assertArrayEquals(expected2, resultgaussjordan2);

        double[][] sample3 = Util.readFromFile(new File("src/test/cases/linearequation/gauss-sample3.txt").getCanonicalPath());
        String[] expected3 =
                {
                        "a",
                        "1.0 - f",
                        "c",
                        "-2.0 - f",
                        "1.0 + f",
                        "f"
                };
        Matrix matrix3 = new Matrix(sample3);
        String[] resultgauss3 = LinearEquation.solveGauss(matrix3);
        String[] resultgaussjordan3 = LinearEquation.solveGaussJordan(matrix3);

        assertArrayEquals(expected3, resultgauss3);
        assertArrayEquals(expected3, resultgaussjordan3);

        double[][] sample4a = Util.readFromFile(new File("src/test/cases/linearequation/gauss-sample4a.txt").getCanonicalPath());
        String[] expected4a =
                {
                        "36.0",
                        "-630.0",
                        "3360.0",
                        "-7560.0",
                        "7560.0",
                        "-2772.0"
                };
        Matrix matrix4a = new Matrix(sample4a);
        String[] resultgauss4a = LinearEquation.solveGauss(matrix4a);
        String[] resultgaussjordan4a = LinearEquation.solveGaussJordan(matrix4a);

        assertArrayEquals(expected4a, resultgauss4a);
        assertArrayEquals(expected4a, resultgaussjordan4a);

        double[][] sample4b = Util.readFromFile(new File("src/test/cases/linearequation/gauss-sample4b.txt").getCanonicalPath());
        String[] expected4b =
                {
                        "100.0", "-4949.8", "79195.67", "-600560.53", "2522331.25", "-6305780.06", "9608745.47", "-8750772.98", "4375365.28", "-923684.29"
                };
        Matrix matrix4b = new Matrix(sample4b);
        String[] resultgauss4b = LinearEquation.solveGauss(matrix4b);
        String[] resultgaussjordan4b = LinearEquation.solveGaussJordan(matrix4b);

        assertArrayEquals(expected4b, resultgauss4b);
        assertArrayEquals(expected4b, resultgaussjordan4b);

        double[][] sample5 = Util.readFromFile(new File("src/test/cases/linearequation/gauss-sample5.txt").getCanonicalPath());
        String[] expected5 =
                {
                        "-1.0 + d",
                        "2.0c",
                        "c",
                        "d"
                };
        Matrix matrix5 = new Matrix(sample5);
        String[] resultgauss5 = LinearEquation.solveGauss(matrix5);
        String[] resultgaussjordan5 = LinearEquation.solveGaussJordan(matrix5);

        assertArrayEquals(expected5, resultgauss5);
        assertArrayEquals(expected5, resultgaussjordan5);

        double[][] sample6 = Util.readFromFile(new File("src/test/cases/linearequation/gauss-sample6.txt").getCanonicalPath());
        String[] expected6 =
                {
                        "0",
                        "2.0",
                        "1.0",
                        "1.0"
                };
        Matrix matrix6 = new Matrix(sample6);
        String[] resultgauss6 = LinearEquation.solveGauss(matrix6);
        String[] resultgaussjordan6 = LinearEquation.solveGaussJordan(matrix6);

        assertArrayEquals(expected6, resultgauss6);
        assertArrayEquals(expected6, resultgaussjordan6);

        double[][] sample7 = Util.readFromFile(new File("src/test/cases/linearequation/gauss-sample7.txt").getCanonicalPath());
        String[] expected7 =
                {
                        "-148.91",
                        "-8.93",
                        "165.84",
                        "248.77",
                        "-140.64",
                        "-93.13",
                        "-93.85",
                        "161.56",
                        "-54.71",
                };
        Matrix matrix7 = new Matrix(sample7);
        String[] resultgauss7 = LinearEquation.solveGauss(matrix7);
        String[] resultgaussjordan7 = LinearEquation.solveGaussJordan(matrix7);

        assertArrayEquals(expected7, resultgauss7);
        assertArrayEquals(expected7, resultgaussjordan7);
    }
}
