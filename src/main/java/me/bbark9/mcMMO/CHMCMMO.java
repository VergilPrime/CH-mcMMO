package me.bbark9.mcMMO;

import org.bukkit.event.Listener;
import com.laytonsmith.annotations.shutdown;
import com.laytonsmith.annotations.startup;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import me.bbark9.mcMMO.events.LevelUp;

public class CHMCMMO implements Listener {

    static MMOListener listener;
    static LevelUp event;

    @startup
    public static void setup() {
        CommandHelperPlugin chp = CommandHelperPlugin.self;
        listener = new MMOListener(chp);
    }

    @shutdown
    public static void unload() {
        listener.unregister();
    }
}
