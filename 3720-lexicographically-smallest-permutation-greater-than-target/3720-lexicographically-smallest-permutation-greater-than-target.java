class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int[] cnt = new int[26];

        for (char c : s.toCharArray())
            cnt[c - 'a']++;

        StringBuilder prefix = new StringBuilder();

        for (int i = 0; i < target.length(); i++) {
            int t = target.charAt(i) - 'a';

            // Try to keep target character
            if (cnt[t] > 0) {
                cnt[t]--;
                prefix.append(target.charAt(i));
            } else {
                // Current character cannot match.
                // Try a character greater than target[i].
                for (int c = t + 1; c < 26; c++) {
                    if (cnt[c] > 0) {
                        cnt[c]--;

                        StringBuilder ans = new StringBuilder(prefix);
                        ans.append((char) ('a' + c));

                        for (int x = 0; x < 26; x++) {
                            while (cnt[x] > 0) {
                                ans.append((char) ('a' + x));
                                cnt[x]--;
                            }
                        }

                        return ans.toString();
                    }
                }

                break;
            }
        }

        // Backtrack to find the rightmost position
        // that can be made larger.
        for (int i = prefix.length() - 1; i >= 0; i--) {
            int t = target.charAt(i) - 'a';

            cnt[t]++;
            prefix.deleteCharAt(prefix.length() - 1);

            for (int c = t + 1; c < 26; c++) {
                if (cnt[c] > 0) {
                    cnt[c]--;

                    StringBuilder ans = new StringBuilder(prefix);
                    ans.append((char) ('a' + c));

                    for (int x = 0; x < 26; x++) {
                        while (cnt[x] > 0) {
                            ans.append((char) ('a' + x));
                            cnt[x]--;
                        }
                    }

                    return ans.toString();
                }
            }
        }

        return "";
    }
}