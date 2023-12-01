package dev.selena.luacore.utils.lua;

import dev.selena.luacore.LuaCore;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LuaManager {


    private static final Globals globals = JsePlatform.standardGlobals();


    /**
     * Used to run a lua script
     * @param function The name of the function you want to run
     * @param scriptPath The path to the folder your script is in
     * @param scriptName The name of the script
     * @param player Used to parse in a player argument
     * @param level Used for custom enchants plugin, can be used for anything else
     * @param event Used for event specific stuff inside the lua script
     * @return True if the function exists in the Lua file
     */
    public static boolean runScript(String function, String scriptPath, String scriptName, Player player, int level, Event event) {

        Long start = System.currentTimeMillis();
        scriptName = scriptName.endsWith(".lua") ? scriptName : scriptName + ".lua";

        LuaMessageUtils.verboseMessage("Attempting to run script: " + scriptName);
        // Load the Lua script from file
        String scriptFilePath = new File(LuaCore.getPlugin().getDataFolder() + "/" + scriptPath, scriptName).getPath();

        String scriptDirectory = LuaCore.getPlugin().getDataFolder() + scriptPath + "/";
        globals.get("package").set("path", globals.get("package").get("path").tojstring() + ";" + scriptDirectory + "?.lua;" + scriptDirectory + "?/?.lua;" + scriptDirectory + "utils/?.lua");
        LuaValue chunk = globals.loadfile(scriptFilePath);
        chunk.call();
        if (!doesFunctionExists(scriptFilePath, function)) {
            LuaMessageUtils.verboseMessage(function + " does not exist in " + scriptName);
            return false;
        }

        // Get the run function from the globals environment
        LuaValue runFunction = globals.get(function);

        // Convert the player object to LuaValue
        LuaValue playerArg = CoerceJavaToLua.coerce(player);
        LuaValue eventArgs = CoerceJavaToLua.coerce(event);
        LuaValue scriptHelper = CoerceJavaToLua.coerce(new ScriptHelper());
        LuaValue levelArg = CoerceJavaToLua.coerce(level);

        LuaValue[] args = {playerArg, levelArg, scriptHelper, eventArgs};

        runFunction.invoke(args);
        Long end = System.currentTimeMillis();
        LuaMessageUtils.verboseMessage("Executed " + scriptName + ". The script took " + (end - start) + "ms");

        return true;
    }


    /**
     * Used for calling a Lua script from Java Code,
     * In this situation Script path is just the plugins Data Folder
     * @param function The name of the function you want to run
     * @param scriptName The name of the Script
     * @param player Used for parsing in player arguments
     * @param level Used for custom enchants but can be used for anything else
     * @return True if the function exists
     */
    public static boolean runScript(String function, String scriptName, Player player, int level) {
        return runScript(function, "", scriptName, player, level, null);
    }

    /**
     * Used for calling a Lua script from Java Code,
     * In this situation Script path is just the plugins Data Folder.
     * This one runs the "run" function
     * @see LuaManager#runScript(String, String, String, Player, int, Event) If you want full control
     * @param scriptName The name of the Script
     * @param player Used for parsing in player arguments
     * @param level Used for custom enchants but can be used for anything else
     * @return True if the function exists
     */
    public static boolean runScript(String scriptName, Player player, int level) {

        return runScript("run", scriptName, player, level);
    }


    /**
     * Used for almost full control of running custom functions from the lua
     * script
     * @param function The name of the function you want to run
     * @param path The folder path that the script is in
     * @param scriptName The name of the script
     * @param player Used for parsing in player arguments
     * @param level Used in custom enchants but can be used for any int
     * @return True if the function exists
     */
    public static boolean runScript(String function, String path, String scriptName, Player player, int level) {
        return runScript(function, path, scriptName, player, level, null);
    }


    /**
     * Used for running events using the "event" function in the lua script
     * @param scriptName The name of the script you want to run
     * @param player Used for parsing in player arguments
     * @param level Used in custom enchants but can be used for any int
     * @param event The Event that is causing this function to be called
     *              Used for handling events in your lua script
     * @return True if the "event" function exists
     */
    public static boolean runEvent(String scriptName, Player player, int level, Event event) {

        return runScript("event", "", scriptName, player, level, event);
    }

    /**
     * Used for running events using the "event" function in the lua script
     * @param scriptName The name of the script you want to run
     * @param player Used for parsing in player arguments
     * @param event The Event that is causing this function to be called
     *              Used for handling events in your lua script
     * @return True if the "event" function exists
     */
    public static boolean runEvent(String scriptName, Player player, Event event) {

        return runScript("event", "", scriptName, player, 0, event);
    }

    /**
     * Used for running events using the "event" function in the lua script
     * @param scriptPath The path to the script
     * @param scriptName The name of the script you want to run
     * @param player Used for parsing in player arguments
     * @param level Used in custom enchants but can be used for any int
     * @param event The Event that is causing this function to be called
     *              Used for handling events in your lua script
     * @return True if the "event" function exists
     */
    public static boolean runEvent(String scriptPath, String scriptName, Player player, int level, Event event) {

        return runScript("event", scriptPath, scriptName, player, level, event);
    }

    /**
     * Used for checking if a lua function exists without trying to run the lua
     * @param luaScriptPath The path to the script
     * @param functionName The name of the script
     * @return True if the function exists
     */
    private static boolean doesFunctionExists(String luaScriptPath, String functionName) {
        LuaMessageUtils.verboseMessage("Checking if " + functionName + " exists");
        // Create a Lua globals environment
        Globals globals = JsePlatform.standardGlobals();

        // Load the Lua script from file
        LuaValue chunk = globals.loadfile(luaScriptPath);
        LuaValue result = chunk.call();

        // Check if the Lua value is a table
        if (result.istable()) {
            // Get the Lua value associated with the function name
            LuaValue functionValue = result.get(functionName);

            LuaMessageUtils.verboseMessage(functionName + " does exist");
            // Check if the Lua value is a function
            return functionValue.isfunction();
        }

        LuaMessageUtils.verboseError(functionName + " does NOT exist");
        return false;
    }


    /**
     * Used for loading a folder from the Resources folder
     * Note its best to add all the resource files into sub folders
     * @param folderName The start path for loading the files
     */
    public static void loadResourceFolder(String folderName) {
        LuaMessageUtils.verboseMessage(folderName);
        File tempDir = new File(LuaCore.getPlugin().getDataFolder(), folderName);
        if (!tempDir.exists() && !tempDir.mkdirs()) {
            return;
        }

        // Get the JAR file path
        String jarFilePath;
        try {
            jarFilePath = Objects.requireNonNull(LuaManager.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


        // Extract the folder content from the JAR file
        try (JarFile jarFile = new JarFile(jarFilePath)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory() && entry.getName().startsWith(folderName)) {
                    new File(tempDir, entry.getName().substring(folderName.length())).mkdirs();
                }
                if (!entry.isDirectory() && entry.getName().startsWith(folderName)) {
                    String fileName = entry.getName().substring(folderName.length());
                    try (InputStream fileStream = jarFile.getInputStream(entry)) {
                        if (fileStream != null) {
                            Path destinationPath = new File(tempDir, fileName).toPath();
                            Files.copy(fileStream, destinationPath);
                        }
                    } catch (FileAlreadyExistsException ignore) {

                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process the files within the temporary directory
        File[] folderContent = tempDir.listFiles();
        if (folderContent != null) {
            for (File file : folderContent) {
                if (file.isFile()) {
                    LuaMessageUtils.verboseMessage("Found file: " + file.getName());
                } else if (file.isDirectory()) {
                    LuaMessageUtils.verboseMessage("Found subdirectory: " + file.getName());
                }
            }
        }

    }

}
