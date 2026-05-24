//package org.example;//package org.example; //comment this out when submitting on sites

import java.io.*;
import java.util.*;

public class RestaurantCustomersCses {
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
        Set<Integer> uniqueTimeStamps = new TreeSet<>();
        for (int i = 0; i < n; ++i) {
            intervals[i] = new Interval();
            intervals[i].start = sc.nextInt();
            intervals[i].end = sc.nextInt();
            uniqueTimeStamps.add(intervals[i].start);
            uniqueTimeStamps.add(intervals[i].end);
        }
        int idx = 0;
        Map<Integer, Integer> num2Idx = new HashMap<>();
        for (Integer timeStamp : uniqueTimeStamps) {
            num2Idx.put(timeStamp, idx);
            idx++;
        }
        int[] prefixSum = new int[num2Idx.size() + 1];
        Arrays.fill(prefixSum, 0);
        for (int i = 0; i < n; ++i) {
            prefixSum[num2Idx.get(intervals[i].start)]++;
            prefixSum[num2Idx.get(intervals[i].end) + 1]--;
        }
        int ans = prefixSum[0];
        for (int i = 1; i < prefixSum.length; ++i) {
            prefixSum[i] += prefixSum[i - 1];
            ans = Math.max(ans, prefixSum[i]);
        }
        out.println(ans);
        out.flush();
    }

    static class Interval {
        int start, end;
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