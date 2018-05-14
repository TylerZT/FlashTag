package me.perryplaysmc.ft.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.perryplaysmc.ft.FlashTag;
import me.perryplaysmc.ft.Arena.Arena;
import mkremins.fanciful.FancyMessage;
@SuppressWarnings("all")
public class ArenaCommand implements CommandExecutor{
	String an;
	Player p;
	FlashTag mini = FlashTag.getPlugin(FlashTag.class);
	String prefix = "&4&lFlash&e&lTag&r&c: ";
	@Override
	public boolean onCommand(CommandSender s, Command c56, String cl, String[] args) {
		FileConfiguration cfg = mini.cfg.getConfig();
		if(cl.equalsIgnoreCase("flashtag")) {
			s.sendMessage(Material.getMaterial(4345).name());
			if(args.length == 2) {
				an = args[1];
				if(args[0].equalsIgnoreCase("create")) {
					if(mini.getArenaManager().getArenaByName(an) !=null) {
						s.sendMessage(mini.translate("&6&lFlashTag: &cThis arena already exists"));
					}else {
						mini.cfg.getConfig().set("FlashTag-Maps." + an,"");
						cfg.set("FlashTag-Maps." + an + ".maxPlayers", 4);
						cfg.set("FlashTag-Maps." + an + ".minPlayers", 2);
						cfg.set("FlashTag-Maps." + an + ".Spawns", new ArrayList<String>());
						cfg.set("FlashTag-Maps." + an + ".Sign", "NONEXISTANT");
						cfg.set("FlashTag-Maps." + an + ".LobbyTime", 60);
						cfg.set("FlashTag-Maps." + an + ".GameTime", 120);
						cfg.set("FlashTag-Maps." + an + ".EndTime", 30);
						cfg.set("FlashTag-Maps." + an + ".WaitTime", 10);

						mini.cfg.saveConfig();
						new Arena(an, cfg.getInt("FlashTag-Maps." + an + ".maxPlayers"),
								cfg.getInt("FlashTag-Maps." + an + ".minPlayers"), null);
						s.sendMessage(mini.translate(prefix + "&cSuccessfully created Arena: &e&l" + an));

					} 
				}


				else if(args[0].equalsIgnoreCase("setLobbySpawn")){
					if(!(s instanceof Player)) {
						s.sendMessage(mini.translate(prefix + "&cOnly players may use /flashtag setLobbySpawn <ArenaName>"));
						return true;
					}
					p = (Player) s;
					Arena a = mini.getArenaManager().getArenaByName(an);
					if(a!=null) {
						cfg.set("FlashTag-Maps." + an + ".LobbySpawn", mini.sLoc(p.getLocation()));
						mini.cfg.saveConfig();
						a.setLobbySpawn(p.getLocation());
						s.sendMessage(mini.translate(prefix + "&cSuccessfully set &e&l" + an + "'s &cLobby Spawn!"));
					}

				}

				else if(args[0].equalsIgnoreCase("addSpawn")){
					if(!(s instanceof Player)) {
						s.sendMessage(mini.translate(prefix + "&cOnly players may use /flashtag addSpawn <ArenaName>"));
						return true;
					}
					p = (Player) s;
					Arena a = mini.getArenaManager().getArenaByName(an);
					if(a!=null) {
						if(cfg.getStringList("FlashTag-Maps." + an + ".Spawns").size() == a.getMaxPlayers()) {
							s.sendMessage(mini.translate(prefix + "&cYou already have enough spawns!"));
							return true;
						}
						a.addGameSpawn(p);
						List<String> cspawns = cfg.getStringList("FlashTag-Maps." + an + ".Spawns");
						cspawns.add(mini.sLoc(p.getLocation()));
						cfg.set("FlashTag-Maps." + an + ".Spawns", cspawns);
						mini.cfg.saveConfig();
						s.sendMessage(mini.translate(prefix + "&cSuccessfully added a spawn to "
								+ "&e&l" + an + " &c&7&l#&e&l" + cfg.getStringList("FlashTag-Maps." + an + ".Spawns").size()));

					}
				}
			}else if(args.length == 1 && !args[0].equalsIgnoreCase("leave")) {
				getHelp().send(s);
			}else if(args.length==0){
				getHelp().send(s);
			}if(args.length == 3) {
				if(args[0].equalsIgnoreCase("setMaxPlayers")){
					String ann = args[2];
					Arena a = mini.getArenaManager().getArenaByName(ann);
					if(a!=null) {
						int maxPlayers;
						try {
							maxPlayers = Integer.parseInt(args[1]);
							cfg.set("FlashTag-Maps." + ann + ".maxPlayers", maxPlayers);
							mini.cfg.saveConfig();
							a.setMaxPlayers(maxPlayers);
							a.updateSign();
							s.sendMessage(mini.translate(prefix + "&cSuccessfully"
									+ " set the maximum players for &e&l" + ann + " &cto &e&l" + maxPlayers));
						}catch (Exception e) {
							s.sendMessage(mini.translate(prefix + "&cInvalid number!"));
						}
					}else {
						s.sendMessage(invalidArena());
					}
				}
				if(args[0].equalsIgnoreCase("setMinPlayers")){
					String ann = args[2];
					Arena a = mini.getArenaManager().getArenaByName(ann);
					if(a!=null) {
						int minPlayers;
						try {
							minPlayers = Integer.parseInt(args[1]);
							cfg.set("FlashTag-Maps." + ann + ".minPlayers", minPlayers);
							mini.cfg.saveConfig();
							a.setMinPlayers(minPlayers);
							a.updateSign();
							s.sendMessage(mini.translate(prefix + "&cSuccessfully"
									+ " set the minimum players for &e&l" + ann + " &cto &e&l" + minPlayers));
						}catch (Exception e) {
							s.sendMessage(mini.translate(prefix + "&cInvalid number!"));
						}
					}else {
						s.sendMessage(invalidArena());
					}
				}
			}else if(args.length == 1) {

				if(args[0].equalsIgnoreCase("leave")){
					if(!(s instanceof Player)) {
						s.sendMessage(mini.translate(prefix + "&cOnly players may use /flashtag leave"));
						return true;
					}
					p = (Player) s;
					Arena a = mini.getArenaManager().getArenaByPlayer(p.getName());
					if(a!=null) {
						a.leave(p);
						a.updateSign();
					}
				}
			}else if(args.length == 1 && !args[0].equalsIgnoreCase("leave")) {
				getHelp().send(s);
			}else if(args.length == 1) {
				getHelp().send(s);
			}
		}
		return true;
	}

