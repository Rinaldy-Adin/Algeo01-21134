import java.util.Scanner;

import tubes.matrix.*;
import tubes.util.Util;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        boolean isActive = true;
        while(isActive) {
          System.out.println("MENU");
          System.out.println("1. Sistem Persamaan Linier");
          System.out.println("2. Determinan");
          System.out.println("3. Matriks balikan");
          System.out.println("4. Interpolasi polinom");
          System.out.println("5. Interpolasi bicubic");
          System.out.println("6. Regresi linier berganda");
          System.out.println("7. Keluar");

          int input = scan.nextInt();

          if(input == 1) {
            // SPL
            Matrix tempMtx;

            System.out.println("1. Metode eliminasi Gauss");
            System.out.println("2. Metode Eliminasi Gauss-Jordan");
            System.out.println("3. Metode matriks balikan");
            System.out.println("4. Kaidah Cramer");
            int inputSub = scan.nextInt();

            System.out.println("1. Masukkan melalui keyboard");
            System.out.println("2. Masukkan melalui file");
            int inputMethod = scan.nextInt();

            Matrix matrix;

            // Get method of matrix input
            if(inputMethod == 1) {
              System.out.print("Jumlah baris matrix: ");
              int n = scan.nextInt();
              System.out.print("Jumlah kolom matrix: ");
              int m = scan.nextInt();
              System.out.println("Masukkan matrix: ");

              matrix = new Matrix(n, m);
              for(int i = 0; i < matrix.getNRows(); i++) {
                for(int j = 0; j < matrix.getNCols(); j++) {
                  matrix.data[i][j] = scan.nextFloat();
                }
              }
              scan.nextLine();
            } 
            else if (inputMethod == 2) {
              System.out.print("Lokasi file masukkan: ");
              scan.nextLine();
              String pathString = scan.nextLine();

              float[][] data = Util.readFromFile(pathString);
              matrix = new Matrix(data);
            } else {
              matrix = new Matrix(0, 0);
            }
            
            // Input Sub-Menu
            if (inputSub == 1) {
              tempMtx = LinearEquation.gaussianElimination(matrix);
              // Solve linear eq. here
              String[] result = LinearEquation.solveGauss(matrix);
              // Display solution
            }
            else if (inputSub == 2) {
              tempMtx = LinearEquation.gaussJordanElimination(matrix);
              // Solve linear eq. here
              String[] result = LinearEquation.solveGaussJordan(matrix);
              // Display solution
            }
            else if (inputSub == 3) {
              Matrix coefficient = new Matrix(matrix.getNRows(), matrix.getNCols()-1);
              Matrix constant = new Matrix(matrix.getNRows(), 1);
              int i, j; 
              // Get coefficients matrix
              for (i=0; i<coefficient.getNRows(); i++) {
                for (j=0; j<coefficient.getNCols(); j++) {
                  coefficient.data[i][j] = matrix.data[i][j];
                }
              }
              
              // Get constants matrix
              for (i=0; i<constant.getNRows(); i++) {
                constant.data[i][0] = matrix.data[i][matrix.getNCols()-1];
              }
              
              tempMtx = LinearEquation.solveLinearWithInverse(coefficient, constant);
              // Solve linear eq. here
              // Display solution
              tempMtx.display();
            }

            else if (inputSub == 4) {
              LinearEquation.cramerRule(matrix).display();
            }

          } else if(input == 2) {
            // Determinan
            System.out.println("1. Metode reduksi baris");
            System.out.println("2. Metode kofaktor");
            int inputSub = scan.nextInt();

            System.out.println("1. Masukkan melalui keyboard");
            System.out.println("2. Masukkan melalui file");
            int inputMethod = scan.nextInt();

            Matrix matrix;
            if(inputMethod == 1) {
              System.out.print("Input ukuran matrix n*n: ");
              int n = scan.nextInt();
              System.out.println("Masukkan matrix: ");

              matrix = new Matrix(n, n);
              for(int i = 0; i < matrix.getNRows(); i++) {
                for(int j = 0; j < matrix.getNCols(); j++) {
                  matrix.data[i][j] = scan.nextFloat();
                }
              }
              scan.nextLine();
            } else if(inputMethod == 2) {
              System.out.print("Lokasi file masukkan: ");
              scan.nextLine();
              String pathString = scan.nextLine();

              float[][] data = Util.readFromFile(pathString);
              matrix = new Matrix(data);
            } else {
              matrix = new Matrix(0, 0);
            }

            float determinant = 0.0f;
            if(inputSub == 1) {
              determinant = Determinant.rowReduction(matrix);
            } else if(inputSub == 2) {
              determinant = Determinant.cofactor(matrix);
            }
            String outputString = "Determinan: " + determinant;
            System.out.print("Lokasi folder output: ");
            String folder = scan.nextLine();
            System.out.print("Nama file: ");
            String fileName = scan.nextLine();

            Util.writeToFile(folder, fileName, outputString);
            System.out.println(outputString);
            
          } else if(input == 3) {
            // Inverse
            System.out.println("1. Metode Gauss-Jordan");
            System.out.println("2. Metode Adjoin");

            int inputSub = scan.nextInt();

            System.out.println("1. Masukkan melalui keyboard");
            System.out.println("2. Masukkan melalui file");
            int inputMethod = scan.nextInt();

            Matrix matrix;
            if (inputMethod == 1) {
              System.out.print("Input ukuran matrix n*n: ");
              int n = scan.nextInt();
              System.out.println("Masukkan matrix: ");
              matrix = new Matrix(n, n);
              for(int i = 0; i < matrix.getNRows(); i++) {
                for(int j = 0; j < matrix.getNCols(); j++) {
                  matrix.data[i][j] = scan.nextFloat();
                }
              }
              scan.nextLine();
            } 
            else if (inputMethod == 2) {
              System.out.print("Lokasi file masukkan: ");
              scan.nextLine();
              String pathString = scan.nextLine();

              float[][] data = Util.readFromFile(pathString);
              matrix = new Matrix(data);
            } else {
              matrix = new Matrix(0, 0);
            }

            if (inputSub == 1) {
              Inverse.gaussJordanInverse(matrix).display();
            }
            else if (inputSub == 2) {
              Inverse.adjoinMethod(matrix).display();
            }

          } else if(input == 4) {
            // Interpolasi Polinom
            System.out.println("1. Masukkan melalui keyboard");
            System.out.println("2. Masukkan melalui file");
            int inputMethod = scan.nextInt();

            Matrix points;
            if(inputMethod == 1) {
              System.out.print("Masukkan jumlah titik: ");
              int n = scan.nextInt();
              System.out.println("Masukkan titik: ");

              points = new Matrix(n, 2);
              for(int i = 0; i < points.getNRows(); i++) {
                for(int j = 0; j < points.getNCols(); j++) {
                  points.data[i][j] = scan.nextFloat();
                }
              }
            } else if(inputMethod == 2) {
              System.out.print("Lokasi file masukkan: ");
              scan.nextLine();
              String pathString = scan.nextLine();

              float[][] data = Util.readFromFile(pathString);
              points = new Matrix(data);
            } else {
              points = new Matrix(0, 0);
            }

            System.out.print("Masukkan nilai x yang akan diaproksimasi: ");
            float x = scan.nextFloat();
            scan.nextLine();

            Matrix coefficients = Interpolation.polynomialInterpolation(points);
            float approx = Interpolation.approximateFunction(points, x);

            String outputString = "f(x) = ";
            for(int i = 0; i < coefficients.getNRows(); i++) {
              if(i == 0) {
                outputString += coefficients.data[i][0] + "x";
              } else if(i != 0) {
                if(coefficients.data[i][0] > 0) {
                  outputString += " + " + coefficients.data[i][0] + "x";
                } else if(coefficients.data[i][0] < 0) {
                  outputString += " - " + -coefficients.data[i][0] + "x";
                }
              }

              if(i != coefficients.getNRows() - 1) {
                outputString += "^" + (coefficients.getNRows() - 1 - i);
              }
            }
            outputString += ", f(" + x + ") = " + approx;

            System.out.print("Lokasi folder output: ");
            String folder = scan.nextLine();
            System.out.print("Nama file: ");
            String fileName = scan.nextLine();

            Util.writeToFile(folder, fileName, outputString);
            System.out.println(outputString);
          } else if(input == 5) {
            // Bicubic Interpolation
            System.out.print("Lokasi file masukkan: ");
            scan.nextLine();
            String pathString = scan.nextLine();

            float[][] data = Util.readFromFile(pathString);
            Matrix temp = new Matrix(data);

            Matrix matrix = new Matrix(temp.getNRows()-1, temp.getNCols());
            for(int i = 0; i < temp.getNRows()-1; i++) {
              for(int j = 0; j < temp.getNCols(); j++) {
                matrix.data[i][j] = temp.data[i][j];
              }
            }

            float x = temp.data[temp.getNRows()-1][0];
            float y = temp.data[temp.getNRows()-1][1];

            float val = BicubicInterpolation.interpolate(matrix, x, y);
            String outputString = "f(" + x + ", " + y + ") = " + val;
            
            System.out.print("Lokasi folder output: ");
            String folder = scan.nextLine();
            System.out.print("Nama file: ");
            String fileName = scan.nextLine();

            Util.writeToFile(folder, fileName, outputString);
            System.out.println(outputString);
          } else if(input == 6) {
            // Regresi Linear Berganda
            System.out.println("1. Masukkan melalui keyboard");
            System.out.println("2. Masukkan melalui file");
            int inputMethod = scan.nextInt();

            Matrix x;
            Matrix y;
            Matrix k;

            if (inputMethod == 1) {
              System.out.println("Masukkan jumlah peubah x: ");
              int n = scan.nextInt();
              System.out.println("Masukkan jumlah sampel: ");
              int m = scan.nextInt();

              x = new Matrix(n, m);
              y = new Matrix(m, 1);
              k = new Matrix(n, m);

              System.out.println("Masukkan matriks X dengan format sebagai berikut: ");
              System.out.println("[ [X1_1, X2_1, ... Xn_1]");
              System.out.println("  [X1_2, X2_2, ... Xn_2]");
              System.out.println("   ...   ...   ...  ...");
              System.out.println("  [X1_m, X2_m, ... Xn_m] ]");
              System.out.println();

              int i;
              int j;

              for (i=0; i<x.getNRows(); i++) {
                for (j=0; j<x.getNCols(); j++) {
                  x.data[i][j] = scan.nextFloat();
                }
              }

              System.out.println("Masukkan matriks Y dengan format sebagai berikut: ");
              System.out.println("[ [Y1]");
              System.out.println("  [Y2]");
              System.out.println("  ...");
              System.out.println("  [Ym] ]");
              System.out.println();

              for (i=0; i<y.getNRows(); i++) {
                for (j=0; j<y.getNCols(); j++) {
                  y.data[i][j] = scan.nextFloat();
                }
              }

              System.out.println("Masukkan matriks Y dengan format sebagai berikut: ");
              System.out.println("[[X1, X2, ... Xn]]");
              System.out.println();

              for (i=0; i<k.getNCols(); i++) {
                k.data[0][i] = scan.nextFloat();
              }
            }
            else if(inputMethod == 2) {
              System.out.print("Lokasi file masukan: ");
              scan.nextLine();
              String pathString = scan.nextLine();
              
              float[][] data = Util.readFromFile(pathString);
              x = new Matrix(data.length-1, data[0].length-1);
              y = new Matrix(data.length-1, 1);
              k = new Matrix(0, data[0].length-1);

              int i, j;

              for (i=0; i<x.getNRows(); i++) {
                for (j=0; j<x.getNCols(); j++) {
                  x.data[i][j] = data[i][j];
                }
              }

              for (i=0; i<y.getNRows(); i++) {
                y.data[i][0] = data[i][data[0].length-1];
              }

              for (i=0; i<k.getNCols(); i++) {
                k.data[0][i] = data[data.length-1][i];
              }
            }
            else {
              x = new Matrix(0, 0);
              y = new Matrix(0, 0);
              k = new Matrix(0, 0);
            }

            // Transpose x such that it follows the formatting in LinearRegression class
            Matrix.transpose(x);
            System.out.println("Persamaan regresi: ");
            LinearRegression.writeMLREquation(x, y);
            System.out.println();
            System.out.println("Hasil taksiran Xk: ");
            System.out.println(LinearRegression.approxMLR(x, y, k));
            

          } else if(input == 7) {
            // Keluar
            isActive = false;
          }
        }

        scan.close();
    }
}
