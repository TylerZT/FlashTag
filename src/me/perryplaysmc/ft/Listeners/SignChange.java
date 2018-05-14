package me.perryplaysmc.ft.Listeners;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import me.perryplaysmc.ft.FlashTag;
import me.perryplaysmc.ft.Arena.Arena;

public class SignChange implements Listener {

	private FlashTag mini = FlashTag.getPlugin(FlashTag.class);
	
	@EventHandler
	void onSignChange(SignChangeEvent e) {
		if(e.getLine(0).equalsIgnoreCase("[arena]")) {
			Arena arena = mini.getArenaManager().getArenaByName(e.getLine(1));
			if(arena!=null) {
				e.setLine(0, "§7[§6§l" + arena.getName() + "§7]");
				e.setLine(1, "§e§l" + (""+arena.getState().toString().charAt(0)).toUpperCase() 
						+ arena.getState().toString().substring(1).toLowerCase());
				e.setLine(2, "§6§l" + arena.getInGame().size() + "§e§l/§6§l" + arena.getMaxPlayers());
				e.setLine(3, "§e§l" + arena.getMinPlayers());
				mini.cfg.getConfig().set("Arenas." + arena.getName() + ".Sign", mini.sLoc(e.getBlock().getLocation()));
				mini.cfg.saveConfig();
			}else {
				System.out.print("Arena doesn't exist");
			}
		}
	}
}
