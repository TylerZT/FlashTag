package me.perryplaysmc.ft.Arena;

import java.util.HashSet;
import java.util.Set;

public class ArenaManager {

	private Set<Arena> arenas;
	
	
	public ArenaManager() {
		
		arenas = new HashSet<>();
	}
	public void addArena(Arena arena){
		arenas.add(arena);
	}
	public void removeArena(Arena arena){
		arenas.remove(arena);
	}

	public Arena getArenaByName(String arenaname){
		for(Arena arena : arenas){
			if(arena.getName().equalsIgnoreCase(arenaname)){
				return arena;
			}
		}
		return null;
	}

	public Arena getArenaByPlayer(String arenaname){
		for(Arena arena : arenas){
			if(arena.getInGame().contains(arenaname)){
				return arena;
			}
		}
		return null;
	}
	
}
