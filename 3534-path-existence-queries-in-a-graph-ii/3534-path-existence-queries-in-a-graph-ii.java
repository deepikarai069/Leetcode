import java.util.*;

class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {

        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        Arrays.sort(arr, Comparator.comparingInt(a -> a[0]));

        int[] pos = new int[n];
        for (int i = 0; i < n; i++) {
            pos[arr[i][1]] = i;
        }

        int[] far = new int[n];
        int r = 0;
        for (int l = 0; l < n; l++) {
            while (r + 1 < n && arr[r + 1][0] - arr[l][0] <= maxDiff) {
                r++;
            }
            far[l] = r;
        }

        int LOG = 18;
        int[][] up = new int[LOG][n];

        for (int i = 0; i < n; i++) {
            up[0][i] = far[i];
        }

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
            }
        }

        int[] ans = new int[queries.length];

        for (int qi = 0; qi < queries.length; qi++) {

            int u = pos[queries[qi][0]];
            int v = pos[queries[qi][1]];

            if (u > v) {
                int t = u;
                u = v;
                v = t;
            }

            if (u == v) {
                ans[qi] = 0;
                continue;
            }

            if (far[u] == u) {
                ans[qi] = -1;
                continue;
            }

            int cur = u;
            int steps = 0;

            for (int k = LOG - 1; k >= 0; k--) {
                if (up[k][cur] < v) {
                    cur = up[k][cur];
                    steps += 1 << k;
                }
            }

            if (far[cur] >= v) {
                ans[qi] = steps + 1;
            } else {
                ans[qi] = -1;
            }
        }

        return ans;
    }
}