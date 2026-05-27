package org.example.cses;

/**
 * Problem Name: Labyrinth
 * Link: https://cses.fi/problemset/task/1193
 *
 * Description:
 * You are given a map of a labyrinth, and your task is to find a path from start to end.
 * You can walk left, right, up and down.
 *
 * Input:
 * The first input line has two integers n and m: the height and width of the map.
 * Then there are n lines of m characters describing the labyrinth.
 * Each character is . (floor), # (wall), A (start), or B (end). There is exactly one A and one B in the input.
 *
 * Output:
 * First print "YES", if there is a path, and "NO" otherwise.
 * If there is a path, print the length of the shortest such path and its description as a string
 * consisting of characters L (left), R (right), U (up), and D (down). You can print any valid solution.
 *
 * Constraints:
 * 1 <= n, m <= 1000
 *
 * Example Input:
 * 5 8
 * ########
 * #.A#...#
 * #.####B#
 * #.....##
 * ########
 *
 * Example Output:
 * YES
 * 9
 * LDDRRRRRU
 */

import java.io.*;
import java.util.*;

public class LabyrinthCses {
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
        char[][] labyrinth = new char[n][m];
        char[][] directions = new char[n][m];
        int startRow = -1, startCol = -1, endRow = -1, endCol = -1;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                labyrinth[i][j] = sc.nextChar();
                if (labyrinth[i][j] == 'A') {
                    startRow = i;
                    startCol = j;
                }
                if (labyrinth[i][j] == 'B') {
                    endRow = i;
                    endCol = j;
                }
            }
        }
        
        char[] dirChars = {'L', 'R', 'U', 'D'};
        int[] dr = {0, 0, -1, 1};
        int[] dc = {-1, 1, 0, 0};
        directions[startRow][startCol] = 'S';

        Queue<Direction> bfsQueue = new LinkedList<>();
        bfsQueue.add(new Direction(startRow, startCol));
        
        while (!bfsQueue.isEmpty()) {
            Direction currentHead = bfsQueue.poll();
            
            if (currentHead.r == endRow && currentHead.c == endCol) {
                break;
            }
            
            for (int i = 0; i < 4; i++) {
                int nextRow = currentHead.r + dr[i];
                int nextCol = currentHead.c + dc[i];
                if (nextRow >= 0 && nextRow < n && nextCol >= 0 && nextCol < m 
                        && labyrinth[nextRow][nextCol] != '#' && directions[nextRow][nextCol] == 0) {
                    directions[nextRow][nextCol] = dirChars[i];
                    bfsQueue.add(new Direction(nextRow, nextCol));
                }
            }
        }

        if (directions[endRow][endCol] == 0) {
            out.println("NO");
        } else {
            out.println("YES");
            StringBuilder sb = new StringBuilder();
            int currRow = endRow;
            int currCol = endCol;
            
            // Backtrack until we hit our starting coordinates
            while (currRow != startRow || currCol != startCol) {
                char d = directions[currRow][currCol];
                sb.append(d);
                if (d == 'L') currCol++;
                else if (d == 'R') currCol--;
                else if (d == 'U') currRow++;
                else if (d == 'D') currRow--;
            }
            out.println(sb.length());
            out.println(sb.reverse().toString());
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

    private static class Direction {
        int r, c;

        public Direction(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}