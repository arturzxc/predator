package predator.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

//    public static List<Player> getEnemyPlayers(LocalPlayer localPlayer, List<Player> entities) {
//        List<Player> myEnemyEntities = new ArrayList<>();
//        for (Player player : entities) {
//            if (player.baseEntity.base == null) continue;
//            if (player.dead == null || player.dead) continue;
//            if (Objects.equals(localPlayer.base, player.baseEntity.base)) continue;
//            if (!Objects.equals(localPlayer.teamNumber, player.baseEntity.teamNumber)) myEnemyEntities.add(player);
//        }
//        return myEnemyEntities;
//    }

}
