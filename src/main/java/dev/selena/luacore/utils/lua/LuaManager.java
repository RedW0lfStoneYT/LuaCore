package dev.selena.luacore.utils.lua;

import dev.selena.luacore.LuaCore;
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


    public static boolean runScript(String function, String scriptPath, String scriptName, Player player, int level, Event event) {
        // Create a Lua globals environment
        String path = new File(".").getPath();
        globals.set("PotionEffectType", CoerceJavaToLua.coerce(org.bukkit.potion.PotionEffectType.class));
        globals.set("Material", CoerceJavaToLua.coerce(org.bukkit.Material.class));

        // Load the Lua script from file
        String scriptFilePath = new File(LuaCore.getPlugin().getDataFolder() + "/" + scriptPath, scriptName + ".lua").getPath();

        String scriptDirectory = LuaCore.getPlugin().getDataFolder() + scriptPath + "/";
        globals.get("package").set("path", globals.get("package").get("path").tojstring() + ";" + scriptDirectory + "?.lua;" + scriptDirectory + "?/?.lua;" + scriptDirectory + "utils/?.lua");
        LuaValue chunk = globals.loadfile(scriptFilePath);
        chunk.call();
        if (!isFunctionExists(scriptFilePath, function))
            return false;

        // Get the run function from the globals environment
        LuaValue runFunction = globals.get(function);

        // Convert the player object to LuaValue
        LuaValue playerArg = CoerceJavaToLua.coerce(player);
        LuaValue eventArgs = CoerceJavaToLua.coerce(event);
        LuaValue scriptHelper = CoerceJavaToLua.coerce(new ScriptHelper());
        LuaValue levelArg = CoerceJavaToLua.coerce(level);

        LuaValue[] args = {playerArg, levelArg, scriptHelper, eventArgs};

        runFunction.invoke(args);

        return true;
    }

    public static Globals getGlobals() {

        return globals;
    }

    public static boolean runScript(String function, String scriptName, Player player, int level) {
        return runScript(function, "Enchants", scriptName, player, level, null);
    }

    public static boolean runScript(String scriptName, Player player, int level) {

        return runScript("run", scriptName, player, level);
    }


    public static boolean runScript(String function, String path, String scriptName, Player player, int level) {
        return runScript(function, path, scriptName, player, level, null);
    }


    public static boolean runEvent(String scriptName, Player player, int level, Event event) {

        return runScript("event", "Enchants", scriptName, player, level, event);
    }

    public static boolean runEvent(String scriptPath, String scriptName, Player player, int level, Event event) {

        return runScript("event", scriptPath, scriptName, player, level, event);
    }

    private static boolean isFunctionExists(String luaScriptPath, String functionName) {
        // Create a Lua globals environment
        Globals globals = JsePlatform.standardGlobals();

        // Load the Lua script from file
        LuaValue chunk = globals.loadfile(luaScriptPath);
        LuaValue result = chunk.call();

        // Check if the Lua value is a table
        if (result.istable()) {
            // Get the Lua value associated with the function name
            LuaValue functionValue = result.get(functionName);

            // Check if the Lua value is a function
            return functionValue.isfunction();
        }

        return false;
    }



    public static void loadResourceFolder(String folderName) {
        System.out.println(folderName);
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
                    // Process the file
                    System.out.println("Found file: " + file.getName());
                } else if (file.isDirectory()) {
                    // Process the subdirectory
                    System.out.println("Found subdirectory: " + file.getName());
                }
            }
        }

    }
}
