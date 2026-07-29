class Solution {
    private static final long CAP = 2_000_000L; // > max possible k (1e6)

    public String smallestPalindrome(String s, int k) {
        long kk = k; // work with long internally to avoid overflow during subtraction
        int[] cnt = new int[26];
        for (char c : s.toCharArray()) cnt[c - 'a']++;

        int oddChar = -1;
        int[] half = new int[26];
        int halfLen = 0;
        for (int i = 0; i < 26; i++) {
            half[i] = cnt[i] / 2;
            halfLen += half[i];
            if ((cnt[i] & 1) == 1) oddChar = i;
        }

        // Check feasibility upfront
        long total = countPermutations(half.clone(), halfLen);
        if (total < kk) return "";

        int[] counts = half.clone();
        int remaining = halfLen;
        StringBuilder sb = new StringBuilder();

        for (int pos = 0; pos < halfLen; pos++) {
            boolean placed = false;
            for (int c = 0; c < 26; c++) {
                if (counts[c] == 0) continue;
                counts[c]--;
                long permCount = countPermutations(counts, remaining - 1);
                if (permCount >= kk) {
                    sb.append((char) ('a' + c));
                    remaining--;
                    placed = true;
                    break;
                } else {
                    kk -= permCount;
                    counts[c]++; // undo, try next letter
                }
            }
            if (!placed) return ""; // shouldn't happen given the feasibility check
        }

        String halfStr = sb.toString();
        StringBuilder result = new StringBuilder(halfStr);
        if (oddChar != -1) result.append((char) ('a' + oddChar));
        result.append(new StringBuilder(halfStr).reverse());
        return result.toString();
    }

    // Number of distinct permutations of the multiset `counts` (total length m), capped at CAP
    private long countPermutations(int[] counts, int m) {
        long result = 1;
        int remaining = m;
        for (int c = 0; c < 26 && result < CAP; c++) {
            int r = counts[c];
            if (r == 0) continue;
            long binom = binomialCapped(remaining, r);
            result *= binom;
            if (result > CAP) result = CAP;
            remaining -= r;
        }
        return result;
    }

    // C(n, r), computed incrementally (stays integer at every step), capped at CAP
    private long binomialCapped(int n, int r) {
        int rr = Math.min(r, n - r);
        long binom = 1;
        for (int i = 1; i <= rr; i++) {
            binom = binom * (n - rr + i) / i;
            if (binom > CAP) { binom = CAP; break; }
        }
        return binom;
    }
}