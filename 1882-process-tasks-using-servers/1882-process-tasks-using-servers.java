import java.util.*;

class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        int n = servers.length;
        int m = tasks.length;

        int[] ans = new int[m];

        PriorityQueue<int[]> available = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0])
                    return Integer.compare(a[0], b[0]);
                return Integer.compare(a[1], b[1]);
            }
        );

        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0])
                    return Long.compare(a[0], b[0]);
                return Long.compare(a[1], b[1]);
            }
        );

        for (int i = 0; i < n; i++) {
            available.offer(new int[]{servers[i], i});
        }

        long currentTime = 0;

        for (int i = 0; i < m; i++) {
            currentTime = Math.max(currentTime, i);

            while (!busy.isEmpty() &&
                   busy.peek()[0] <= currentTime) {

                long[] s = busy.poll();
                int index = (int) s[1];

                available.offer(new int[]{servers[index], index});
            }

            if (available.isEmpty()) {
                currentTime = busy.peek()[0];

                while (!busy.isEmpty() &&
                       busy.peek()[0] <= currentTime) {

                    long[] s = busy.poll();
                    int index = (int) s[1];

                    available.offer(new int[]{servers[index], index});
                }
            }

            int[] server = available.poll();
            int index = server[1];

            ans[i] = index;

            busy.offer(new long[]{
                currentTime + tasks[i], index
            });
        }

        return ans;
    }
}