# Tubes Algeo 1: SPL, Determinan, dan Aplikasinya

Program ini merupakan program pengolahan matriks yang dapat mengolah Sistem Persamaan Linear, Determinan, Invers, Interpolasi, dan Interpolasi Bicubic. Terdapat juga pembesar gambar yang memanfaatkan interpolasi bicubic.

### Anggota Kelompok:
- Rinaldy Adin (13521134)
- Enrique Alifio Ditya (13521142)
- Rava Maulana Azzikri (13521149)

## How to run

1. Clone repository
    ```
    $ git clone https://github.com/Rinaldy-Adin/Algeo01-21134.git
    ```
2. Masuk kedalam folder project
    ```
    $ cd Algeo01-21134
    ```
3. Jalankan program
    * Menggunakan java .class

        1. Masuk kedalam folder bin
            ```bash
            $ cd bin
            ```
        2. Run file Main.class
            ```bash
            $ java Main
            ```

    * Menggunakan file .jar

        1. Masuk kedalam folder lib
            ```bash
            $ cd lib
            ```
        2. Run file Algeo01-21134.jar
            ```bash
            $ java -jar Algeo01-21134.jar
            ```

## Disclaimer

1. Pastikan sudah terdapat folder ```/output``` pada root folder project ini

2. Untuk melakukan pengolahan menggunakan masukan dari file, pastikan lokasi yang diberikan lokasi absolut dari file. Contoh:
    ````
    Lokasi absolut file masukkan: C:\Users\Rinaldy Adin\Documents\1.txt
    ````

## Format File Masukan

1. Setiap file masukan **harus diakhiri dengan karakter newline/enter**

2. Untuk file masukan SPL, Determinan, dan Invers, format file berupa angka/pecahan yang terpisahkan oleh **satu spasi** dalam setaip kolom serta setiap baris terpisahkan oleh newline/enter. Setiap file masukan **harus diakhiri dengan karakter newline/enter** di akhir file. Contoh:
    ```
    1 1 -1 -1 1
    2 5 -7 -5 -2
    2 -1 1 3 4
    5 2 -4 2 6

    ```

3. Untuk file masukan Interpolasi Polinom, format file berupa masukan data titik x dan y yang dipisahkan oleh **satu spasi**. Contoh:
    ```
    0.4 0.043
    0.7 0.005
    0.11 0.058
    0.14 0.072
    0.17 0.1
    0.2 0.13
    0.23 0.147

    ```

4. Untuk file masukan Regresi Linear, format file berupa masukan data titik x1, x2, ..., xn dan y yang dipisahkan oleh **satu spasi**. Contoh:
    ```
    10 7 23
    2 3 7
    4 2 15
    6 4 17
    8 6 23
    7 5 22
    4 3 10
    6 3 14
    7 4 20
    6 3 19

    ```

5. Untuk file masukan Interpolasi Bicubic, format file berupa masukan data titik x1, x2, ..., xn dan y yang dipisahkan oleh **satu spasi** diikuti dengan nilai x dan y yang ingin ditaksi pada baris terakhir, serta 2 angka bebas setelah nilai x dan y. Contoh:
    ```
    159 59 210 96
    125 161 72 81
    98 101 42 12
    21 51 0 16
    0.5 0.5 0 0

    ```

