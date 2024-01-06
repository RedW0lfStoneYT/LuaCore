package dev.selena.luacore;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import dev.selena.luacore.utils.text.ContentUtils;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.MissingResourceException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoreLogger extends Logger {
    private String pluginName;
    private boolean unitTest;
    public CoreLogger(Plugin context) {
        super(context.getClass().getCanonicalName(), null);

        try {
            String prefix = context.getDescription().getPrefix();
            pluginName = prefix != null ? "[" + prefix + "] " : "[" + context.getDescription().getName() + "] ";
            setParent(context.getServer().getLogger());
        } catch (Exception e) {
            pluginName = "[UNITTEST] ";
            unitTest = true;
        }
        setLevel(Level.ALL);
    }


    @Override
    public void log(Level level, String message) {
        if (!isLoggable(level)) {
            return;
        }
        if (unitTest) {
            System.out.println("Likely in a unittest right now, skipping logging");
            return;
        }
        String levelStr = level == Level.SEVERE ? "&4&l[" + level + "] " : level == Level.WARNING ? "&e[" + level + "] " : "";
        Bukkit.getConsoleSender().sendMessage(ContentUtils.color(levelStr + pluginName + IridiumColorAPI.stripColorFormatting(message)));
    }

}
