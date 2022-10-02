package tubes.matrix;

public class Inverse {
    // Pre-condition :
    // 1. Matrix is square
    // 2. Matrix is non-singular
    public static Matrix gaussJordanInverse(Matrix matrix) {
        // Pre-condition : Matrix is square
        int i, j, k;
        // Save a copy of input matrix such that the initial matrix doesn't get affected
        Matrix tempCopy = Matrix.copyMatrix(matrix);
        // Declare and initialize Identity Matrix
        Matrix identity = Matrix.createIdentityMatrix(matrix.nRows);

        // Using Gauss-Jordan Elimination Algorithm
        int currentRow = 0;
        for (j = 0; j < tempCopy.nCols && currentRow < tempCopy.nRows; j++) {
            for (i = currentRow + 1; i < tempCopy.nRows && tempCopy.data[currentRow][j] == 0; i++) {
                if (tempCopy.data[i][j] != 0) {
                    tempCopy.swapRow(currentRow, i);
                    identity.swapRow(currentRow, i);
                }
            }

            if (tempCopy.data[currentRow][j] != 0) {
                identity.divideRowByK(currentRow, tempCopy.data[currentRow][j]);
                tempCopy.divideRowByK(currentRow, tempCopy.data[currentRow][j]);

                for (i = currentRow + 1; i < tempCopy.nRows; i++) {
                    float[] rowArray = tempCopy.getRowAsArray(currentRow);
                    float[] rowIdentity = identity.getRowAsArray(currentRow);
                    for (k = 0; k < tempCopy.nCols; k++) {
                        rowArray[k] *= tempCopy.data[i][j];
                        rowIdentity[k] *= tempCopy.data[i][j];
                    }

                    tempCopy.subtractRowByArray(i, rowArray);
                    identity.subtractRowByArray(i, rowIdentity);
                }

                currentRow++;
            }
        }

        for (i = 1; i < tempCopy.nRows; i++) {
            int colOfPivot = 0;
            while (colOfPivot < tempCopy.nCols-1 && tempCopy.data[i][colOfPivot] == 0) {
                colOfPivot++;
            }

            if (tempCopy.data[i][colOfPivot] != 0) {
                for (j = 0; j < i && tempCopy.data[j][colOfPivot] != 0; j++) {
                    float ratio = tempCopy.data[j][colOfPivot] / tempCopy.data[i][colOfPivot];
                    // Operate Echelon matrix parallel with identity matrix
                    for (k = 0; k < tempCopy.nCols; k++) {
                        tempCopy.data[j][k] -= tempCopy.data[i][k] * ratio;
                        identity.data[j][k] -= identity.data[i][k] * ratio;
                    }
                }
            }
        }

        identity.removeNegativeZero();
        return identity;
    }

    public static Matrix adjoinMethod(Matrix matrix) {
      // Compute the inverse of an invertible matrix using adjoin method

        Matrix cofactorMatrix = new Matrix(matrix.getNRows(), matrix.getNCols());
        for (int i = 0; i < matrix.getNRows(); i++) {
            for (int j = 0; j < matrix.getNCols(); j++) {
                Matrix minor = new Matrix(matrix.getNRows() - 1, matrix.getNCols() - 1);
                for (int k = 0; k < i; k++) {
                    for (int l = 0; l < j; l++) {
                        minor.data[k][l] = matrix.data[k][l];
                    }
                }
                for (int k = 0; k < i; k++) {
                    for (int l = j + 1; l < matrix.getNCols(); l++) {
                        minor.data[k][l - 1] = matrix.data[k][l];
                    }
                }
                for (int k = i + 1; k < matrix.getNRows(); k++) {
                    for (int l = 0; l < j; l++) {
                        minor.data[k - 1][l] = matrix.data[k][l];
                    }
                }
                for (int k = i + 1; k < matrix.getNRows(); k++) {
                    for (int l = j + 1; l < matrix.getNCols(); l++) {
                        minor.data[k - 1][l - 1] = matrix.data[k][l];
                    }
                }

                if ((i + j) % 2 == 0) {
                    cofactorMatrix.data[i][j] = Determinant.cofactor(minor);
                } else {
                    cofactorMatrix.data[i][j] = -1.0f * Determinant.cofactor(minor);
                }
            }
        }

        float mul = 1 / Determinant.cofactor(matrix);
        Matrix transposeCofactor = Matrix.transpose(cofactorMatrix);
        for (int i = 0; i < transposeCofactor.getNRows(); i++) {
            transposeCofactor.multiplyRowByK(i, mul);
        }
        return transposeCofactor;
    }
}
