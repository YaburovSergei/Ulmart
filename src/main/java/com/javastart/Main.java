package com.javastart;


import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Main {

    public static void main(String[] args) {

        MyMap<Integer, Long> st = new MyHashMap();
        MyMap<Integer, Long> m = null;
        FileWriter writer = null;

        try {
            m = readDataFromFile(args[0]);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the input file");
        }

        saveDataToFile(args[1], m);
    }

    private static void saveDataToFile(String fileName, MyMap msp) {
        FileWriter writer;
        try {
            writer = new FileWriter(fileName);
            Object[] keys = msp.keySet();
            for (Object key : keys) {
                Integer key1 = (Integer) key;
                String k = key1 + " " + msp.get(key1);
                writer.write(k);
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while saving data to file");
        }
    }

    private static MyMap<Integer, Long> readDataFromFile(String filePath) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath, "r");
        FileChannel chan = file.getChannel();
        long fileLength = file.length();
        MyMap<Integer, Long> msp = new MyHashMap<Integer, Long>();
        if (fileLength != 0) {
            try {
                MappedByteBuffer buffer = null;
                long bufferSize;
                char sym;

                for (long j = 0; j < fileLength; j += bufferSize) {
                    bufferSize = 2;
                    buffer = chan.map(FileChannel.MapMode.READ_ONLY, j, bufferSize);

                    for (int i = 0; i < bufferSize; i = i + 2) {
                        if (Character.isDigit(buffer.get(i)) && Character.isDigit(buffer.get(i + 1))) {
                            char[] ch = new char[2];
                            ch[0] = (char) buffer.get(i);
                            ch[1] = (char) buffer.get(i + 1);
                            String s = String.valueOf(ch);

                            int anInt = Integer.parseInt(s);
                            if (msp.containsKey(anInt)) {
                                Long removed = msp.remove(anInt);
                                msp.put(anInt, removed + 1);
                            } else {
                                msp.put(anInt, 1L);
                            }
                        }
                    }
                    if (buffer != null) {
                        buffer.clear();
                    }
                }
            } finally {
                chan.close();
                file.close();
            }
        } else {
            System.out.println("Error, input file is empty");
        }
        return msp;
    }
}
