package org.example.cses;

/**
 * Problem Name: Movie Festival
 * Link: https://cses.fi/problemset/task/1629
 *
 * Description:
 * In a movie festival n movies will be shown. You know the starting and ending time of each movie.
 * What is the maximum number of movies you can watch entirely?
 *
 * Input:
 * The first input line has an integer n: the number of movies.
 * After this, there are n lines that describe the movies. Each line has two integers a and b:
 * the starting and ending times of a movie.
 *
 * Output:
 * Print one integer: the maximum number of movies.
 *
 * Constraints:
 * 1 <= n <= 2 * 10^5
 * 1 <= a < b <= 10^9
 *
 * Example Input:
 * 3
 * 3 5
 * 4 9
 * 5 8
 *
 * Example Output:
 * 2
 */

import java.io.*;
import java.util.*;

public class MovieTicketsCses {
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

        int n = sc.nextInt();
        Interval[] intervals = new Interval[n];
        for (int i = 0; i < n; ++i) {
            intervals[i] = new Interval(sc.nextInt(), sc.nextInt());
        }
        Arrays.sort(intervals, Comparator.comparingInt(i -> i.end));
        // [1, 2], [1, 2], [2, 3], [2, 4], [3, 5], [1, 6]
        int prevEnd = intervals[0].end;
        int ans = 1, currentIdx = 1;
        List<Interval> chosenIntervals = new ArrayList<>();
        chosenIntervals.add(intervals[0]);
        while(currentIdx < n) {
            int currentStart = intervals[currentIdx].start;
            int currentEnd = intervals[currentIdx].end;
            if (currentStart >= prevEnd) {
                ans++;
                prevEnd = currentEnd;
            }
            currentIdx++;
        }
        out.println(ans);
        out.flush();
    }

    static class Interval {
        int start, end;
        int time;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
            this.time = end - start;
        }
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