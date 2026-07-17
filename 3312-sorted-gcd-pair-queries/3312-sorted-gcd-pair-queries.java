class Solution {

    public int[] gcdValues(int[] nums, long[] queries) {

        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }

        int[] cnt = new int[mx + 1];
        for (int x : nums) {
            cnt[x]++;
        }

        long[] cntG = new long[mx + 1];

        for (int i = mx; i >= 1; i--) {

            int v = 0;

            for (int j = i; j <= mx; j += i) {

                v += cnt[j];
                cntG[i] -= cntG[j];
            }

            cntG[i] += 1L * v * (v - 1) / 2;
        }

        for (int i = 2; i <= mx; i++) {
            cntG[i] += cntG[i - 1];
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            ans[i] = search(cntG, queries[i]);
        }

        return ans;
    }

    private int search(long[] prefix, long target) {

        int l = 0;
        int r = prefix.length;

        while (l < r) {

            int mid = (l + r) / 2;

            if (prefix[mid] > target)
                r = mid;
            else
                l = mid + 1;
        }

        return l;
    }
}