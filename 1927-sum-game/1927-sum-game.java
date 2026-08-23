class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int diff = 0;
        int qLeft = 0, qRight = 0;

        for (int i = 0; i < n / 2; i++) {
            if (num.charAt(i) == '?')
                qLeft++;
            else
                diff += num.charAt(i) - '0';
        }

        for (int i = n / 2; i < n; i++) {
            if (num.charAt(i) == '?')
                qRight++;
            else
                diff -= num.charAt(i) - '0';
        }

        // If number of ? is odd, Alice can always force inequality.
        if ((qLeft + qRight) % 2 == 1)
            return true;

        // Alice wins if the current difference cannot be balanced.
        return diff != (qRight - qLeft) / 2 * 9;
    }
}