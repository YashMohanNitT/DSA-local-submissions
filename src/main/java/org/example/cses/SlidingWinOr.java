import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class SlidingWinOr {
    public static void main(String[] args) {
        configureLocalFilesIfPresent();

        FastReader sc = new FastReader();
        FastWriter out = new FastWriter();

        int n = sc.nextInt(), k = sc.nextInt();
        if (n <= 0 || k <= 0 || k > n) {
            out.flush();
            return;
        }

        long x = sc.nextInt();
        long a = sc.nextInt();
        long b = sc.nextInt();
        long c = sc.nextInt();

        long[] values = new long[n];
        long previousValue = x;
        for (int i = 0; i < n; ++i) {
            long value = i == 0 ? x : (a * previousValue + b) % c;
            values[i] = value;
            previousValue = value;
        }

        long ans = 0;
        long[] prefixOr = new long[n];
        long[] suffixOr = new long[n];

        // Loop 1: Calculate prefixOr
        for (int i = 0; i < n; ++i) {
            if (i % k == 0) {
                prefixOr[i] = values[i];
            } else {
                prefixOr[i] = prefixOr[i - 1] | values[i];
            }
        }

        // Loop 2: Calculate suffixOr iterating backwards
        for (int i = n - 1; i >= 0; --i) {
            if (i == n - 1 || (i + 1) % k == 0) {
                suffixOr[i] = values[i];
            } else {
                suffixOr[i] = suffixOr[i + 1] | values[i];
            }
        }

        for (int start = 0; start + k <= n; ++start) {
            long windowOr = suffixOr[start] | prefixOr[start + k - 1];
            ans ^= windowOr;
        }

        out.print(ans);
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