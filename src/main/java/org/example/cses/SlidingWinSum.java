import java.io.*;

public class SlidingWinSum {
    public static void main(String[] args) {
        try {
            System.setIn(new FileInputStream("input.txt"));
            System.setOut(new PrintStream(new FileOutputStream("output.txt")));
        } catch (Exception e) {
            // Ignored for online judges
        }

        FastReader sc = new FastReader();
        FastWriter out = new FastWriter();

        int n = sc.nextInt(), k = sc.nextInt();
        int x = sc.nextInt(), a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
        long[] arr = new long[n];
        arr[0] = x;
        for (int i = 1; i < n; ++i) {
            arr[i] = (a*arr[i-1] + b)%c;
        }
        int l = 0;
        long currWinSum = 0, ans = -1;
        for (int r = 0; r < n; ++r) {
            currWinSum += arr[r];
            while (r - l + 1 > k) {
                currWinSum -= arr[l];
                l++;
            }
            if (r - l + 1 == k) {
                if (ans == -1) {
                    ans = currWinSum;
                } else {
                    ans = ans ^ currWinSum;
                }
            }
        }
        out.print(ans);

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