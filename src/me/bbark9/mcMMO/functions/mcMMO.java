package me.bbark9.mcMMO.functions;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import me.bbark9.mcMMO.CHDev;
import me.bbark9.mcMMO.CHDev.DevFunction;

import org.bukkit.entity.Player;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.laytonsmith.abstraction.bukkit.BukkitMCPlayer;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;

public class mcMMO {
        
	public String docs() {
		return "Function testing for EntityManagement class.";
	}
	
	
	@api
	public static class mcmmo_get extends DevFunction { //Mob Initate function
		
		public ExceptionType[] thrown() {
			// TODO Auto-generated method stub
			return new ExceptionType[]{ExceptionType.CastException, ExceptionType.BadEntityException};
		}
		
		public Construct exec(Target t, Environment environment,
				Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin(CHDev.depend, t); 
			//Player player = Static.GetPlayer(args[0], t);
			BukkitMCPlayer MCPlayer = (BukkitMCPlayer) Static.GetPlayer(args[0], t);
			Player player = MCPlayer._Player();
			
			Map<String, Integer> skills = new HashMap<String, Integer>();
			
			if(player.isOnline()) {;
				for(SkillType s : SkillType.values()) {
					int Level = ExperienceAPI.getLevel(player, s.toString());
					skills.put(s.toString(), Level);
				}
			} else {
				for(SkillType s : SkillType.values()) {
					int Level = ExperienceAPI.getLevelOffline(player.getName(), s.toString());
					skills.put(s.toString(), Level);
				}
			}
            int total = 0;
			CArray Skills = new CArray(t);
			for (Entry<String, Integer> entry : skills.entrySet()) { 
                total = total + entry.getValue();
				CString EntryKey = new CString(entry.getKey(), t);
				CInt EntryInt = new CInt(entry.getValue(), t);
				Skills.set(EntryKey, EntryInt, t); 
			}
			Skills.set(CString("Total", t), CInt(total))
			return Skills; //Returns Void
		}
		
		public String getName() {
			return "mcmmo_get"; //Name of function
		}
		
		public Integer[] numArgs() {
			return new Integer[]{1}; //Arguments in function
		}
		
		public String docs() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
}
