/**
 * Problem Name: Building Teams
 * Link: https://cses.fi/problemset/task/1668
 *
 * Description:
 * There are n pupils in Uolevi's class, and m friendships between them. You want to divide the class into two teams
 * in such a way that no two pupils in a team are friends. You can freely choose the sizes of the teams.
 *
 * Input:
 * The first input line has two integers n and m: the number of pupils and friendships.
 * After this, there are m lines describing the friendships. Each line has two integers a and b:
 * pupils a and b are friends.
 *
 * Output:
 * Print an example of how to build the teams. For each pupil, print "1" or "2" depending on to which team they will be assigned.
 * You can print any valid team. If there are no solutions, print "IMPOSSIBLE".
 *
 * Constraints:
 * 1 <= n <= 10^5
 * 1 <= m <= 2 * 10^5
 * 1 <= a, b <= n
 */

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BuildingTeams {
    public static void main(String[] args) {
        // Uncomment the lines below to read from input.txt and write to output.txt locally.
        // On platforms like CSES/Codeforces, keep this commented out or use ONLINE_JUDGE properties
        try {
            System.setIn(new FileInputStream("input.txt"));
            System.setOut(new PrintStream(new FileOutputStream("output.txt")));
        } catch (Exception e) {
            System.err.println("Error setting up local I/O: " + e.getMessage());
        }

        FastReader sc = new FastReader();
        FastWriter out = new FastWriter();

        int n = sc.nextInt(), m = sc.nextInt();
        List<ArrayList<Integer>> adjList = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; ++i) {
            adjList.add(new ArrayList<>());
        }
        for (int i = 0; i < m; ++i) {
            int u = sc.nextInt(), v = sc.nextInt();
            adjList.get(u).add(v);
            adjList.get(v).add(u);
        }
        int[] team = new int[n + 1];
        for (int i = 1; i < n + 1; ++i) {
            team[i] = -1;
        }
        Queue<Integer> bfsQueue = new LinkedList<>();
        boolean isBipartite = true;
        for (int pupil = 1; pupil <= n; ++pupil) {
            if (team[pupil] == -1) {
                team[pupil] = 0;
                bfsQueue.add(pupil);
                while (!bfsQueue.isEmpty()) {
                    int currentPupil = bfsQueue.poll();
                    for(int nextPupil : adjList.get(currentPupil)) {
                        if (team[nextPupil] == -1) {
                            team[nextPupil] = team[currentPupil] ^ 1;
                            bfsQueue.add(nextPupil);
                        } else if (team[currentPupil] == team[nextPupil]) {
                            isBipartite = false;
                            break;
                        }
                    }
                    if (!isBipartite) {
                        break;
                    }
                }
            }
            if (!isBipartite) {
                break;
            }
        }
        if (!isBipartite) {
            out.println("IMPOSSIBLE");
        } else {
            for (int i = 1; i <= n; ++i) {
                out.print(team[i] + 1);
                out.print(" ");
            }
        }
        out.flush();
    }

    // FastReader as a static inner class for single-file submission
    static class FastReader {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int head = 0;
        private int tail = 0;

        public FastReader() {
            in = System.in;
        }

        private int read() {
            if (head >= tail) {
                head = 0;
                try {
                    tail = in.read(buffer, 0, buffer.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (tail <= 0) return -1;
            }
            return buffer[head++];
        }

        String next() {
            int c = read();
            while (c <= 32) {
                if (c == -1) return null;
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.append((char) c);
                c = read();
            } while (c > 32);
            return res.toString();
        }

        int nextInt() {
            int c = read();
            while (c <= 32) {
                if (c == -1) return -1;
                c = read();
            }
            boolean negative = false;
            if (c == '-') {
                negative = true;
                c = read();
            }
            int res = 0;
            do {
                res = res * 10 + c - '0';
                c = read();
            } while (c > 32);
            return negative ? -res : res;
        }

        long nextLong() {
            int c = read();
            while (c <= 32) {
                if (c == -1) return -1;
                c = read();
            }
            boolean negative = false;
            if (c == '-') {
                negative = true;
                c = read();
            }
            long res = 0;
            do {
                res = res * 10 + c - '0';
                c = read();
            } while (c > 32);
            return negative ? -res : res;
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        float nextFloat() {
            return Float.parseFloat(next());
        }

        char nextChar() {
            int c = read();
            while (c <= 32) {
                if (c == -1) return '\0';
                c = read();
            }
            return (char) c;
        }

        String nextString() {
            return next();
        }

        byte nextByte() {
            return (byte) nextInt();
        }

        short nextShort() {
            return (short) nextInt();
        }

        boolean nextBoolean() {
            String s = next();
            return s != null && s.equalsIgnoreCase("true");
        }

        String nextLine() {
            int c = read();
            while (c == '\r' || c == '\n') {
                if (c == -1) return null;
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.append((char) c);
                c = read();
            } while (c != '\r' && c != '\n' && c != -1);
            return res.toString();
        }
    }

    static class FastWriter {
        private final OutputStream out;
        private final byte[] buffer = new byte[1 << 16];
        private int pos = 0;

        public FastWriter() {
            out = System.out;
        }

        public void print(int i) {
            if (i == 0) {
                write((byte) '0');
                return;
            }
            long temp = i;
            if (temp < 0) {
                write((byte) '-');
                temp = -temp;
            }
            int index = 0;
            byte[] chars = new byte[10];
            while (temp > 0) {
                chars[index++] = (byte) ((temp % 10) + '0');
                temp /= 10;
            }
            while (index > 0) {
                write(chars[--index]);
            }
        }

        public void println(int i) {
            print(i);
            write((byte) '\n');
        }

        public void print(long i) {
            if (i == 0) {
                write((byte) '0');
                return;
            }
            if (i < 0) {
                write((byte) '-');
                i = -i;
            }
            int index = 0;
            byte[] chars = new byte[21];
            while (i > 0) {
                chars[index++] = (byte) ((i % 10) + '0');
                i /= 10;
            }
            while (index > 0) {
                write(chars[--index]);
            }
        }

        public void println(long i) {
            print(i);
            write((byte) '\n');
        }

        public void print(String s) {
            if (s == null) {
                print("null");
                return;
            }
            for (int i = 0; i < s.length(); i++) {
                write((byte) s.charAt(i));
            }
        }

        public void println(String s) {
            print(s);
            write((byte) '\n');
        }

        public void print(char c) {
            write((byte) c);
        }

        public void println(char c) {
            print(c);
            write((byte) '\n');
        }

        public void print(double d) {
            print(String.valueOf(d));
        }

        public void println(double d) {
            print(d);
            write((byte) '\n');
        }

        public void print(float f) {
            print(String.valueOf(f));
        }

        public void println(float f) {
            print(f);
            write((byte) '\n');
        }

        public void print(boolean b) {
            print(b ? "true" : "false");
        }

        public void println(boolean b) {
            print(b);
            write((byte) '\n');
        }

        private void write(byte b) {
            if (pos == buffer.length) {
                flush();
            }
            buffer[pos++] = b;
        }

        public void flush() {
            try {
                out.write(buffer, 0, pos);
                pos = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}