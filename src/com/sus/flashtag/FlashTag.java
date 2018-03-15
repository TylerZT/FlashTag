package me.perryplaysmc.ft;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class FlashTag extends JavaPlugin {

	private static FlashTag instance;

	@Override
	public void onEnable() {
		instance = this;


	}

	public String name() {
		return "FlashTag";
	}


	public void registerEvents(Listener... lists) {
		Arrays.stream(lists).forEach(l -> Bukkit.getPluginManager().registerEvents(l, this));
	}


	public static FlashTag getInstance() {
		return instance;
	}


}
