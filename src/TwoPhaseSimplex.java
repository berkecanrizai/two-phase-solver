public class TwoPhaseSimplex {
    private static final double EPSILON = 1.0E-8;

    private double[][] a; 

    private int m;         
    private int n;         

    private int[] basis;   

    // simplex tableau
    public TwoPhaseSimplex(double[][] A, double[] b, double[] c) {
        m = b.length;
        n = c.length;
        a = new double[m+2][n+m+m+1];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = A[i][j];
        for (int i = 0; i < m; i++)
            a[i][n+i] = 1.0;
        for (int i = 0; i < m; i++)
            a[i][n+m+m] = b[i];
        for (int j = 0; j < n; j++)
            a[m][j] = c[j];

        // if negative RHS, multiply by -1
        for (int i = 0; i < m; i++) {
            if (b[i] < 0) {
                for (int j = 0; j <= n+m+m; j++)
                    a[i][j] = -a[i][j];
            }
        }

        for (int i = 0; i < m; i++)
            a[i][n+m+i] = 1.0;
        for (int i = 0; i < m; i++)
            a[m+1][n+m+i] = -1.0;
        for (int i = 0; i < m; i++)
            pivot(i, n+m+i);

        basis = new int[m];
        for (int i = 0; i < m; i++)
            basis[i] = n + m + i;

        show();
        
        phase1();

        System.out.println("Before phase II");
        show();

        phase2();

        System.out.println("After phase II");
         show();
         
         System.out.println("Optimal solution= "+value());
        // primal();
       for(int i=0;i<primal().length;i++) {
        	 System.out.println("Variable "+i+" ="+primal()[i]);
         }

        // check optimality conditions
        assert check(A, b, c);
    }

    // run phase I simplex algorithm to find basic feasible solution
    private void phase1() {
        while (true) {

            int q = bland1();
            if (q == -1) break;  // optimal

            int p = minRatioRule(q);
            assert p != -1 : "Entering column = " + q;

            pivot(p, q);

            basis[p] = q;
        }
        if (a[m+1][n+m+m] > EPSILON) throw new ArithmeticException("infeasible");
    }


    // run simplex algorithm starting from initial basic feasible solution
    private void phase2() {
        while (true) {

            //  entering
            int q = bland2();
            if (q == -1) break;  // optimal
            //  leaving
            int p = minRatioRule(q);
            if (p == -1) throw new ArithmeticException("unbounded");

            // pivot
            pivot(p, q);

            // basis updates
            basis[p] = q;
        }
    }


    private int bland1() {
        for (int j = 0; j < n+m; j++)
            if (a[m+1][j] > EPSILON) return j;
        return -1;  // optimal
    }

    private int bland2() {
        for (int j = 0; j < n+m; j++)
            if (a[m][j] > EPSILON) return j;
        return -1;  // optimal
    }


    private int minRatioRule(int q) {
        int p = -1;
        for (int i = 0; i < m; i++) {
            // if (a[i][q] <= 0) continue;
            if (a[i][q] <= EPSILON) continue;
            else if (p == -1) p = i;
            else if ((a[i][n+m+m] / a[i][q]) < (a[p][n+m+m] / a[p][q])) p = i;
        }
        return p;
    }


    private void pivot(int p, int q) {

        for (int i = 0; i <= m+1; i++)
            for (int j = 0; j <= n+m+m; j++)
                if (i != p && j != q) a[i][j] -= a[p][j] * a[i][q] / a[p][q];

        for (int i = 0; i <= m+1; i++)
            if (i != p) a[i][q] = 0.0;


        for (int j = 0; j <= n+m+m; j++)
            if (j != q) a[p][j] /= a[p][q];
        a[p][q] = 1.0;
    }

   
    public double value() {
        return -a[m][n+m+m];
    }

    
    public double[] primal() {
        double[] x = new double[n];
        for (int i = 0; i < m; i++)
            if (basis[i] < n) x[basis[i]] = a[i][n+m+m];
        return x;
    }

   
    public double[] dual() {
        double[] y = new double[m];
        for (int i = 0; i < m; i++)
            y[i] = -a[m][n+i];
        return y;
    }


    
    private boolean isPrimalFeasible(double[][] A, double[] b) {
        double[] x = primal();

        for (int j = 0; j < x.length; j++) {
            if (x[j] < 0.0) {
            	 System.out.println("x[" + j + "] = " + x[j] + " is negative");
                return false;
            }
        }


        for (int i = 0; i < m; i++) {
            double sum = 0.0;
            for (int j = 0; j < n; j++) {
                sum += A[i][j] * x[j]; }
            if (sum > b[i] + EPSILON) {
            	 System.out.println("not primal feasible");
            	 System.out.println("b[" + i + "] = " + b[i] + ", sum = " + sum);
                return false;
            }
        }
        return true;
    }

   
    private boolean isDualFeasible(double[][] A, double[] c) {
        double[] y = dual();

        for (int i = 0; i < y.length; i++) {
            if (y[i] < 0.0) {
            	 System.out.println("y[" + i + "] = " + y[i] + " is negative");
                return false;
            }
        }

        for (int j = 0; j < n; j++) {
            double sum = 0.0;
            for (int i = 0; i < m; i++) {
                sum += A[i][j] * y[i];
            }
            if (sum < c[j] - EPSILON) {
            	 System.out.println("not dual feasible");
                System.out.println("c[" + j + "] = " + c[j] + ", sum = " + sum);
                return false;
            }
        }
        return true;
    }

    
    private boolean isOptimal(double[] b, double[] c) {
        double[] x = primal();
        double[] y = dual();
        double value = value();


        double value1 = 0.0;
        for (int j = 0; j < x.length; j++)
            value1 += c[j] * x[j];
        double value2 = 0.0;
        for (int i = 0; i < y.length; i++)
            value2 += y[i] * b[i];
        if (Math.abs(value - value1) > EPSILON || Math.abs(value - value2) > EPSILON) {
        	 System.out.println("value = " + value + ", cx = " + value1 + ", yb = " + value2);
            return false;
        }
        return true;
    }

    private boolean check(double[][]A, double[] b, double[] c) {
        return isPrimalFeasible(A, b) && isDualFeasible(A, c) && isOptimal(b, c);
    }

    // print tableaux
    public void show() {
        System.out.println("m = " + m);
        System.out.println("n = " + n);
        System.out.print("Z Row ");
        for (int i = 0; i <= m+1; i++) {
            for (int j = 0; j <= n+m+m; j++) {
            	if(i==m)
            	 System.out.printf("%7.2f ", a[i][j]);
            }
           System.out.println();
        }
        
        for (int i = 0; i <= m+1; i++) {
            for (int j = 0; j <= n+m+m; j++) {
            	if(i!=m)
            	 System.out.printf("%7.2f ", a[i][j]);
                if (j == n+m-1 || j == n+m+m-1)  System.out.print(" |");
            }
            System.out.println();
        }
        System.out.print("basis = ");
        for (int i = 0; i < m; i++)
        	 System.out.print(basis[i] + " ");
        System.out.println();
        System.out.println();
    }

}