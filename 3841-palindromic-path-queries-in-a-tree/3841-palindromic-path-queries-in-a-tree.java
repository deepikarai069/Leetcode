import java.util.*;

class Solution {
    private List<Integer>[] adj;
    private int[] tin, tout, depth, mask, bit;
    private int[][] up;
    private int timer, LOG;

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        mask = new int[n];
        up = new int[LOG][n];
        bit = new int[n + 2];

        dfs(0, 0, s);

        // Initialize subtree XOR ranges after DFS.
        for (int i = 0; i < n; i++) {
            rangeXor(tin[i], tout[i], mask[i]);
        }

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
            }
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");
            int u = Integer.parseInt(p[1]);

            if (p[0].equals("update")) {
                int next = 1 << (p[2].charAt(0) - 'a');
                int delta = mask[u] ^ next;

                if (delta != 0) {
                    rangeXor(tin[u], tout[u], delta);
                    mask[u] = next;
                }
            } else {
                int v = Integer.parseInt(p[2]);
                int l = lca(u, v);

                int path = point(tin[u])
                         ^ point(tin[v])
                         ^ mask[l];

                ans.add((path & (path - 1)) == 0);
            }
        }

        return ans;
    }

    private void dfs(int u, int parent, String s) {
        tin[u] = ++timer;
        up[0][u] = parent;
        mask[u] = 1 << (s.charAt(u) - 'a');

        for (int v : adj[u]) {
            if (v == parent) continue;

            depth[v] = depth[u] + 1;
            dfs(v, u, s);
        }

        tout[u] = timer;
    }

    private void rangeXor(int l, int r, int val) {
        update(l, val);
        update(r + 1, val);
    }

    private void update(int i, int val) {
        for (; i < bit.length; i += i & -i) {
            bit[i] ^= val;
        }
    }

    private int point(int i) {
        int res = 0;

        for (; i > 0; i -= i & -i) {
            res ^= bit[i];
        }

        return res;
    }

    private int lca(int a, int b) {
        if (depth[a] < depth[b]) {
            int t = a;
            a = b;
            b = t;
        }

        int diff = depth[a] - depth[b];

        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0) {
                a = up[k][a];
            }
        }

        if (a == b) return a;

        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][a] != up[k][b]) {
                a = up[k][a];
                b = up[k][b];
            }
        }

        return up[0][a];
    }
}