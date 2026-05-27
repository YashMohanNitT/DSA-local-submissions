package org.example.fastio;

import java.io.IOException;
import java.io.OutputStream;

public class FastWriter {
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

    public void println(int i) {
        print(i);
        write((byte) '\n');
    }

    public void print(long i) {
        if (i == 0) {
            write((byte) '0');
            return;
        }
        if (i < 0) {
            write((byte) '-');
            i = -i;
        }
        int index = 0;
        byte[] chars = new byte[21];
        while (i > 0) {
            chars[index++] = (byte) ((i % 10) + '0');
            i /= 10;
        }
        while (index > 0) {
            write(chars[--index]);
        }
    }

    public void println(long i) {
        print(i);
        write((byte) '\n');
    }

    public void print(String s) {
        if (s == null) {
            print("null");
            return;
        }
        for (int i = 0; i < s.length(); i++) {
            write((byte) s.charAt(i));
        }
    }

    public void println(String s) {
        print(s);
        write((byte) '\n');
    }

    public void print(char c) {
        write((byte) c);
    }

    public void println(char c) {
        print(c);
        write((byte) '\n');
    }

    public void print(double d) {
        print(String.valueOf(d));
    }

    public void println(double d) {
        print(d);
        write((byte) '\n');
    }

    public void print(float f) {
        print(String.valueOf(f));
    }

    public void println(float f) {
        print(f);
        write((byte) '\n');
    }

    public void print(boolean b) {
        print(b ? "true" : "false");
    }

    public void println(boolean b) {
        print(b);
        write((byte) '\n');
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
            e.printStackTrace();
        }
    }
}