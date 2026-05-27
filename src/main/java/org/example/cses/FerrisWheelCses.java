package org.example.cses;

/**
 * Problem Name: Ferris Wheel
 * Link: https://cses.fi/problemset/task/1090
 *
 * Description:
 * There are n children who want to go to a Ferris wheel, and your task is to find a gondola for each child.
 * Each gondola may have one or two children in it, and in addition, the total weight in a gondola may not exceed x.
 * You know the weight of every child.
 * What is the minimum number of gondolas needed for the children?
 *
 * Input:
 * The first input line contains two integers n and x: the number of children and the maximum allowed weight.
 * The next line contains n integers p_1, p_2, ..., p_n: the weight of each child.
 *
 * Output:
 * Print one integer: the minimum number of gondolas.
 *
 * Constraints:
 * 1 <= n <= 2 * 10^5
 * 1 <= x <= 10^9
 * 1 <= p_i <= x
 *
 * Example Input:
 * 4 10
 * 7 2 3 9
 *
 * Example Output:
 * 3
 */

import java.io.*;
import java.util.Arrays;

public class FerrisWheelCses {
    public static void main(String[] args) {
        try {
            System.setIn(new FileInputStream("input.txt"));
            System.setOut(new PrintStream(new FileOutputStream("output.txt")));
        } catch (Exception e) {
            System.err.println("Error setting up local I/O: " + e.getMessage());
        }

        FastReader fr = new FastReader();
        FastWriter out = new FastWriter();
        int n = fr.nextInt(), x = fr.nextInt();
        int[] childrenWeights = new int[n];
        for (int i = 0; i < n; i++) {
            childrenWeights[i] = fr.nextInt();
        }
        Arrays.sort(childrenWeights);
        int i = 0, j = n - 1;
        int ans = 0;
        while (i <= j) {
            if (childrenWeights[i] + childrenWeights[j] <= x) {
                ans++;
                i++;
                j--;
            } else {
                ans++;
                j--;
            }
        }
        out.println(ans);
        out.flush();
    }
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
