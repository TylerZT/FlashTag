package me.perryplaysmc.ft.utilities;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import net.minecraft.util.org.apache.commons.io.FileUtils;

public class Config {

	private File f, dir;
	private YamlConfiguration cfg;
	private String name;

	public Config(Plugin pl, File dir, String name) {
		if(dir != null && !dir.isDirectory()) {
			dir.mkdirs(); 
			this.dir = dir;
		}
		if(dir == null) {
			dir = pl.getDataFolder();
		}
		this.f = new File(dir, name);
		if(!f.exists()) {
			try {
				if(pl != null) {
					if(pl.getResource(f.getName())  != null) {
						FileUtils.copyInputStreamToFile(pl.getResource(f.getName()), f);
					}else {
						f.createNewFile();
					}
				}else {
					f.createNewFile();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		reload();
	}

	public Config(Plugin pl, String dir, String sub, String name) {
		new Config(pl, new File(dir, sub), name);
	}
	public Config(Plugin pl, String dir, String name) {
		new Config(pl, new File(dir), name);
	}
	public Config(Plugin pl, String name) {
		new Config(pl, pl.getDataFolder(), name);
	}
	public Config(String name) {
		new Config(null, name);
	}


	public String getName() {
		return name;
	}


	public File getDirectory() {
		return dir;
	}


	public File getFile() {
		return f;
	}


	public YamlConfiguration getConfig() {
		return cfg;
	}


	public String getString(String path) {
		return cfg.getString(path);
	}


	public boolean getBoolean(String path) {
		return cfg.getBoolean(path);
	}


	public Object get(String path) {
		return cfg.get(path);
	}


	public ConfigurationSection getSection(String path) {
		return cfg.getConfigurationSection(path);
	}


	public int getInt(String path) {
		return cfg.getInt(path);
	}


	public double getDouble(String path) {
		return cfg.getDouble(path);
	}


	public List<String> getStringList(String path) {
		return cfg.getStringList(path);
	}

	public List<?> getObjectList(String path) {
		return cfg.getList(path);
	}

	public void set(String path, Object obj) {
		cfg.set(path, obj);
		save();
	}


	public void reload() {
		cfg = YamlConfiguration.loadConfiguration(f);
	}


	public void save() {
		try {
			cfg.save(f);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}



}
