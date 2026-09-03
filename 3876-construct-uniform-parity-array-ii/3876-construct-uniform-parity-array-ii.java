class Solution {
    public boolean uniformArray(int[] nums1) {
        boolean hasEven = false;
        boolean hasOdd = false;

        for (int x : nums1) {
            if (x % 2 == 0)
                hasEven = true;
            else
                hasOdd = true;
        }

        // If all elements already have same parity
        if (!hasEven || !hasOdd)
            return true;

        // Mixed parity is possible only when the minimum
        // element is odd.
        int min = nums1[0];

        for (int x : nums1) {
            min = Math.min(min, x);
        }

        return min % 2 == 1;
    }
}