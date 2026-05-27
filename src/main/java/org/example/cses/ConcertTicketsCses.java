package org.example.cses;

/**
 * Problem Name: Concert Tickets
 * Link: https://cses.fi/problemset/task/1091
 *
 * Description:
 * There are n concert tickets available, each with a certain price. Then, m customers arrive, one after another.
 * Each customer announces the maximum price they are willing to pay for a ticket, and after this,
 * they will get a ticket with the nearest possible price such that it does not exceed the maximum price.
 *
 * Input:
 * The first input line contains integers n and m: the number of tickets and the number of customers.
 * The next line contains n integers h_1, h_2, ..., h_n: the price of each ticket.
 * The last line contains m integers t_1, t_2, ..., t_m: the maximum price for each customer in the order they arrive.
 *
 * Output:
 * Print, for each customer, the price that they will pay for their ticket. After this, the ticket cannot be purchased again.
 * If a customer cannot get any ticket, print -1.
 *
 * Constraints:
 * 1 <= n, m <= 2 * 10^5
 * 1 <= h_i, t_i <= 10^9
 *
 * Example Input:
 * 5 3
 * 5 3 7 8 5
 * 4 8 3
 *
 * Example Output:
 * 3
 * 8
 * -1
 */

import java.io.*;
import java.util.TreeMap;

public class ConcertTicketsCses {
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
        TreeMap<Integer, Integer> tickets = new TreeMap<>();
        for (int i = 0; i < n; ++i) {
            int price = sc.nextInt();
            tickets.put(price, tickets.getOrDefault(price, 0) + 1);
        }
        for (int i = 0; i < m; ++i) {
            int customerBudget = sc.nextInt();
            Integer match = tickets.floorKey(customerBudget);
            if (match == null) {
                out.println(-1);
            } else {
                out.println(match);
                int count = tickets.get(match);
                if (count == 1) {
                    tickets.remove(match);
                } else {
                    tickets.put(match, count - 1);
                }
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