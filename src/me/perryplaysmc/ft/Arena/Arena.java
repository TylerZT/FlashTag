package me.perryplaysmc.ft.Arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.perryplaysmc.ft.FlashTag;

public class Arena {
	FlashTag mini = FlashTag.getPlugin(FlashTag.class);
	public Scoreboard b = mini.b;
	public Objective ob = mini.ob;
	private String name;
	private int maxPlayers, minPlayers;
	private Timer timer;
	private GameState state;
	private List<String> inGame;
	private List<Location> gameSpawns;
	private Map<String, Integer> kills;
	private Map<String, Scoreboard> sbs;
	private Map<UUID, ItemStack[]> contents;
	private Location lobbySpawn;
	private Sign sign;
	private	String prefix = "&4&lFlash&e&lTag&r&c: ";


	public Arena(String name, int maxPlayers, int minPlayers, Sign sign) {
		this.name = name;
		this.maxPlayers = maxPlayers;
		this.minPlayers = minPlayers;
		this.state = GameState.Lobby;
		this.timer = new Timer(this);
		this.inGame = new ArrayList<>();
		this.sign = sign;
		this.contents = new HashMap<>();
		this.kills = new HashMap<>();
		this.sbs = new HashMap<>();
		this.gameSpawns = new ArrayList<>();
		FlashTag.getPlugin(FlashTag.class).getArenaManager().addArena(this);
	}

	public void end(Player p) {
		FileConfiguration cfg = FlashTag.getPlugin(FlashTag.class).cfg.getConfig();
		if(getInGame().contains(p.getName())) {
			broadCast("&e&l" + p.getName() + "&c Left the game!");
			p.teleport(FlashTag.getPlugin(FlashTag.class).uLoc(cfg.getString("FlashTag-Maps." + getName() + ".Sign")));
			p.sendMessage("&6&lFlashTag: &cThe game has ended. You have been sent back to the lobby".replace("&", "§"));
			p.setFlying(false);
			p.setAllowFlight(false);
			p.removePotionEffect(PotionEffectType.INVISIBILITY);
			if(contents.containsKey(p.getUniqueId())) {
				p.getInventory().setContents(contents.get(p.getUniqueId()));
			}
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(mini, new Runnable() {

				@Override
				public void run() {
					p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
					getInGame().remove(p.getName());
					updateSign();
				}

			}, 20);

		}else {
			p.sendMessage(prefix + "&cYou are not in that game.".replace("&", "§"));
		}
	}

	public void leave(Player p) {
		FileConfiguration cfg = FlashTag.getPlugin(FlashTag.class).cfg.getConfig();
		if(getInGame().contains(p.getName())) {
			broadCast("&e&l" + p.getName() + "&c Left the game!");
			broadCast("&e&l" + p.getName() + "&c Left the game!");
			p.teleport(FlashTag.getPlugin(FlashTag.class).uLoc(cfg.getString("FlashTag-Maps." + getName() + ".Sign")));
			p.sendMessage("&6&lFlashTag: &cThe game has ended. You have been sent back to the lobby".replace("&", "§"));
			p.setFlying(false);
			p.setAllowFlight(false);
			p.removePotionEffect(PotionEffectType.INVISIBILITY);
			ob.getScoreboard().getTeam("name").removeEntry(t("&d"));

			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(mini, new Runnable() {

				@Override
				public void run() {
					p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
					getInGame().remove(p.getName());
					updateSign();
				}

			}, 20);
			p.sendMessage(prefix + "&cYou have left the game.".replace("&", "§"));
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(mini, new Runnable() {
				@Override
				public void run() {
					getInGame().remove(p.getName());
					updateSign();
					if(getInGame().size()==1&&getState() == GameState.Started || getState() == GameState.Waiting) {
						setState(GameState.Lobby);
						getTimer().setPaused(true);
						for(String a : getInGame()) {
							end(Bukkit.getPlayer(a));
						}
						updateSign();
						return;
					}
				}

			}, 20);
		}else {
			p.sendMessage(prefix + "You are not in that game.".replace("&", "§"));
		}
	}

	public void join(Player p) {
		if(getInGame().size() < maxPlayers) {
			updateSign(); 
			if(!getInGame().contains(p.getName())) {
				updateSign(); 
				if(getState() == GameState.Started) {
					getKills().put(p.getName(), 0);
				}
				if(getState() == GameState.Lobby) {
					getInGame().add(p.getName());
					contents.put(p.getUniqueId(), p.getInventory().getContents());
					p.getInventory().clear();
					Scoreboard(p);
					broadCast("&e&l" + p.getName() + "&c Joined the game! (§6§l"+getInGame().size() + "§e§l/§6§l" + getMaxPlayers()+"&c)");
					//cfg.set("FlashTag-Maps." + an + ".LobbySpawn"
					FileConfiguration cfg = FlashTag.getPlugin(FlashTag.class).cfg.getConfig();
					p.teleport(FlashTag.getPlugin(FlashTag.class).uLoc(cfg.getString("FlashTag-Maps." + getName() + ".LobbySpawn")));
					updateSign();
					if(getInGame().size() == getMinPlayers()) {
						getTimer().setPaused(false);
						updateSign(); 
					}else if(getInGame().size() < getMinPlayers()) {
						getTimer().setPaused(true);
						updateSign(); 
					}


					System.out.print("Added scoreboard to: " + p.getName());  
					updateSign(); 
				}else {
					p.sendMessage(prefix + "That game has already been started!".replace("&", "§"));
				}
			}else {
				p.sendMessage(prefix + "You are already in that game!".replace("&", "§"));
			}
		}
	}
	public void updateSign() {
		if(getSign() !=null) {
			getSign().setLine(0, "§7[§6§l" + getName() + "§7]");
			getSign().setLine(1, "§e§l" + (""+getState().toString().charAt(0)).toUpperCase() 
					+ getState().toString().substring(1).toLowerCase());
			getSign().setLine(2, "§6§l" + getInGame().size() + "§e§l/§6§l" + getMaxPlayers());
			getSign().setLine(3, "§e§l" + getMinPlayers());
			getSign().update(true);
		}
	}
	public void broadCast(String msg) {
		for(String name : inGame) {
			Bukkit.getPlayer(name).sendMessage(ChatColor.translateAlternateColorCodes('&', 
					"§6§lGoldenGalaxyMC: §c" + msg));
		}
	}
	public int getMinPlayers() {
		return minPlayers;
	}
	public int setMinPlayers(int minPlayers) {
		return this.minPlayers = minPlayers;
	}

