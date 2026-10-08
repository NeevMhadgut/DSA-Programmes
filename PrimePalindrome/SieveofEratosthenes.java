import java.util.*;

class Solution {
    private static final int MAX_PRIME = 20000;
    private static final List<Integer> primes = new ArrayList<>();

    // Static block to run Sieve of Eratosthenes once for all testcases
    static {
        boolean[] isPrimeArr = new boolean[MAX_PRIME + 1];
        Arrays.fill(isPrimeArr, true);
        isPrimeArr[0] = false;
        isPrimeArr[1] = false;

        for (int p = 2; p * p <= MAX_PRIME; p++) {
            if (isPrimeArr[p]) {
                for (int i = p * p; i <= MAX_PRIME; i += p) {
                    isPrimeArr[i] = false;
                }
            }
        }

        for (int i = 2; i <= MAX_PRIME; i++) {
            if (isPrimeArr[i]) {
                primes.add(i);
            }
        }
    }

    // Check primality using the sieved prime list
    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int p : primes) {
            if ((long) p * p > n) break;
            if (n % p == 0) return false;
        }
        return true;
    }

    // Check if n is a palindrome
    private boolean isPalin(int n) {
        int t = n, p = 0;
        while (t > 0) {
            p = p * 10 + (t % 10);
            t /= 10;
        }
        return n == p;
    }

    public int primePalindrome(int n) {
        while (true) {
            if (isPalin(n) && isPrime(n)) {
                return n;
            }

            n++;

            // Skip all even-length palindromes (divisible by 11)
            // Range 1: 4-digit numbers (1,000 to 9,999) -> jump to 10,000
            if (n > 1000 && n < 10000) {
                n = 10000;
            } 
            // Range 2: 6-digit numbers (100,000 to 999,999) -> jump to 1,000,000
            else if (n > 100000 && n < 1000000) {
                n = 1000000;
            } 
            // Range 3: 8-digit numbers (10,000,000 to 99,999,999) -> jump to 100,000,000
            else if (n > 10000000 && n < 100000000) {
                n = 100000000;
            }
        }
    }
}
