class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {

        int m = grid.length;
        int n = grid[0].length;

        int total = m * n;

        k %= total;

        int[][] ans = new int[m][n];

        for (int r = 0; r < m; r++) {

            for (int c = 0; c < n; c++) {

                int oldIndex = r * n + c;

                int newIndex = (oldIndex + k) % total;

                int newRow = newIndex / n;

                int newCol = newIndex % n;

                ans[newRow][newCol] = grid[r][c];
            }
        }

        List<List<Integer>> result = new ArrayList<>();

        for (int[] row : ans) {

            List<Integer> list = new ArrayList<>();

            for (int x : row)
                list.add(x);

            result.add(list);
        }

        return result;
    }
}