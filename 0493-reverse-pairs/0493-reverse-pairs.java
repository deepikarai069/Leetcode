class Solution {

    int count = 0;

    public int reversePairs(int[] nums) {

        mergeSort(nums, 0, nums.length - 1);

        return count;
    }

    private void mergeSort(int[] nums, int low, int high) {

        if (low >= high)
            return;

        int mid = low + (high - low) / 2;

        mergeSort(nums, low, mid);
        mergeSort(nums, mid + 1, high);

        countPairs(nums, low, mid, high);

        merge(nums, low, mid, high);
    }

    private void countPairs(int[] nums, int low, int mid, int high) {

        int right = mid + 1;

        for (int i = low; i <= mid; i++) {

            while (right <= high &&
                    (long) nums[i] > 2L * nums[right]) {

                right++;
            }

            count += right - (mid + 1);
        }
    }

    private void merge(int[] nums, int low, int mid, int high) {

        int[] temp = new int[high - low + 1];

        int left = low;
        int right = mid + 1;
        int k = 0;

        while (left <= mid && right <= high) {

            if (nums[left] <= nums[right]) {
                temp[k++] = nums[left++];
            } else {
                temp[k++] = nums[right++];
            }
        }

        while (left <= mid)
            temp[k++] = nums[left++];

        while (right <= high)
            temp[k++] = nums[right++];

        for (int i = 0; i < temp.length; i++)
            nums[low + i] = temp[i];
    }
}