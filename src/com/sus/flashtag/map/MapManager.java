package me.perryplaysmc.ft.map;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

public class MapManager {
	
	private static Set<Map> maps;
	
	public static void addMap(Map map) {
		if(maps == null) {
			maps = new HashSet<>();
		}
		maps.add(map);
	}
	
	public static Set<Map> getMaps() {
		if(maps == null) {
			maps = new HashSet<>();
		}
		
		return maps;
		
	}
	

	public static Map getMap(String name) {
		for(Map map : maps) {
			if(name.equalsIgnoreCase(map.getName())) return map;
		}
		return null;
	}
	
	public static Map getMap(Player p) {
		for(Map map : maps) {
			if(map.getPlayers().contains(p)) return map;
		}
		return null;
	}
	
	
}
