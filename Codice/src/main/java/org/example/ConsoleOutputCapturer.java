package org.example;
import java.io.OutputStream;
import java.io.PrintStream;

public class ConsoleOutputCapturer {
    private static StringBuilder capturedOutput = new StringBuilder();
    private static PrintStream originalOut = System.out;

    public static void startCapture() {
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                capturedOutput.append((char) b);
            }
        }));
    }

    public static String stopCapture() {
        System.setOut(originalOut);
        String output = capturedOutput.toString();
        capturedOutput.setLength(0);
        return output;
    }
}
