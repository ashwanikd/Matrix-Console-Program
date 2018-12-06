package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the rows and columns of first matrix :");
        Matrix m1 = new Matrix(scan.nextInt(), scan.nextInt());
        System.out.println("Enter the first matrix :");
        for(int i=0;i<m1.row;i++){
            for(int j=0;j<m1.col;j++){
                m1.set(i,j,scan.nextDouble());
            }
        }
        System.out.println("Enter the rows and columns of second matrix :");
        Matrix m2 = new Matrix(scan.nextInt(), scan.nextInt());
        System.out.println("Enter the rows second matrix :");
        for(int i=0;i<m1.row;i++){
            for(int j=0;j<m1.col;j++){
                m2.set(i,j,scan.nextDouble());
            }
        }
        MatrixOperations mo = new MatrixOperations();
        try {
            // write strassen in place of recursive if you want to use strassens algorithm
            Matrix m = mo.multiply(m1,m2,"recursive");
            System.out.println("Result :");
            m.printMatrix();
        } catch (MatrixOperations.MatrixException e) {
            e.printStackTrace();
        }

    }
}
