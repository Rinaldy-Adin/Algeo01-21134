package tubes.matrix;

import java.util.Arrays;

public class LinearEquation {
    public static Matrix gaussianElimination(Matrix matrix) {
        int currentRow = 0;
        for (int j = 0; j < matrix.nCols && currentRow < matrix.nRows; j++) {
            for (int i = currentRow + 1; i < matrix.nRows && matrix.data[currentRow][j] == 0; i++) {
                if (matrix.data[i][j] != 0)
                    matrix.swapRow(currentRow, i);
            }


            if (matrix.data[currentRow][j] != 0) {
                matrix.divideRowByK(currentRow, matrix.data[currentRow][j]);

                for (int i = currentRow + 1; i < matrix.nRows; i++) {
                    float[] rowArray = matrix.getRowAsArray(currentRow);
                    for (int k = 0; k < matrix.nCols; k++) {
                        rowArray[k] *= matrix.data[i][j];
                    }

                    matrix.subtractRowByArray(i, rowArray);
                }

                currentRow++;
            }
        }

        matrix.removeNegativeZero();
        return matrix;
    }

    public static String[] solveRowEchelon(Matrix matrix) {
        float[][] coefs = new float[matrix.nCols - 1][27];
        String[] res = new String[matrix.nCols - 1];

        Arrays.fill(res, "");

        for (int i = matrix.nRows - 1; i >= 0; i--) {
            int pivot = 0;
            while (pivot < matrix.nCols && matrix.data[i][pivot] == 0) {
                pivot++;
            }
            if (pivot >= matrix.nCols - 1) continue;

            coefs[i][matrix.nCols - 1] += matrix.data[i][matrix.nCols - 1];
            for (int j = matrix.nCols - 2; j > pivot; j--) {
                coefs[i][j] -= matrix.data[i][j];
            }

            for (int j = pivot + 1; j < matrix.nCols - 1; j++) {
                int matchingRow = i;
                for (int k = i + 1; k < matrix.nRows; k++) {
                    int kPivot = 0;

                    while (kPivot < matrix.nCols - 1 && matrix.data[k][kPivot] != 1) {
                        kPivot++;
                    }
                    if (kPivot == j)
                        matchingRow = k;
                }

                if (matchingRow != i) {
                    float currentCoef = coefs[i][j];
                    coefs[i][j] = 0;

                    for (int l = 0; l < matrix.nCols; l++) {
                        coefs[i][l] += coefs[matchingRow][l] * currentCoef;
                    }
                }
            }

            if (coefs[i][matrix.nCols - 1] != 0) res[pivot] += coefs[i][matrix.nCols - 1];

            for (int j = pivot + 1; j < matrix.nCols - 1; j++) {
                if (coefs[i][j] == 0) continue;

                if (res[pivot].equals("")) {
                    if (coefs[i][j] < 0) {
                        res[pivot] += "-";
                    }
                } else {
                    if (coefs[i][j] > 0) {
                        res[pivot] += " + ";
                    } else {
                        res[pivot] += " - ";
                    }
                }

                if (Math.abs(coefs[i][j]) != 1) res[pivot] += Math.abs(coefs[i][j]);
                char var = 'a';
                var += j;
                res[pivot] += var;
            }

            if (res[pivot].equals("")) res[pivot] += 0;
        }

        for (int i = 0; i < matrix.nCols - 1; i++) {
            if (res[i].equals("")) {
                char var = 'a';
                var += i;
                res[i] += var;
            }
        }

        return res;
    }

    public static String[] solveGauss(Matrix matrix) {
        Matrix rowEchelon = gaussianElimination(matrix);
        return solveRowEchelon(rowEchelon);
    }

