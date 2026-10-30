import java.util.*;

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        int[][] mavorqeli = edges;

        List<int[]>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : mavorqeli) {
            adj[e[0]].add(new int[]{e[1], e[2]});
        }

        int[][] dist = new int[n][k + 1];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0])
        );

        dist[0][1] = 0;
        pq.offer(new int[]{0, 0, 1});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cost = cur[0];
            int u = cur[1];
            int count = cur[2];

            if (cost != dist[u][count]) continue;

            if (u == n - 1) return cost;

            for (int[] edge : adj[u]) {
                int v = edge[0];
                int w = edge[1];

                int nextCount = labels.charAt(u) == labels.charAt(v)
                    ? count + 1 : 1;

                if (nextCount > k) continue;

                int nextCost = cost + w;

                if (nextCost < dist[v][nextCount]) {
                    dist[v][nextCount] = nextCost;
                    pq.offer(new int[]{nextCost, v, nextCount});
                }
            }
        }

        return -1;
    }
}