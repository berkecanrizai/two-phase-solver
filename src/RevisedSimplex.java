import java.util.Arrays;
import java.util.stream.IntStream;


public class RevisedSimplex {
	// arrays and vectors initialisation
	private double[][] a;
	private int m;
	private int n;
	double[][] b;
	double[][] binv;
	double[] rhs;
	double[] cb;
	
private int[] basis;
	public RevisedSimplex(double[][] A, double[] b, double[] c) {
		m = b.length;
        n = c.length;
        cb=new double[m];
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

        
	}
	
	public boolean isOptimal() {
		//matrixVector(binv,rhs);
		double[] result=vectorMatrix(cb, binv);
		for(int i=0; i<result.length;i++) {
			result[i]=result[i]-a[0][i];
		}
		
		double index=0;
		double value=result[0];
		for(int i=0; i<result.length;i++) {
			if(value>result[i]) {
				index=i;
				value=result[i];
			}
		}
		return false;
	}
	
	

	private double[] matrixVector(double[][] matrix, double[] vector) {
		 return Arrays.stream(matrix)
                 .mapToDouble(row -> 
                    IntStream.range(0, row.length)
                             .mapToDouble(col -> row[col] * vector[col])
                             .sum()
                 ).toArray();
		
	}

	public void initB() {
		for (int i = n; i < m+n; i++) {
			for(int j=0; j<m;j++) {
            	b[j][i-n]=a[j][i];
            }
		}
            
	}
	
	public void initRhs() {
		for(int i=0; i<m; i++)
		rhs[i]=a[i][n+2*m];
	}
	
	//B-1(B inverse) init
	public void updateBinv() {
		binv=invert(b);
	}
	
	
	
	public static double[][] invert(double a[][]) 
    {
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i) 
            b[i][i] = 1;
 
        gaussian(a, index);
 
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k]
                    	    -= a[index[j]][i]*b[index[i]][k];
 
        for (int i=0; i<n; ++i) 
        {
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j) 
            {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k) 
                {
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }
		private double[] vectorMatrix(double[] cb2, double[][] binv2) {
			// TODO Auto-generated method stub
			return null;
		}
 
 // to help get inverse of B
    public static void gaussian(double a[][], int index[]) 
    {
        int n = index.length;
        double c[] = new double[n];
 
        for (int i=0; i<n; ++i) 
            index[i] = i;
 
        for (int i=0; i<n; ++i) 
        {
            double c1 = 0;
            for (int j=0; j<n; ++j) 
            {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }
 
        int k = 0;
        for (int j=0; j<n-1; ++j) 
        {
            double pi1 = 0;
            for (int i=j; i<n; ++i) 
            {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) 
                {
                    pi1 = pi0;
                    k = i;
                }
            }
 
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i) 	
            {
                double pj = a[index[i]][j]/a[index[j]][j];
 
                a[index[i]][j] = pj;
 
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }
    // get pivot
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
}////
