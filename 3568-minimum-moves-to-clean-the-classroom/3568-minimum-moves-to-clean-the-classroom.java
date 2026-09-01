import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int sr = 0, sc = 0;

        int[][] litterId = new int[m][n];

        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }

        int litterCount = 0;

        // Find S and assign IDs to L
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    sr = i;
                    sc = j;
                }

                if (ch == 'L') {
                    litterId[i][j] = litterCount++;
                }
            }
        }

        int fullMask = (1 << litterCount) - 1;

        // visited[row][col][mask][energy]
        boolean[][][][] visited =
            new boolean[m][n][1 << litterCount][energy + 1];

        Queue<State> queue = new ArrayDeque<>();

        queue.offer(new State(sr, sc, 0, energy));
        visited[sr][sc][0][energy] = true;

        int moves = 0;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {

            int size = queue.size();

            while (size-- > 0) {

                State cur = queue.poll();

                int r = cur.r;
                int c = cur.c;
                int mask = cur.mask;
                int e = cur.energy;

                // All litter collected
                if (mask == fullMask) {
                    return moves;
                }

                // No energy → cannot move
                if (e == 0) {
                    continue;
                }

                for (int d = 0; d < 4; d++) {

                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    // Boundary
                    if (nr < 0 || nr >= m ||
                        nc < 0 || nc >= n) {
                        continue;
                    }

                    // Obstacle
                    if (classroom[nr].charAt(nc) == 'X') {
                        continue;
                    }

                    // One move costs one energy
                    int ne = e - 1;

                    // Update litter mask
                    int newMask = mask;

                    if (litterId[nr][nc] != -1) {
                        newMask |= (1 << litterId[nr][nc]);
                    }

                    // Reset area
                    if (classroom[nr].charAt(nc) == 'R') {
                        ne = energy;
                    }

                    if (!visited[nr][nc][newMask][ne]) {

                        visited[nr][nc][newMask][ne] = true;

                        queue.offer(
                            new State(nr, nc, newMask, ne)
                        );
                    }
                }
            }

            moves++;
        }

        return -1;
    }

    static class State {
        int r;
        int c;
        int mask;
        int energy;

        State(int r, int c, int mask, int energy) {
            this.r = r;
            this.c = c;
            this.mask = mask;
            this.energy = energy;
        }
    }
}