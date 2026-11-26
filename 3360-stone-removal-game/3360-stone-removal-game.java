
class Solution {
    public boolean canAliceWin(int n) {
        int stones = n;
        int turn = 10;
        boolean alice = true;

        while (stones >= turn) {
            stones -= turn;
            turn--;

            alice = !alice;
        }

        return !alice;
    }
}
