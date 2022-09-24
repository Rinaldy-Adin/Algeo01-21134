package tubes.matrix;

public class Inverse {
    // Pre-condition :
    // 1. Matrix is square
    // 2. Matrix is non-singular
    public static Matrix gaussJordanInverse (Matrix matrix) {
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

        for (i=1; i<tempCopy.nRows; i++) {
            int colOfPivot = 0;
            while (colOfPivot < tempCopy.nCols && tempCopy.data[i][colOfPivot] == 0) {
                colOfPivot++;
            }

            if (tempCopy.data[i][colOfPivot] != 0) {
                for (j=0; j<i && tempCopy.data[j][colOfPivot] != 0; j++) {
                    float ratio = tempCopy.data[j][colOfPivot]/tempCopy.data[i][colOfPivot];
                    // Operate Echelon matrix parallel with identity matrix
                    for (k=0; k<tempCopy.nCols; k++) {
                        tempCopy.data[j][k] -= tempCopy.data[i][k]*ratio;
                        identity.data[j][k] -= identity.data[i][k]*ratio;
                    }
                }
            }
        }

        identity.removeNegativeZero();
        return identity;
    }

}
