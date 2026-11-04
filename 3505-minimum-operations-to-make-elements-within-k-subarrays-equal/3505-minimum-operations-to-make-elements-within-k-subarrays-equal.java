import java.util.*;

class Solution {
    TreeMap<Integer, Integer> low = new TreeMap<>();
    TreeMap<Integer, Integer> high = new TreeMap<>();

    int lowSize = 0, highSize = 0;
    long lowSum = 0, highSum = 0;

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] cost = new long[n - x + 1];

        low.clear();
        high.clear();
        lowSize = highSize = 0;
        lowSum = highSum = 0;

        for (int i = 0; i < x; i++) add(nums[i]);
        cost[0] = getCost();

        for (int i = x; i < n; i++) {
            remove(nums[i - x]);
            add(nums[i]);
            cost[i - x + 1] = getCost();
        }

        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[k + 1][n + 1];

        for (int t = 1; t <= k; t++) {
            Arrays.fill(dp[t], INF);
        }

        for (int t = 1; t <= k; t++) {
            for (int i = 1; i <= n; i++) {
                dp[t][i] = dp[t][i - 1];

                if (i >= x && dp[t - 1][i - x] != INF) {
                    dp[t][i] = Math.min(
                        dp[t][i],
                        dp[t - 1][i - x] + cost[i - x]
                    );
                }
            }
        }

        return dp[k][n];
    }

    private void add(int val) {
        if (low.isEmpty() || val <= low.lastKey()) {
            low.merge(val, 1, Integer::sum);
            lowSize++;
            lowSum += val;
        } else {
            high.merge(val, 1, Integer::sum);
            highSize++;
            highSum += val;
        }
        balance();
    }

    private void remove(int val) {
        if (low.containsKey(val)) {
            erase(low, val);
            lowSize--;
            lowSum -= val;
        } else {
            erase(high, val);
            highSize--;
            highSum -= val;
        }
        balance();
    }

    private void erase(TreeMap<Integer, Integer> map, int val) {
        int count = map.get(val);
        if (count == 1) map.remove(val);
        else map.put(val, count - 1);
    }

    private void balance() {
        while (lowSize > highSize + 1) {
            int val = low.lastKey();
            erase(low, val);
            lowSize--;
            lowSum -= val;

            high.merge(val, 1, Integer::sum);
            highSize++;
            highSum += val;
        }

        while (lowSize < highSize) {
            int val = high.firstKey();
            erase(high, val);
            highSize--;
            highSum -= val;

            low.merge(val, 1, Integer::sum);
            lowSize++;
            lowSum += val;
        }
    }

    private long getCost() {
        long median = low.lastKey();

        return median * lowSize - lowSum
             + highSum - median * highSize;
    }
}