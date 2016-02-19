package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_InteractEvent;
import kipperorigin.armamentseffects.event.AE_PlayerInteractEntityEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AE_EffectPotionRightClick extends AE_EffectParent {

    @Override
    public void run(AE_InteractEvent event) {
        Player player = event.getPlayer();
        String[] args = event.getArgs();
        String potion;
        int amp = 1;
        int time = 5;
        if (args.length == 0)
            return;
        if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            potion = args[0];

            try {
                if (args.length > 1) {
                    amp = Integer.parseInt(args[1]);
                }
                if (args.length > 2) {
                    time = Integer.parseInt(args[2]);
                }
            } catch (NumberFormatException e) {
                    return;
            }

            PotionEffectType type = PotionEffectType.getByName(potion);
            if (type == null)
                return;
            if (type.isInstant())
                time = 1;
            player.addPotionEffect(new PotionEffect(type, time * 20, amp - 1));
            return;
        }
    }

    @Override
    public void run(AE_PlayerInteractEntityEvent event) {
        String args[] = event.getArgs();
        String potion;
        int amp = 1;
        int time = 5;
        Player player = (Player) event.getEntity();

        if (args.length == 0)
            return;

        potion = args[0];
        try {
            if (args.length > 1) {
                amp = Integer.parseInt(args[1]);
            }
            if (args.length > 2) {
                time = Integer.parseInt(args[2]);
            }
        } catch (NumberFormatException e) {
            return;
        }

        PotionEffectType type = PotionEffectType.getByName(potion);
        if (type == null)
            return;
        if (type.isInstant())
            time = 1;

        player.addPotionEffect(new PotionEffect(type, time * 20, amp - 1));
        return;
    }
}
