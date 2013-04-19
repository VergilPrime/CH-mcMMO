package me.bbark9.mcMMO;

import org.bukkit.event.Listener;
import com.laytonsmith.annotations.shutdown;
import com.laytonsmith.annotations.startup;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.functions.AbstractFunction;
import me.bbark9.mcMMO.events.LevelUp;

public class CHMCMMO implements Listener {

    static MCMMOListener listener;
    static LevelUp event;
    public static String depend = "mcMMO";

    @startup
    public static void setup() {
        CommandHelperPlugin chp = CommandHelperPlugin.self;
        listener = new MCMMOListener(chp);
    }

    @shutdown
    public static void unload() {
        listener.unregister();
    }

    public static abstract class DevFunction extends AbstractFunction {

        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false;
        }

        public CHVersion since() {
            return CHVersion.V3_3_1;
        }
    }

    public static abstract class DevEvent extends AbstractEvent {

        public CHVersion since() {
            return CHVersion.V3_3_1;
        }

        public Driver driver() {
            return Driver.EXTENSION;
        }
    }
}
