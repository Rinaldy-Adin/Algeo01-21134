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
          System.out.println();
          
          System.out.print("Input: ");
          int input = scan.nextInt();
          System.out.println();

          if(input == 1) {
            // SPL
            Matrix tempMtx;

            System.out.println("1. Metode eliminasi Gauss");
            System.out.println("2. Metode eliminasi Gauss-Jordan");
            System.out.println("3. Metode matriks balikan");
            System.out.println("4. Kaidah Cramer");
            
            System.out.println();
            System.out.print("Input: ");
            int inputSub = scan.nextInt();
            System.out.println();

            System.out.println("1. Masukkan melalui keyboard");
            System.out.println("2. Masukkan melalui file");

            System.out.println();
            System.out.print("Input: ");
            int inputMethod = scan.nextInt();
            System.out.println();

            Matrix matrix;
            String outputString = "";

            // Get method of matrix input
            if(inputMethod == 1) {
              System.out.print("Jumlah baris matrix: ");
              int n = scan.nextInt();
              System.out.println();
              System.out.print("Jumlah kolom matrix: ");
              int m = scan.nextInt();
              System.out.println();
              System.out.println("Masukkan matrix: ");
              System.out.println();

              matrix = new Matrix(n, m);
              for(int i = 0; i < matrix.getNRows(); i++) {
                for(int j = 0; j < matrix.getNCols(); j++) {
                  matrix.data[i][j] = scan.nextDouble();
                }
              }
              scan.nextLine();
              System.out.println();
            } 

            else if (inputMethod == 2) {
              System.out.print("Lokasi file masukan: ");
              scan.nextLine();
              String pathString = scan.nextLine();
              System.out.println();

              double[][] data = Util.readFromFile(pathString);
              matrix = new Matrix(data);
            } else {
              matrix = new Matrix(0, 0);
            }
            
            // Input Sub-Menu
            if (inputSub == 1) {
              // Solve linear eq. here
              String[] result = LinearEquation.solveGauss(matrix);
              // Display solution
              int i;
              for (i=0; i<result.length; i++) {
                outputString += "X" + (i+1) + " = ";
                outputString += result[i];
                if (i<result.length-1) {
                  outputString += ", ";
                }
              }
            }

            else if (inputSub == 2) {
              // Solve linear eq. here
              String[] result = LinearEquation.solveGaussJordan(matrix);
              // Display solution
              int i;
              for (i=0; i<result.length; i++) {
                outputString += "X" + (i+1) + " = ";
                outputString += result[i];
                if (i<result.length-1) {
                  outputString += ", ";
                }
              }
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
              
              // Solve linear eq. here
              tempMtx = Matrix.transpose(LinearEquation.solveLinearWithInverse(coefficient, constant));
              // Display solution
              double result[] = tempMtx.data[0];
              for (i=0; i<result.length; i++) {
                outputString += "X" + (i+1) + " = ";
                outputString += result[i];
                if (i<result.length-1) {
                  outputString += ", ";
                }
              }
            }

            else if (inputSub == 4) {
              Matrix cramer = Matrix.transpose(LinearEquation.cramerRule(matrix));
              double result[] = cramer.data[0];
              int i;
              for (i=0; i<result.length; i++) {
                outputString += "X" + (i+1) + " = ";
                outputString += result[i];
                if (i<result.length-1) {
                  outputString += ", ";
                }
              }
            }
            
            System.out.println();
            System.out.print("Nama file: ");
            String fileName = scan.nextLine();
            System.out.println();

            Util.writeToFile(fileName, outputString);
            System.out.println(outputString);
            System.out.println();
          } 
          
          else if(input == 2) {
            // Determinant
            System.out.println("1. Metode reduksi baris");
            System.out.println("2. Metode kofaktor");

            System.out.println();
            System.out.print("Input: ");
            int inputSub = scan.nextInt();
            System.out.println();

            System.out.println("1. Masukkan melalui keyboard");
            System.out.println("2. Masukkan melalui file");

            System.out.println();
            System.out.print("Input: ");
            int inputMethod = scan.nextInt();
            System.out.println();

            Matrix matrix;
            if(inputMethod == 1) {
              System.out.print("Masukkan ukuran matrix n*n: ");
              int n = scan.nextInt();
              System.out.println();
              System.out.println("Masukkan matrix: ");
              System.out.println();

              matrix = new Matrix(n, n);
              for(int i = 0; i < matrix.getNRows(); i++) {
                for(int j = 0; j < matrix.getNCols(); j++) {
                  matrix.data[i][j] = scan.nextDouble();
                }
              }
              scan.nextLine();
            } else if(inputMethod == 2) {
              System.out.print("Lokasi file masukan: ");
              scan.nextLine();
              String pathString = scan.nextLine();
              System.out.println();

              double[][] data = Util.readFromFile(pathString);
              matrix = new Matrix(data);
            } else {
              matrix = new Matrix(0, 0);
            }

            double determinant = 0.0f;
            if(inputSub == 1) {
              determinant = Determinant.rowReduction(matrix);
            } else if(inputSub == 2) {
              determinant = Determinant.cofactor(matrix);
            }

            String outputString = "Determinan: " + determinant;
            System.out.println();
            System.out.println();
            System.out.print("Nama file: ");
            String fileName = scan.nextLine();
            System.out.println();

            Util.writeToFile(fileName, outputString);
            System.out.println(outputString);
            System.out.println();
          } 
          
          else if(input == 3) {
            // Inverse
            System.out.println("1. Metode Gauss-Jordan");
            System.out.println("2. Metode Adjoin");

            System.out.println();
            System.out.print("Input: ");
            int inputSub = scan.nextInt();
            System.out.println();

            System.out.println("1. Masukkan melalui keyboard");
            System.out.println("2. Masukkan melalui file");

            System.out.println();
            System.out.print("Input: ");
            int inputMethod = scan.nextInt();
            System.out.println();

            Matrix matrix;
            String outputString = "";

            if (inputMethod == 1) {
              System.out.print("Masukkan ukuran matrix n*n: ");
              int n = scan.nextInt();
              System.out.println();
              System.out.println("Masukkan matrix: ");
              System.out.println();
              matrix = new Matrix(n, n);
              for(int i = 0; i < matrix.getNRows(); i++) {
                for(int j = 0; j < matrix.getNCols(); j++) {
                  matrix.data[i][j] = scan.nextDouble();
                }
              }
              scan.nextLine();
              System.out.println();
            } 
            else if (inputMethod == 2) {
              System.out.print("Lokasi file masukan: ");
              scan.nextLine();
              String pathString = scan.nextLine();
              System.out.println();

              double[][] data = Util.readFromFile(pathString);
              matrix = new Matrix(data);
            } else {
              matrix = new Matrix(0, 0);
            }
            
            if (Determinant.cofactor(matrix) == 0) {
              outputString += "Matrix tidak memiliki invers";
            }
            else {
              Matrix inv;
              if (inputSub == 1) {
                inv = Matrix.copyMatrix(Inverse.gaussJordanInverse(matrix));
              }
              else {
                inv = Matrix.copyMatrix(Inverse.adjoinMethod(matrix));
              }
  
              // Fill in output string
              int i, j;
              for (i=0; i<inv.getNRows(); i++) {
                for (j=0; j<inv.getNCols(); j++) {
                  outputString += inv.data[i][j];
                  if (j < inv.getNCols()-1) {
                    outputString += " ";
                  }
                  else if (i < inv.getNRows()-1) {
                    outputString += "\n";
                  }
                }
              }
            }

            System.out.println();
            System.out.print("Nama file: ");
            String fileName = scan.nextLine();
            System.out.println();

            Util.writeToFile(fileName, outputString);
            System.out.println(outputString);
            System.out.println();
          } 
          
          else if(input == 4) {
            // Interpolasi Polinom
            System.out.println("1. Masukkan melalui keyboard");
            System.out.println("2. Masukkan melalui file");

            System.out.println();
            System.out.print("Input: ");
            int inputMethod = scan.nextInt();
            System.out.println();

            Matrix points;
            if(inputMethod == 1) {
              System.out.print("Masukkan jumlah titik: ");
              int n = scan.nextInt();
              System.out.println();
              System.out.println("Masukkan titik: ");
              System.out.println();

              points = new Matrix(n, 2);
              for(int i = 0; i < points.getNRows(); i++) {
                for(int j = 0; j < points.getNCols(); j++) {
                  points.data[i][j] = scan.nextDouble();
                }
              }
              System.out.println();

            } else if(inputMethod == 2) {
              System.out.print("Lokasi file masukan: ");
              scan.nextLine();
              String pathString = scan.nextLine();
              System.out.println();

              double[][] data = Util.readFromFile(pathString);
              points = new Matrix(data);
            } else {
              points = new Matrix(0, 0);
            }

            System.out.print("Masukkan nilai x yang akan diaproksimasi: ");
            double x = scan.nextDouble();
            scan.nextLine();
            System.out.println();

            Matrix coefficients = Interpolation.polynomialInterpolation(points);
            double approx = Interpolation.approximateFunction(points, x);

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

            System.out.println();
            System.out.print("Nama file: ");
            String fileName = scan.nextLine();
            System.out.println();

            Util.writeToFile(fileName, outputString);
            System.out.println(outputString);
            System.out.println();
          } 
          
          else if(input == 5) {
            // Bicubic Interpolation
            System.out.print("Lokasi file masukan: ");
            scan.nextLine();
            String pathString = scan.nextLine();
            System.out.println();

            double[][] data = Util.readFromFile(pathString);
            Matrix temp = new Matrix(data);

            Matrix matrix = new Matrix(temp.getNRows()-1, temp.getNCols());
            for(int i = 0; i < temp.getNRows()-1; i++) {
              for(int j = 0; j < temp.getNCols(); j++) {
                matrix.data[i][j] = temp.data[i][j];
              }
            }

            double x = temp.data[temp.getNRows()-1][0];
            double y = temp.data[temp.getNRows()-1][1];

            double val = BicubicInterpolation.interpolate(matrix, x, y);
            String outputString = "f(" + x + ", " + y + ") = " + val;
            
            System.out.println();
            System.out.print("Nama file: ");
            String fileName = scan.nextLine();
            System.out.println();

            Util.writeToFile(fileName, outputString);
            System.out.println(outputString);
            System.out.println();
          } 
          
          else if(input == 6) {
            // Multiple Linear Regression
            System.out.println("1. Masukkan melalui keyboard");
            System.out.println("2. Masukkan melalui file");

            System.out.println();
            System.out.print("Input: ");
            int inputMethod = scan.nextInt();
            System.out.println();

            Matrix x;
            Matrix y;
            Matrix k;
            String outputStringEq = "";
            String outputStringApx = "";
            String outputStringFile;

            if (inputMethod == 1) {
              System.out.print("Masukkan jumlah peubah x: ");
              int n = scan.nextInt();
              System.out.println();
              System.out.print("Masukkan jumlah sampel: ");
              int m = scan.nextInt();
              System.out.println();

              x = new Matrix(m, n);
              y = new Matrix(m, 1);
              k = new Matrix(1, n);

              System.out.println("Masukkan matriks regressor (x): ");
              System.out.println();

              int i;
              int j;

              for (i=0; i<x.getNRows(); i++) {
                for (j=0; j<x.getNCols(); j++) {
                  x.data[i][j] = scan.nextDouble();
                }
              }
              System.out.println();

              System.out.println("Masukkan matriks variabel dependen (y): ");
              System.out.println();

              for (i=0; i<y.getNRows(); i++) {
                for (j=0; j<y.getNCols(); j++) {
                  y.data[i][j] = scan.nextDouble();
                }
              }
              System.out.println();

              System.out.println("Masukkan nilai yang akan ditaksir (Xk): ");
              System.out.println();
              
              for (i=0; i<k.getNCols(); i++) {
                k.data[0][i] = scan.nextDouble();
              }
              System.out.println();
            }

            else if(inputMethod == 2) {
              System.out.print("Lokasi file masukan: ");
              scan.nextLine();
              String pathString = scan.nextLine();
              System.out.println();
              
              double[][] data = Util.readFromFile(pathString);
              x = new Matrix(data.length-1, data[0].length-1);
              y = new Matrix(data.length-1, 1);
              k = new Matrix(1, data[0].length-1);

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

            // Transpose x such that it follows the formatting needed in LinearRegression class
            Matrix transX = Matrix.transpose(x);
            
            // Fill in output string
            outputStringEq += LinearRegression.writeMLREquation(transX, y);

            int i;
            outputStringApx += "f(";
            for (i=1; i<=k.getNCols(); i++) {
              outputStringApx += k.data[0][i-1];
              if (i < k.getNCols()) {
                outputStringApx += ", ";
              }
              else {
                outputStringApx += ")";
              }
            }
            outputStringApx += " = " + LinearRegression.approxMLR(transX, y, k);
            
            outputStringFile = outputStringEq + "\n" + outputStringApx;

            scan.nextLine();
            System.out.println();
            System.out.print("Nama file: ");
            String fileName = scan.nextLine();
            System.out.println();
            Util.writeToFile(fileName, outputStringFile);

            System.out.println("Persamaan regresi: ");
            System.out.println(outputStringEq);
            System.out.println();
            System.out.println("Hasil taksiran Xk: ");
            System.out.println(outputStringApx);
            System.out.println();
          } 
          
          else if(input == 7) {
            // Keluar
            isActive = false;
          }
        }

        scan.close();
    }
}
