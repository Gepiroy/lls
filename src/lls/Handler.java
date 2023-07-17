package lls;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class Handler implements Listener{
	public static ArrayList<String> perks = new ArrayList<String>();
	public static ArrayList<Integer> kontr = new ArrayList<Integer>();
	public static ArrayList<Integer> otraz = new ArrayList<Integer>();
	public static ArrayList<Integer> regen = new ArrayList<Integer>();
	@EventHandler
	public void join(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if(Main.stage.equals("start"))start.pStart(p);
	}
	@EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        p.teleport(new Location(p.getWorld(), p.getLocation().getX(), 10, p.getLocation().getZ()));
        EntityDamageEvent damevent = p.getLastDamageCause();
        DamageCause cause = damevent.getCause();
        if (cause == DamageCause.ENTITY_ATTACK){
            LivingEntity killer = p.getKiller();
            if(killer instanceof Player){
            	killer.sendMessage(ChatColor.GREEN + "Вы убили санту " + ChatColor.DARK_RED + p.getName() + ChatColor.GREEN + ". " + ChatColor.GOLD + "+1 гепчик!");
            	((Player) killer).playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
        }
        EntityDamageEvent ldc = p.getLastDamageCause();
        if(ldc instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) ldc;
            if(ev.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) ev.getDamager();
                if(arrow.getShooter() instanceof Player) {
                    Player shooter = (Player) arrow.getShooter();
                    shooter.sendMessage(ChatColor.GREEN + "Вы застрелили санту " + ChatColor.DARK_RED + p.getName() + ChatColor.GREEN + ". " + ChatColor.GOLD + "+1 гепчик!");
                }
            }
        }
        p.setGameMode(GameMode.SPECTATOR);
        p.setHealth(20);
        if(p.getLastDamageCause().equals(DamageCause.VOID))
        p.teleport(new Location(Bukkit.getWorld("world"),10,7,-56));
    }
	@EventHandler
    public void onPlayerHurt(EntityDamageByEntityEvent e){
		if(Main.stage.equals("start")){
			e.setCancelled(true);
		}
    }
	@EventHandler
	public void select(InventoryClickEvent e){
		if(e.getClickedInventory() != null) {
			if(e.getCurrentItem().getItemMeta()!=null){
				Player p = (Player) e.getWhoClicked();
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.AQUA + "Перки")){
					e.setCancelled(true);
				}
				else{return;}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Контратака") && e.getCurrentItem().getType() == Material.CACTUS){
					for(int i=0;i<perks.size();i++){
						if(perks.get(i).equalsIgnoreCase(p.getName())){
							if(kontr.get(i)>=10){
								p.sendMessage(ChatColor.YELLOW+"Вы максимально улучшили этот перк. "+ChatColor.GREEN+"На сайте САЙТАПОКАНЕТУ вы сможете расширить это ограничение, купив донат.");
								return;
							}
							kontr.set(i,kontr.get(i)+1);
						}
					}
					p.closeInventory();
				}
				else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "Уворот") && e.getCurrentItem().getType() == Material.ARROW){
					for(int i=0;i<perks.size();i++){
						if(perks.get(i).equalsIgnoreCase(p.getName())){
							if(otraz.get(i)>=15){
								p.sendMessage(ChatColor.YELLOW+"Вы максимально улучшили этот перк. "+ChatColor.GREEN+"На сайте САЙТАПОКАНЕТУ вы сможете расширить это ограничение, купив донат.");
								return;
							}
							otraz.set(i,otraz.get(i)+1);
						}
					}
					p.closeInventory();
				}
				else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Регенерация") && e.getCurrentItem().getType() == Material.GHAST_TEAR){
					for(int i=0;i<perks.size();i++){
						if(perks.get(i).equalsIgnoreCase(p.getName())){
							if(regen.get(i)>=10){
								p.sendMessage(ChatColor.YELLOW+"Вы максимально улучшили этот перк. "+ChatColor.GREEN+"На сайте САЙТАПОКАНЕТУ вы сможете расширить это ограничение, купив донат.");
								return;
							}
							regen.set(i,regen.get(i)+1);
						}
					}
					p.closeInventory();
				}
			}
		}
	}
	@EventHandler
	public void breakb(BlockBreakEvent e){
		Player p = e.getPlayer();
		Block b = e.getBlock();
		if(p.getGameMode().equals(GameMode.CREATIVE) && p.getInventory().getItemInMainHand().getType().equals(Material.FLINT) && b.getType().equals(Material.GLASS)){
			Main.spawnlist.add(b.getLocation());
			p.sendMessage(ChatColor.YELLOW+"Теперь игра рассчитана на "+ChatColor.GREEN+Main.spawnlist.size()+ChatColor.YELLOW+" сант.");
		}
	}
	@EventHandler
	public void walk(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(Main.stage.equals("start")&&Main.plocs.containsKey(p.getName())){
			Location loc = Main.plocs.get(p.getName());
			p.teleport(new Location(p.getWorld(),loc.getX()+0.5,loc.getY(),loc.getZ()+0.5,p.getLocation().getYaw(),p.getLocation().getPitch()), TeleportCause.PLUGIN);
		}
	}
}