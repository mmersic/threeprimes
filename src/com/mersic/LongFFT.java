package com.mersic;

import java.util.Random;


public class LongFFT {

	static final public long p1 = 9223372036854775783l;
	static final public long p2 = 9223372036854775643l;
	static final public long p3 = 9223372036854775549l;
	
	static final public long pi1 = 2147483647;
	static final public long wi1 = 7;
	static final public long pi2 = 2147483629;
	static final public long wi2 = 2;
	static final public long pi3 = 2147483587;
	static final public long wi3 = 2;
	
	static final public Random R = new Random(System.currentTimeMillis());
	
	/**
	 * Get the multiplicative inverses of a mod p.
	 * Assume a > p.
	 * 
	 * @param a
	 * @param p
	 * @return
	 */
	public static long modinv(long a, long p) {
		long P = p;
		long x1 = 1; long y1 = 0;
		long x2 = 0; long y2 = 1;
		
		while(p != 0) {
			long t = a % p;  // t = a3
			long q = a/p;    
			long x3 = x1 - q*x2;
			long y3 = y1 - q*y2;
			a = p; p = t;
			x1 = x2; x2 = x3;
			y1 = y2; y2 = y3;
		}
		
		return (x1 > 0 ? x1 : x1+P);
	}	
	
	/**
	 * Computes the FFT mod P.
	 * 
	 * 
	 * @param N size of a.
	 * @param a input polynomial.
	 * @param A The result is placed here.
	 * @param P Prime number P.
	 * @param w Roots of omega.
	 */
	public static void FFTmodP(int N, long[] a, long[] A, long P, long w) {
		if (N == 1) {
			A[0] = a[0];
		} else {
			int n = N/2;
			long[] B = new long[n];
			long[] C = new long[n];
			long[] b = new long[n];
			long[] c = new long[n];
			
			for (int i = 0; i < n; i++)
			{
				b[i] = a[2*i];
				c[i] = a[2*i+1];
			}
			
			long w2 = w*w % P;
			FFTmodP(n,b,B, P, w2);  //if (P == 5767169) System.out.println("A: w^2 mod p: " + power(P, w, (pow+pow)%Nth));
			FFTmodP(n,c,C, P, w2);

			long wP = 1;
			for (int k = 0; k < n; k++) {
				A[k] = B[k]+wP*C[k];
				A[k] %= P;
				if (A[k] < 0) 
					A[k] += P;
				A[k+n] = B[k]-wP*C[k];
				A[k+n] %= P;
				if (A[k+n] < 0)
					A[k+n] += P;
				wP = wP * w % P;
			}
		}
	}
	
	public static void InvFFTmodP(int N, long[] a, long[] A, long P, long w) {
		long winv = modinv(w, P);
		long Ninv = modinv(N, P);
		System.out.println("w: " + w + " winv: " + winv + " in P: " + P);
		FFTmodP(N, a, A, P, winv);
		for (int i = 0; i < N; i++) {
			A[i] = (A[i] * Ninv) % P;
		}
	}

	/**
	 * Taken from wikipedia - Exponentiation by Squaring.
	 * @param p
	 * @param w
	 * @param i
	 * @return
	 */
	public static long power(long p, long w, long i) {
		long result = 1;
		while (i != 0) {
			if (i%2 != 0) {
				result *= w; result %= p;
				i--;
			}
			w *= w; w %= p;
			i /= 2;
		}
		return result;
	}
	
	static int size = 1024;

	public static void main(String args[])
	{
	
	}
	
	private static void primElement(long p, long pow) {
		for (int i = 2; i < p; i++) {
			long o = order(p, i);
			System.out.println("o: " + o);
			if (o == pow) {
				System.out.print(i + " is a primative element of " + p);
				return;
			}
		}
		
	}

	private static void fourierPrimes(int e) {
		
		nextk: for (int k = 1; k < 100; k+=2) {
			long p = (long) Math.pow(2, e);
			long sq = (long) Math.sqrt(p);
			p*=k;
			p++;

			for (int i = 2; i < sq; i++) {
				if (p%i==0) {
					//System.out.println(i + " divides " + p);
					continue nextk;
				}
			}
			
			System.out.println(p + " is good.");
		}
		
	}
	
	private static long[] randomVal(int i) {
		long[] B = new long[i];
		for (int j = 0; j < i; j++) {
			B[j] = R.nextInt((int)(pi3-100));
		}
		return B;
	}
	


	private static void primes() {
		int found = 0;
		outer: for (long i = Integer.MAX_VALUE; found < 3; i--) {
			if (i%2 == 0)
				continue;
			for (long j = 3; j < Math.sqrt(i); j += 2)
			{
				if (i%j==0)
					continue outer;
			
			}
			System.out.println("prime: " + i);
			found++;
		}
	}
	
	/**
	 * Order of g in p.
	 * 
	 * @param p
	 * @param g
	 * @return
	 */
	private static long order(long p, long g) {
		long current = (g*g)%p;
	    long order = 1;
		while (current != g) {
			current = (current*g)%p;
			order++;
		}
		
		return order;
	}

	private static void printElements(long p) {
		for (long i = 0; i < p; i++) {
			long ord = 0;
			if ((ord = order(p,i)) == p-1) {
				System.out.println("found omega: " + i + " order: " + ord);
				break;
			}
				
			//System.out.println("order of " + i + " is: " + ord);
		}
	}
}
