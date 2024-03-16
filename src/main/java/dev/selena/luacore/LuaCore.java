package dev.selena.luacore;

import dev.selena.luacore.nms.Compatibility;
import dev.selena.luacore.nms.Version;
import dev.selena.luacore.utils.data.UserDataManager;
import dev.selena.luacore.utils.entities.EntityEvents;
import dev.selena.luacore.utils.items.ItemEvent;
import dev.selena.luacore.utils.items.ItemEventSingleton;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * Main class for the library
 */
public class LuaCore {

    private static final String luaCoreLockFolderName = "LuaCoreLocks";

    /**
     * -- GETTER --
     *  Gets your plugins instance
     *
     * @return Plugin instance
     */
    @Getter
    private static Plugin plugin;
    /**
     * -- GETTER --
     *  Used for checking if verbose is enabled
     *
     * @return The verbose value
     */
    @Getter
    private static boolean verbose;
    /**
     * -- GETTER --
     *  Used for accessing the UserDataManager class, This is in early beta currently
     *
     * @return The instance of UserDataManger
     */
    @Getter
    private static UserDataManager userDataManager;
    @Getter
    @Setter
    private static CoreLogger coreLogger;
    @Getter
    private static boolean nmsVersionSupported = true;
    @Getter
    private static Version nmsVersion;

    /**
     * This needs to be called in the onEnable method of your plugin for it to work
     * @param plugin Your main class that extends JavaPlugin
     */
    public static void setPlugin(Plugin plugin) {
        LuaCore.plugin = plugin;
    }

    /**
     * Method to setup the library with the required methods (setPlugin and setCoreLogger)
     * @param plugin Your plugin instance
     */
    public static void setupCore(Plugin plugin) {
        setPlugin(plugin);
        setCoreLogger(new CoreLogger(plugin));
        setVerbose(true);
        File folder = new File(luaCoreLockFolderName);
        if (!folder.exists())
            folder.mkdirs();
        lockCrashHandling();
        String version = Compatibility.getVersion();
        if (version.equals("NOT_SUPPORTED")) {
            LuaMessageUtils.consoleWarn("No NMS supported");
            nmsVersionSupported = false;
        }
        else
            try {
                nmsVersion = Version.valueOf(version);
                LuaMessageUtils.verboseMessage("Using NMS version " + version);
            } catch (IllegalArgumentException e) {
                LuaMessageUtils.consoleWarn("No NMS supported");
                nmsVersionSupported = false;
            }
        plugin.getServer().getPluginManager().registerEvents(new EntityEvents(), plugin);

    }

    /**
     * Used for crash handling of the lock files.
     * If the server is to crash and this method didn't exist,
     * then you would have issues with lock files related to classes
     * NOTE:
     * If there is a start-up error causing crashes, you will likely encounter an issue with the lock files.
     * If you need to manually delete them for any reason,
     * they are located in the LuaCoreLocks inside your servers main folder.
     * Not the plugin folder
     * @param force set to true to force clearing the lock folder.
     *              Only one needs to go here it's just a way to avoid a second method.
     */
    @SneakyThrows
    public static void lockCrashHandling(boolean ... force) {
        File lastLoaded = new File(luaCoreLockFolderName, "last_load.info");
        if (!lastLoaded.exists() || (force.length > 0 && force[0])) {
            LuaMessageUtils.verboseMessage((force.length > 0 && force[0]) ? "Force reset" : "File doesn't exist yet");
            deleteLockFiles();
            createLastLoadedFile(lastLoaded);
            return;
        }
        String lastLoadedTimeStr = Files.readString(lastLoaded.toPath());
        if (lastLoadedTimeStr == null) {
            deleteLockFiles();
            createLastLoadedFile(lastLoaded);
            return;
        }
        Long lastTime = Long.parseLong(lastLoadedTimeStr);
        Long timeNow = System.currentTimeMillis();
        if ((timeNow - lastTime) >= 60000) { // 1 minute
            LuaMessageUtils.verboseMessage("Is more than 1 min");
            deleteLockFiles();
            createLastLoadedFile(lastLoaded);
            return;
        }
        LuaMessageUtils.verboseMessage("Is less than 1 min");
    }

    @SneakyThrows
    private static void createLastLoadedFile(File lastLoaded) {
        lastLoaded.createNewFile();
        Files.write(lastLoaded.toPath(), String.valueOf(System.currentTimeMillis()).getBytes(), StandardOpenOption.APPEND);

    }

    /**
     * Used for setting up user data management, This is in early beta currently
     * @param userDataManager The instance of the UserDataManager class
     */
    public static void setUserDataManager(UserDataManager userDataManager) {
        LuaCore.userDataManager = userDataManager;
    }


    /**
     * This should only be called by one plugin on your server,
     * Used for preventing the placement/usage of un usable items
     */
    public static void registerItemEvents() {
        ItemEvent itemEvent = ItemEventSingleton.getInstance();
        if (itemEvent == null)
            return;
        plugin.getServer().getPluginManager().registerEvents(itemEvent, plugin);

    }

    /**
     * Used for enabling extra messages
     * @param verbose True if you want extra messages
     */
    public static void setVerbose(boolean verbose) {
        LuaCore.verbose = verbose;
        LuaMessageUtils.verboseMessage("Verbose mode is enabled");
    }

    /**
     * Used for NBT compounds
     * @return Returns the compound name for your plugin
     */
    public static String getCompountName() {
        String name = LuaCore.getPlugin().getName();
        LuaMessageUtils.verboseMessage(name);
        return name;
    }

    /**
     * Used for saving all needed data call this in onDisable
     */
    public static void save() {
        try {
            if (userDataManager != null)
                userDataManager.saveAllUserData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This needs to be called in the onDisable method of lock file management
     * @see LuaCore#lockCrashHandling(boolean...)
     */
    public static void disable() {
        save();
        deleteLockFiles();
    }

    private static void deleteLockFiles() {
        File lockFolder = new File(luaCoreLockFolderName);
        if (lockFolder.exists() && lockFolder.isDirectory()) {
            if (lockFolder.listFiles() == null)
                return;
            for (File lock : lockFolder.listFiles()) {
                lock.delete();
            }
        }
    }

    public static String getLockFileName(String name) {
        return luaCoreLockFolderName + File.separator + name;
    }

}
