package me.perryplaysmc.ft.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class GUIUtil {
	
	private static Inventory inv;
	private static GUIUtil g;
	private static int currentSlot;
	
	private void is() {
		g = this;
	}
	
	
	public static GUIUtil get() {
		new GUIUtil().is();
		return g;
	}
	
	
	public static GUIUtil newInventory(String name, int rows) {
		get();
		inv = Bukkit.createInventory(null,  rows*9, ChatColor.translateAlternateColorCodes('&', name));
		return g;
	}
	
	public GUIUtil setItem(int slot, ItemStack i) {
		currentSlot = slot;
		inv.setItem(currentSlot, i);
		return this;
	}
	
	public GUIUtil getItem(int slot) {
		currentSlot = slot;
		return this;
	}
	
	public GUIUtil setItemName(String name) {
		ItemMeta im = inv.getItem(currentSlot).getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		inv.getItem(currentSlot).setItemMeta(im);
		return this;
	}
	
	public ItemStack getItemStack(int slot) {
		return inv.getItem(slot);
	}
	
	public Inventory getInventory() {
		if(inv == null) {
			return null;
		}
		return inv;
	}
	
	
	
	public Inventory create() {
		return inv;
	}
	
	
	
	
	

}
