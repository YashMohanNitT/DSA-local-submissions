package org.example.cses;

import java.io.*;
import java.util.HashMap;
import java.util.TreeSet;

public class SlidingWinMode {
    static class ModeEntry {
        final int value;
        final int frequency;

        ModeEntry(int value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }
    }

    public static void main(String[] args) {
        configureLocalFilesIfPresent();

        FastReader sc = new FastReader();
        FastWriter out = new FastWriter();

        int n = sc.nextInt(), k = sc.nextInt();
        if (n <= 0 || k <= 0 || k > n) {
            out.flush();
            return;
        }
        int[] window = new int[k];
        HashMap<Integer, Integer> freqMap = new HashMap<>();
        TreeSet<ModeEntry> modes = new TreeSet<>((first, second) -> {

            // 1. Compare frequencies (Highest frequency wins)
            if (first.frequency > second.frequency) {
                return -1; // Tells Java: 'first' is "smaller" (put it closer to the front)
            } else if (first.frequency < second.frequency) {
                return 1;  // Tells Java: 'first' is "larger" (push it toward the back)
            }

            // 2. Tie-Breaker: Frequencies are equal, compare values (Lowest value wins)
            else {
                if (first.value < second.value) {
                    return -1; // Tells Java: 'first' has a lower number (put it closer to the front)
                } else if (first.value > second.value) {
                    return 1;  // Tells Java: 'first' has a higher number (push it toward the back)
                } else {
                    return 0;  // Absolute identical duplicate (same frequency AND same value)
                }
            }
        });

        for (int i = 0; i < n; ++i) {
            int value = sc.nextInt();

            if (i >= k) {
                int leavingValue = window[(i - k) % k];
                int count = freqMap.get(leavingValue);
                modes.remove(new ModeEntry(leavingValue, count));

                if (count == 1) {
                    freqMap.remove(leavingValue);
                } else {
                    freqMap.put(leavingValue, count - 1);
                    modes.add(new ModeEntry(leavingValue, count - 1));
                }
            }

            window[i % k] = value;
            int count = freqMap.getOrDefault(value, 0);
            if (count > 0) {
                modes.remove(new ModeEntry(value, count));
            }
            freqMap.put(value, count + 1);
            modes.add(new ModeEntry(value, count + 1));

            if (i >= k - 1) {
                out.print(modes.first().value);
                out.print(' ');
            }
        }
        out.flush();
    }

    private static void configureLocalFilesIfPresent() {
        File inputFile = new File("input.txt");
        if (!inputFile.isFile()) {
            return;
        }

        try {
            System.setIn(new FileInputStream(inputFile));
            System.setOut(new PrintStream(new FileOutputStream("output.txt")));
        } catch (IOException ignored) {
            // Keep standard input/output when local file redirection is unavailable.
        }
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
                    throw new RuntimeException(e);
                }
                if (tail <= 0) return -1;
            }
            return buffer[head++];
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
    }

    static class FastWriter {
        private final OutputStream out;
        private final byte[] buffer = new byte[1 << 16];
        private int pos = 0;

        public FastWriter() {
            this.out = System.out;
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

        public void print(long i) {
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
            byte[] chars = new byte[30];
            while (temp > 0) {
                chars[index++] = (byte) ((temp % 10) + '0');
                temp /= 10;
            }
            while (index > 0) {
                write(chars[--index]);
            }
        }

        public void print(char c) {
            write((byte) c);
        }

        private void write(byte b) {
            if (pos == buffer.length) flush();
            buffer[pos++] = b;
        }

        public void flush() {
            try {
                out.write(buffer, 0, pos);
                pos = 0;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}