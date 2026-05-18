package com.example.clubmanagement.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataFileUtil {
    private static final String DATA_DIR = "./data";
    
    public static void ensureDataDirectory() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    public static void writeObjectToFile(Object obj, String filename) {
        ensureDataDirectory();
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_DIR + "/" + filename))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T readObjectFromFile(String filename) {
        File file = new File(DATA_DIR + "/" + filename);
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static <T> void appendObjectToFile(T obj, String filename) {
        ensureDataDirectory();
        File file = new File(DATA_DIR + "/" + filename);
        List<T> list = new ArrayList<>();
        
        if (file.exists()) {
            List<T> existing = readObjectFromFile(filename);
            if (existing != null) {
                list.addAll(existing);
            }
        }
        
        list.add(obj);
        writeObjectToFile(list, filename);
    }
    
    public static void writeListToFile(List<?> list, String filename) {
        ensureDataDirectory();
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_DIR + "/" + filename))) {
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T> List<T> readListFromFile(String filename) {
        File file = new File(DATA_DIR + "/" + filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public static boolean deleteFile(String filename) {
        File file = new File(DATA_DIR + "/" + filename);
        return file.delete();
    }
}