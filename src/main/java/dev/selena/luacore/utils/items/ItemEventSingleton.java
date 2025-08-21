package dev.selena.luacore.utils.items;

import dev.selena.luacore.LuaCore;
import dev.selena.luacore.utils.text.LuaMessageUtils;

import java.io.File;
import java.io.IOException;

/**
 * Used for ensuring there is ever only one instance of ItemEvent loaded
 */
public class ItemEventSingleton {
    private static final String LOCK_FILE_PATH = LuaCore.getLockFileName("item_events_lock");

    private ItemEventSingleton() {}

    /**
     * Used for creating one single instance of ItemEvents
     * @return The instance of ItemEvent or null if already initialized
     */
    public static synchronized ItemEvent getInstance() {
        if (lockFileExists()) {
            LuaMessageUtils.consoleError("ItemEvent has already been instantiated.");
            return null;
        }

        // Create lock file
        try {
            createLockFile();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create lock file.");
        }

        return new ItemEvent();
    }

    private static boolean lockFileExists() {
        File lockFile = new File(LOCK_FILE_PATH);
        return lockFile.exists();
    }

    private static void createLockFile() throws IOException {
        File lockFile = new File(LOCK_FILE_PATH);
        lockFile.createNewFile();
    }
}