    public static Matrix gaussJordanElimination(Matrix matrix) {
        int i, j, k;

        // Forward phase, turn matrix into Echelon form
        LinearEquation.gaussianElimination(matrix);

        // Pivot = first non-zero element from the left of row
        // i increments reference row
        // j increments row to be operated
        // k traverse through row elements to be added/subtracted with another row

        // Loop through the 2nd row onwards
        for (i = 1; i < matrix.nRows; i++) {
            // Find the index of pivot
            int colOfPivot = 0;
            while (colOfPivot < matrix.nCols && matrix.data[i][colOfPivot] == 0) {
                colOfPivot++;
            }

            // Check if row didn't contain all zeros (pivot is a non-zero)
            if (colOfPivot < matrix.nCols && matrix.data[i][colOfPivot] != 0) {
                // Operate every row above pivot with a non-zero element at the same column as pivot
                for (j = 0; j < i && matrix.data[j][colOfPivot] != 0; j++) {
                    float ratio = matrix.data[j][colOfPivot] / matrix.data[i][colOfPivot];
                    for (k = 0; k < matrix.nCols; k++) {
                        matrix.data[j][k] -= matrix.data[i][k] * ratio;
                    }
                }
            }
        }

        matrix.removeNegativeZero();
        return matrix;
    }

    public static String[] solveReducedRowEchelon(Matrix matrix) {
        String[] res = new String[matrix.nCols - 1];
        Arrays.fill(res, "");

        for (int i = 0; i < matrix.nRows; i++) {
            int pivot = 0;
            while (pivot < matrix.nCols - 1 && matrix.data[i][pivot] != 1) {
                pivot++;
            }

            if (matrix.data[i][matrix.nCols - 1] != 0) res[pivot] += matrix.data[i][matrix.nCols - 1];

            for (int j = pivot + 1; j < matrix.nCols - 1; j++) {
                if (matrix.data[i][j] == 0) continue;

                if (res[pivot].equals("")) {
                    if (matrix.data[i][j] > 0) {
                        res[pivot] += "-";
                    }
                } else {
                    if (matrix.data[i][j] < 0) {
                        res[pivot] += " + ";
                    } else {
                        res[pivot] += " - ";
                    }
                }

                if (Math.abs(matrix.data[i][j]) != 1) res[pivot] += Math.abs(matrix.data[i][j]);
                char var = 'a';
                var += j;
                res[pivot] += var;
            }
        }

        for (int i = 0; i < matrix.nCols - 1; i++) {
            if (res[i].equals("")) {
                char var = 'a';
                var += i;
                res[i] += var;
            }
        }

        return res;
    }

    public static String[] solveGaussJordan(Matrix matrix) {
        Matrix reducedRowEchelon = gaussJordanElimination(matrix);
        return solveRowEchelon(reducedRowEchelon);
    }

    public static Matrix cramerRule(Matrix matrix) {
        Matrix output = new Matrix(matrix.getNRows(), 1);
        Matrix inputMatrix = new Matrix(matrix.getNRows(), matrix.getNCols() - 1);

        // Kasus kalo matriksnya bukan matriks persegi
        if (matrix.getNCols() - 1 != matrix.getNRows()) {
            for (int i = 0; i < output.getNRows(); i++) {
                output.data[i][0] = 0.0f;
            }
            return output;
        }

        for (int i = 0; i < inputMatrix.getNRows(); i++) {
            for (int j = 0; j < inputMatrix.getNCols(); j++) {
                inputMatrix.data[i][j] = matrix.data[i][j];
            }
        }
        float determinant = Determinant.rowReduction(inputMatrix);

        for (int k = 0; k < output.getNRows(); k++) {
            Matrix cramerMatrix = new Matrix(matrix.getNRows(), matrix.getNCols() - 1);

            for (int i = 0; i < cramerMatrix.getNRows(); i++) {
                for (int j = 0; j < cramerMatrix.getNCols(); j++) {
                    if (j == k) {
                        cramerMatrix.data[i][j] = matrix.data[i][matrix.getNCols() - 1];
                    } else {
                        cramerMatrix.data[i][j] = matrix.data[i][j];
                    }
                }
            }
            float kDeterminant = Determinant.rowReduction(cramerMatrix);
            output.data[k][0] = kDeterminant / determinant;
        }

        return output;
    }

    public static Matrix solveLinearWithInverse(Matrix coefficient, Matrix constant) {
        return Matrix.multiplyMatrix(Inverse.gaussJordanInverse(coefficient), constant);
    }
}
