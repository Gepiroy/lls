package lls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	public static List<Location> spawnlist = new ArrayList<>();
	public static List<Location> spawns = new ArrayList<>();
	public static HashMap<String,Location> plocs = new HashMap<>();
	public static String stage = "start";
	public static int timer=20;
	int secrate = 0;
	public void onEnable(){
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				int livep = 0;
				for(Player p:Bukkit.getOnlinePlayers()){
					p.sendMessage("timer="+timer+", stage="+stage);
					if(!p.getGameMode().equals(GameMode.SPECTATOR)){
						livep++;
					}
				}
				if(livep<=1&&stage.equals("game")){
					for(Player p:Bukkit.getOnlinePlayers()){
						if(!p.getGameMode().equals(GameMode.SPECTATOR)){
							start.won(p);
						}
					}
				}
				secrate++;
				if(secrate==4){
					//TODO secrate mark
					if(stage.equals("won")||Bukkit.getOnlinePlayers().size()>=2&&stage.equals("start")){
						timer--;
						if(stage.equals("won")&&timer<=0){
							start.startGame();
						}
						if(stage.equals("start")){
							for(Player p:Bukkit.getOnlinePlayers()){
								if(timer==3){p.sendTitle(ChatColor.YELLOW+"3", "", 5, 10, 5);}
								if(timer==2){p.sendTitle(ChatColor.GOLD+"2", "", 5, 10, 5);}
								if(timer==1){p.sendTitle(ChatColor.RED+"1", "", 5, 10, 5);}
								if(timer==0){
									p.sendTitle(ChatColor.DARK_RED+"FIGHT!", "", 5, 10, 5);
									stage = "game";
								}
							}
						}
					}
					secrate=0;
				}
				
			}
		},0,5);
		getLogger().info("enabled!");
		Bukkit.getPluginManager().registerEvents(new Handler(), this);
		getCommand("perks").setExecutor(new perks(this));
		for (String b : getConfig().getConfigurationSection("spawns").getKeys(false)) {
            spawnlist.add(new Location(Bukkit.getWorld(getConfig().getString("spawns." + b + ".World")),getConfig().getInt("spawns." + b + ".x"), getConfig().getInt("spawns." + b + ".y"), getConfig().getInt("spawns." + b + ".z")));
		}
		spawns=spawnlist;
		start.startGame();
	}
	public void onDisable(){
		for (int i=0;i<spawnlist.size();i++) {
            getConfig().set("spawns."+i+".World", spawnlist.get(i).getWorld().getName());
            getConfig().set("spawns."+i+".x", spawnlist.get(i).getX());
            getConfig().set("spawns."+i+".y", spawnlist.get(i).getY());
            getConfig().set("spawns."+i+".z", spawnlist.get(i).getZ());
        }
		saveConfig();
	}
}
