package predator.core;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

@SuppressWarnings("unused")
public class Mem {

    public static int pid;

    public static void AwaitPID() {
        try {
            String targetProcessId = Util.executeCommand("pidof -s R5Apex.exe");
            System.out.println("Game found! PID: " + targetProcessId);
            pid = Integer.parseInt(targetProcessId.replace("\n", ""));
        } catch (Exception ex) {
            System.out.println("Waiting for you to open the game");
            try {
                Thread.sleep(1000 * 10);
                AwaitPID();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
        byte[] memoryData = Mem.readMemory(pid, Pointer.nativeValue(pointer), 8);
        if (memoryData.length == 0) throw new RuntimeException("Pointer resolution failed. Empty memoryData");
        try (Memory memory = new Memory(memoryData.length)) {
            memory.write(0, memoryData, 0, memoryData.length);
            return memory.getPointer(0);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String readString(Pointer pointer, int bytesToRead) {
        byte[] memoryData = Mem.readMemory(pid, Pointer.nativeValue(pointer), bytesToRead);
        if (memoryData.length == 0) throw new RuntimeException("String read failed. Empty memoryData");
        StringBuilder sb = new StringBuilder();
        for (byte b : memoryData) {
            if (b == 0) break;
            sb.append((char) b);
        }
        return sb.toString();
    }

    public static Short readShort(Pointer pointer) {
        byte[] memoryData = Mem.readMemory(pid, Pointer.nativeValue(pointer), Short.BYTES);
        if (memoryData.length == 0) throw new RuntimeException(" Empty memoryData");
        return ByteBuffer.wrap(memoryData).getShort();
    }

    public static Short readShortTest(Pointer pointer) {
        byte[] memoryData = Mem.readMemory(pid, Pointer.nativeValue(pointer), Short.BYTES);
        if (memoryData.length == 0) throw new RuntimeException(" Empty memoryData");
        return ByteBuffer.wrap(memoryData).getShort();
    }

    public static void writeShort(Pointer pointer, Short num) {
        if (num == null) throw new RuntimeException("Invalid num");
        byte[] memoryData = Util.shortToBytes(num);
        writeMemory(pid, Pointer.nativeValue(pointer), memoryData);
    }

    public static Float readFloat(Pointer pointer) {
        byte[] memoryData = Mem.readMemory(pid, Pointer.nativeValue(pointer), Float.BYTES);
        if (memoryData.length == 0) throw new RuntimeException(" Empty memoryData");
        return ByteBuffer.wrap(memoryData).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    public static FloatVector3D readFloatVector3D(Pointer pointer) {
        byte[] memoryData = Mem.readMemory(pid, Pointer.nativeValue(pointer), Float.BYTES * 3);
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

    public static void writeFloatVector2D(Pointer pointer, FloatVector2D vector) {
        byte[] slice1 = Util.floatToLittleEndianBytes(vector.x);
        byte[] slice2 = Util.floatToLittleEndianBytes(vector.y);
        byte[] memoryData = new byte[]{
                slice1[3], slice1[2], slice1[1], slice1[0],
                slice2[3], slice2[2], slice2[1], slice2[0]};
        writeMemory(pid, Pointer.nativeValue(pointer), memoryData);
    }

    public static void writeFloatVector3D(Pointer pointer, FloatVector3D vector) {
        byte[] slice1 = Util.floatToLittleEndianBytes(vector.x);
        byte[] slice2 = Util.floatToLittleEndianBytes(vector.y);
        byte[] slice3 = Util.floatToLittleEndianBytes(vector.z);
        byte[] memoryData = new byte[]{
                slice1[3], slice1[2], slice1[1], slice1[0],
                slice2[3], slice2[2], slice2[1], slice2[0],
                slice3[3], slice3[2], slice3[1], slice3[0]};
        writeMemory(pid, Pointer.nativeValue(pointer), memoryData);
    }

    public static FloatVector2D readFloatVector2D(Pointer pointer) {
        byte[] memoryData = Mem.readMemory(pid, Pointer.nativeValue(pointer), Float.BYTES * 2);
        if (memoryData.length == 0) throw new RuntimeException(" Empty memoryData");
        byte[] sliceX = Arrays.copyOfRange(memoryData, 0, Float.BYTES);
        byte[] sliceY = Arrays.copyOfRange(memoryData, Float.BYTES, Float.BYTES * 2);
        FloatVector2D fv = new FloatVector2D();
        fv.x = ByteBuffer.wrap(sliceX).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        fv.y = ByteBuffer.wrap(sliceY).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        return fv;
    }

    public static Double readDouble(Pointer pointer) {
        byte[] memoryData = Mem.readMemory(pid, Pointer.nativeValue(pointer), Double.BYTES);
        if (memoryData.length == 0) throw new RuntimeException(" Empty memoryData");
        return ByteBuffer.wrap(memoryData).order(ByteOrder.LITTLE_ENDIAN).getDouble();
    }

    public static Integer readInteger(Pointer pointer) {
        byte[] memoryData = Mem.readMemory(pid, Pointer.nativeValue(pointer), Integer.BYTES);
        if (memoryData.length == 0) throw new RuntimeException(" Empty memoryData");
        return ByteBuffer.wrap(memoryData).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public static void writeInteger(Pointer pointer, Integer num) {
        if (num == null) throw new RuntimeException("Invalid num");
        byte[] memoryData = Util.intToBytes(num);
        writeMemory(pid, Pointer.nativeValue(pointer), memoryData);
    }

    public static Long readLong(Pointer pointer) {
        byte[] memoryData = Mem.readMemory(pid, Pointer.nativeValue(pointer), Long.BYTES);
        if (memoryData.length == 0) throw new RuntimeException(" Empty memoryData");
        return ByteBuffer.wrap(memoryData).order(ByteOrder.LITTLE_ENDIAN).getLong();
    }
}
