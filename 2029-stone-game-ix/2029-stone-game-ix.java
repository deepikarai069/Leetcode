class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] cnt = new int[3];

        for (int x : stones) {
            cnt[x % 3]++;
        }

        int a = cnt[1], b = cnt[2], c = cnt[0];

        if (a == 0 || b == 0) {
            return Math.max(a, b) > 2 && c % 2 == 1;
        }

        return Math.abs(a - b) > 2 || c % 2 == 0;
    }
}