import java.util.*;

class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        long k = (long) k1 + k2;
        long[] diff = new long[n];
        long max = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs((long) nums1[i] - nums2[i]);
            max = Math.max(max, diff[i]);
        }

        long total = 0;
        for (long d : diff) {
            total += d;
        }

        if (total <= k) return 0;

        long left = 0, right = max;

        while (left < right) {
            long mid = left + (right - left) / 2;
            long needed = 0;

            for (long d : diff) {
                if (d > mid) {
                    needed += d - mid;
                    if (needed > k) break;
                }
            }

            if (needed <= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        long level = left;
        long used = 0;
        long ans = 0;

        for (long d : diff) {
            if (d > level) {
                used += d - level;
                ans += level * level;
            } else {
                ans += d * d;
            }
        }

        long remaining = k - used;

        for (long d : diff) {
            if (remaining == 0) break;

            if (d >= level && level > 0) {
                ans -= 2 * level - 1;
                remaining--;
            }
        }

        return ans;
    }
}