package me.perryplaysmc.ft.map.players;

import org.bukkit.entity.Player;

@SuppressWarnings("all")
public class ReverseFlash {

	private Player base;

	public ReverseFlash(Player base) {
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
