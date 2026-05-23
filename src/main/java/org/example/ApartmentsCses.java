package org.example; //comment this out when submitting on sites

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ApartmentsCses {
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

        int n = sc.nextInt(), m = sc.nextInt(), k = sc.nextInt();
        int[] apartmentSize = new int[n];
        for (int i = 0; i < n; i++) {
            apartmentSize[i] = sc.nextInt();
        }
        int[] desiredApartmentSize = new int[m];
        for (int i = 0; i < m; i++) {
            desiredApartmentSize[i] = sc.nextInt();
        }
        Arrays.sort(apartmentSize);
        Arrays.sort(desiredApartmentSize);
        int i = 0, j = 0;
        int ans = 0;
        while (i < n && j < m) {
            if (Math.abs(apartmentSize[i] - desiredApartmentSize[j]) <= k) {
                ans++;
                i++;
                j++;
            } else if (apartmentSize[i] < desiredApartmentSize[j]) {
                i++;
            } else {
                j++;
            }
        }
        out.println(ans);
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