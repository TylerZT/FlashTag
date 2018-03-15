package me.perryplaysmc.ft.map;

public enum States {
	LOBBY("§a§lLobby"), STARTING("§c§lStarting"), INGAME("§e§lIn§4§lGame"), END("§c§lEnd");
	
	private String name;
	
	States(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
