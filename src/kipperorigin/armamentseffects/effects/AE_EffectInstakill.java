package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_DamageEvent;

public class AE_EffectInstakill extends AE_EffectParent {

	@Override
	public void run(AE_DamageEvent event) {
		event.getRawEvent().setDamage(9001d);
		return;
	}

}
