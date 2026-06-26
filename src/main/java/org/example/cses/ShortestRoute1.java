package org.example.cses;

import java.io.*;
import java.util.*;

public class ShortestRoute1 {
    public static void main(String[] args) {
        InputStream inputStream;
        OutputStream outputStream;

        // Local setup
        try {
            if (new File("input.txt").exists()) {
                inputStream = new FileInputStream("input.txt");
                outputStream = new FileOutputStream("output.txt");
            } else {
                inputStream = System.in;
                outputStream = System.out;
            }
        } catch (Exception e) {
            inputStream = System.in;
            outputStream = System.out;
        }

        FastScanner sc = new FastScanner(inputStream);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));

        int n = sc.nextInt();
        int m = sc.nextInt();

        // Optimization: Array of ArrayLists instead of List<ArrayList>
        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] adjList = new ArrayList[n + 1];
        for (int i = 1; i <= n; ++i) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; ++i) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            adjList[u].add(new Edge(v, w));
        }

        PriorityQueue<QueueNode> pq = new PriorityQueue<>();
        long[] distance = new long[n + 1];
        Arrays.fill(distance, Long.MAX_VALUE);

        distance[1] = 0;
        pq.add(new QueueNode(1, 0));

        while (!pq.isEmpty()) {
            QueueNode currNode = pq.poll();
            int u = currNode.vertex;
            long d = currNode.currentDistance;

            // Important: skip outdated paths
            if (d > distance[u]) {
                continue;
            }

            for (Edge edge : adjList[u]) {
                int v = edge.to;
                int weight = edge.weight;

                if (distance[u] + weight < distance[v]) {
                    distance[v] = distance[u] + weight;
                    pq.add(new QueueNode(v, distance[v]));
                }
            }
        }

        for (int node = 1; node <= n; ++node) {
            out.print(distance[node] + " ");
        }
        out.flush();
    }

    static final class QueueNode implements Comparable<QueueNode> {
        final int vertex;
        final long currentDistance;

        public QueueNode(int vertex, long currentDistance) {
            this.vertex = vertex;
            this.currentDistance = currentDistance;
        }

        @Override
        public int compareTo(QueueNode other) {
            return Long.compare(this.currentDistance, other.currentDistance);
        }
    }

    static class Edge {
        int to, weight;
        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    // Optimization: Byte-level fast I/O
    static class FastScanner {
        private final InputStream is;
        private final byte[] buffer = new byte[1024 * 16]; // 16KB buffer
        private int head, tail;

        public FastScanner(InputStream is) {
            this.is = is;
        }

        private int read() {
            if (head >= tail) {
                head = 0;
                try {
                    tail = is.read(buffer, 0, buffer.length);
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
            int res = 0;
            while (c > 32) {
                if (c < '0' || c > '9') throw new InputMismatchException();
                res = res * 10 + c - '0';
                c = read();
            }
            return res;
        }
    }
}
