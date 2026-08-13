class Solution {
    int[] left, right, pref, suff, best, len;
    char[] s;

    void build(int node, int l, int r) {
        len[node] = r - l + 1;
        if (l == r) {
            left[node] = right[node] = pref[node] = suff[node] = best[node] = 1;
            return;
        }

        int mid = (l + r) / 2;
        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);
        merge(node, node * 2, node * 2 + 1);
    }

    void merge(int p, int a, int b) {
        len[p] = len[a] + len[b];

        left[p] = left[a];
        right[p] = right[b];

        pref[p] = pref[a];
        suff[p] = suff[b];
        best[p] = Math.max(best[a], best[b]);

        // Store character indirectly using extra arrays
    }

    // Easier implementation using Node
    class Node {
        char lc, rc;
        int pre, suf, best, len;

        Node(char c) {
            lc = rc = c;
            pre = suf = best = len = 1;
        }
    }

    Node[] tree;

    Node mergeNodes(Node a, Node b) {
        if (a == null) return b;
        if (b == null) return a;

        Node c = new Node(a.lc);
        c.lc = a.lc;
        c.rc = b.rc;
        c.len = a.len + b.len;

        c.pre = a.pre;
        if (a.pre == a.len && a.rc == b.lc)
            c.pre += b.pre;

        c.suf = b.suf;
        if (b.suf == b.len && a.rc == b.lc)
            c.suf += a.suf;

        c.best = Math.max(a.best, b.best);

        if (a.rc == b.lc)
            c.best = Math.max(c.best, a.suf + b.pre);

        return c;
    }

    void buildTree(int node, int l, int r) {
        if (l == r) {
            tree[node] = new Node(s[l]);
            return;
        }

        int mid = (l + r) / 2;
        buildTree(node * 2, l, mid);
        buildTree(node * 2 + 1, mid + 1, r);

        tree[node] = mergeNodes(tree[node * 2], tree[node * 2 + 1]);
    }

    void update(int node, int l, int r, int idx, char ch) {
        if (l == r) {
            tree[node] = new Node(ch);
            return;
        }

        int mid = (l + r) / 2;

        if (idx <= mid)
            update(node * 2, l, mid, idx, ch);
        else
            update(node * 2 + 1, mid + 1, r, idx, ch);

        tree[node] = mergeNodes(tree[node * 2], tree[node * 2 + 1]);
    }

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        this.s = s.toCharArray();

        int n = s.length();
        int k = queryIndices.length;

        tree = new Node[4 * n];

        buildTree(1, 0, n - 1);

        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {
            int idx = queryIndices[i];
            char ch = queryCharacters.charAt(i);

            update(1, 0, n - 1, idx, ch);

            ans[i] = tree[1].best;
        }

        return ans;
    }
}