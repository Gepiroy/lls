package utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {
	public static ItemStack create(Material material, int amount, byte data, String DisplayName, String lore1, String lore2, String lore3, String lore4) {
		ItemStack item = new ItemStack(material, amount, data);
		ItemMeta meta = item.getItemMeta();
		
		if(DisplayName != null){
			meta.setDisplayName(DisplayName);
		}
		ArrayList<String> lore = new ArrayList<String>();
		if(lore1 != null){
			lore.add(lore1);
		}
		if(lore1 != null){
			lore.add(lore2);
		}
		if(lore1 != null){
			lore.add(lore3);
		}
		if(lore1 != null){
			lore.add(lore4);
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public static ItemStack create(Material material, String DisplayName){
		return create(material, 1, (byte)0, DisplayName, null, null, null, null);
	}
	public static ItemStack create(Material material, int amount, byte data, String DisplayName, String lore1, String lore2, String lore3){
		return create(material, amount, data, DisplayName, lore1, lore2, lore3, null);
	}
	public static ItemStack create(Material material, int amount, byte data){
		return create(material, amount, data, null, null, null, null, null);
	}
}
