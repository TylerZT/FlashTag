package me.perryplaysmc.ft.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import me.perryplaysmc.ft.FlashTag;
import me.perryplaysmc.ft.Arena.Arena;
import me.perryplaysmc.ft.Arena.GameState;

public class PlayerDamage implements Listener {

	Player p, t;
	FlashTag mini = FlashTag.getPlugin(FlashTag.class);
	@EventHandler
	void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			p = (Player) e.getEntity();
			Arena a = mini.getArenaManager().getArenaByPlayer(p.getName());
			if(a!=null) {
				if(a.getState() == GameState.Lobby) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	
	@EventHandler
	void onDamage(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			p = (Player) e.getDamager();
			t = (Player) e.getEntity();
			Arena a = mini.getArenaManager().getArenaByPlayer(p.getName());
			if(a!=null) {
				if(a.getInGame().contains(p.getName()) && !a.getInGame().contains(t.getName())) {
					if(a.getState() == GameState.Lobby) {
						e.setCancelled(true);
					}
				}
				if(a.getState() == GameState.Lobby) {
					e.setCancelled(true);
				}
			}
		}
	}
	
}
