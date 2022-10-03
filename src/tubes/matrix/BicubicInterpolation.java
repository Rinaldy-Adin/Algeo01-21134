package tubes.matrix;

import java.awt.Color;
import java.lang.Math;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

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
    public static void scaleImage(String inputPath, String outputFileName) {
      try {
        BufferedImage image = ImageIO.read(new File(inputPath));

        for(int i = 0; i < 4; i++) {
          for(int j = 0; j < 4; j++) {
            System.out.print((new Color(image.getRGB(j, i))).getGreen() + " ");
          }
          System.out.println();
        }

        BufferedImage scaled = new BufferedImage(2*image.getWidth(), 2*image.getHeight(), image.getType());
        for(int i = 0; i < scaled.getHeight(); i++) {
          for(int j = 0; j < scaled.getWidth(); j++) {
            if(i == 0 && j == 0) {
              // Top-left corner
              scaled.setRGB(j, i, image.getRGB(0, 0));
            } else if(i == 0 && j == scaled.getWidth()-1) {
              // Top-right corner
              scaled.setRGB(j, i, image.getRGB(image.getWidth()-1, 0));
            } else if(i == scaled.getHeight()-1 && j == 0) {
              // Bottom-left corner
              scaled.setRGB(j, i, image.getRGB(0, image.getHeight()-1));
            } else if(i == scaled.getHeight()-1 && j == scaled.getHeight()-1) {
              // Bottom-right corner
              scaled.setRGB(j, i, image.getRGB(image.getWidth()-1, image.getHeight()-1));
            } else if(i == 0 || i == scaled.getHeight()-1) {
              // Top and bottom side
              int endJ = (j+1) / 2;

              double[][] dataRed = {{endJ-1, (new Color(image.getRGB(endJ-1, i/2))).getRed()}, {endJ, (new Color(image.getRGB(endJ, i/2))).getRed()}};
              double[][] dataGreen = {{endJ-1, (new Color(image.getRGB(endJ-1, i/2))).getGreen()}, {endJ, (new Color(image.getRGB(endJ, i/2))).getGreen()}};
              double[][] dataBlue = {{endJ-1, (new Color(image.getRGB(endJ-1, i/2))).getBlue()}, {endJ, (new Color(image.getRGB(endJ, i/2))).getBlue()}};
              double[][] dataAlpha = {{endJ-1, (new Color(image.getRGB(endJ-1, i/2))).getBlue()}, {endJ, (new Color(image.getRGB(endJ, i/2))).getBlue()}};


              Matrix pointsRed = new Matrix(dataRed);
              Matrix pointsGreen = new Matrix(dataGreen);
              Matrix pointsBlue = new Matrix(dataBlue);
              Matrix pointsAlpha = new Matrix(dataAlpha);

              int approxRed = (int) Interpolation.approximateFunction(pointsRed, 0.25f*((j-1)%2 + 1));
              int approxGreen = (int) Interpolation.approximateFunction(pointsGreen, 0.25f*((j-1)%2 + 1));
              int approxBlue = (int) Interpolation.approximateFunction(pointsBlue, 0.25f*((j-1)%2 + 1));
              int approxAlpha = (int) Interpolation.approximateFunction(pointsAlpha, 0.25f*((j-1)%2 + 1));

              int approxRGB = (approxAlpha << 24) + (approxRed << 16) + (approxGreen << 8) + approxBlue;

              scaled.setRGB(j, i, approxRGB);
            } else if(j == 0 || j == scaled.getHeight()-1) {
              // Left and right side
              int endI = (i+1) / 2;

              double[][] dataRed = {{endI-1, (new Color(image.getRGB(j/2, endI-1))).getRed()}, {endI, (new Color(image.getRGB(j/2, endI-1))).getRed()}};
              double[][] dataGreen = {{endI-1, (new Color(image.getRGB(j/2, endI-1))).getGreen()}, {endI, (new Color(image.getRGB(j/2, endI-1))).getGreen()}};
              double[][] dataBlue = {{endI-1, (new Color(image.getRGB(j/2, endI-1))).getBlue()}, {endI, (new Color(image.getRGB(j/2, endI-1))).getBlue()}};
              double[][] dataAlpha = {{endI-1, (new Color(image.getRGB(j/2, endI-1))).getBlue()}, {endI, (new Color(image.getRGB(j/2, endI-1))).getBlue()}};

              Matrix pointsRed = new Matrix(dataRed);
              Matrix pointsGreen = new Matrix(dataGreen);
              Matrix pointsBlue = new Matrix(dataBlue);
              Matrix pointsAlpha = new Matrix(dataAlpha);

              int approxRed = (int) Interpolation.approximateFunction(pointsRed, 0.25f*((j-1)%2 + 1));
              int approxGreen = (int) Interpolation.approximateFunction(pointsGreen, 0.25f*((j-1)%2 + 1));
              int approxBlue = (int) Interpolation.approximateFunction(pointsBlue, 0.25f*((j-1)%2 + 1));
              int approxAlpha = (int) Interpolation.approximateFunction(pointsAlpha, 0.25f*((j-1)%2 + 1));

              int approxRGB = (approxAlpha << 24) + (approxRed << 16) + (approxGreen << 8) + approxBlue;

              scaled.setRGB(j, i, approxRGB);
            } else {
              // Middle
              int endI = (i+1) / 2;
              int endJ = (j+1) / 2;

              Matrix redApproxMatrix = new Matrix(4, 4);
              Matrix greenApproxMatrix = new Matrix(4, 4);
              Matrix blueApproxMatrix = new Matrix(4, 4);
              Matrix alphaApproxMatrix = new Matrix(4, 4);
              for(int k = 0; k < 4; k++) {
                for(int l = 0; l < 4; l++) {
                  if(endI-2+k < 0) {
                    if(endJ-2+l < 0) {
                      redApproxMatrix.data[k][l] = (new Color(image.getRGB(0, 0))).getRed();
                      greenApproxMatrix.data[k][l] = (new Color(image.getRGB(0, 0))).getGreen();
                      blueApproxMatrix.data[k][l] = (new Color(image.getRGB(0, 0))).getBlue();
                      alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(0, 0))).getAlpha();
                    } else if(endJ-2+l == image.getWidth()) {
                      redApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, 0))).getRed();
                      greenApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, 0))).getGreen();
                      blueApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, 0))).getBlue();
                      alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, 0))).getAlpha();
                    } else {
                      redApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+l, 0))).getRed();
                      greenApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+l, 0))).getGreen();
                      blueApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+l, 0))).getBlue();
                      alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+l, 0))).getAlpha();
                    }
                  } else if(endI-2+k == image.getHeight()) {
                    if(endJ-2+l < 0) {
                      redApproxMatrix.data[k][l] = (new Color(image.getRGB(0, image.getHeight()-1))).getRed();
                      greenApproxMatrix.data[k][l] = (new Color(image.getRGB(0, image.getHeight()-1))).getGreen();
                      blueApproxMatrix.data[k][l] = (new Color(image.getRGB(0, image.getHeight()-1))).getBlue();
                      alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(0, image.getHeight()-1))).getAlpha();
                    } else if(endJ-2+l == image.getWidth()) {
                      redApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, image.getHeight()-1))).getRed();
                      greenApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, image.getHeight()-1))).getGreen();
                      blueApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, image.getHeight()-1))).getBlue();
                      alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, image.getHeight()-1))).getAlpha();
                    } else {
                      redApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+l, image.getHeight()-1))).getRed();
                      greenApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+l, image.getHeight()-1))).getGreen();
                      blueApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+l, image.getHeight()-1))).getBlue();
                      alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+l, image.getHeight()-1))).getAlpha();
                    }
                  } else if(endJ-2+l < 0) {
                    redApproxMatrix.data[k][l] = (new Color(image.getRGB(0, endI-2+k))).getRed();
                    greenApproxMatrix.data[k][l] = (new Color(image.getRGB(0, endI-2+k))).getGreen();
                    blueApproxMatrix.data[k][l] = (new Color(image.getRGB(0, endI-2+k))).getBlue();
                    alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(0, endI-2+k))).getAlpha();
                  } else if(endJ-2+l == image.getWidth()) {
                    redApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, endI-2+k))).getRed();
                    greenApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, endI-2+k))).getGreen();
                    blueApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, endI-2+k))).getBlue();
                    alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, endI-2+k))).getAlpha();
                  } else {
                    redApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+l, endI-2+k))).getRed();
                    greenApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+l, endI-2+k))).getGreen();
                    blueApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+l, endI-2+k))).getBlue();
                    alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+l, endI-2+k))).getAlpha();
                  }
                }
              }

              int approxRed = (int) interpolate(redApproxMatrix, 0.25f*(((j-1)%2) + 1), 0.25f*(((i-1)%2) + 1));
              int approxGreen = (int) interpolate(greenApproxMatrix, 0.25f*(((j-1)%2) + 1), 0.25f*(((i-1)%2) + 1));
              int approxBlue = (int) interpolate(blueApproxMatrix, 0.25f*(((j-1)%2) + 1), 0.25f*(((i-1)%2) + 1));
              int approxAlpha = (int) interpolate(alphaApproxMatrix, 0.25f*(((j-1)%2) + 1), 0.25f*(((i-1)%2) + 1));

              if(approxRed > 255) {
                approxRed = 255;
              } else if(approxRed < 0) {
                approxRed = 0;
              }

              if(approxGreen > 255) {
                approxGreen = 255;
              } else if(approxGreen < 0) {
                approxGreen = 0;
              }

              if(approxBlue > 255) {
                approxBlue = 255;
              } else if(approxBlue < 0) {
                approxBlue = 0;
              }

              if(approxAlpha > 255) {
                approxAlpha = 255;
              } else if(approxAlpha < 0) {
                approxAlpha = 0;
              }

              int approxRGB = (approxAlpha << 24) + (approxRed << 16) + (approxGreen << 8) + approxBlue;
              
              scaled.setRGB(j, i, approxRGB);
          }
        }
      }
        File output = new File(outputFileName);
        ImageIO.write(scaled, "png", output);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }

