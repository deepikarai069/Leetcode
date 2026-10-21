class Solution {
    public int minOperations(String s) {
        int n = s.length();
        char[] arr = s.toCharArray();

        boolean sorted = true;
        char mn = arr[0], mx = arr[0];

        for (int i = 1; i < n; i++) {
            if (arr[i] < arr[i - 1]) {
                sorted = false;
            }
            mn = (char) Math.min(mn, arr[i]);
            mx = (char) Math.max(mx, arr[i]);
        }

        if (sorted) return 0;
        if (n == 2) return -1;

        if (arr[0] == mn || arr[n - 1] == mx) {
            return 1;
        }

        for (int i = 1; i < n - 1; i++) {
            if (arr[i] == mn || arr[i] == mx) {
                return 2;
            }
        }

        return 3;
    }
}