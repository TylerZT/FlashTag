package me.perryplaysmc.ft.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import me.perryplaysmc.ft.FlashTag;
import me.perryplaysmc.ft.Arena.Arena;

public class PlayerFood implements Listener {



	FlashTag mini = FlashTag.getPlugin(FlashTag.class);
	@EventHandler
	void a(FoodLevelChangeEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			Arena a = mini.getArenaManager().getArenaByPlayer(p.getName());
			if(a!=null) {
				e.setCancelled(true);
			}
		}
	}

}
