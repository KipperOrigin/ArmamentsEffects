package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.resources.AE_RemoveItem;

public class AE_EffectInstakill extends AE_EffectParent {

	private AE_Main plugin;

	public AE_EffectInstakill(AE_Main plugin) {
		this.plugin = plugin;
	}
	
	AE_RemoveItem AE_RI = new AE_RemoveItem(plugin);

	@Override
	public void run(AE_DamageEvent event) {
		event.getRawEvent().setDamage(9001d);
		AE_RI.removeItem(event.getPlayer());
		return;
	}

}
