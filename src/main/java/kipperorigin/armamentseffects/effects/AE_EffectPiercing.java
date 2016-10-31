package kipperorigin.armamentseffects.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.resources.AE_Color;

public class AE_EffectPiercing extends AE_EffectParent {
	
	AE_Color color = new AE_Color();
	
    @Override
    public void run(AE_DamageEvent event) {
    	Player player = event.getPlayer();
        String[] args = event.getArgs();
        double d;
        double ofd = event.getRawEvent().getFinalDamage();
        if (args.length < 4 && args.length > 1) {
            try {
            	d = Double.valueOf(args[1]);
            } catch (NumberFormatException e) {
            	player.sendMessage("Argument must be a number 1-100");
            	return;
            }
            
            if ((d > 100) || (d < 1)) {
            	player.sendMessage("Argument must be a number 1-100");
            	return;
            }
            
            d = 1 - (d/100);
            
            if (args.length == 3 && args[2].equalsIgnoreCase("stats")) {
            	player.sendMessage(color.color("&6-----Stats Start-----"));
            	player.sendMessage(color.color("&eOriginal Damage = " + event.getRawEvent().getDamage()));
            	player.sendMessage(color.color("&ePiercing Type = " + args[0]));
            	player.sendMessage(color.color("&ePiercing Ammount = " + args[1] + "%"));
            	player.sendMessage(color.color("&cPre-Piercing Protection"));
            	player.sendMessage(color.color("&cAbsorbtion&f = " + event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.ABSORPTION)));
            	player.sendMessage(color.color("&cArmor&f = " + event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.ARMOR)));
            	player.sendMessage(color.color("&cMagic&f = " + event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.MAGIC)));
            	player.sendMessage(color.color("&cResistance&f = " + event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.RESISTANCE)));
            	player.sendMessage(color.color("&c__________"));
            	player.sendMessage(color.color("&cFinal Damage = " + event.getRawEvent().getFinalDamage()));
            }
            
            if (args[0].equalsIgnoreCase("True")) {
            	event.getRawEvent().setDamage(EntityDamageEvent.DamageModifier.ABSORPTION, event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.ABSORPTION) * d);
            	event.getRawEvent().setDamage(EntityDamageEvent.DamageModifier.ARMOR, event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.ARMOR) * d);
            	event.getRawEvent().setDamage(EntityDamageEvent.DamageModifier.MAGIC, event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.MAGIC) * d);
            	event.getRawEvent().setDamage(EntityDamageEvent.DamageModifier.RESISTANCE, event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.RESISTANCE) * d);
            } else if (args[0].equalsIgnoreCase("Magical")) {
            	event.getRawEvent().setDamage(EntityDamageEvent.DamageModifier.MAGIC, event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.MAGIC) * d);
            	event.getRawEvent().setDamage(EntityDamageEvent.DamageModifier.RESISTANCE, event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.RESISTANCE) * d);
            } else if (args[0].equalsIgnoreCase("Physical")) {
            	event.getRawEvent().setDamage(EntityDamageEvent.DamageModifier.ABSORPTION, event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.ABSORPTION) * d);
            	event.getRawEvent().setDamage(EntityDamageEvent.DamageModifier.ARMOR, event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.ARMOR) * d);
            } else if (args[0].equalsIgnoreCase("Absorbtion"))
            	event.getRawEvent().setDamage(EntityDamageEvent.DamageModifier.ABSORPTION, event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.ABSORPTION) * d);
            else if (args[0].equalsIgnoreCase("Armor"))
            	event.getRawEvent().setDamage(EntityDamageEvent.DamageModifier.ARMOR, event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.ARMOR) * d);
            else if (args[0].equalsIgnoreCase("Magic"))
            	event.getRawEvent().setDamage(EntityDamageEvent.DamageModifier.MAGIC, event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.MAGIC) * d);
            else if (args[0].equalsIgnoreCase("Resistance"))
            	event.getRawEvent().setDamage(EntityDamageEvent.DamageModifier.RESISTANCE, event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.RESISTANCE) * d);
            else return;  	
            
        	if (args.length == 3 && args[2].equalsIgnoreCase("stats")) {
            	player.sendMessage(color.color("&aPost-Piercing Protection"));
            	player.sendMessage(color.color("&aAbsorbtion&f = " + event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.ABSORPTION)));
            	player.sendMessage(color.color("&aArmor&f = " + event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.ARMOR)));
            	player.sendMessage(color.color("&aMagic&f = " + event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.MAGIC)));
            	player.sendMessage(color.color("&aResistance&f = " + event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.RESISTANCE)));
            	player.sendMessage(color.color("&a__________"));
            	player.sendMessage(color.color("&aDamage Increased = " + (event.getRawEvent().getFinalDamage() - ofd)));
            	player.sendMessage(color.color("&aFinal Damage = " + event.getRawEvent().getFinalDamage()));
            	player.sendMessage(color.color("&6-----Stats End-----"));
            }
        }
    }
}
