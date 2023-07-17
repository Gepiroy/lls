package lls;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import utils.ItemUtil;


public class GUI implements Listener {
	static ArrayList<String> perks = Handler.perks;
	static ArrayList<Integer> kontr = Handler.kontr;
	static ArrayList<Integer> otraz = Handler.otraz;
	static ArrayList<Integer> regen = Handler.regen;
	static int perks(Player p){
		for(int i=0; i<perks.size();i++){
			if(perks.get(i).equalsIgnoreCase(p.getName())){
				return i;
			}
		}
		perks.add(p.getName());
		kontr.add(0);
		otraz.add(0);
		regen.add(0);
		return perks.size();
	}
	static int pricekontr(int i){
		if(i==0){return 25;}
		if(i==1){return 50;}
		if(i==2){return 100;}
		if(i==3){return 200;}
		if(i==4){return 500;}
		if(i==5){return 1000;}
		if(i==6){return 1500;}
		if(i==7){return 2000;}
		if(i==8){return 2500;}
		if(i==9){return 3000;}
		return -1;
	}
	static int priceotraz(int i){
		if(i==0){return 10;}
		if(i==1){return 25;}
		if(i==2){return 50;}
		if(i==3){return 75;}
		if(i==4){return 100;}
		if(i==5){return 150;}
		if(i==6){return 225;}
		if(i==7){return 350;}
		if(i==8){return 475;}
		if(i==9){return 600;}
		if(i==10){return 775;}
		if(i==11){return 1000;}
		if(i==12){return 1250;}
		if(i==13){return 1500;}
		if(i==14){return 2000;}
		return -1;
	}
	static int priceregen(int i){
		if(i==0){return 15;}
		if(i==1){return 30;}
		if(i==2){return 75;}
		if(i==3){return 115;}
		if(i==4){return 150;}
		if(i==5){return 200;}
		if(i==6){return 275;}
		if(i==7){return 425;}
		if(i==8){return 550;}
		if(i==9){return 700;}
		return -1;
	}
	public static void OpenGUI(Player p){
		int i=perks(p);
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.AQUA + "Перки");
		inv.setItem(0,ItemUtil.create(Material.CACTUS, 1, (byte)0, ChatColor.YELLOW + "Контратака",ChatColor.DARK_AQUA+"Шанс "+kontr.get(i)+"%-->"+(kontr.get(i)+1)+"% отразить атаку.",ChatColor.GOLD+"Цена: "+pricekontr(kontr.get(i))+" Гепчиков.",""));
		inv.setItem(4,ItemUtil.create(Material.ARROW, 1, (byte)0, ChatColor.AQUA + "Уворот",ChatColor.DARK_AQUA+"Шанс "+otraz.get(i)+"%-->"+(otraz.get(i)+1)+"% увернуться от стрелы.",ChatColor.GOLD+"Цена: "+priceotraz(otraz.get(i))+"Гепчиков",""));
		inv.setItem(8,ItemUtil.create(Material.GHAST_TEAR, 1, (byte)0, ChatColor.LIGHT_PURPLE + "Регенерация",ChatColor.DARK_AQUA+"Шанс "+regen.get(i)+"%-->"+(regen.get(i)+1)+"% получить регенерацию после получения урона.",ChatColor.GOLD+"Цена: "+priceregen(regen.get(i))+"Гепчиков",""));
		p.openInventory(inv);
	}
}
