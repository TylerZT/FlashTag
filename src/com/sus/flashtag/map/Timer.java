package me.perryplaysmc.ft.map;

import org.bukkit.scheduler.BukkitRunnable;

import me.perryplaysmc.ft.FlashTag;

@SuppressWarnings("all")
public class Timer extends BukkitRunnable {

	
	private FlashTag ft = FlashTag.getInstance();
	private int lobby, wait, game, end;
	private Map m;
	
	public Timer(Map m, int lobby, int wait, int game, int end) {
		this.m = m;
		this.lobby = lobby;
		this.wait = wait;
		this.game = game;
		this.end = end;
		this.runTaskTimer(ft, 0, 20);
	}
	
	
	
	@Override
	public void run() {
		
	}
	public int getHours() {
		switch(m.getState()) {
		case LOBBY:
			return lobby / 60*60;
		case STARTING:
			return wait / 60*60;
		case INGAME:
			return game / 60*60;
		case END:
			return end / 60*60;
		default: return 0;
		}
	}

	public int getMins() {
		switch(m.getState()) {
		case LOBBY:
			return lobby / 60;
		case STARTING:
			return wait / 60;
		case INGAME:
			return game / 60;
		case END:
			return end / 60;
		default: return 0;
		}
	}
	public int getSecs() {
		switch(m.getState()) {
		case LOBBY:
			return lobby;
		case STARTING:
			return wait;
		case INGAME:
			return game;
		case END:
			return end;
		default: return 0;
		}
	}


	public static String getFormatedTime(int seconds) {
		int minutes = seconds / 60;
		int hours = minutes / 60;
		int days = hours / 24;

		seconds -= minutes * 60;
		minutes -= hours * 60;
		hours -= days * 24;

		int m = minutes, h = hours, d = days;

		StringBuilder sb = new StringBuilder();
		if(days > 0)
		{ 
			sb.append(days + ":");
			sb.append(hours + ":"); 
			sb.append(minutes + ":"); 
			sb.append(seconds); 
			return sb.toString(); 
		} 
		else if(seconds > 0 && m == 0 && h == 0 && d == 0)
		{ 
			sb.append(seconds); 
			return sb.toString();
		}
		else if(minutes > 0 && h == 0 && d == 0)
		{ 
			sb.append(minutes + ":"); 
			sb.append(seconds); 
			return sb.toString();
		} 
		else if(hours > 0 && d == 0)
		{	
			sb.append(hours + ":"); 
			sb.append(minutes + ":"); 
			sb.append(seconds); 
			return sb.toString();
		} 

		return sb.toString();
	}

	public String format() {
		int i = getSecs();
		if(i > -1) {
			return getFormatedTime(i);
		}else {
			return "";
		}
	}
}
