package me.perryplaysmc.ft;

import java.io.File;
import java.util.Arrays;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import me.perryplaysmc.ft.Arena.Arena;
import me.perryplaysmc.ft.Arena.ArenaManager;
import me.perryplaysmc.ft.Commands.ArenaCommand;
import me.perryplaysmc.ft.Listeners.OnDeath;
import me.perryplaysmc.ft.Listeners.PlayerDamage;
import me.perryplaysmc.ft.Listeners.PlayerFood;
import me.perryplaysmc.ft.Listeners.PlayerInteract;
import me.perryplaysmc.ft.Listeners.SignChange;
import me.perryplaysmc.ft.Listeners.onMove;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_7_R4.LocaleLanguage;

@SuppressWarnings("all")
public class FlashTag extends JavaPlugin {

	public Scoreboard b;
	public Objective ob;
	private ArenaManager arenaManager;
	public Config cfg = new Config(this, getDataFolder(), "maps.yml");
	@Override
	public void onEnable(){
		b = Bukkit.getScoreboardManager().getNewScoreboard();
		ob = b.registerNewObjective("stats", "dummy");
		arenaManager = new ArenaManager();
		registerListeners(new PlayerInteract(this), new SignChange(), new OnDeath(), new PlayerDamage(), new PlayerFood(), new onMove());
		registerCommands();
		loadArenas();
	}

	@Override
	public void onDisable(){
		for(Player p : Bukkit.getOnlinePlayers()) {
			Arena a = getArenaManager().getArenaByPlayer(p.getName());
			if(a!=null) {
				if(a.getInGame().contains(p.getName())) {
					p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
				}
			}
		}
	}


	private void loadArenas() {
		FileConfiguration cf = cfg.getConfig();
		if(cf.getConfigurationSection("Arenas") !=null) {
			for(String arena : cfg.getConfig().getConfigurationSection("Arenas").getKeys(false)) {
				new Arena(arena, cf.getInt("Arenas." + arena + ".maxPlayers")
						, cf.getInt("Arenas." + arena + ".minPlayers"),
						cf.getString("Arenas." + arena + ".Sign").equalsIgnoreCase("NONEXISTANT")
						? null : (Sign)uLoc(cf.getString("Arenas." + arena +".Sign")).getWorld()
								.getBlockAt(uLoc(cf.getString("Arenas." + arena +".Sign"))).getState());
			}
		}
	}

	void registerCommands() {
		getCommand("flashtag").setExecutor(new ArenaCommand());
	}
	public String translate(String input) {
		String output = input;
		return ChatColor.translateAlternateColorCodes('&', output);
	}

	public Location uLoc(String locs) {
		if(locs == "NONEXISTANT") {
			return null;
		}
		String[] split = locs.split("/");
		Location loc = new Location(getServer().getWorld(split[0]),
				Double.parseDouble(split[1]),
				Double.parseDouble(split[2]),
				Double.parseDouble(split[3]));
		loc.setPitch(Float.parseFloat(split[4]));
		loc.setYaw(Float.parseFloat(split[5]));
		return loc;
	}

	public String sLoc(Location loc) {
		return loc.getWorld().getName() + "/" + loc.getX() + "/" + loc.getY()
		+ "/" + loc.getZ() + "/" + loc.getPitch() + "/" + loc.getYaw();
	}


	void registerListeners(Listener... listeners){
		Arrays.stream(listeners).forEach(l -> getServer().getPluginManager().registerEvents(l, this));
	}
	public ArenaManager getArenaManager(){
		return arenaManager;
	}

}
