package me.perryplaysmc.ft.Listeners;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.perryplaysmc.ft.FlashTag;
import me.perryplaysmc.ft.Arena.Arena;

/**
 * Created by Family on 8/16/17.
 */
public class PlayerInteract implements Listener {

	protected FlashTag mini;
	public PlayerInteract(FlashTag mini) {
		this.mini = mini;
	}
	@EventHandler
	void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
				FileConfiguration cfg = mini.cfg.getConfig();
				for(String arena : cfg.getConfigurationSection("Arenas").getKeys(false)) {
					if(mini.cfg.getConfig().getString("Arenas." + arena + ".Sign").contains(mini.sLoc(e.getClickedBlock().getLocation()))) {
						Arena a = mini.getArenaManager().getArenaByName(arena);
						if(a!=null) {
							a.updateSign();
							a.join(e.getPlayer());
							a.updateSign();
							a.updateSign();
						}}}}}}}
