package org.example.fastio;

import java.io.InputStream;
import java.io.IOException;

public class FastReader {
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

    public String next() {
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

    public int nextInt() {
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

    public long nextLong() {
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

    public double nextDouble() {
        return Double.parseDouble(next());
    }

    public String nextLine() {
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
