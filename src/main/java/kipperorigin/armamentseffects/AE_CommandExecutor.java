package kipperorigin.armamentseffects;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AE_CommandExecutor implements CommandExecutor {

	private final AE_Main plugin;
	
	public AE_CommandExecutor(AE_Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
			String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("effect")) {
			
		}
		
		return false;
	}
	
}
