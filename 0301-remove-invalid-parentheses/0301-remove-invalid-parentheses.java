import java.util.*;

class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();
        remove(s, 0, 0, new char[]{'(', ')'}, result);
        return result;
    }

    private void remove(String s, int start, int check, char[] par,
                         List<String> result) {
        int balance = 0;

        for (int i = check; i < s.length(); i++) {
            if (s.charAt(i) == par[0]) {
                balance++;
            } else if (s.charAt(i) == par[1]) {
                balance--;
            }

            if (balance >= 0) continue;

            for (int j = start; j <= i; j++) {
                if (s.charAt(j) == par[1] &&
                    (j == start || s.charAt(j - 1) != par[1])) {

                    remove(s.substring(0, j) + s.substring(j + 1),
                           j, i, par, result);
                }
            }

            return;
        }

        String reversed = new StringBuilder(s).reverse().toString();

        if (par[0] == '(') {
            remove(reversed, 0, 0, new char[]{')', '('}, result);
        } else {
            result.add(reversed);
        }
    }
}