package tubes.matrix;

import java.lang.Math;

public class BicubicInterpolation {
    private static Matrix generateVariablesMatrix() {
        Matrix matrix = new Matrix(16, 16);

        for (int y = -1; y <= 2; y++) {
            for (int x = -1; x <= 2; x++) {
                int row = (x + 1) + (y + 1) * 4;

                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 4; i++) {
                        int col = i + j * 4;
                        matrix.data[row][col] = (Math.pow(x, i) * Math.pow(y, j));
                    }
                }
            }
        }

        return matrix;
    }

    public static Matrix solveCoefficient(Matrix values) {
        Matrix valuesMatrixOneCol = new Matrix(16, 1);
        for (int y = -1; y <= 2; y++) {
            for (int x = -1; x <= 2; x++) {
                valuesMatrixOneCol.data[(x + 1) + (y + 1) * 4][0] = values.data[x + 1][y + 1];
            }
        }

        Matrix inverseVariables = Inverse.gaussJordanInverse(generateVariablesMatrix());
        Matrix coefMatrixOneCol = Matrix.multiplyMatrix(inverseVariables, valuesMatrixOneCol);

        Matrix coefMatrix = new Matrix(4, 4);
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                coefMatrix.data[i][j] = coefMatrixOneCol.data[i + j * 4][0];
            }
        }

        return coefMatrix;
    }

    public static double interpolate(Matrix values, double x, double y) {
        if (x < 0 || x > 1 || y < 0 || y > 1) {
            return Double.NaN;
        }

        Matrix diff = new Matrix(16, 17);
        Matrix vars = generateVariablesMatrix();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                diff.data[i][j] = vars.data[i][j];
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                diff.data[j + i * 4][16] = values.data[i][j];
            }
        }

        Matrix res = LinearEquation.gaussJordanElimination(diff);

        double[] col = res.getColAsArray(16);
        Matrix coefficient = new Matrix(4, 4);
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                coefficient.data[i][j] = col[i + j * 4];
            }
        }


        double result = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                result += coefficient.data[i][j] * Math.pow(x, i) * Math.pow(y, j);
            }
        }

        return result;
    }
}
