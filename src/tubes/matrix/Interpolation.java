package tubes.matrix;

public class Interpolation {
  public static Matrix polynomialInterpolation(Matrix points) {
    Matrix matrix = new Matrix(points.getNRows(), points.getNRows() + 1);

    for(int i = 0; i < matrix.getNRows(); i++) {
      for(int j = 0; j < matrix.getNCols(); j++) {
        if(j != matrix.getNCols() - 1) {
          matrix.data[i][j] = (float) Math.pow(points.data[i][0], j);
        } else {
          matrix.data[i][j] = points.data[i][1];
        }
      }
    }

    return LinearEquation.cramerRule(matrix);
  }
}
