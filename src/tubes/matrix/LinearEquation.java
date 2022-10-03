package tubes.matrix;

import java.util.Arrays;

public class LinearEquation {
    public static Matrix gaussianElimination(Matrix matrixIn) {
        Matrix matrix = Matrix.copyMatrix(matrixIn);

        int currentRow = 0;
        for (int j = 0; j < matrix.nCols - 1 && currentRow < matrix.nRows; j++) {
            for (int i = currentRow + 1; i < matrix.nRows && matrix.data[currentRow][j] == 0; i++) {
                if (matrix.data[i][j] != 0)
                    matrix.swapRow(currentRow, i);
            }


            if (matrix.data[currentRow][j] != 0) {
                matrix.divideRowByK(currentRow, matrix.data[currentRow][j]);
                matrix.data[currentRow][j] = 1;

                for (int i = currentRow + 1; i < matrix.nRows; i++) {
                    double[] rowArray = matrix.getRowAsArray(currentRow);
                    for (int k = 0; k < matrix.nCols; k++) {
                        rowArray[k] *= matrix.data[i][j];
                    }

                    matrix.subtractRowByArray(i, rowArray);
//                    matrix.data[i][j] = 0;
                }

                currentRow++;
            }
        }

        matrix.removeNegativeZero();
        return matrix;
    }

    public static String[] solveRowEchelon(Matrix matrixIn) {
        Matrix matrix = Matrix.copyMatrix(matrixIn);

        double[][] coefs = new double[matrix.nCols - 1][27];
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
                    double currentCoef = coefs[i][j];
                    coefs[i][j] = 0;

                    for (int l = 0; l < matrix.nCols; l++) {
                        coefs[i][l] += coefs[matchingRow][l] * currentCoef;
                    }
                }
            }

            if (coefs[i][matrix.nCols - 1] != 0) res[pivot] += Math.round(coefs[i][matrix.nCols - 1] * 100) / 100d;

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

                if (Math.abs(coefs[i][j]) != 1) res[pivot] += Math.round(Math.abs(coefs[i][j]) * 100) / 100d;
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

    public static String[] solveGauss(Matrix matrixIn) {
        Matrix matrix = Matrix.copyMatrix(matrixIn);
        Matrix rowEchelon = gaussianElimination(matrix);

        for (int i = 0; i < rowEchelon.nRows; i++) {
            boolean hasNonZero = false;
            for (int j = 0; j < rowEchelon.nCols - 1; j++) {
                if (rowEchelon.data[i][j] != 0) hasNonZero = true;
            }
            if (!hasNonZero && rowEchelon.data[i][rowEchelon.nCols - 1] != 0) {
                String[] output = {"Tidak ada solusi"};
                return output;
            }
        }

        return solveRowEchelon(rowEchelon);
    }

    public static Matrix gaussJordanElimination(Matrix matrixIn) {
        Matrix matrix = Matrix.copyMatrix(matrixIn);
        int i, j, k;

        // Forward phase, turn matrix into Echelon form
        matrix = LinearEquation.gaussianElimination(matrix);

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
            if (colOfPivot < matrix.nCols - 1 && matrix.data[i][colOfPivot] != 0) {
                // Operate every row above pivot with a non-zero element at the same column as pivot
                for (j = 0; j < i; j++) {
                    if (matrix.data[j][colOfPivot] == 0) continue;
                    double ratio = matrix.data[j][colOfPivot] / matrix.data[i][colOfPivot];
                    for (k = 0; k < matrix.nCols; k++) {
                        matrix.data[j][k] -= matrix.data[i][k] * ratio;
                    }
//                    matrix.data[j][colOfPivot] = 0;
                }
            }
        }

        matrix.removeNegativeZero();
        return matrix;
    }

    public static String[] solveReducedRowEchelon(Matrix matrixIn) {
        Matrix matrix = Matrix.copyMatrix(matrixIn);
        String[] res = new String[matrix.nCols - 1];
        Arrays.fill(res, "");

        for (int i = 0; i < matrix.nRows; i++) {
            int pivot = 0;
            while (pivot < matrix.nCols - 1 && matrix.data[i][pivot] != 1) {
                pivot++;
            }
            if (pivot >= matrix.nCols - 1) continue;

            if (matrix.data[i][matrix.nCols - 1] != 0) {
                res[pivot] += Math.round(matrix.data[i][matrix.nCols - 1] * 100) / 100d;
            }

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

                if (Math.abs(matrix.data[i][j]) != 1)
                    res[pivot] += Math.round(Math.abs(matrix.data[i][j]) * 100) / 100d;
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

    public static String[] solveGaussJordan(Matrix matrixIn) {
        Matrix matrix = Matrix.copyMatrix(matrixIn);
        Matrix reducedRowEchelon = gaussJordanElimination(matrix);

        for (int i = 0; i < reducedRowEchelon.nRows; i++) {
            boolean hasNonZero = false;
            for (int j = 0; j < reducedRowEchelon.nCols - 1; j++) {
                if (reducedRowEchelon.data[i][j] != 0) hasNonZero = true;
            }
            if (!hasNonZero && reducedRowEchelon.data[i][reducedRowEchelon.nCols - 1] != 0) {
                String[] output = {"Tidak ada solusi"};
                return output;
            }
        }

        return solveReducedRowEchelon(reducedRowEchelon);
    }

    public static Matrix cramerRule(Matrix matrixIn) {
        Matrix matrix = Matrix.copyMatrix(matrixIn);
        Matrix output = new Matrix(matrix.getNRows(), 1);
        Matrix inputMatrix = new Matrix(matrix.getNRows(), matrix.getNCols() - 1);

        // Kasus kalo matriksnya bukan matriks persegi
        if (matrix.getNCols() - 1 != matrix.getNRows()) {
            for (int i = 0; i < output.getNRows(); i++) {
                output.data[i][0] = 0.0;
            }
            return output;
        }

        for (int i = 0; i < inputMatrix.getNRows(); i++) {
            for (int j = 0; j < inputMatrix.getNCols(); j++) {
                inputMatrix.data[i][j] = matrix.data[i][j];
            }
        }
        double determinant = Determinant.rowReduction(inputMatrix);

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
            double kDeterminant = Determinant.rowReduction(cramerMatrix);
            output.data[k][0] = kDeterminant / determinant;
        }

        return output;
    }

    public static String[] solveCramer(Matrix matrixIn) {
        if (matrixIn.nCols - 1 != matrixIn.nRows) {
            String[] msg = {"Tidak ada solusi tunggal"};
            return msg;
        }

        Matrix matrix = Matrix.copyMatrix(matrixIn);
        Matrix cramerResult = cramerRule(matrix);


        String[] output = new String[matrix.nRows];
        for (int i = 0; i < cramerResult.nRows; i++) {
            output[i] = String.format("%.2f", cramerResult.data[i][0]);
            if (output[i].contains("Infinity")) {
                String[] msg = {"Tidak ada solusi tunggal"};
                return msg;
            }
        }

        return output;
    }

    public static Matrix solveLinearWithInverse(Matrix coefficient, Matrix constant) {
        return Matrix.multiplyMatrix(Inverse.gaussJordanInverse(coefficient), constant);
    }
}
