package me.perryplaysmc.ft.Listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_7_R4.Item;

@SuppressWarnings("all")
public class Gui implements Listener {
	
	private Inventory inv;
	private ItemStack suitSelection;
	private ItemStack flash, flashCW, flashCW2;
	
	
	public Gui() {
		inv = Bukkit.createInventory(null, 9*4, "§c§lSuits");
		items(inv, new ItemStack(Material.WOOL, 1, (short)14), "&4&lSuits", 2, Arrays.asList("&eSelect any suit :>"));
		items(inv, new ItemStack(Material.WOOL, 1, (short)4), "&4&l", 2, Arrays.asList("&eBecome the flash"));
	}
	
	
	void items(Inventory inv, ItemStack item, String name, int slot, List<String> lore) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		List<String> l = new ArrayList<>();
		for(String lor : lore) {
			l.add(ChatColor.translateAlternateColorCodes('&', lor));
		}
		im.setLore(l);
		item.setItemMeta(im);
		inv.setItem(slot, item);
	}

	@EventHandler
	void onClick(InventoryClickEvent e) {
		if(!e.getClickedInventory().getName().equalsIgnoreCase(inv.getName())) return;
		SuitClick(e, flash);
	}
	
	void SuitClick(InventoryClickEvent e, ItemStack i) {
		
	}

}
