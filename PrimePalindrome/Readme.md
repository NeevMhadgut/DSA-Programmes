---

# Prime Palindrome (LeetCode 866)

## Problem Overview

Given an integer `n`, find and return the smallest prime palindrome greater than or equal to `n`.

* **Palindrome:** A number that reads the same backward as forward (e.g., `101`, `12321`).
* **Prime:** A number greater than `1` with no positive divisors other than `1` and itself.

---

## Method & Algorithm Breakdown

A naive search that tests every number sequentially using trial division causes **Time Limit Exceeded (TLE)** because `n` can reach up to $10^8$. To solve this efficiently within runtime and memory limits, the solution combines three core strategies:

```
                  ┌──────────────────────┐
                  │ Precompute Primes    │ (Sieve of Eratosthenes up to 20,000)
                  └──────────┬───────────┘
                             ▼
┌──────────────────► Loop current n ─────────────────┐
│                            │                       │
│                            ▼                       │
│                    Is 'n' Palindrome?              │
│                     /              \               │
│                  [Yes]             [No]            │
│                   /                  \             │
│        Is 'n' Prime?                  │            │
│        (via Sieve Primes)             │            │
│          /        \                   │            │
│       [Yes]       [No]                │            │
│        /            \                 │            │
│   Return n           ▼                │            │
│               n falls in even length? │            │
│                 /          \          │            │
│              [Yes]         [No]       │            │
│               /              \        │            │
│   Jump to next 10^k         n++ ──────┘            │
└────────────────────────────────────────────────────┘

```

### 1. Precomputed Primes via Sieve of Eratosthenes

* Running a full sieve up to $2 \times 10^8$ requires ~200 MB of heap memory, triggering **Memory Limit Exceeded (MLE)**.
* Any composite number $n$ must have at least one prime factor $p \le \sqrt{n}$. For $n \le 2 \times 10^8$, $\sqrt{n} \approx 14,142$.
* We run the **Sieve of Eratosthenes** once up to $20,000$, collecting all $2,262$ primes into an array.
* Primality tests only divide $n$ by these precomputed primes instead of checking all integers, reducing prime-check operations by over $80\%$.

### 2. Fast Fail Order: Palindrome First

* Palindromes are much rarer than primes in higher ranges.
* Extracting digits using modulo/division takes $O(\log_{10} n)$ operations (~8 iterations for $10^8$), which is almost instant.
* By running `isPalin(n)` **before** `isPrime(n)`, the heavier primality check is skipped for $>99.9\%$ of candidates.

### 3. Even-Length Palindrome Skip

Every palindrome with an **even number of digits** is divisible by $11$:

$$\sum_{\text{odd places}} d_i - \sum_{\text{even places}} d_i = 0 \implies \text{Multiple of } 11$$

* Examples: $1001 = 11 \times 91$, $1221 = 11 \times 111$.
* The only prime palindrome with an even number of digits is **$11$ itself**.
* Therefore, we skip all multi-digit even-length ranges entirely:
* **4-digit range:** $1,000 \to 9,999 \implies$ Jump directly to $10,000$.
* **6-digit range:** $100,000 \to 999,999 \implies$ Jump directly to $1,000,000$.
* **8-digit range:** $10,000,000 \to 99,999,999 \implies$ Jump directly to $100,000,000$.


* This jump eliminates **~90 million redundant checks**.

---

## Complexity Analysis

| Metric | Complexity | Explanation |
| --- | --- | --- |
| **Time Complexity** | $O(\sqrt{M} \log \log \sqrt{M} + K \cdot \pi(\sqrt{M}))$ | Sieve initialization takes $O(\sqrt{M} \log \log \sqrt{M})$ where $\sqrt{M} \le 20,000$. Search tests $K$ palindromes against at most $\pi(\sqrt{M}) \approx 2,262$ primes. |
| **Space Complexity** | $O(\sqrt{M})$ | The sieve array and list of primes require $< 1 \text{ KB}$ of memory. |

---
