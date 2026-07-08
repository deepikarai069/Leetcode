class Solution {

    static final int MOD = 1_000_000_007;

    public int[] sumAndMultiply(String s, int[][] queries) {

        int n = s.length();

        int[] map = new int[n];
        java.util.Arrays.fill(map, -1);

        java.util.ArrayList<Integer> digit = new java.util.ArrayList<>();

        int idx = 0;

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != '0') {
                map[i] = idx++;
                digit.add(s.charAt(i) - '0');
            }
        }

        int m = digit.size();

        long[] prefixSum = new long[m + 1];
        long[] prefixNum = new long[m + 1];
        long[] pow10 = new long[m + 1];

        pow10[0] = 1;

        for (int i = 1; i <= m; i++)
            pow10[i] = (pow10[i - 1] * 10) % MOD;

        for (int i = 0; i < m; i++) {
            prefixSum[i + 1] = prefixSum[i] + digit.get(i);
            prefixNum[i + 1] = (prefixNum[i] * 10 + digit.get(i)) % MOD;
        }

        int[] next = new int[n];
        int[] prev = new int[n];

        int last = -1;
        for (int i = 0; i < n; i++) {
            if (map[i] != -1)
                last = map[i];
            prev[i] = last;
        }

        int nxt = -1;
        for (int i = n - 1; i >= 0; i--) {
            if (map[i] != -1)
                nxt = map[i];
            next[i] = nxt;
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int l = queries[i][0];
            int r = queries[i][1];

            int L = next[l];
            int R = prev[r];

            if (L == -1 || R == -1 || L > R) {
                ans[i] = 0;
                continue;
            }

            int len = R - L + 1;

            long x = (prefixNum[R + 1]
                    - (prefixNum[L] * pow10[len]) % MOD
                    + MOD) % MOD;

            long sum = prefixSum[R + 1] - prefixSum[L];

            ans[i] = (int) ((x * sum) % MOD);
        }

        return ans;
    }
}