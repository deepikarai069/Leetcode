class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        int minLen = Integer.MAX_VALUE;
        String ans = "";

        for (int i = 0; i < n; i++) {
            int ones = 0;

            for (int j = i; j < n; j++) {
                if (s.charAt(j) == '1')
                    ones++;

                if (ones == k) {
                    String cur = s.substring(i, j + 1);

                    if (cur.length() < minLen ||
                        (cur.length() == minLen && cur.compareTo(ans) < 0)) {
                        minLen = cur.length();
                        ans = cur;
                    }

                    break;
                }
            }
        }

        return ans;
    }
}