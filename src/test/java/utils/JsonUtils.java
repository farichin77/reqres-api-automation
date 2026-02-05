package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import models.User;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtils {
    private static final String RESOURCES_PATH = "src/test/resources/json/";
    private static final String CREATED_USER_FILE = "created_user.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void saveCreatedUser(String jsonResponse) {
        try {
            // Buat directory jika belum ada
            Files.createDirectories(Paths.get(RESOURCES_PATH));
            
            // Simpan JSON response ke file
            File file = new File(RESOURCES_PATH + CREATED_USER_FILE);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, objectMapper.readTree(jsonResponse));
            
            System.out.println("[JsonUtils] Saved created user to: " + file.getAbsolutePath());
            
        } catch (IOException e) {
            System.err.println("[JsonUtils] Failed to save created user: " + e.getMessage());
        }
    }

    public static String getLastCreatedUserId() {
        try {
            File file = new File(RESOURCES_PATH + CREATED_USER_FILE);
            if (!file.exists()) {
                System.out.println("[JsonUtils] No created user file found");
                return null;
            }

            JsonNode jsonNode = objectMapper.readTree(file);
            JsonNode idNode = jsonNode.get("id");
            
            if (idNode != null) {
                String userId = idNode.asText();
                System.out.println("[JsonUtils] Retrieved user ID: " + userId);
                return userId;
            } else {
                System.out.println("[JsonUtils] No ID found in created user file");
                return null;
            }
            
        } catch (IOException e) {
            System.err.println("[JsonUtils] Failed to read created user: " + e.getMessage());
            return null;
        }
    }

    public static User getLastCreatedUser() {
        try {
            File file = new File(RESOURCES_PATH + CREATED_USER_FILE);
            if (!file.exists()) {
                return null;
            }

            return objectMapper.readValue(file, User.class);
            
        } catch (IOException e) {
            System.err.println("[JsonUtils] Failed to parse created user: " + e.getMessage());
            return null;
        }
    }

    public static void clearCreatedUser() {
        try {
            File file = new File(RESOURCES_PATH + CREATED_USER_FILE);
            if (file.exists()) {
                file.delete();
                System.out.println("[JsonUtils] Cleared created user file");
            }
        } catch (Exception e) {
            System.err.println("[JsonUtils] Failed to clear created user: " + e.getMessage());
        }
    }
}
