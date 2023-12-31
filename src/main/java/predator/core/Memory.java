package predator.core;

import com.sun.jna.Pointer;
import predator.math.FloatVector2D;
import predator.math.FloatVector3D;
import predator.structs.GlowMode;

import javax.swing.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

@SuppressWarnings("unused")
public class Memory {

    public static int pid;

    public static void FindPID() {
        try {
            String targetProcessId = ShellExecutor.executeCommand("pidof -s r5apex.exe");
            pid = Integer.parseInt(targetProcessId.replace("\n", ""));
            System.out.println("Game found! PID: " + targetProcessId);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "GAME NOT FOUND!");
            System.exit(-1);
        }
    }

    private static byte[] readMemory(int pid, long address, int bytesToRead) {
        if (pid == 0) throw new RuntimeException("Invalid pid");
        try (RandomAccessFile memFile = new RandomAccessFile("/proc/" + pid + "/mem", "r")) {
            memFile.seek(address);
            byte[] buffer = new byte[bytesToRead];
            memFile.readFully(buffer);
            return buffer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeMemory(int pid, long address, byte[] data) {
        if (pid == 0) throw new RuntimeException("Invalid pid");
        try (RandomAccessFile memFile = new RandomAccessFile("/proc/" + pid + "/mem", "rw")) {
            memFile.seek(address);
            memFile.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Pointer resolvePointer(Pointer pointer) {
        byte[] memoryData = Memory.readMemory(pid, Pointer.nativeValue(pointer), 8);
        if (memoryData.length == 0) throw new RuntimeException("Pointer resolution failed. Empty memoryData");
        try (com.sun.jna.Memory memory = new com.sun.jna.Memory(memoryData.length)) {
            memory.write(0, memoryData, 0, memoryData.length);
            return memory.getPointer(0);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static GlowMode readGlowMode(Pointer pointer) {
        byte[] memoryData = Memory.readMemory(pid, Pointer.nativeValue(pointer), Byte.BYTES * 3);
        if (memoryData.length == 0) throw new RuntimeException(" Empty memoryData");
        return new GlowMode(memoryData[2], memoryData[1], memoryData[0]);
    }

    public static void writeGlowMode(Pointer pointer, GlowMode glowMode) {
        byte[] memoryData = new byte[]{glowMode.bodyStyle, glowMode.borderStyle, glowMode.borderWidth};
        writeMemory(pid, Pointer.nativeValue(pointer), memoryData);
    }

    public static void writeGlowModeVisible(Pointer pointer, GlowMode glowMode) {
        byte[] memoryData = new byte[]{124, 6, 127};
        writeMemory(pid, Pointer.nativeValue(pointer), memoryData);
    }

    public static void writeGlowModeInvis(Pointer pointer, GlowMode glowMode) {
        byte[] memoryData = new byte[]{118, 0, 0};
        writeMemory(pid, Pointer.nativeValue(pointer), memoryData);
    }

    public static Short readShort(Pointer pointer) {
        byte[] memoryData = Memory.readMemory(pid, Pointer.nativeValue(pointer), Short.BYTES);
        if (memoryData.length == 0) throw new RuntimeException(" Empty memoryData");
        return ByteBuffer.wrap(memoryData).getShort();
    }

    public static void writeShort(Pointer pointer, Short num) {
        if (num == null) throw new RuntimeException("Invalid num");
        byte[] memoryData = shortToBytes(num);
        writeMemory(pid, Pointer.nativeValue(pointer), memoryData);
    }

    public static Float readFloat(Pointer pointer) {
        byte[] memoryData = Memory.readMemory(pid, Pointer.nativeValue(pointer), Float.BYTES);
        if (memoryData.length == 0) throw new RuntimeException(" Empty memoryData");
        return ByteBuffer.wrap(memoryData).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    public static void writeFloat(Pointer pointer, Float num) {
        byte[] slice1 = floatToBytes(num);
        byte[] memoryData = new byte[]{slice1[3], slice1[2], slice1[1], slice1[0]};
        writeMemory(pid, Pointer.nativeValue(pointer), memoryData);
    }

    public static FloatVector3D readFloatVector3D(Pointer pointer) {
        byte[] memoryData = Memory.readMemory(pid, Pointer.nativeValue(pointer), Float.BYTES * 3);
        if (memoryData.length == 0) throw new RuntimeException(" Empty memoryData");
        byte[] sliceX = Arrays.copyOfRange(memoryData, 0, Float.BYTES);
        byte[] sliceY = Arrays.copyOfRange(memoryData, Float.BYTES, Float.BYTES * 2);
        byte[] sliceZ = Arrays.copyOfRange(memoryData, Float.BYTES * 2, Float.BYTES * 3);
        FloatVector3D fv = new FloatVector3D();
        fv.x = ByteBuffer.wrap(sliceX).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        fv.y = ByteBuffer.wrap(sliceY).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        fv.z = ByteBuffer.wrap(sliceZ).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        return fv;
    }

    public static void writeFloatVector3D(Pointer pointer, FloatVector3D vector) {
        byte[] slice1 = floatToBytes(vector.x);
        byte[] slice2 = floatToBytes(vector.y);
        byte[] slice3 = floatToBytes(vector.z);
        byte[] memoryData = new byte[]{
                slice1[3], slice1[2], slice1[1], slice1[0],
                slice2[3], slice2[2], slice2[1], slice2[0],
                slice3[3], slice3[2], slice3[1], slice3[0]};
        writeMemory(pid, Pointer.nativeValue(pointer), memoryData);
    }

    public static FloatVector2D readFloatVector2D(Pointer pointer) {
        byte[] memoryData = Memory.readMemory(pid, Pointer.nativeValue(pointer), Float.BYTES * 2);
        if (memoryData.length == 0) throw new RuntimeException(" Empty memoryData");
        byte[] sliceX = Arrays.copyOfRange(memoryData, 0, Float.BYTES);
        byte[] sliceY = Arrays.copyOfRange(memoryData, Float.BYTES, Float.BYTES * 2);
        FloatVector2D fv = new FloatVector2D();
        fv.x = ByteBuffer.wrap(sliceX).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        fv.y = ByteBuffer.wrap(sliceY).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        return fv;
    }

    public static void writeFloatVector2D(Pointer pointer, FloatVector2D vector) {
        byte[] slice1 = floatToBytes(vector.x);
        byte[] slice2 = floatToBytes(vector.y);
        byte[] memoryData = new byte[]{
                slice1[3], slice1[2], slice1[1], slice1[0],
                slice2[3], slice2[2], slice2[1], slice2[0]};
        writeMemory(pid, Pointer.nativeValue(pointer), memoryData);
    }

    public static Double readDouble(Pointer pointer) {
        byte[] memoryData = Memory.readMemory(pid, Pointer.nativeValue(pointer), Double.BYTES);
        if (memoryData.length == 0) throw new RuntimeException(" Empty memoryData");
        return ByteBuffer.wrap(memoryData).order(ByteOrder.LITTLE_ENDIAN).getDouble();
    }

    public static Integer readInteger(Pointer pointer) {
        byte[] memoryData = Memory.readMemory(pid, Pointer.nativeValue(pointer), Integer.BYTES);
        if (memoryData.length == 0) throw new RuntimeException(" Empty memoryData");
        return ByteBuffer.wrap(memoryData).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public static void writeInteger(Pointer pointer, Integer num) {
        if (num == null) throw new RuntimeException("Invalid num");
        byte[] memoryData = intToBytes(num);
        writeMemory(pid, Pointer.nativeValue(pointer), memoryData);
    }

    public static Long readLong(Pointer pointer) {
        byte[] memoryData = Memory.readMemory(pid, Pointer.nativeValue(pointer), Long.BYTES);
        if (memoryData.length == 0) throw new RuntimeException(" Empty memoryData");
        return ByteBuffer.wrap(memoryData).order(ByteOrder.LITTLE_ENDIAN).getLong();
    }

    public static String readString(Pointer pointer, int bytesToRead) {
        byte[] memoryData = Memory.readMemory(pid, Pointer.nativeValue(pointer), bytesToRead);
        if (memoryData.length == 0) throw new RuntimeException("String read failed. Empty memoryData");
        StringBuilder sb = new StringBuilder();
        for (byte b : memoryData) {
            if (b == 0) break;
            sb.append((char) b);
        }
        return sb.toString();
    }

    private static byte[] floatToBytes(float value) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putFloat(value);
        return buffer.order(java.nio.ByteOrder.LITTLE_ENDIAN).array();
    }

    private static byte[] intToBytes(int value) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(value);
        byte[] buff = buffer.order(ByteOrder.LITTLE_ENDIAN).array();
        reverseByteArray(buff);
        return buff;
    }

    private static byte[] shortToBytes(short value) {
        ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
        buffer.putShort(value);
        return buffer.order(ByteOrder.LITTLE_ENDIAN).array();
    }

    private static void reverseByteArray(byte[] arr) {
        int start = 0;
        int end = arr.length - 1;
        while (start < end) {
            byte temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
}
