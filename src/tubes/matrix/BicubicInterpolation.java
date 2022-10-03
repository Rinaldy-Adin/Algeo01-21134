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

    public static void scaleImage(String inputPath, String outputFileName) {
      try {
        BufferedImage image = ImageIO.read(new File(inputPath));
        // BufferedImage image = new BufferedImage(4, 4, test.getType());
        // image.setRGB(0, 0, 0xFFFF0000);
        // image.setRGB(1, 0, 0xFFFF0000);
        // image.setRGB(0, 1, 0xFFFF0000);
        // image.setRGB(1, 1, 0xFFFF0000);

        // image.setRGB(0, 2, 0xFF00FF00);
        // image.setRGB(0, 3, 0xFF00FF00);
        // image.setRGB(1, 2, 0xFF00FF00);
        // image.setRGB(1, 3, 0xFF00FF00);

        // image.setRGB(2, 0, 0xFF0000FF);
        // image.setRGB(2, 1, 0xFF0000FF);
        // image.setRGB(3, 0, 0xFF0000FF);
        // image.setRGB(3, 1, 0xFF0000FF);
        
        // image.setRGB(2, 2, 0xFF000000);
        // image.setRGB(2, 3, 0xFF000000);
        // image.setRGB(3, 2, 0xFF000000);
        // image.setRGB(3, 3, 0xFF000000);

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

              float[][] dataRed = {{endJ-1, (new Color(image.getRGB(endJ-1, i/2))).getRed()}, {endJ, (new Color(image.getRGB(endJ, i/2))).getRed()}};
              float[][] dataGreen = {{endJ-1, (new Color(image.getRGB(endJ-1, i/2))).getGreen()}, {endJ, (new Color(image.getRGB(endJ, i/2))).getGreen()}};
              float[][] dataBlue = {{endJ-1, (new Color(image.getRGB(endJ-1, i/2))).getBlue()}, {endJ, (new Color(image.getRGB(endJ, i/2))).getBlue()}};
              float[][] dataAlpha = {{endJ-1, (new Color(image.getRGB(endJ-1, i/2))).getBlue()}, {endJ, (new Color(image.getRGB(endJ, i/2))).getBlue()}};


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

              float[][] dataRed = {{endI-1, (new Color(image.getRGB(j/2, endI-1))).getRed()}, {endI, (new Color(image.getRGB(j/2, endI-1))).getRed()}};
              float[][] dataGreen = {{endI-1, (new Color(image.getRGB(j/2, endI-1))).getGreen()}, {endI, (new Color(image.getRGB(j/2, endI-1))).getGreen()}};
              float[][] dataBlue = {{endI-1, (new Color(image.getRGB(j/2, endI-1))).getBlue()}, {endI, (new Color(image.getRGB(j/2, endI-1))).getBlue()}};
              float[][] dataAlpha = {{endI-1, (new Color(image.getRGB(j/2, endI-1))).getBlue()}, {endI, (new Color(image.getRGB(j/2, endI-1))).getBlue()}};

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
              int endI = (i+1) / 2;
              int endJ = (j+1) / 2;

              Matrix redApproxMatrix = new Matrix(4, 4);
              Matrix greenApproxMatrix = new Matrix(4, 4);
              Matrix blueApproxMatrix = new Matrix(4, 4);
              Matrix alphaApproxMatrix = new Matrix(4, 4);
              for(int k = 0; k < 4; k++) {
                for(int l = 0; l < 4; l++) {
                  if(endI-2+k < 0) {
                    if(endJ-2+k < 0) {
                      redApproxMatrix.data[k][l] = (new Color(image.getRGB(0, 0))).getRed();
                      greenApproxMatrix.data[k][l] = (new Color(image.getRGB(0, 0))).getGreen();
                      blueApproxMatrix.data[k][l] = (new Color(image.getRGB(0, 0))).getBlue();
                      alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(0, 0))).getBlue();
                    } else if(endJ-2+k == image.getWidth()) {
                      redApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, 0))).getRed();
                      greenApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, 0))).getGreen();
                      blueApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, 0))).getBlue();
                      alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, 0))).getBlue();
                    } else {
                      redApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+k, 0))).getRed();
                      greenApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+k, 0))).getGreen();
                      blueApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+k, 0))).getBlue();
                      alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+k, 0))).getBlue();
                    }
                  } else if(endI-2+k == image.getHeight()) {
                    if(endJ-2+k < 0) {
                      redApproxMatrix.data[k][l] = (new Color(image.getRGB(0, image.getHeight()-1))).getRed();
                      greenApproxMatrix.data[k][l] = (new Color(image.getRGB(0, image.getHeight()-1))).getGreen();
                      blueApproxMatrix.data[k][l] = (new Color(image.getRGB(0, image.getHeight()-1))).getBlue();
                      alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(0, image.getHeight()-1))).getBlue();
                    } else if(endJ-2+k == image.getWidth()) {
                      redApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, image.getHeight()-1))).getRed();
                      greenApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, image.getHeight()-1))).getGreen();
                      blueApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, image.getHeight()-1))).getBlue();
                      alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, image.getHeight()-1))).getBlue();
                    } else {
                      redApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+k, image.getHeight()-1))).getRed();
                      greenApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+k, image.getHeight()-1))).getGreen();
                      blueApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+k, image.getHeight()-1))).getBlue();
                      alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+k, image.getHeight()-1))).getBlue();
                    }
                  } else if(endJ-2+k < 0) {
                    redApproxMatrix.data[k][l] = (new Color(image.getRGB(0, endI-2+k))).getRed();
                    greenApproxMatrix.data[k][l] = (new Color(image.getRGB(0, endI-2+k))).getGreen();
                    blueApproxMatrix.data[k][l] = (new Color(image.getRGB(0, endI-2+k))).getBlue();
                    alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(0, endI-2+k))).getBlue();
                  } else if(endJ-2+k == image.getWidth()) {
                    redApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, endI-2+k))).getRed();
                    greenApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, endI-2+k))).getGreen();
                    blueApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, endI-2+k))).getBlue();
                    alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(image.getWidth()-1, endI-2+k))).getBlue();
                  } else {
                    redApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+k, endI-2+k))).getRed();
                    greenApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+k, endI-2+k))).getGreen();
                    blueApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+k, endI-2+k))).getBlue();
                    alphaApproxMatrix.data[k][l] = (new Color(image.getRGB(endJ-2+k, endI-2+k))).getBlue();
                  }
                }
              }

              int approxRed = (int) interpolate(redApproxMatrix, 0.25f*(((j-1)%2) + 1), 0.25f*(((i-1)%2) + 1));
              int approxGreen = (int) interpolate(greenApproxMatrix, 0.25f*(((j-1)%2) + 1), 0.25f*(((i-1)%2) + 1));
              int approxBlue = (int) interpolate(blueApproxMatrix, 0.25f*(((j-1)%2) + 1), 0.25f*(((i-1)%2) + 1));
              int approxAlpha = (int) interpolate(alphaApproxMatrix, 0.25f*(((j-1)%2) + 1), 0.25f*(((i-1)%2) + 1));

              int approxRGB = (approxAlpha << 24) + (approxRed << 16) + (approxGreen << 8) + approxBlue;

              scaled.setRGB(j, i, approxRGB);
              // System.out.println("=========================");
              // redApproxMatrix.display();
              // System.out.println();
              // greenApproxMatrix.display();
              // System.out.println();
              // blueApproxMatrix.display();
              // System.out.println("==================");
            }

            // System.out.print(String.format("0x%08X", scaled.getRGB(j, i)) + " ");
          }
          // System.out.println();
        }

        File output = new File(outputFileName);
        ImageIO.write(scaled, "jpg", output);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
}
