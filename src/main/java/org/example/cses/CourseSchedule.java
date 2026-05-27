import java.io.*;
import java.util.*;

public class CourseSchedule {
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
        PrintWriter out = new PrintWriter(System.out);

        int n = sc.nextInt(), m = sc.nextInt();
        List<ArrayList<Integer>> adjList = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; ++i) {
            adjList.add(new ArrayList<>());
        }
        int[] inDegree = new int[n + 1];
        for (int i = 0; i < m; ++i) {
            int u = sc.nextInt(), v = sc.nextInt();
            adjList.get(u).add(v);
            inDegree[v]++;
        }
        Queue<Integer> bfsQueue = new LinkedList<>();
        for (int course = 1; course <= n; ++ course) {
            if (inDegree[course] == 0) {
                bfsQueue.add(course);
            }
        }
        ArrayList<Integer> courseSchedule = new ArrayList<>();
        while (!bfsQueue.isEmpty()) {
            Integer currentCourse = bfsQueue.poll();
            courseSchedule.add(currentCourse);
            for (Integer nextCourse : adjList.get(currentCourse)) {
                inDegree[nextCourse]--;
                if (inDegree[nextCourse] == 0) {
                    bfsQueue.add(nextCourse);
                }
            }
        }
        if (courseSchedule.size() != n) {
            out.println("IMPOSSIBLE");
        } else {
            for (Integer course : courseSchedule) {
                out.print(course);
                out.print(" ");
            }
        }
        out.flush();
    }

    // FastReader as a static inner class for single-file submission
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    String line = br.readLine();
                    if (line == null) return null; // End of File
                    st = new StringTokenizer(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                if (st != null && st.hasMoreTokens()) {
                    str = st.nextToken("\n");
                } else {
                    str = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}