	private String invalidArena() {
		// TODO Auto-generated method stub
		return mini.translate(prefix + "&cThat is an invalid arena!");
	}
	String t(String input) {
		String output = input;
		return ChatColor.translateAlternateColorCodes('&', output);
	}
	private FancyMessage getHelp() {
		FancyMessage fm = new FancyMessage();
		fm.text(t("&e&m&l---------&7&l[" + prefix.split(":")[0] + "&7&l]&e&l&m---------"))
			.tooltip(t(prefix + " commands"));	
		fm.then("\n");
		fm.then(t("&d&l/flashtag create <arenaname>"))
			.tooltip(t("&e&lCreate an Arena!"))
			.suggest("/flashtag create <arenaname>");
		fm.then("\n");
		fm.then(t("&d&l/flashtag setlobbyspawn <arenaname>"))
			.tooltip(t("&e&lSet the lobby spawn for an Arena!"))
			.suggest("/flashtag setlobbyspawn <arenaname>");
		fm.then("\n");
		fm.then(t("&d&l/flashtag addspawn <arenaname>"))
			.tooltip(t("&e&lAdd a spawn to and arena!"))
			.suggest("/flashtag addspawn <arenaname>");
		fm.then("\n");
		fm.then(t("&d&l/flashtag setminplayers <number> <name>"))
			.tooltip(t("&e&lSet the minimum players required to start for an Arena!"))
			.suggest("/flashtag setminplayers <number> <name>");
		fm.then("\n");
		fm.then(t("&d&l/flashtag setmaxplayers <number> <arenaname>"))
			.tooltip(t("&e&lSet the maximum players to an Arena!"))
			.suggest("/flashtag setmaxplayers <number> <arenaname>");
		return fm;
	}

}
