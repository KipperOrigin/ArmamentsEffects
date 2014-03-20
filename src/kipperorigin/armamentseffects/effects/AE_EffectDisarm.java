package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.resources.AE_Color;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class AE_EffectDisarm extends AE_EffectParent {
	
	AE_Color color = new AE_Color();

	@Override
	public void run(AE_DamageEvent event) {
		if (!(event.getVictim() instanceof Player))
			return;

		Player victim = (Player)event.getVictim();

		ItemStack item = victim.getItemInHand();
		if (item == null || item.getType() == Material.AIR)
			return;

		PlayerInventory inv = victim.getInventory();
		int oldSlot = inv.getHeldItemSlot();
		int newSlot = inv.firstEmpty();
		inv.setItem(newSlot, item);
		inv.setItem(oldSlot, null);
		victim.sendMessage(color.color("&4You have been disarmed!"));
	}
}
