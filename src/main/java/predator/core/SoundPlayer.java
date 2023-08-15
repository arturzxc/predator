package predator.core;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@SuppressWarnings("unused")
public class SoundPlayer {

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
        ClassLoader classLoader = ShellExecutor.class.getClassLoader();
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
