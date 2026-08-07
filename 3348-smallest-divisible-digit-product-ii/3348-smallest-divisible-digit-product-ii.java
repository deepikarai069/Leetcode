class Solution {

    // digit -> {exp of 2, exp of 3, exp of 5, exp of 7}
    private static final int[][] DIGIT_VEC = new int[10][];
    static {
        DIGIT_VEC[1] = new int[]{0,0,0,0};
        DIGIT_VEC[2] = new int[]{1,0,0,0};
        DIGIT_VEC[3] = new int[]{0,1,0,0};
        DIGIT_VEC[4] = new int[]{2,0,0,0};
        DIGIT_VEC[5] = new int[]{0,0,1,0};
        DIGIT_VEC[6] = new int[]{1,1,0,0};
        DIGIT_VEC[7] = new int[]{0,0,0,1};
        DIGIT_VEC[8] = new int[]{3,0,0,0};
        DIGIT_VEC[9] = new int[]{0,2,0,0};
    }

    private int maxA, maxB, maxC, maxD;
    private int[][][][] minDigits; // min # of digits (2..9) to cover a need state

    public String smallestNumber(String num, long t) {
        int[] need0 = factor(t);
        if (need0 == null) return "-1";

        maxA = need0[0]; maxB = need0[1]; maxC = need0[2]; maxD = need0[3];
        buildMinDigits();

        int L = num.length();
        int[] digits = new int[L];
        for (int i = 0; i < L; i++) digits[i] = num.charAt(i) - '0';

        // 1) check num itself
        int[] cur = need0.clone();
        boolean ok = true;
        for (int dg : digits) {
            if (dg == 0) { ok = false; break; }
            cur = sub(cur, dg);
        }
        if (ok && isZero(cur)) return num;

        // 2) prefix states: prefixState[i] = need after applying digits[0..i-1]
        int[][] prefixState = new int[L + 1][];
        prefixState[0] = need0.clone();
        int firstZero = L;
        for (int i = 0; i < L; i++) {
            if (digits[i] == 0 && firstZero == L) firstZero = i;
            if (digits[i] == 0) {
                prefixState[i + 1] = prefixState[i]; // unused beyond firstZero
            } else {
                prefixState[i + 1] = sub(prefixState[i], digits[i]);
            }
        }

        int upper = Math.min(L - 1, firstZero);
        String ans = null;
        for (int i = upper; i >= 0; i--) {
            int[] base = prefixState[i];
            int bestD = -1;
            int[] bestState = null;
            for (int d = digits[i] + 1; d <= 9; d++) {
                int[] ns = sub(base, d);
                int remainingPositions = L - 1 - i;
                if (minDigitsNeeded(ns) <= remainingPositions) {
                    bestD = d;
                    bestState = ns;
                    break;
                }
            }
            if (bestD != -1) {
                StringBuilder sb = new StringBuilder();
                sb.append(num, 0, i);
                sb.append((char) ('0' + bestD));
                sb.append(smallestCompletion(bestState, L - 1 - i));
                ans = sb.toString();
                break;
            }
        }
        if (ans != null) return ans;

        // 3) need a longer length
        int Lp = L + 1;
        while (minDigitsNeeded(need0) > Lp) Lp++;
        return smallestCompletion(need0, Lp);
    }

    // factor t into powers of 2,3,5,7; return null if impossible
    private int[] factor(long t) {
        int[] primes = {2, 3, 5, 7};
        int[] exp = new int[4];
        for (int i = 0; i < 4; i++) {
            while (t % primes[i] == 0) {
                t /= primes[i];
                exp[i]++;
            }
        }
        if (t != 1) return null;
        return exp;
    }

    private int[] sub(int[] state, int digit) {
        int[] v = DIGIT_VEC[digit];
        return new int[]{
            Math.max(0, state[0] - v[0]),
            Math.max(0, state[1] - v[1]),
            Math.max(0, state[2] - v[2]),
            Math.max(0, state[3] - v[3])
        };
    }

    private boolean isZero(int[] s) {
        return s[0] == 0 && s[1] == 0 && s[2] == 0 && s[3] == 0;
    }

    private int minDigitsNeeded(int[] state) {
        int a = Math.min(state[0], maxA);
        int b = Math.min(state[1], maxB);
        int c = Math.min(state[2], maxC);
        int d = Math.min(state[3], maxD);
        return minDigits[a][b][c][d];
    }

    // builds minDigits table by increasing sum(a+b+c+d)
    private void buildMinDigits() {
        minDigits = new int[maxA + 1][maxB + 1][maxC + 1][maxD + 1];
        final int INF = Integer.MAX_VALUE / 2;
        int maxSum = maxA + maxB + maxC + maxD;

        // group state indices by sum for processing order
        java.util.List<java.util.List<int[]>> bySum = new java.util.ArrayList<>();
        for (int s = 0; s <= maxSum; s++) bySum.add(new java.util.ArrayList<>());

        for (int a = 0; a <= maxA; a++)
            for (int b = 0; b <= maxB; b++)
                for (int c = 0; c <= maxC; c++)
                    for (int d = 0; d <= maxD; d++) {
                        minDigits[a][b][c][d] = INF;
                        bySum.get(a + b + c + d).add(new int[]{a, b, c, d});
                    }

        minDigits[0][0][0][0] = 0;

        for (int s = 0; s <= maxSum; s++) {
            for (int[] st : bySum.get(s)) {
                int a = st[0], b = st[1], c = st[2], d = st[3];
                if (a == 0 && b == 0 && c == 0 && d == 0) continue;
                int best = INF;
                for (int dig = 2; dig <= 9; dig++) {
                    int[] v = DIGIT_VEC[dig];
                    int na = Math.max(0, a - v[0]);
                    int nb = Math.max(0, b - v[1]);
                    int nc = Math.max(0, c - v[2]);
                    int nd = Math.max(0, d - v[3]);
                    if (na == a && nb == b && nc == c && nd == d) continue; // no progress
                    int cand = 1 + minDigits[na][nb][nc][nd];
                    if (cand < best) best = cand;
                }
                minDigits[a][b][c][d] = best;
            }
        }
    }

    // smallest lexicographic string of length L (digits 1-9) satisfying `state`
    // assumes feasibility: minDigitsNeeded(state) <= L
    private String smallestCompletion(int[] state, int L) {
        StringBuilder sb = new StringBuilder();
        int[] cur = state;
        for (int i = 0; i < L; i++) {
            int remainingPositions = L - 1 - i;
            for (int dig = 1; dig <= 9; dig++) {
                int[] ns = sub(cur, dig);
                if (minDigitsNeeded(ns) <= remainingPositions) {
                    sb.append((char) ('0' + dig));
                    cur = ns;
                    break;
                }
            }
        }
        return sb.toString();
    }
}