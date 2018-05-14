package me.perryplaysmc.ft.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.perryplaysmc.ft.FlashTag;
import me.perryplaysmc.ft.Arena.Arena;
import me.perryplaysmc.ft.Arena.GameState;

@SuppressWarnings("all")
public class onMove implements Listener {
	FlashTag mini = FlashTag.getPlugin(FlashTag.class);
	@EventHandler
	void onPlayerMove(PlayerMoveEvent e) {
		int fx = (int) e.getFrom().getX();
		int fz = (int) e.getFrom().getZ();
		int tx = (int) e.getTo().getX();
		int tz = (int) e.getTo().getZ();
		Arena a = mini.getArenaManager().getArenaByPlayer(e.getPlayer().getName());
		if(a!=null) {
			if(a.getState() == GameState.Waiting) {
//				if(tx != fx || tz != fz) {
//					e.getPlayer().teleport(e.getFrom());
//				}
			}
		}
	}

}
