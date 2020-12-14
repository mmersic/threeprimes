package com.mersic;

public class CRT {
	
  /**
   * Garner's Algorithm.
   * Primes m.  Residues u.
   * 
   * @param m
   * @param u
   * @return
   */
  public static long IntegerCRT(long[] m, long[] u, int n) {
	  long g[] = new long[n];
	  long v[] = new long[n];
	  for (int k = 1; k < n; k++) {
		  long product = m[0] % m[k];
		  for (int i = 1; i <= k-1; i++)
		  {
			  product = (product * m[i]) % m[k]; 
		  }
		  g[k] = LongFFT.modinv(product, m[k]);
	  }
	  
	  v[0] = u[0];
	  for (int k = 1; k < n; k++) {
		  long temp = v[k-1];
		  for (int j = k-2; j >= 0; j--) {
			  temp *= m[j];
			  temp %= m[k];
			  temp += v[j];
			  temp %= m[k];
		  }
		  v[k] = u[k]-temp; 
		  v[k] %= m[k]; if (v[k] < 0) v[k] += m[k];
		  v[k] *= g[k]; 
		  v[k] %= m[k]; if (v[k] < 0) v[k] += m[k];
	  }
	  
	  long uz = v[n-1];
	  
	  for (int k = n-2; k >= 0; k--)
	  {
		  uz *= m[k];
		  uz += v[k];
	  }
	  
	  return uz;
  }

  public static void testCase1() {
	  long u = IntegerCRT(new long[] {97, 89, 73}, new long[] {49, 21, 30}, 3);
	  System.out.println("u: " + u + ". Should be: " + 12659);
  }
  
  public static void main(String args[]) {
	  testCase1();
//	  long u = IntegerCRT(new long[] { 99, 97, 95}, new long[] {49, 21, 30}, 3);
//	  
//	  System.out.println("u: " + u);
//	  System.out.println("m1: " + u%99);
//	  System.out.println("m2: " + u%97);
//	  System.out.println("m3: " + u%95);
  }
}
