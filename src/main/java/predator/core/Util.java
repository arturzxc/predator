package predator.core;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Util {

    public static String executeCommand(String command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        StringBuilder output = new StringBuilder();
        try (InputStream inputStream = process.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        int exitCode = process.waitFor();
        if (exitCode == 0)
            return output.toString();
        else
            throw new RuntimeException("Command execution failed with exit code: " + exitCode);
    }

    public static byte[] floatToLittleEndianBytes(float value) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putFloat(value);
        return buffer.order(java.nio.ByteOrder.LITTLE_ENDIAN).array();
    }

    public static byte[] intToBytes(int value) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(value);
        byte[] buff = buffer.order(ByteOrder.LITTLE_ENDIAN).array();
        reverseByteArray(buff);
        return buff;
    }

    public static void reverseByteArray(byte[] arr) {
        int start = 0;
        int end = arr.length - 1;

        while (start < end) {
            // Swap elements at start and end indices
            byte temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;

            // Move the indices towards the center
            start++;
            end--;
        }
    }

    public static void playSound(String fileName) {
        try {
            File f = loadResourceAsFile(fileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public static File loadResourceAsFile(String fileName) {
        ClassLoader classLoader = Util.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            if (inputStream == null)
                throw new IllegalArgumentException("Resource not found: " + fileName);
            File tempFile = File.createTempFile("resource", fileName);
            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
            }
            return tempFile;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
