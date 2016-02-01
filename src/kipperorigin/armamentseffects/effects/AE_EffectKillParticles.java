package kipperorigin.armamentseffects.effects;

import org.bukkit.Bukkit;
import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_InteractEvent;

public class AE_EffectKillParticles extends AE_EffectParent {
	
	private AE_Main plugin;

	public AE_EffectKillParticles(AE_Main plugin) {

		this.plugin = plugin;
	}
	
	@Override
	public void run(final AE_InteractEvent event) {
		String[] args = event.getArgs();
		
		if (args.length > 0) {
			for(int i = 0; i <= args.length; i++) {
				try {
					Bukkit.getScheduler().cancelTask(Integer.parseInt(args[i]));
				} catch (ArrayIndexOutOfBoundsException e) {
					
				} catch (NumberFormatException e) {
					
				}
			}
		} else {
			Bukkit.getScheduler().cancelTasks(plugin);
		}
	}
}
