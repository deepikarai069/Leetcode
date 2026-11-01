class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int mod = 1_000_000_007;

        int[][] right = new int[m + 1][n + 1];
        int[][] down = new int[m + 1][n + 1];

        right[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int r = right[i][j];
                int d = down[i][j];

                if (grid[i][j] == 0) {
                    int total = (int) (((long) r + d) % mod);

                    right[i][j + 1] =
                        (right[i][j + 1] + total) % mod;

                    down[i + 1][j] =
                        (down[i + 1][j] + total) % mod;
                } else {
                    // Entering mirror from the left redirects down
                    down[i + 1][j] =
                        (int) ((down[i + 1][j] + (long) r) % mod);

                    // Entering mirror from above redirects right
                    right[i][j + 1] =
                        (int) ((right[i][j + 1] + (long) d) % mod);
                }
            }
        }

        return (int) (((long) right[m - 1][n - 1]
                     + down[m - 1][n - 1]) % mod);
    }
}