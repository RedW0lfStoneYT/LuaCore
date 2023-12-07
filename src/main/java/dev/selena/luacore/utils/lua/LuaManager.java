package dev.selena.luacore.utils.lua;

import dev.selena.luacore.LuaCore;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

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
     */
    public static boolean runEvent(String scriptPath, String scriptName, LuaArgValue ... args) {
        return runScript("event", scriptPath, scriptName, args);
    }

    /**
     * Used for running lua script functions
     * @param script The path to the script inclusive (test/test.lua)
     * @param args The list of argument variables you want to pass in
     * @return True when all checks are parsed
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
     */
    public static void loadResourceFolder(String folderName) throws URISyntaxException, IOException {
        LuaMessageUtils.verboseMessage(folderName);

        File tempDir = new File(LuaManager.class.getClassLoader().getResource(folderName).toURI());
        if (!tempDir.exists() && !tempDir.mkdirs()) {
            return;
        }

        // Process the files within the temporary directory
        File[] folderContent = tempDir.listFiles();

        if (folderContent != null) {
            for (File file : folderContent) {

                if (file.isFile()) {
                    LuaMessageUtils.verboseMessage("Found file: " + file.getName());
                    File destFile = new File(LuaCore.getPlugin().getDataFolder() + "/" + folderName, file.getName());
                    if (destFile.exists())
                        continue;
                    Path destinationPath = destFile.toPath();

                    Path input = new File("/" + file.getParent() + "/" + file.getName()).toPath();
                    destinationPath.toFile().getParentFile().mkdirs();
                    Files.copy(input, destinationPath);
                } else if (file.isDirectory()) {
                    LuaMessageUtils.verboseMessage("Found subdirectory: " + file.getName());
                    new File(LuaCore.getPlugin().getDataFolder() + "/" + folderName, file.getName()).mkdirs();
                }
            }
        }

    }

}
