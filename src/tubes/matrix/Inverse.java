package tubes.matrix;

public class Inverse {
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
