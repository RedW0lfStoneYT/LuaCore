package dev.selena.luacore.utils.lua;

import dev.selena.luacore.LuaCore;
import dev.selena.luacore.utils.config.FileManager;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.server.Launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Main class for Lua management
 */
public class LuaManager {


    private static final Globals globals = JsePlatform.standardGlobals();


    /**
     * Used to run a lua script
     * @param function The name of the function you want to run
     * @param scriptPath The path to the folder your script is in
     * @param scriptName The name of the script
     * @param args List of arguments in the form of {@link LuaArgValue LuaArgValue}
     * @return True if the function exists in the Lua file
     */
    public static boolean runScript(String function, String scriptPath, String scriptName, LuaArgValue ... args) {

        Long start = System.currentTimeMillis();
        scriptName = scriptName.endsWith(".lua") ? scriptName : scriptName + ".lua";

        LuaMessageUtils.verboseMessage("Attempting to run script: " + scriptName);
        // Load the Lua script from file
        File scriptFile = new File(LuaCore.getPlugin().getDataFolder() + "/" + scriptPath, scriptName);
        if (!scriptFile.exists()) {
            LuaMessageUtils.consoleError(scriptName + " Does not exist in " + scriptPath + "! Was there a typo?");
            return false;
        }
        String scriptFilePath = scriptFile.getPath();

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


        LuaTable argsTable = new LuaTable();
        for (LuaArgValue argValue : args) {
            argsTable.set(argValue.name(), CoerceJavaToLua.coerce(argValue.value()));
        }
        argsTable.set("scriptHelper", CoerceJavaToLua.coerce(new ScriptHelper()));



        // Combine all arguments
        LuaValue[] parsedArgs = {argsTable};


        try {
            runFunction.invoke(parsedArgs);
        } catch (Exception e) {
            LuaMessageUtils.consoleError("There was an error while running the script " + scriptName + ".\n" +
                    "Failed with exception: " + e.getMessage());
            return false;
        }
        Long end = System.currentTimeMillis();
        LuaMessageUtils.verboseMessage("Executed " + scriptName + ". The script took " + (end - start) + "ms");

        return true;
    }

    /**
     * Used for running lua script functions
     * @param function The name of the function you want to run
     * @param script The path to the script inclusive (test/test.lua)
     * @param args The list of argument variables you want to pass in
     * @return True when all checks are parsed
     */
    public static boolean runScript(String function, String script, LuaArgValue ... args) {
        return runScript(function,"", script, args);
    }

    /**
     * Used for running lua script event function
     * @param scriptPath The path to the script
     * @param scriptName The name of the script file
     * @param args The list of argument variables you want to pass in
     * @return True when all checks are parsed
     * NOTE: Be sure to add the Event instance into the LuaArgValue list
     */
    public static boolean runEvent(String scriptPath, String scriptName, LuaArgValue ... args) {
        return runScript("event", scriptPath, scriptName, args);
    }

    /**
     * Used for running lua script functions
     * @param script The path to the script inclusive (test/test.lua)
     * @param args The list of argument variables you want to pass in
     * @return True when all checks are parsed
     * NOTE: Be sure to add the Event instance into the LuaArgValue list
     */
    public static boolean runEvent(String script, LuaArgValue... args) {
        return runEvent("", script, args);
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
     * @throws IOException thrown when the files fail to copy from the plugin into the allocated folder
     */
    public static void loadResourceFolder(String folderName) throws IOException {
        folderName = folderName.replace("/", File.separator).replace("\\", File.separator);
        final File jarFile = new File(LuaManager.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        if(jarFile.isFile()) {  // Run with JAR file
            final JarFile jar = new JarFile(jarFile);
            final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            while(entries.hasMoreElements()) {
                JarEntry next = entries.nextElement();
                final String name = next.getName();
                if (name.startsWith(folderName + File.separator) && !name.equals(folderName + File.separator)) { //filter according to the path
                    File dest = LuaCore.getPlugin().getDataFolder();
                    File file = new File(dest, name);
                    LuaMessageUtils.verboseMessage(file.getPath());
                    LuaMessageUtils.verboseMessage(name);

                    if (!next.isDirectory()) {
                        file.getParentFile().mkdir();
                        file.createNewFile();

                        changeContents(jar, next, file);

                        LuaMessageUtils.verboseMessage("Found file: " + file.getName());

                    } else {
                        file.mkdir();
                        LuaMessageUtils.verboseMessage("Found subdirectory: " + file.getName());
                        new File(LuaCore.getPlugin().getDataFolder() + File.separator + folderName, file.getName()).mkdirs();
                    }

                }
            }
            jar.close();
        } else { // Run with IDE
            final URL url = Launcher.class.getResource("/" + folderName);
            if (url != null) {
                try {
                    final File apps = new File(url.toURI());
                    for (File app : apps.listFiles()) {
                        File file = FileManager.file("", app.getPath().split("test", 2)[1]);
                        if (file.exists())
                            continue;
                        if (file.isDirectory())
                            file.mkdirs();
                        else
                            file.getParentFile().mkdirs();
                        System.out.println(file.toPath());
                        Files.copy(app.toPath(), file.toPath());
                    }
                } catch (URISyntaxException ex) {
                    // never happens
                }
            }
        }
    }

    private static void changeContents(JarFile jarFile, JarEntry entry, File file) throws IOException {
        InputStream input = jarFile.getInputStream(entry); // stream from jar
        FileOutputStream fileOutput = new FileOutputStream(file); // stream from dist file
        while (input.available() > 0) {  // write contents of 'jar' to 'file'
            fileOutput.write(input.read());
        }
        fileOutput.close();
        input.close();
    }

}
