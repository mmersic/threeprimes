package com.mersic;

import java.util.Random;


public class ThreePrimes {

	public static long[] threePrimes(long[] a, long[] b, int N, long p[], long w[]) {
		long[] c1 = new long[N];
		long[] c2 = new long[N];
		long[] c3 = new long[N];
		
		polyMult(a, b, c1, p[0], w[0], N);
		polyMult(a, b, c2, p[1], w[1], N);
		polyMult(a, b, c3, p[2], w[2], N);
		
		long[] uz = new long[N];
		long[] c = new long[3];
		for (int i = 0; i < N; i++) {
			c[0] = c1[i];
			c[1] = c2[i];
			c[2] = c3[i];
			uz[i] = CRT.IntegerCRT(p, c, 3);
		}
		
		return uz;
	}
	
	public static void polyMult(long[] a, long[] b, long[] c, long p, long w, int N) {
		long[] A = new long[N];
		long[] B = new long[N];
		long[] C = new long[N];
		
		long wN = LongFFT.power(p, w, (p-1)/N);

		LongFFT.FFTmodP(N, a, A, p, wN);
		LongFFT.FFTmodP(N, b, B, p, wN);
		
		for (int i = 0; i < N; i++) {
			C[i] = ((B[i] * A[i] % p)*LongFFT.modinv(N,p)) % p;  
		}

		LongFFT.FFTmodP(N, C, c, p, LongFFT.modinv(wN,p));
	}
	
	
	public static void testCase2() {
		long[] uz = threePrimes(new long[] {12, 2, 0, 0}, new long[] {18,15,0,0}, 4, new long[] {5767169,786433,1179649}, new long[] {3, 10, 19});
		long uuz = 0;
		for (int i = 0; i < uz.length; i++) {
			uuz += Math.pow(20, i)*uz[i];
		}
		if (uuz != 16536)
			System.out.println("Test case 2 failed.  Expected 16536, got: " + uuz);
	}
	
	public static void testCase3() {
		long[] uz = threePrimes(new long[] {0, 19, 1, 0, 0, 0, 0, 0}, new long[] {5, 18, 1, 0, 0, 0, 0, 0}, 8, new long[] {5767169,786433,1179649}, new long[] {3, 10, 19});
		long uuz = 0;
		for (int i = 0; i < uz.length; i++) {
			uuz += Math.pow(20, i)*uz[i];
		}
		if (uuz != 596700)
			System.out.println("Test case 3 failed.  Expected 596700, got: " + uuz);
		
	}

	public static void testCase4() {
		long[] uz = threePrimes(new long[] {15, 18, 12, 7, 0, 0, 0, 0}, new long[] {12, 15, 13, 3, 0, 0, 0, 0}, 8, new long[] {5767169,786433,1179649}, new long[] {3, 10, 19});
		long uuz = 0;
		for (int i = 0; i < uz.length; i++) {
			uuz += Math.pow(20, i)*uz[i];
		}
		if (uuz != 1805396600)
			System.out.println("Test case 4 failed.  Expected 1805396600, got: " + uuz);
		
	}
	
	public static void testCase5() {
		long[] uz = threePrimes(new long[] {77809, 181436, 1807, 0, 0, 0, 0, 0}, new long[] {88178, 225687, 907, 0, 0, 0, 0, 0}, 8, new long[] {5767169,786433,1179649}, new long[] {3, 10, 19});
		int i;
		boolean failed = false;
		
		if (uz[0] != 6861042002l) failed = true;
		if (uz[1] != 33559143391l) failed = true;
		if (uz[2] != 41177656941l) failed = true;
		if (uz[3] != 572378861) failed = true;
		if (uz[4] != 1638949) failed = true;
		
		for (i = 5; i < 8; i++) {
			if (uz[i] != 0) failed = true;
		}
		
		if (failed) {
			System.out.println("testCase5() failed.");
		}
	}
	
	public static void testCase6() {
		long a[] = new long[] {582202, 59376, 568094, 553342, 341972, 186542, 65351, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		long b[] = new long[] {502177, 493935, 151182, 64977, 398684, 316370, 6611, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int N = 16;
		long p[] = new long[] {5767169,786433,1179649};
		long w[] = new long[] {3, 10, 19};
		
		long [] uz = threePrimes(a, b, N, p, w);

		boolean failed = false;
		if (uz[0] != 292368453754l) failed = true;
		if (uz[1] != 317387206422l) failed = true;
		if (uz[2] != 402630087962l) failed = true;
		if (uz[3] != 605283457210l) failed = true;
		if (uz[4] != 766903737442l) failed = true;
		if (uz[5] != 591020943760l) failed = true;
		if (uz[6] != 461735616773l) failed = true;
		if (uz[7] != 483430288917l) failed = true;
		if (uz[8] != 337156077238l) failed = true;
		if (uz[9] != 190465448257l) failed = true;
		if (uz[10] != 87331467516l) failed = true;
		if (uz[11] != 21908325032l) failed = true;
		if (uz[12] != 432035461l) failed = true;
		if (uz[13] != 0l) failed = true;
		if (uz[14] != 0l) failed = true;
		if (uz[15] != 0l) failed = true;
		if (failed) { System.out.println("testCase6() failed."); }
	}
	
	private static void printChecker(long[] uz) {
		System.out.println("boolean failed = false;");
		for (int i = 0; i < uz.length; i++)
		{
			System.out.println("if (uz[" + i + "] != " + uz[i] + "l) failed = true;");
		}
		System.out.println("if (failed) { System.out.println(\"testCase6() failed.\"); }");
	}

	static void runRandom(int N) {
		long seed = System.currentTimeMillis();
		System.out.println("seed: " + seed);
		Random R = new Random(seed);
		int size = N;
		int radix = 70000;
		int i;
		long p[] = {5767169, 786433, 1179649};
		long w[] = {3, 10, 19};
		long a[] = new long[size];
		long b[] = new long[size];
		for (i = 0; i < size/2; i++) {
			a[i] = R.nextLong()%radix;
			b[i] = R.nextLong()%radix;
		}

		long startTime = System.currentTimeMillis();
		long[] uz = threePrimes(a, b, size, p, w);
		long finishTime = System.currentTimeMillis();

		for (i = 0; i < size; i++) {
			if (uz[i] < 0) {
				System.out.println("this value doesn't look good: " + uz[i]);
			}
		}

		System.out.println("time to execute milliseconds: " + (finishTime-startTime));
	}	
	
	/**
	 * FFTmodP optimizations.
	 * 1. Call power() 1x instead of 2x.  Saved 50% total time.
	 * 2. Don't call recursively for size 2.  Saves another 20% time.
	 * @param args
	 */
	public static void main(String args[]) {
		testCase2();
		testCase3();
		testCase4();
		testCase5();
		testCase6();
		//2^14 about 1 second
		//2^15 about 2 seconds
		//2^16 about 3.79 seconds
		//2^17 about 8.701 seconds
		//runRandom(1024*2*2*2*2*2*2*2);
		runRandom(1024*2*2*2*2*2*2*2*2*2*2*2);
	}
}
