class Solution {
    static final long MOD = 1_000_000_007L;

    public int zigZagArrays(int n, int l, int r) {
        int m = r - l + 1;

        if (n == 1) {
            return m;
        }

        int size = 2 * m;

        long[] base = new long[size];

        for (int i = 0; i < m; i++) {
            base[i] = i;
            base[m + i] = m - i - 1;
        }

        long[][] T = new long[size][size];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < i; j++) {
                T[i][m + j] = 1;
            }

            for (int j = i + 1; j < m; j++) {
                T[m + i][j] = 1;
            }
        }

        long[][] P = power(T, n - 2);

        long[] result = multiply(P, base);

        long ans = 0;

        for (long x : result) {
            ans = (ans + x) % MOD;
        }

        return (int) ans;
    }

    private long[][] power(long[][] matrix, int exp) {
        int n = matrix.length;

        long[][] result = new long[n][n];

        for (int i = 0; i < n; i++) {
            result[i][i] = 1;
        }

        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = multiply(result, matrix);
            }

            matrix = multiply(matrix, matrix);
            exp >>= 1;
        }

        return result;
    }

    private long[][] multiply(long[][] A, long[][] B) {
        int n = A.length;

        long[][] C = new long[n][n];

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                if (A[i][k] == 0) {
                    continue;
                }

                for (int j = 0; j < n; j++) {
                    C[i][j] = (C[i][j] + A[i][k] * B[k][j]) % MOD;
                }
            }
        }

        return C;
    }

    private long[] multiply(long[][] A, long[] v) {
        int n = A.length;

        long[] res = new long[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i] = (res[i] + A[i][j] * v[j]) % MOD;
            }
        }

        return res;
    }
}