class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;

        long sum = 0;

        // Prefix sum
        for (int x : stones)
            sum += x;

        long best = sum;

        // Work backwards from n-2
        for (int i = n - 2; i >= 1; i--) {
            sum -= stones[i + 1];
            best = Math.max(best, sum - best);
        }

        return (int) best;
    }
}