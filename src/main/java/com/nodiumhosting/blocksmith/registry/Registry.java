package com.nodiumhosting.blocksmith.registry;

import com.nodiumhosting.blocksmith.Blocksmith;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Registry<T extends IRegistryEntry> {
    private final Class<T> type;
    private final Map<String, T> registry = new HashMap<>();

    public Registry(String dirPath, Class<T> type) {
        this.type = type;

        ClassLoader classLoader = Blocksmith.class.getClassLoader();
        URL url = classLoader.getResource(dirPath);
        if (url == null) {
            catchException(dirPath);
            return;
        }
        try {
            Path p = Paths.get(url.toURI());
            File file = p.toFile();
            load(file);
        } catch (Exception e) {
            catchException(dirPath);
//            e.printStackTrace();
        }
    }

    public static void catchException(String entryName) {
        Blocksmith.LOGGER.error("Failed to load registry or registry entry: {}", entryName);
    }

    private void load(File fileOrDir) {
        if (!fileOrDir.exists()) return;
        if (fileOrDir.isDirectory()) {
            File[] files = fileOrDir.listFiles();
            if (files == null) return;
            for (File file : files) {
                load(file);
            }
        } else {
            String name = fileOrDir.getName();
            if (!name.endsWith(".json")) return;
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileOrDir));
                try {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                        line = br.readLine();
                    }
                    String json = sb.toString();
                    T entry = Registries.gson.fromJson(json, type);
                    if (!entry.isValid()) {
                        catchException(fileOrDir.getName());
                        return;
                    }
                    this.register(entry);
                } catch (Exception e) {
                    catchException(fileOrDir.getName());
//                    e.printStackTrace();
                } finally {
                    br.close();
                }
            } catch (Exception e) {
                catchException(fileOrDir.getName());
//                e.printStackTrace();
            }
        }
    }

    private void register(T entry) {
        registry.put(entry.id(), entry);
    }

    @Nullable
    public T get(String nid) {
        return registry.get(nid);
    }

    public boolean contains(String nid) {
        return registry.containsKey(nid);
    }
}
