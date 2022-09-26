package tubes.matrix;

import java.lang.Math;

public class BicubicInterpolation {
    private static Matrix generateVariablesMatrix() {
        Matrix matrix = new Matrix(16, 16);

        for (int y = -1; y <= 2; y++) {
            for (int x = -1; x <= 2; x++) {
                int row = (x + 1) + (y + 1) * 4;

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        int col = j + i * 4;
                        matrix.data[row][col] = (float) (Math.pow(x, i) * Math.pow(y, j));
                    }
                }
            }
        }

        return matrix;
    }

    public static Matrix solveCoefficient(Matrix values) {
        float[] valuesArray = values.getMatrixAsOneDimensionalArray();
        Matrix valuesMatrixOneRow = new Matrix(16, 1);
        for (int i = 0; i < 16; i++) {
            valuesMatrixOneRow.data[i][0] = valuesArray[i];
        }

        Matrix inverseVariables = Inverse.gaussJordanInverse(generateVariablesMatrix());
        Matrix coefMatrixOneRow = Matrix.multiplyMatrix(inverseVariables, valuesMatrixOneRow);

        Matrix coefMatrix = new Matrix(4, 4);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                coefMatrix.data[i][j] = coefMatrixOneRow.data[j + i * 4][0];
            }
        }

        return coefMatrix;
    }

    public static float interpolate(Matrix values, float x, float y) {
        if (x < 0 || x > 1 || y < 0 || y > 1) {
            return Float.NaN;
        }

        Matrix coefficient = solveCoefficient(values);

        float result = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result += coefficient.data[i][j] * Math.pow(x, i) * Math.pow(y, j);
            }
        }

        return result;
    }
}
