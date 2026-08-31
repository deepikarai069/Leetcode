class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int first = -1;
        int last = -1;
        int minDist = Integer.MAX_VALUE;

        ListNode prev = head;
        ListNode curr = head.next;

        int index = 1;

        while (curr.next != null) {
            ListNode next = curr.next;

            boolean critical =
                (curr.val > prev.val && curr.val > next.val) ||
                (curr.val < prev.val && curr.val < next.val);

            if (critical) {
                if (first == -1) {
                    first = index;
                }

                if (last != -1) {
                    minDist = Math.min(minDist, index - last);
                }

                last = index;
            }

            prev = curr;
            curr = next;
            index++;
        }

        if (first == -1 || first == last) {
            return new int[]{-1, -1};
        }

        int maxDist = last - first;

        return new int[]{minDist, maxDist};
    }
}