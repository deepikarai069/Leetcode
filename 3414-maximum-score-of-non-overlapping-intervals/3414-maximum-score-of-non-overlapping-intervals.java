import java.util.*;

class Solution {
    static class State {
        long score;
        List<Integer> ids;

        State(long score, List<Integer> ids) {
            this.score = score;
            this.ids = ids;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        Integer[] order = new Integer[n];

        for (int i = 0; i < n; i++) {
            order[i] = i;
        }

        // Sort by right endpoint
        Arrays.sort(order, (a, b) -> {
            int ra = intervals.get(a).get(1);
            int rb = intervals.get(b).get(1);

            if (ra != rb)
                return Integer.compare(ra, rb);

            return Integer.compare(a, b);
        });

        // Find previous non-overlapping interval
        int[] prev = new int[n];

        for (int i = 0; i < n; i++) {
            int current = order[i];
            int left = intervals.get(current).get(0);

            int lo = 0, hi = i - 1;
            int ans = -1;

            while (lo <= hi) {
                int mid = (lo + hi) / 2;

                int midIndex = order[mid];
                int right = intervals.get(midIndex).get(1);

                if (right < left) {
                    ans = mid;
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }

            prev[i] = ans;
        }

        State[][] dp = new State[n + 1][5];

        for (int k = 0; k <= 4; k++) {
            dp[0][k] = new State(0, new ArrayList<>());
        }

        for (int i = 1; i <= n; i++) {
            int current = order[i - 1];
            int weight = intervals.get(current).get(2);

            for (int k = 0; k <= 4; k++) {

                // Don't take current interval
                State best = dp[i - 1][k];

                // Take current interval
                if (k > 0) {
                    int p = prev[i - 1] + 1;

                    State old = dp[p][k - 1];

                    List<Integer> ids = new ArrayList<>(old.ids);
                    ids.add(current);
                    Collections.sort(ids);

                    State take = new State(
                        old.score + weight,
                        ids
                    );

                    if (better(take, best)) {
                        best = take;
                    }
                }

                dp[i][k] = best;
            }
        }

        List<Integer> result = dp[n][4].ids;

        int[] answer = new int[result.size()];

        for (int i = 0; i < result.size(); i++) {
            answer[i] = result.get(i);
        }

        return answer;
    }

    private boolean better(State a, State b) {
        if (a.score != b.score) {
            return a.score > b.score;
        }

        int n = Math.min(a.ids.size(), b.ids.size());

        for (int i = 0; i < n; i++) {
            if (!a.ids.get(i).equals(b.ids.get(i))) {
                return a.ids.get(i) < b.ids.get(i);
            }
        }

        return a.ids.size() < b.ids.size();
    }
}