## File Structure
```
│
├───bin
│   │   Main.class
│   │
│   ├───test
│   │   └───cases
│   │       ├───bicubicinterpolation
│   │       │       bicubic1.txt
│   │       │
│   │       ├───determinant
│   │       │       determinant1.txt
│   │       │       determinant2.txt
│   │       │
│   │       ├───general
│   │       │       counting3x3.txt
│   │       │       counting4x4.txt
│   │       │       identity3x3.txt
│   │       │       identity4x4.txt
│   │       │
│   │       ├───interpolation
│   │       │       interpolation1.txt
│   │       │       interpolation2.txt
│   │       │       interpolation3.txt
│   │       │
│   │       ├───inverse
│   │       │       expected1.txt
│   │       │       expected2.txt
│   │       │       inverse1.txt
│   │       │       inverse2.txt
│   │       │
│   │       ├───linearequation
│   │       │       gauss-sample1.txt
│   │       │       gauss-sample2.txt
│   │       │       gauss-sample3.txt
│   │       │       gauss-sample4a.txt
│   │       │       gauss-sample4b.txt
│   │       │       gauss-sample5.txt
│   │       │       gauss-sample6.txt
│   │       │       gauss-sample7.txt
│   │       │
│   │       ├───linearregression
│   │       │       linreg1.txt
│   │       │       linreg1x.txt
│   │       │       linreg1y.txt
│   │       │       linreg2.txt
│   │       │       linreg2x.txt
│   │       │       linreg2y.txt
│   │       │
│   │       └───matrixtests
│   │               multiplier.txt
│   │               multiplyresult.txt
│   │               transpose.txt
│   │
│   └───tubes
│       ├───matrix
│       │       BicubicInterpolation.class
│       │       Determinant.class
│       │       Interpolation.class
│       │       Inverse.class
│       │       LinearEquation.class
│       │       LinearRegression.class
│       │       Matrix.class
│       │
│       └───util
│               Util.class
│
├───lib
│       Algeo01-21124.jar
│
├───output
│
├───src
│   │   Main.java
│   │
│   ├───test
│   │   ├───cases
│   │   │   ├───bicubicinterpolation
│   │   │   │       bicubic1.txt
│   │   │   │
│   │   │   ├───determinant
│   │   │   │       determinant1.txt
│   │   │   │       determinant2.txt
│   │   │   │
│   │   │   ├───general
│   │   │   │       counting3x3.txt
│   │   │   │       counting4x4.txt
│   │   │   │       identity3x3.txt
│   │   │   │       identity4x4.txt
│   │   │   │
│   │   │   ├───interpolation
│   │   │   │       interpolation1.txt
│   │   │   │       interpolation2.txt
│   │   │   │       interpolation3.txt
│   │   │   │
│   │   │   ├───inverse
│   │   │   │       expected1.txt
│   │   │   │       expected2.txt
│   │   │   │       inverse1.txt
│   │   │   │       inverse2.txt
│   │   │   │
│   │   │   ├───linearequation
│   │   │   │       gauss-sample1.txt
│   │   │   │       gauss-sample2.txt
│   │   │   │       gauss-sample3.txt
│   │   │   │       gauss-sample4a.txt
│   │   │   │       gauss-sample4b.txt
│   │   │   │       gauss-sample5.txt
│   │   │   │       gauss-sample6.txt
│   │   │   │       gauss-sample7.txt
│   │   │   │
│   │   │   ├───linearregression
│   │   │   └───matrixtests
│   │   │           multiplier.txt
│   │   │           multiplyresult.txt
│   │   │           transpose.txt
│   │   │
│   │   └───main
│   │           BicubicIntepolationTest.java
│   │           DeterminantTest.java
│   │           InterpolationTest.java
│   │           InverseTest.java
│   │           LinearEquationTest.java
│   │           LinearRegressionTest.java
│   │           MatrixTest.java
│   │
│   └───tubes
│       ├───matrix
│       │       BicubicInterpolation.java
│       │       Determinant.java
│       │       Interpolation.java
│       │       Inverse.java
│       │       LinearEquation.java
│       │       LinearRegression.java
│       │       Matrix.java
│       │
│       └───util
│               Util.java
│
└───test
    ├───case-1
    │       1_a.txt
    │       1_b.txt
    │       1_c.txt
    │       1_d1.txt
    │       1_d2.txt
    │
    ├───case-2
    │       2_a.txt
    │       2_b.txt
    │
    ├───case-3
    │       3_a.txt
    │       3_b.txt
    │
    ├───case-4
    │       4_a.txt
    │       4_b.txt
    │       4_c.txt
    │
    ├───case-5
    │       5.txt
    │
    └───case-6
            6.txt
```