class Solution {
    static class Node {
        long[] pref, suff, total;
        int prod;

        Node(int k) {
            pref = new long[k];
            suff = new long[k];
            total = new long[k];
        }
    }

    int k;
    Node[] tree;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.k = k;
        tree = new Node[4 * nums.length];

        build(1, 0, nums.length - 1, nums);

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            update(1, 0, nums.length - 1, index, value % k);

            Node res = query(1, 0, nums.length - 1,
                             start, nums.length - 1);

            ans[i] = (int) res.pref[x];
        }

        return ans;
    }

    private void build(int node, int l, int r, int[] nums) {
        if (l == r) {
            tree[node] = new Node(k);

            int v = nums[l] % k;

            tree[node].prod = v;
            tree[node].pref[v] = 1;
            tree[node].suff[v] = 1;
            tree[node].total[v] = 1;

            return;
        }

        int mid = (l + r) / 2;

        build(node * 2, l, mid, nums);
        build(node * 2 + 1, mid + 1, r, nums);

        tree[node] = merge(tree[node * 2],
                           tree[node * 2 + 1]);
    }

    private Node merge(Node a, Node b) {
        Node res = new Node(k);

        res.prod = (int) ((long) a.prod * b.prod % k);

        // Prefixes
        for (int i = 0; i < k; i++) {
            res.pref[i] += a.pref[i];
        }

        for (int i = 0; i < k; i++) {
            if (b.pref[i] == 0) continue;

            int rem = (int) ((long) a.prod * i % k);
            res.pref[rem] += b.pref[i];
        }

        // Suffixes
        for (int i = 0; i < k; i++) {
            res.suff[i] += b.suff[i];
        }

        for (int i = 0; i < k; i++) {
            if (a.suff[i] == 0) continue;

            int rem = (int) ((long) i * b.prod % k);
            res.suff[rem] += a.suff[i];
        }

        // All subarrays
        for (int i = 0; i < k; i++) {
            res.total[i] += a.total[i];
            res.total[i] += b.total[i];
        }

        for (int i = 0; i < k; i++) {
            if (a.suff[i] == 0) continue;

            for (int j = 0; j < k; j++) {
                if (b.pref[j] == 0) continue;

                int rem = (int) ((long) i * j % k);

                res.total[rem] +=
                    a.suff[i] * b.pref[j];
            }
        }

        return res;
    }

    private void update(int node, int l, int r,
                        int index, int value) {

        if (l == r) {
            tree[node] = new Node(k);

            tree[node].prod = value;
            tree[node].pref[value] = 1;
            tree[node].suff[value] = 1;
            tree[node].total[value] = 1;

            return;
        }

        int mid = (l + r) / 2;

        if (index <= mid) {
            update(node * 2, l, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, r, index, value);
        }

        tree[node] = merge(tree[node * 2],
                           tree[node * 2 + 1]);
    }

    private Node query(int node, int l, int r,
                       int ql, int qr) {

        if (r < ql || l > qr) {
            return null;
        }

        if (ql <= l && r <= qr) {
            return tree[node];
        }

        int mid = (l + r) / 2;

        Node left = query(node * 2, l, mid, ql, qr);
        Node right = query(node * 2 + 1, mid + 1, r, ql, qr);

        if (left == null) return right;
        if (right == null) return left;

        return merge(left, right);
    }
}