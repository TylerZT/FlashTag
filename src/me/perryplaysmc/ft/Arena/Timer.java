package me.perryplaysmc.ft.Arena;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.perryplaysmc.ft.FlashTag;

/**
 * Created by Family on 8/16/17.
 */
public class Timer extends BukkitRunnable {
	Arena arena;
	Arena a;
	FlashTag mini = FlashTag.getPlugin(FlashTag.class);
	int lobbyTime, gameTime, endTime, waitTime, refillTime;
	boolean paused;

	public Timer(Arena arena) {
		this.arena = arena;
		a = arena;
		lobbyTime = mini.cfg.getConfig().getInt("FlashTag-Maps." + arena.getName() + ".LobbyTime");
		gameTime = mini.cfg.getConfig().getInt("FlashTag-Maps." + arena.getName() + ".GameTime");
		endTime = mini.cfg.getConfig().getInt("FlashTag-Maps." + arena.getName() + ".EndTime");
		refillTime = mini.cfg.getConfig().getInt("FlashTag-Maps." + arena.getName() + ".RefillTime");
		waitTime = mini.cfg.getConfig().getInt("FlashTag-Maps." + arena.getName() + ".WaitTime");
		paused = true;
		this.runTaskTimer(mini, 0, 20);
	}
	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	@Override
	public void run() {
		if(!paused) {
			if(arena.getInGame().size() < arena.getMinPlayers()) {
				setPaused(true);
				lobbyTime = mini.cfg.getConfig().getInt("FlashTag-Maps." + arena.getName() + ".LobbyTime") + 1;
				gameTime = mini.cfg.getConfig().getInt("FlashTag-Maps." + arena.getName() + ".GameTime") + 1;
				endTime = mini.cfg.getConfig().getInt("FlashTag-Maps." + arena.getName() + ".EndTime") + 1;
				waitTime = mini.cfg.getConfig().getInt("FlashTag-Maps." + arena.getName() + ".WaitTime") + 1;
				arena.updateSign(); 
			}else {
				setPaused(false);
			}
			arena.updateScoreBoards();
			switch(arena.getState()) {
			case Lobby:
				if(lobbyTime == 11){
					arena.broadCast("10 seconds until the game begins!");
				}
				if(lobbyTime == 6){
					arena.broadCast("5 seconds until the game begins!");
				}
				if(lobbyTime == 5){
					arena.broadCast("4 seconds until the game begins!");
				}
				if(lobbyTime == 4){
					arena.broadCast("3 seconds until the game begins!");
				}
				if(lobbyTime == 3){
					arena.broadCast("2 seconds until the game begins!");
				}
				if(lobbyTime == 2){
					arena.broadCast("1 second until the game begins!");
				}
				if(lobbyTime != 0) {
					lobbyTime--;
					a.updateSign();
					arena.updateScoreBoards();
				}else {
					for(int i=0; i < arena.getInGame().size(); i++) {
						Bukkit.getPlayer(arena.getInGame().get(i)).
						teleport(mini.uLoc(mini.cfg.getConfig().getStringList("FlashTag-Maps." + arena.getName() + ".Spawns").get(i)));
					}
					endTime = mini.cfg.getConfig().getInt("FlashTag-Maps." + arena.getName() + ".EndTime");
					arena.broadCast("Game starting!");
					arena.setState(GameState.Waiting);
					arena.updateSign();
					//TODO: Send players to spawns.
				}
				arena.updateScoreBoards();
				break;
			case Waiting:
				if(waitTime == 11){
					arena.broadCast("10 seconds until the game starts!");
				}
				if(waitTime == 6){
					arena.broadCast("5 seconds until the game starts!");
				}
				if(waitTime == 5){
					arena.broadCast("4 seconds until the game starts!");
				}
				if(waitTime == 4){
					arena.broadCast("3 seconds until the game starts!");
				}
				if(waitTime == 3){
					arena.broadCast("2 seconds until the game starts!");
				}
				if(waitTime == 2){
					arena.broadCast("1 second until the game starts!");
				}
				if(waitTime != 0) {
					waitTime--;
					a.updateSign();
					arena.updateScoreBoards();
				}else {
					for(int i=0; i < arena.getInGame().size(); i++) {
						Bukkit.getPlayer(arena.getInGame().get(i)).
						teleport(mini.uLoc(mini.cfg.getConfig().getStringList("FlashTag-Maps." + arena.getName() + ".Spawns").get(i)));
					}
					endTime = mini.cfg.getConfig().getInt("FlashTag-Maps." + arena.getName() + ".EndTime");
					arena.broadCast("Game starting!");
					arena.setState(GameState.Started);
					arena.updateSign();
				}
				arena.updateScoreBoards();
				break;
			case Started:
				if(gameTime != 0) {
					gameTime--;
					a.updateSign();
					arena.updateScoreBoards();
				}else {
					arena.setState(GameState.End);
					arena.updateSign();
					//TODO: END GAME.
				}
				arena.updateScoreBoards();
				break;
			case End:
				if(endTime == 11){
					arena.broadCast("10 seconds until the game ends!");
				}
				if(endTime == 6){
					arena.broadCast("5 seconds until the game ends!");
				}
				if(endTime == 5){
					arena.broadCast("4 seconds until the game ends!");
				}
				if(endTime == 4){
					arena.broadCast("3 seconds until the game ends!");
				}
				if(endTime == 3){
					arena.broadCast("2 seconds until the game ends!");
				}
				if(endTime == 2){
					arena.broadCast("1 second until the game ends!");
				}
				if(endTime != 0) {
					endTime--;
					arena.updateScoreBoards();
				}else {
					arena.setState(GameState.Lobby);
					arena.updateSign();
					setPaused(true);
					for(String a : arena.getInGame()) {
						if(a!=null) {
							arena.end(Bukkit.getPlayer(a));
						}
					}

				}
				lobbyTime = mini.cfg.getConfig().getInt("FlashTag-Maps." + arena.getName() + ".LobbyTime");
				waitTime = mini.cfg.getConfig().getInt("FlashTag-Maps." + arena.getName() + ".WaitTime");
				gameTime = mini.cfg.getConfig().getInt("FlashTag-Maps." + arena.getName() + ".GameTime");
				arena.updateScoreBoards();
				break;

			default:
				break;

			}
		}
	}
	public int getHours() {
		switch(arena.getState()) {
		case Lobby:
			return lobbyTime / 60*60;
		case Waiting:
			return waitTime / 60*60;
		case Started:
			return gameTime / 60*60;
		case End:
			return endTime / 60*60;
		default: return 0;
		}
	}

	public int getMins() {
		switch(arena.getState()) {
		case Lobby:
			return lobbyTime / 60;
		case Waiting:
			return waitTime / 60;
		case Started:
			return gameTime / 60;
		case End:
			return endTime / 60;
		default: return 0;
		}
	}
	public int getSecs() {
		switch(arena.getState()) {
		case Lobby:
			return lobbyTime;
		case Waiting:
			return waitTime;
		case Started:
			return gameTime;
		case End:
			return endTime;
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
		if(gameTime > -1) {
			return getFormatedTime(getSecs());
		}else {
			return "";
		}
	}




}
