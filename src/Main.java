import java.util.Scanner;

import tubes.matrix.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("MENU");
        System.out.println("1. Sistem Persamaan Linier");
        System.out.println("2. Determinan");
        System.out.println("3. Matriks balikan");
        System.out.println("4. Interpolasi polinom");
        System.out.println("5. Interpolasi bicubic");
        System.out.println("6. Regresi linier berganda");
        System.out.println("7. Keluar");

        boolean isActive = true;
        while(isActive) {
          int input = scan.nextInt();
          if(input == 1) {
            // SPL
          } else if(input == 2) {
            // Determinan
          } else if(input == 3) {
            // Inverse
          } else if(input == 4) {
            // Interpolasi polinom
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
