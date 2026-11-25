
import java.util.*;

class Solution {
    static final long MOD1 = 1000000007L;
    static final long MOD2 = 1000000009L;
    static final long BASE1 = 100019L;
    static final long BASE2 = 100043L;

    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;

        long[] pow1 = new long[n + 1];
        long[] pow2 = new long[n + 1];
        long[] h1 = new long[n + 1];
        long[] h2 = new long[n + 1];

        pow1[0] = 1;
        pow2[0] = 1;

        for (int i = 0; i < n; i++) {
            pow1[i + 1] = pow1[i] * BASE1 % MOD1;
            pow2[i + 1] = pow2[i] * BASE2 % MOD2;

            h1[i + 1] = (h1[i] * BASE1 + nums[i]) % MOD1;
            h2[i + 1] = (h2[i] * BASE2 + nums[i]) % MOD2;
        }

        int left = 1, right = n;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (check(nums, mid, h1, h2, pow1, pow2)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private boolean check(int[] nums, int len,
                          long[] h1, long[] h2,
                          long[] pow1, long[] pow2) {

        Map<Long, Integer> map = new HashMap<>();
        int n = nums.length;

        for (int i = 0; i + len <= n; i++) {
            int j = i + len;

            long hash1 = (h1[j] - h1[i] * pow1[len] % MOD1 + MOD1) % MOD1;
            long hash2 = (h2[j] - h2[i] * pow2[len] % MOD2 + MOD2) % MOD2;

            long key = (hash1 << 32) | hash2;

            map.put(key, map.getOrDefault(key, 0) + 1);
        }

        for (int count : map.values()) {
            if (count == 1) {
                return true;
            }
        }

        return false;
    }
}
