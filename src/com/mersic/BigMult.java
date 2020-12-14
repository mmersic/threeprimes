package com.mersic;

import java.math.BigInteger;
import java.util.Random;


public class BigMult {
	static long[][] runRandom(int N) {
		long seed = System.currentTimeMillis();
		//System.out.println("seed: " + seed);
		Random R = new Random(seed);
		int size = N;
		int radix = 70000;
		int i;
		long p[] = {5767169, 786433, 1179649};
		long w[] = {3, 10, 19};
		long a[] = new long[size];
		long b[] = new long[size];
		for (i = 0; i < size/2; i++) {
			a[i] = R.nextLong()%radix; if (a[i] < 0) a[i] *= -1;
			b[i] = R.nextLong()%radix; if (b[i] < 0) b[i] *= -1;
		}

		long startTime = System.currentTimeMillis();
		long[] uz = ThreePrimes.threePrimes(a, b, size, p, w);
		long finishTime = System.currentTimeMillis();

		for (i = 0; i < size; i++) {
			if (uz[i] < 0) {
				System.out.println("this value doesn't look good: " + uz[i]);
			}
		}

		//System.out.println("time to execute milliseconds: " + (finishTime-startTime));
		
		return new long[][] { a, b, uz };
	}	
	
	public static boolean testCase1(int N) {
		int radix = 700000;
		long[][] result = runRandom(N);
		
		long[] a = result[0];
		long[] b = result[1];
		long[] c = result[2];
		
		BigInteger A = toBigInt(a, radix);
		BigInteger B = toBigInt(b, radix);
		BigInteger C = toBigInt(c, radix);
		
		BigInteger builtIn = A.multiply(B);

		return C.equals(builtIn);
	}
	
	private static BigInteger toBigInt(long[] a, int radix) {
		BigInteger result = BigInteger.ZERO;
		BigInteger Radix = new BigInteger(""+radix);
		BigInteger base = BigInteger.ONE;
		for (int i = 0; i < a.length; i++) {
			BigInteger coeff = new BigInteger(""+a[i]);
			BigInteger limb = base.multiply(coeff);
			result = result.add(limb);
			base = base.multiply(Radix);
		}
		
		return result;
	}

	public static void runTest(int size) {
		int trueCount = 0;
		int samples = 10;
		for (int i = 0; i < samples; i++) {
			System.out.println("working on: " + i);
			boolean result = testCase1(size);
			if (result) {
				trueCount++;
				System.out.println("passed...");
			} else {
				System.out.println("failed...");
			}
		}
		if (trueCount == samples)
			System.out.println("passed for size: " + size);
	}
	
	public static void main(String args[]) {
		runTest(1024);
		runTest(1024*2);
		runTest(1024*2*2);
		runTest(1024*2*2*2);
		runTest(1024*2*2*2*2);
		runTest(1024*2*2*2*2*2);
		runTest(1024*2*2*2*2*2*2);
	}
}
