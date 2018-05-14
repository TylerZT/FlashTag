package me.perryplaysmc.ft;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Charsets;

import net.minecraft.util.org.apache.commons.io.FileUtils;
@SuppressWarnings("all")
public class Config {

	private Plugin plugin;
	private File file, folder;
	private YamlConfiguration config;
	private String name;
	private String foldername;

	public Config(Plugin plugin, File dir, String name) {
		if(plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdirs();
		}
		if(dir == null) {
			dir = plugin.getDataFolder();
		}else {
			if(!dir.isDirectory()) {
				dir.mkdirs();
			}
		}
		this.plugin = plugin;
		this.file = new File(dir, name);
		if(!file.exists()) {
			try {
				if(plugin.getResource(name) != null) {
					FileUtils.copyInputStreamToFile(plugin.getResource(name), file);
				}else {
					file.createNewFile();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		reloadConfig();
	}

	public Config(Plugin plugin, String name, String foldername) {
		this(plugin, new File("plugins/" + plugin.getDataFolder().getName(), foldername), name);
		reloadConfig();
	}
	public YamlConfiguration getConfig() {
		return config;
	}
	public void reloadConfig() {

		config = YamlConfiguration.loadConfiguration(file);
	}
	public void saveDefaultConfig()
	{
		if (!file.exists()) {
			plugin.saveResource(file.getName(), false);
		}
	}
	public void saveConfig() {
		try {
			config.save(file);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


}
