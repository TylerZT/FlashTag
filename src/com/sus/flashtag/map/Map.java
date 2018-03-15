package me.perryplaysmc.ft.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import me.perryplaysmc.ft.FlashTag;
import me.perryplaysmc.ft.map.players.Flash;
import me.perryplaysmc.ft.map.players.ReverseFlash;
import me.perryplaysmc.ft.utilities.Config;

@SuppressWarnings("all")
public class Map {

	private String name;
	private int maxPlayers, minPlayers, maxTeamSize;
	private List<Flash> flashes;
	private List<ReverseFlash> revFlashes;
	private List<Player> players;
	private Sign s;
	private FlashTag ft = FlashTag.getInstance();
	private Config cfg;
	private Timer t;
	private UUID r;
	private States state;

	public Map(String name, Sign s, int maxPlayers, int minPlayers, int maxTeamSize) {
		if(name == null || name == "null" || name == "") {
			UUID r = UUID.randomUUID();
			name = ""+r;
		}
		this.name = name;
		this.s = s;
		this.maxPlayers = maxPlayers;
		this.minPlayers = minPlayers;
		this.maxTeamSize = maxTeamSize;
		this.flashes = new ArrayList<>();
		this.revFlashes = new ArrayList<>();
		this.players = new ArrayList<>();
		this.r = UUID.randomUUID();
		this.cfg = new Config(ft, ft.name(), "maps", name + ".yml");
		if(cfg != null) {
			cfg.set("name", name);
			cfg.set("uuid", r.toString());
			cfg.set("maxPlayers", maxPlayers);
			cfg.set("minPlayers", minPlayers);
			cfg.set("maxTeamSize", maxTeamSize);
			cfg.set("Times.LobbyTime", 10);
			cfg.set("Times.WaitTime", 5);
			cfg.set("Times.GameTime", 60*5);
			cfg.set("Times.EndTime", 15);
			cfg.set("Sign", "");
		}
		this.state = States.LOBBY;

		this.t = new Timer(this, cfg.getInt("LobbyTime"), cfg.getInt("WaitTime"), cfg.getInt("GameTime"), cfg.getInt("EndTime"));
		MapManager.addMap(this);
	}


	public void broadCast(String message) {
		for(Player p : getPlayers()) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
		}
	}
	public void sendChat(String message) {
		for(Player p : getPlayers()) {
			if(p.hasPermission("essentials.chat.color")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
			}
			p.sendMessage(message);
		}
	}

	public States getState() {
		return state;
	}

	public Config getConfig() {
		return cfg;
	}

	public Sign getSign() {
		return s;
	}


	public void addPlayer(Player p) {

	}

	public List<Flash> getFlashes() {
		return flashes;
	}

	public List<ReverseFlash> getReverseFlashes() {
		return revFlashes;
	}

	public String getName() {
		return name;
	}


	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}


	public int getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}


	public int getMaxTeamSize() {
		return maxTeamSize;
	}

	public void setMaxTeamSize(int maxTeamSize) {
		this.maxTeamSize = maxTeamSize;
	}



	public void setScoreboard(Player p) {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj;
		if(sb.getObjective("FT") == null) {
			obj = sb.registerNewObjective("FT", "dummy");
		}else {
			obj = sb.getObjective("FT");
		}
		obj.setDisplayName("§e§lFlash§4§lTag:§a§l " + t.format());
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score s1 = obj.getScore("Reverse Flash: ");
		s1.setScore(revFlashes.size());
		for(int i = revFlashes.size()+1; i < revFlashes.size()+1*2; i++) {
			Score s = obj.getScore("§4"+revFlashes.get(i).getName());
			s.setScore(i);
		}
		Score s2 = obj.getScore("Flash: ");
		s2.setScore(flashes.size());
		for(int i = flashes.size()+1; i < flashes.size()+1*2; i++) {
			Score s = obj.getScore("§e"+flashes.get(i).getName());
			s.setScore(i);
		}
		p.setScoreboard(sb);
	}

	public List<Player> getPlayers() {
		return players;
	}




}