package me.perryplaysmc.ft.listeners;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.perryplaysmc.ft.map.Map;
import me.perryplaysmc.ft.map.MapManager;
import me.perryplaysmc.ft.map.players.Flash;
import me.perryplaysmc.ft.map.players.ReverseFlash;

@SuppressWarnings("all")
public class ChatEvents implements Listener{



	private Map m;

	@EventHandler
	void onChat(AsyncPlayerChatEvent e) {
		for(String a : e.getMessage().split(" ")) {
			Player p = Bukkit.getPlayer(a);
			if(p != null) {
				if(MapManager.getMap(p) != null && MapManager.getMap(e.getPlayer()) == null) {
					e.getPlayer().sendMessage("§cThat player is in a game of §e§lFlash§4§lTag");
				}
			}
		}
		if(MapManager.getMap(e.getPlayer()) == null) return;
		m = MapManager.getMap(e.getPlayer());
		Flash f = null;
		ReverseFlash rf;
		for(Flash fl : m.getFlashes()) {
			if(fl.getName() == e.getPlayer().getName()) f = fl;
		}
		for(ReverseFlash fl : m.getReverseFlashes()) {
			if(fl.getName() == e.getPlayer().getName()) rf = fl;
		}
		String flashType = (f == null ? "§4§lReverseFlash" : "§e§lFlash");
		m.sendChat("§l[" + flashType + "§l]" + "§e§l" + e.getPlayer().getName() + "§8:§r " + e.getMessage());
	}



















}
