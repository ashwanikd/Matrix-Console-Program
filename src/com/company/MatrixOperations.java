package com.company;

public class MatrixOperations {

    Matrix add(Matrix m1,Matrix m2) throws MatrixException {
        if(m1.row!=m2.row || m1.col!=m2.col){
            throw new MatrixException("rows and columns must be same");
        }
        Matrix c = new Matrix(m1.row,m1.col);
        for(int i=0;i<m1.row;i++){
            for(int j=0;j<m1.col;j++){
                c.set(i,j,m1.get(i,j)+m2.get(i,j));
            }
        }
        return c;
    }

    Matrix sub(Matrix m1,Matrix m2) throws MatrixException {
        if(m1.row!=m2.row || m1.col!=m2.col){
            throw new MatrixException("rows and columns must be same");
        }
        Matrix c = new Matrix(m1.row,m1.col);
        for(int i=0;i<m1.row;i++){
            for(int j=0;j<m1.col;j++){
                c.set(i,j,m1.get(i,j)-m2.get(i,j));
            }
        }
        return c;
    }

    double a[][],b[][];

    Matrix multiply(Matrix m1,Matrix m2,String x) throws MatrixException {
        if(m1.col!=m2.row){
            throw new MatrixException("columns of first must be equal to rows of second for multiplication");
        }
        if(x.equals("recursive")){
            a = m1.matrix;
            b = m2.matrix;
            Matrix c = new Matrix(m1.row,m2.col);
            c.matrix = multiplyRecursive(0,0,0,0,m1.n);
            return c;
        }else if(x.equals("strassen")){
            Matrix c = new Matrix(m1.row,m2.col);
            c.matrix = multiplystrassen(m1.matrix,m2.matrix);
            return c;
        }else {
            Matrix c = new Matrix(m1.row,m2.col);
            for(int i=0;i<m1.row;i++){
                for(int j=0;j<m2.col;j++){
                    for(int k=0;k<m1.col;k++){
                        c.matrix[i][j] = c.matrix[i][j] + m1.get(i,k)*m2.get(k,j);
                    }
                }
            }
            return c;
        }
    }

    double [][] multiplyRecursive(int x1,int y1,int x2,int y2,int n){
        if(n == 1){
            double x[][] = new double[1][1];
            x[0][0] = a[x1][y1]*b[x2][y2];
            return x;
        }else {
            double x[][] = new double[n][n];
            double y[][] = new double[n/2][n/2];
            // partitioning and multiplying matrices a and b
            y = add2DArrays(multiplyRecursive(x1,y1,x2,y2,n/2),multiplyRecursive(x1,y1+n/2,x2+n/2,y2,n/2));
            x = subMatrix(x,y,0,0);
            y = add2DArrays(multiplyRecursive(x1,y1,x2,y2+n/2,n/2),multiplyRecursive(x1,y1+n/2,x2+n/2,y2+n/2,n/2));
            x = subMatrix(x,y,0,n/2);
            y = add2DArrays(multiplyRecursive(x1+n/2,y1,x2,y2,n/2),multiplyRecursive(x1+n/2,y1+n/2,x2+n/2,y2,n/2));
            x = subMatrix(x,y,n/2,0);
            y = add2DArrays(multiplyRecursive(x1+n/2,y1,x2,y2+n/2,n/2),multiplyRecursive(x1+n/2,y1+n/2,x2+n/2,y2+n/2,n/2));
            x = subMatrix(x,y,n/2,n/2);
            return x;
        }
    }

    double [][] subMatrix(double a[][],double b[][],int x1,int y1){
        for(int i=0;i<b.length;i++){
            for(int j=0;j<b[i].length;j++){
                a[i+x1][j+y1] = b[i][j];
            }
        }
        return a;
    }

    double [][] add2DArrays(double[][] x1,double[][] x2){
        double x[][] = x1;
        for(int i=0;i<x1.length;i++){
            for(int j=0;j<x1[i].length;j++){
                x[i][j] = x1[i][j]+x2[i][j];
            }
        }
        return x;
    }

    double [][] sub2DArrays(double[][] x1,double[][] x2){
        double x[][] = x1;
        for(int i=0;i<x1.length;i++){
            for(int j=0;j<x1[i].length;j++){
                x[i][j] = x1[i][j]-x2[i][j];
            }
        }
        return x;
    }


