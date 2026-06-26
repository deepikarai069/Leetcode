class BinaryIndexedTree {
    int n;
    int[] bit;

    public BinaryIndexedTree(int n) {
        this.n = n;
        bit = new int[n + 1];
    }

    public void update(int index, int val) {
        while (index <= n) {
            bit[index] += val;
            index += index & -index;
        }
    }

    public int query(int index) {
        int sum = 0;

        while (index > 0) {
            sum += bit[index];
            index -= index & -index;
        }

        return sum;
    }
}

class Solution {
    public long countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;

        BinaryIndexedTree tree = new BinaryIndexedTree(2 * n + 1);

        int prefix = n + 1;
        tree.update(prefix, 1);

        long ans = 0;

        for (int num : nums) {
            if (num == target) {
                prefix++;
            } else {
                prefix--;
            }

            ans += tree.query(prefix - 1);
            tree.update(prefix, 1);
        }

        return ans;
    }
}