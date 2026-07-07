class Solution {
    public long sumAndMultiply(int n) {
        StringBuilder sb = new StringBuilder();

        while (n > 0) {
            int digit = n % 10;
            if (digit != 0) {
                sb.insert(0, digit);
            }
            n /= 10;
        }

        if (sb.length() == 0) {
            return 0;
        }

        long x = Long.parseLong(sb.toString());

        long sum = 0;
        while (x > 0) {
            sum += x % 10;
            x /= 10;
        }

        x = Long.parseLong(sb.toString());

        return x * sum;
    }
}