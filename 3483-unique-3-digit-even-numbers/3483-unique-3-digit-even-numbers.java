class Solution {
    public int totalNumbers(int[] digits) {
        int[] count = new int[10];

        for (int d : digits) {
            count[d]++;
        }

        int ans = 0;

        // Hundreds digit: 1-9
        for (int a = 1; a <= 9; a++) {
            if (count[a] == 0) continue;

            count[a]--;

            // Tens digit: 0-9
            for (int b = 0; b <= 9; b++) {
                if (count[b] == 0) continue;

                count[b]--;

                // Units digit must be even
                for (int c = 0; c <= 8; c += 2) {
                    if (count[c] > 0) {
                        ans++;
                    }
                }

                count[b]++;
            }

            count[a]++;
        }

        return ans;
    }
}