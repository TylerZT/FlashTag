package me.perryplaysmc.ft.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.perryplaysmc.ft.FlashTag;
import me.perryplaysmc.ft.Arena.Arena;

public class OnDeath implements Listener{


	@EventHandler
	void onRespawn(PlayerRespawnEvent e) {
		p = e.getPlayer();

		k = p.getKiller();
		Arena a = mini.getArenaManager().getArenaByPlayer(p.getName());
		if(a!=null) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(mini, new Runnable() {

				@Override
				public void run() {
					
					p.setGameMode(GameMode.SURVIVAL);
					p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 0, true));
					p.setAllowFlight(true);
					p.setFlying(true);
					p.spigot().setCollidesWithEntities(false);
//					p.teleport(new Location(p.getLocation().getWorld(),
//							p.getLocation().getX(),p.getLocation().getY()+2,p.getLocation().getZ()
//							,p.getLocation().getPitch(),p.getLocation().getYaw()));
					if(p.getKiller() !=null) {
						p.teleport(p.getKiller().getLocation());
					}
				}

			}, 20);
		
		}
	}

	FlashTag mini = FlashTag.getPlugin(FlashTag.class);
	Player p, k;
	int aaa = 10;
	@EventHandler
	void onDeath(PlayerDeathEvent e) {
		p = e.getEntity();
		k = p.getKiller();
		Arena a = mini.getArenaManager().getArenaByPlayer(p.getName());
		if(a!=null) {
		
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(mini, new Runnable() {

				@Override
				public void run() {
					if(aaa == 0) {
						aaa=10;
						return;
					}
					aaa--;
				}

			}, 0, 10);
			e.setDeathMessage("");
			if(k!=null) {
				p.spigot().respawn();
				if(!a.getKills().containsKey(k.getName())) {
					a.getKills().put(k.getName(), 0);
				}
				a.getKills().put(k.getName(), a.getKills().get(k.getName())+1);
				if(aaa>=1 && aaa < 3) {
					a.broadCast("&e&l" + k.getName() + " &cKilled: &e&l" + p.getName() + "&c.");
				}
				else if(aaa>=3 && aaa < 5) {
					a.broadCast("&e&l" + p.getName() + " &cWas slain by: &e&l" + k.getName() + "&c.");
				}
				else if(aaa>=5 && aaa < 7) {
					a.broadCast("&e&l" + k.getName() + " &cMurdered: &e&l" + p.getName() + "&c.");
				}
				else if(aaa>=7 && aaa < 9) {
					a.broadCast("&e&l" + k.getName() + " &cButchered: &e&l" + p.getName() + "&c.");
				}
			}else {
				p.spigot().respawn();
				a.broadCast("&e&l" + p.getName() + "&c has died.");
			}
		}
	}
}
