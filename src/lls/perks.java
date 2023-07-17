package lls;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class perks implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main plugin;
	public perks(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender send, Command cmd, String label, String[] args) {
		GUI.OpenGUI((Player) send);
		return true;
	}
}