    double [][] multiplystrassen(double [][] x,double [][] y){
        if(x.length == 1){
            double[][] a = new double[1][1];
            a[0][0] = x[0][0]*y[0][0];
            return a;
        }else {
            double[][] s1 = s1(x,y);
            double[][] s2 = s2(x,y);
            double[][] s3 = s3(x,y);
            double[][] s4 = s4(x,y);
            double[][] s5 = s5(x,y);
            double[][] s6 = s6(x,y);
            double[][] s7 = s7(x,y);
            double[][] s8 = s8(x,y);
            double[][] s9 = s9(x,y);
            double[][] s10 = s10(x,y);
            double[][] p1 = multiplystrassen(a11(x),s1);
            double[][] p2 = multiplystrassen(s2,a22(y));
            double[][] p3 = multiplystrassen(s3,a11(y));
            double[][] p4 = multiplystrassen(a22(x),s4);
            double[][] p5 = multiplystrassen(s5,s6);
            double[][] p6 = multiplystrassen(s7,s8);
            double[][] p7 = multiplystrassen(s9,s10);
            int n = x.length;
            double[][] result = new double[n][n];
            result = subMatrix(result,sub2DArrays(add2DArrays(p5,add2DArrays(p4,p6)),p2),0,0);
            result = subMatrix(result,add2DArrays(p1,p2),0,n/2);
            result = subMatrix(result,add2DArrays(p3,p4),n/2,0);
            result = subMatrix(result,add2DArrays(p5,sub2DArrays(sub2DArrays(p1,p3),p7)),n/2,n/2);
            return result;

        }
    }
    // calculation for strassen
    double[][] a11(double[][] x){
        int n = x.length;
        double a[][] = new double[n/2][n/2];
        for(int i=0;i<n/2;i++){
            for(int j=0;j<n/2;j++){
                a[i][j] = x[i][j];
            }
        }
        return a;
    }
    double[][] a12(double[][] x){
        int n = x.length;
        double a[][] = new double[n/2][n/2];
        for(int i=0;i<n/2;i++){
            for(int j=0;j<n/2;j++){
                a[i][j] = x[i][j+n/2];
            }
        }
        return a;
    }
    double[][] a21(double[][] x){
        int n = x.length;
        double a[][] = new double[n/2][n/2];
        for(int i=0;i<n/2;i++){
            for(int j=0;j<n/2;j++){
                a[i][j] = x[i+n/2][j];
            }
        }
        return a;
    }
    double[][] a22(double[][] x){
        int n = x.length;
        double a[][] = new double[n/2][n/2];
        for(int i=0;i<n/2;i++){
            for(int j=0;j<n/2;j++){
                a[i][j] = x[i+n/2][j+n/2];
            }
        }
        return a;
    }
    double [][] s1(double [][] x,double [][] y){
        int n = x.length;
        double [][] a = new double[n/2][n/2];
        for(int i=0;i<n/2;i++){
            for(int j=0;j<n/2;j++){
                a[i][j] = y[i][j+n/2] - y[i+n/2][j+n/2];
            }
        }
        return a;
    }
    double [][] s2(double [][] x,double [][] y){
        int n = x.length;
        double [][] a = new double[n/2][n/2];
        for(int i=0;i<n/2;i++){
            for(int j=0;j<n/2;j++){
                a[i][j] = x[i][j] + x[i][j+n/2];
            }
        }
        return a;
    }
    double [][] s3(double [][] x,double [][] y){
        int n = x.length;
        double [][] a = new double[n/2][n/2];
        for(int i=0;i<n/2;i++){
            for(int j=0;j<n/2;j++){
                a[i][j] = x[i+n/2][j] + x[i+n/2][j+n/2];
            }
        }
        return a;
    }
    double [][] s4(double [][] x,double [][] y){
        int n = x.length;
        double [][] a = new double[n/2][n/2];
        for(int i=0;i<n/2;i++){
            for(int j=0;j<n/2;j++){
                a[i][j] = y[i+n/2][j] - y[i][j];
            }
        }
        return a;
    }
    double [][] s5(double [][] x,double [][] y){
        int n = x.length;
        double [][] a = new double[n/2][n/2];
        for(int i=0;i<n/2;i++){
            for(int j=0;j<n/2;j++){
                a[i][j] = x[i][j] + x[i+n/2][j+n/2];
            }
        }
        return a;
    }
    double [][] s6(double [][] x,double [][] y){
        int n = x.length;
        double [][] a = new double[n/2][n/2];
        for(int i=0;i<n/2;i++){
            for(int j=0;j<n/2;j++){
                a[i][j] = y[i][j] + y[i+n/2][j+n/2];
            }
        }
        return a;
    }
    double [][] s7(double [][] x,double [][] y){
        int n = x.length;
        double [][] a = new double[n/2][n/2];
        for(int i=0;i<n/2;i++){
            for(int j=0;j<n/2;j++){
                a[i][j] = x[i][j+n/2] - x[i+n/2][j+n/2];
            }
        }
        return a;
    }
    double [][] s8(double [][] x,double [][] y){
        int n = x.length;
        double [][] a = new double[n/2][n/2];
        for(int i=0;i<n/2;i++){
            for(int j=0;j<n/2;j++){
                a[i][j] = y[i+n/2][j] + y[i+n/2][j+n/2];
            }
        }
        return a;
    }
    double [][] s9(double [][] x,double [][] y){
        int n = x.length;
        double [][] a = new double[n/2][n/2];
        for(int i=0;i<n/2;i++){
            for(int j=0;j<n/2;j++){
                a[i][j] = x[i][j] - x[i+n/2][j];
            }
        }
        return a;
    }
    double [][] s10(double [][] x,double [][] y){
        int n = x.length;
        double [][] a = new double[n/2][n/2];
        for(int i=0;i<n/2;i++){
            for(int j=0;j<n/2;j++){
                a[i][j] = y[i][j] + y[i][j+n/2];
            }
        }
        return a;
    }

    class MatrixException extends Exception{
        MatrixException(String message){
            super(message);
        }
    }
}
