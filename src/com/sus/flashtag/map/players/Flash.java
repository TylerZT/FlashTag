package me.perryplaysmc.ft.map.players;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@SuppressWarnings("all")
public class Flash {

	private Player base;

	public Flash(Player base) {
		this.base = base;
	}




	public boolean isReverse() {
		return base.getInventory().getChestplate().getType().getId() == 10;
	}


	public Player getBase() {
		return base;
	}


	public String getName() {
		// TODO Auto-generated method stub
		return base.getName();
	}
	
	

}
