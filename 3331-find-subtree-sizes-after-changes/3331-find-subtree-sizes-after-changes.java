
import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        List<Integer>[] tree = new ArrayList[n];
        List<Integer>[] newTree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
            newTree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        int[] last = new int[26];
        Arrays.fill(last, -1);

        int[] previous = new int[n];
        int[] newParent = new int[n];

        int[] stack = new int[2 * n + 5];
        boolean[] exit = new boolean[2 * n + 5];

        int top = 0;
        stack[top] = 0;
        exit[top++] = false;

        while (top > 0) {
            top--;

            int node = stack[top];

            if (exit[top]) {
                int c = s.charAt(node) - 'a';
                last[c] = previous[node];
                continue;
            }

            int c = s.charAt(node) - 'a';

            previous[node] = last[c];

            newParent[node] = (last[c] != -1)
                    ? last[c] : parent[node];

            last[c] = node;

            stack[top] = node;
            exit[top++] = true;

            List<Integer> children = tree[node];

            for (int i = children.size() - 1; i >= 0; i--) {
                stack[top] = children.get(i);
                exit[top++] = false;
            }
        }

        for (int i = 1; i < n; i++) {
            newTree[newParent[i]].add(i);
        }

        int[] order = new int[n];
        top = 0;

        order[top++] = 0;

        for (int i = 0; i < top; i++) {
            int node = order[i];

            for (int child : newTree[node]) {
                order[top++] = child;
            }
        }

        int[] ans = new int[n];
        Arrays.fill(ans, 1);

        for (int i = n - 1; i > 0; i--) {
            int node = order[i];
            ans[newParent[node]] += ans[node];
        }

        return ans;
    }
}
