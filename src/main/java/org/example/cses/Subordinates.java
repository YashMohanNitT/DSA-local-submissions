
import java.io.*;

public class Subordinates {
    public static void main(String[] args) {
        try {
            System.setIn(new FileInputStream("input.txt"));
            System.setOut(new PrintStream(new FileOutputStream("output.txt")));
        } catch (Exception e) {
            // Ignored for online judges
        }

        FastReader sc = new FastReader();
        FastWriter out = new FastWriter();

        int n = sc.nextInt();
        
        int[] bossOf = new int[n + 1];
        int[] degree = new int[n + 1]; // Tracks the number of direct subordinates (children)
        
        // 1. Read input and count the number of children for each node
        for (int i = 2; i <= n; ++i) {
            bossOf[i] = sc.nextInt();
            degree[bossOf[i]]++;
        }

        // 2. Initialize subSize. Every employee counts themselves as size 1 initially.
        int[] subSize = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            subSize[i] = 1;
        }

        // 3. Queue for Topological Sort (Kahn's Algorithm) starting from the leaves
        int[] queue = new int[n + 1];
        int head = 0;
        int tail = 0;

        // Add all leaf nodes (employees with 0 subordinates) to the queue
        for (int i = 1; i <= n; i++) {
            if (degree[i] == 0) {
                queue[tail++] = i;
            }
        }

        // 4. Process the tree bottom-up (Iterative approach avoids StackOverflowError)
        while (head < tail) {
            int u = queue[head++];
            
            // The general director (node 1) has no boss, so we can skip them
            if (u == 1) continue; 
            
            int boss = bossOf[u];
            
            // Add the subordinate's total subtree size to their boss's subtree size
            subSize[boss] += subSize[u];
            
            // Decrement the boss's pending children count
            degree[boss]--;
            
            // If all of the boss's children have been processed, the boss is ready to be processed
            if (degree[boss] == 0) {
                queue[tail++] = boss;
            }
        }

        // 5. Output results (subtract 1 because the problem asks for the number of subordinates, not subtree size)
        for (int i = 1; i <= n; ++i) {
            out.print(subSize[i] - 1);
            out.print(' ');
        }
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