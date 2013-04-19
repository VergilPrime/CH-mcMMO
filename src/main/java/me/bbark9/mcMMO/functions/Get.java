package me.bbark9.mcMMO.functions;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.api.exceptions.InvalidPlayerException;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.laytonsmith.abstraction.bukkit.BukkitMCPlayer;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.environments.CommandHelperEnvironment;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;

public class Get {

    public String docs() {
        return "MCMMO functionality.";
    }

    @api
    public static class mcmmo_get extends AbstractFunction {
        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false;
        }

        public CHVersion since() {
            return CHVersion.V3_3_1;
        }
        
        public ExceptionType[] thrown() {
            return new ExceptionType[]{};
        }

        public Construct exec(Target t, Environment environment,
                Construct... args) throws ConfigRuntimeException {
            // Make sure mcmmo is installed.
            Static.checkPlugin("mcMMO", t);

            BukkitMCPlayer player = null;

            // Get user if online.
            try {
                if (args.length == 1) {
                    player = (BukkitMCPlayer) Static.GetPlayer(args[0], t);
                } else {
                    player = (BukkitMCPlayer) environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
                }
            } catch (ConfigRuntimeException e) {
                if (args.length == 0) {
                    throw new ConfigRuntimeException("You need to specify a player.", t);
                }
                // Player is offline, we'll use the given string name later.
            }

            CArray skills = new CArray(t);

            // Accumulate skills into the skill map.
            for (SkillType skillname : SkillType.values()) {
                int level;
                int power;

                if (player != null) {
                    power = ExperienceAPI.getPowerLevel(player._Player());
                    level = ExperienceAPI.getLevel(player._Player(), skillname.name());
                } else {
                    String name = args[0].val();

                    try {
                        power = ExperienceAPI.getPowerLevelOffline(name);
                        level = ExperienceAPI.getLevelOffline(name, skillname.toString());
                    } catch (InvalidPlayerException e) {
                        throw new ConfigRuntimeException("Unknown McMMO player, " + name, t);
                    }
                }

                CString skill = new CString(skillname.name(), t);
                CInt value = new CInt(level, t);

                skills.set("POWER", new CInt(power, t), t);
                skills.set(skill, value, t);
            }

            return skills;
        }

        public String getName() {
            return "mcmmo_get"; //Name of function
        }

        public Integer[] numArgs() {
            return new Integer[]{0, 1}; //Arguments in function
        }

        public String docs() {
            return "array {[player]} Return an associative array of skills by either"
                    + " the current player or the given player. Will throw an exception"
                    + " if the player has not joined the server since McMMO was installed.";
        }
    }
}
