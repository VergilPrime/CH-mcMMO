package me.bbark9.mcMMO;

import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import com.gmail.nossr50.events.skills.McMMOPlayerSkillEvent;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;
import me.bbark9.mcMMO.abstraction.PlayerSkillEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 *
 * @author import
 */
public class MCMMOListener implements Listener {

    CommandHelperPlugin plugin;

    public MCMMOListener(CommandHelperPlugin plugin) {
        this.plugin = plugin;
        plugin.registerEvent(this);
    }

    public void unregister() {
        McMMOPlayerSkillEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onLevelUp(McMMOPlayerLevelUpEvent event) {
        PlayerSkillEvent e = new PlayerSkillEvent(event);
        System.out.println("osihaldfh");
        EventUtils.TriggerListener(Driver.EXTENSION, "mcmmo_levelup", e);
    }
}
