class Solution {
    public String reverseParentheses(String s) {
        StringBuilder sb = new StringBuilder();
        int n = s.length();

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            if (c == ')') {
                int j = sb.length() - 1;

                while (j >= 0 && sb.charAt(j) != '(') {
                    j--;
                }

                sb.deleteCharAt(j);
                int left = j;
                int right = sb.length() - 1;

                while (left < right) {
                    char temp = sb.charAt(left);
                    sb.setCharAt(left, sb.charAt(right));
                    sb.setCharAt(right, temp);
                    left++;
                    right--;
                }
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}