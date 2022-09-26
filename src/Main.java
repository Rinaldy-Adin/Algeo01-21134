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
          } else if(input == 2) {
            System.out.println("1. Metode reduksi baris");
            System.out.println("2. Metode kofaktor");
            int inputSub = scan.nextInt();

            System.out.println("1. Masukkan melalui keyboard");
            System.out.println("2. Masukkan melalui file");
            int inputMethod = scan.nextInt();

            Matrix matrix;
            if(inputMethod == 1) {
              System.out.print("Ukuran matrix: ");
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
          } else if(input == 4) {
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
            // Interpolasi bicubic
          } else if(input == 6) {
            // Regresi
          } else if(input == 7) {
            isActive = false;
          }
        }

        scan.close();
    }
}
