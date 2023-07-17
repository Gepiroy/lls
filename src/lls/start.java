package lls;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class start {
	public static void won(Player p){
		for(Player pl:Bukkit.getOnlinePlayers()){
			pl.sendTitle(p.getName(), "Истинный санта!", 10, 20, 20);
			Main.stage = "won";
			Main.timer = 3;
		}
	}
	public static void startGame(){
		Main.stage = "start";
		Main.timer=20;
		Main.spawns=Main.spawnlist;
		for(Player p:Bukkit.getOnlinePlayers()){
			p.setGameMode(GameMode.ADVENTURE);
			p.sendMessage("spawns="+Main.spawns);
			p.sendMessage("spawnlist="+Main.spawnlist);
			int i = new Random().nextInt(Main.spawns.size());
			Main.plocs.put(p.getName(), Main.spawns.get(i));
			List<Location> locs = new ArrayList<>();
			Main.spawns.remove(i);
			for(Location loc:Main.spawns){
				locs.add(loc);
			}
			Main.spawns=locs;
		}
	}
	public static void pStart(Player p){
		p.setGameMode(GameMode.ADVENTURE);
		int i = new Random().nextInt(Main.spawns.size());
		Main.plocs.put(p.getName(), Main.spawns.get(i));
		List<Location> locs = new ArrayList<>();
		Main.spawns.remove(i);
		for(Location loc:Main.spawns){
			locs.add(loc);
		}
		Main.spawns=locs;
	}
}
