package tubes.matrix;

public class Inverse {
    // Pre-condition :
    // 1. Matrix is square
    // 2. Matrix is non-singular
    public static Matrix gaussJordanInverse (Matrix matrix) {
        // Pre-condition : Matrix is square
        int i, j, k;

        // Declare and initialize Identity Matrix
        Matrix identity = Matrix.createIdentityMatrix(matrix.nRows);

        // Using Gauss-Jordan Elimination Algorithm
        int currentRow = 0;
        for (j = 0; j < matrix.nCols && currentRow < matrix.nRows; j++) {
            for (i = currentRow + 1; i < matrix.nRows && matrix.data[currentRow][j] == 0; i++) {
                if (matrix.data[i][j] != 0) {
                    matrix.swapRow(currentRow, i);
                    identity.swapRow(currentRow, i);
                }
            }

            if (matrix.data[currentRow][j] != 0) {
                identity.divideRowByK(currentRow, matrix.data[currentRow][j]);
                matrix.divideRowByK(currentRow, matrix.data[currentRow][j]);

                for (i = currentRow + 1; i < matrix.nRows; i++) {
                    float[] rowArray = matrix.getRowAsArray(currentRow);
                    float[] rowIdentity = identity.getRowAsArray(currentRow);
                    for (k = 0; k < matrix.nCols; k++) {
                        rowArray[k] *= matrix.data[i][j];
                        rowIdentity[k] *= matrix.data[i][j];
                    }

                    matrix.subtractRowByArray(i, rowArray);
                    identity.subtractRowByArray(i, rowIdentity);
                }

                currentRow++;
            }
        }

        for (i=1; i<matrix.nRows; i++) {
            int colOfPivot = 0;
            while (matrix.data[i][colOfPivot] == 0 && colOfPivot < matrix.nCols) {
                colOfPivot++;
            }

            if (matrix.data[i][colOfPivot] != 0) {
                for (j=0; j<i && matrix.data[j][colOfPivot] != 0; j++) {
                    float ratio = matrix.data[j][colOfPivot]/matrix.data[i][colOfPivot];
                    // Operate Echelon matrix parallel with identity matrix
                    for (k=0; k<matrix.nCols; k++) {
                        matrix.data[j][k] -= matrix.data[i][k]*ratio;
                        identity.data[j][k] -= identity.data[i][k]*ratio;
                    }
                }
            }
        }

        identity.removeNegativeZero();
        return identity;
    }

  public static Matrix adjoinMethod(Matrix matrix) {
    Matrix cofactorMatrix = new Matrix(matrix.getNRows(), matrix.getNCols());
    for(int i = 0; i < matrix.getNRows(); i++) {
      for(int j = 0; j < matrix.getNCols(); j++) {
        Matrix minor = new Matrix(matrix.getNRows() - 1, matrix.getNCols() - 1);
        for(int k = 0; k < i; k++) {
          for(int l = 0; l < j; l++) {
            minor.data[k][l] = matrix.data[k][l];
          }
        }
        for(int k = 0; k < i; k++) {
          for(int l = j+1; l < matrix.getNCols(); l++) {
            minor.data[k][l-1] = matrix.data[k][l];
          }
        }
        for(int k = i+1; k < matrix.getNRows(); k++) {
          for(int l = 0; l < j; l++) {
            minor.data[k-1][l] = matrix.data[k][l];
          }
        }
        for(int k = i+1; k < matrix.getNRows(); k++) {
          for(int l = j+1; l < matrix.getNCols(); l++) {
            minor.data[k-1][l-1] = matrix.data[k][l];
          }
        }

        if((i+j)%2 == 0) {
          cofactorMatrix.data[i][j] = Determinant.cofactor(minor);
        } else {
          cofactorMatrix.data[i][j] = -1.0f*Determinant.cofactor(minor);
        }
      }
    }

    float mul = 1 / Determinant.cofactor(matrix);
    cofactorMatrix.transpose();
    for(int i = 0; i < cofactorMatrix.getNRows(); i++) {
      cofactorMatrix.multiplyRowByK(i, mul);
    }
    return cofactorMatrix;
  }
}
