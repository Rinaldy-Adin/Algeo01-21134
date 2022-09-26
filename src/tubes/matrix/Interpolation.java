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

  public static float approximateFunction(Matrix points) {
    Matrix coefficients = polynomialInterpolation(points);
    float output = 0.0f;
    for(int i = 0; i < coefficients.getNRows(); i++) {
      output += coefficients.data[i][0]*Math.pow(points.data[i][0], i);
    }
    return output;
  }
}