	public String getName() {
		return name;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}
	public int setMaxPlayers(int maxPlayers) {
		return this.maxPlayers = maxPlayers;
	}

	public Timer getTimer() {
		return timer;
	}

	public List<String> getInGame() {
		return inGame;
	}


	public GameState getState() {
		return state;
	}
	public void setState(GameState state) {
		this.state = state;
	}


	public Sign getSign() {
		return sign;
	}

	public void setLobbySpawn(Location lobbySpawn) {
		this.lobbySpawn = lobbySpawn;
	}

	public Location getLobbySpawn() {
		return lobbySpawn;
	}
	//	public List<Location> getChests(){
	//		return chests;
	//	}
	//	public ChestState getChestState() {
	//		return this.chestState;
	//	}
	//	public void setChestState(ChestState chestState) {
	//		this.chestState = chestState;
	//	}
	//	public void addChest(Location loc) {
	//		List<String> cs = mini.cfg.getConfig().getStringList("FlashTag-Maps." + getName() + ".Chests");
	//		cs.add(mini.sLoc(loc));
	//		mini.cfg.getConfig().set("FlashTag-Maps." + getName() + ".Chests", cs);
	//		
	//		mini.cfg.saveConfig();
	//		chests.add(loc);
	//	}

	public List<Location> getGameSpawns() {
		return gameSpawns;
	}

	public void addGameSpawn(Player loc) {
		gameSpawns.add(loc.getLocation());
	}
	private String t(String input) {
		String output = input;
		return FlashTag.getPlugin(FlashTag.class).translate(output);
	}
	public void Scoreboard(Player p) {
		ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		ob.setDisplayName(t("&6&lMiniGame: &e&l" + getTimer().format()));
		if(b.getTeam("name")==null) {
			Team a = b.registerNewTeam("name");
			a.setPrefix(t("7e&lName: "));
			a.addEntry(t("&d"));
			a.setSuffix(t("&c&l"));
			ob.getScore(t("&d")).setScore(5);
		}
		Team name = b.getTeam("name");
		name.addEntry(t("&d"));
		name.setPrefix(t("&e&lName: "));
		ob.getScore(t("&d")).setScore(5);

		if(b.getTeam("aname")==null) {
			Team aname = b.registerNewTeam("aname");
			aname.setPrefix(t("&r"));
			aname.setSuffix(t("&c&l"+p.getName()));
			aname.addEntry(t("&r"));
			ob.getScore(t("&r")).setScore(4);
		}
		Team aname = b.getTeam("aname");
		aname.setPrefix(t("&r"));
		aname.setSuffix(t("&c&l"+p.getName()));
		aname.addEntry(t("&r"));
		ob.getScore(t("&r")).setScore(4);

		if(b.getTeam("blank")==null) {
			Team b1 = b.registerNewTeam("blank");
			b1.addEntry(t("&f"));
			ob.getScore(t("&f")).setScore(3);
		}
		//
		Team b1 = b.getTeam("blank");
		b1.addEntry(t("&f"));
		ob.getScore(t("&f")).setScore(3);

		if(b.getTeam("kills")==null) {
			Team kills = b.registerNewTeam("kills");
			kills.addEntry(t("&c"));
			kills.setPrefix(t("&e&lKills: "));
			kills.setSuffix(t("&c&l"+this.getKills().get(p.getName())));
			ob.getScore(t("&c")).setScore(2);;
		}
		//
		Team kills = b.getTeam("kills");
		kills.addEntry(t("&c"));
		kills.setPrefix(t("&e&lKills: "));
		kills.setSuffix(t("&c&l"+this.getKills().get(p.getName())));
		ob.getScore(t("&c")).setScore(2);
		if(b.getTeam("state")==null) {
			Team state = b.registerNewTeam("state");
			state.addEntry(t("&e"));
			state.setPrefix(t("&e&lState: "));
			state.setSuffix(t(("&c&l"+getState().toString().charAt(0)).toUpperCase() 
					+ getState().toString().substring(1).toLowerCase()));
			ob.getScore(t("&e")).setScore(1);
		}
		//
		Team state = b.getTeam("state");
		state.addEntry(t("&e"));
		state.setPrefix(t("&e&lState: "));
		state.setSuffix(t(("&c&l"+getState().toString().charAt(0)).toUpperCase() 
				+ getState().toString().substring(1).toLowerCase()));
		ob.getScore(t("&e")).setScore(1);
		p.setScoreboard(b);
		sbs.put(p.getName(), b);
		this.kills.put(p.getName(), 0);
		System.out.print("Added scoreboard to: " + p.getName());
	}
	public void updateScoreBoards() {
		for(String user : sbs.keySet()) {
			Scoreboard b = sbs.get(user);
			b.getObjective(DisplaySlot.SIDEBAR).setDisplayName(t(prefix + getTimer().format()));
		}
	}

	public Map<String, Integer> getKills() {
		return kills;
	}
}
