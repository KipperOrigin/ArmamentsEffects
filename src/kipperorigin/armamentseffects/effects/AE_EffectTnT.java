package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_InteractEvent;
import kipperorigin.armamentseffects.resources.AE_LaunchTnT;
import kipperorigin.armamentseffects.resources.AE_RemoveItem;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AE_EffectTnT extends AE_EffectParent {

	@SuppressWarnings("unused")
	private AE_Main plugin;

	public AE_EffectTnT(AE_Main plugin) {
		this.plugin = plugin;
	}

	AE_RemoveItem AE_RI = new AE_RemoveItem();
	AE_LaunchTnT tnt = new AE_LaunchTnT();

	@Override
	public void run(final AE_InteractEvent event) {
		/* -s Single
		 * -t Trishot
		 * -sg Shotgun
		 * -mr Mortar
		 * -mn Mine
		 * -sn Sniper?
		 */

		final String args[] = event.getArgs();
		Player player = event.getPlayer();
		Location loc = player.getLocation();
		int multiply = 1;
		int timer = 0;
		loc = loc.add(0, 1, 0);

		Vector adjustY = new Vector(0, .35, 0);
		Vector dir = event.getPlayer().getEyeLocation().getDirection().add(adjustY);

		if (args.length < 1 || args.length > 3)
			return;

		if (args.length >= 2) {
			try {
				multiply = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				return;
			}
		}

		if (args.length >= 3) {
			try {
				timer = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				return;
			}
		}

		if (args[0].equalsIgnoreCase("-s")) {
			// Single shot
			dir.normalize().multiply(multiply);

			tnt.fireTnT(dir, loc, timer);
			AE_RI.removeItem(event.getPlayer());
		} else if (args[0].equalsIgnoreCase("-t")) {
			// Trishot
			// TODO
		} else if (args[0].equalsIgnoreCase("-sg")) {
			// Shotgun
			for (int i = 0; i < 8; i++) {
				Vector vec = dir.clone().add(randomOffset());
				vec.normalize().multiply(multiply);

				tnt.fireTnT(vec, loc, timer);
				AE_RI.removeItem(event.getPlayer());
			}
		} else if (args[0].equalsIgnoreCase("-mr")) {
			// Mortar
			// TODO
		} else if (args[0].equalsIgnoreCase("-mn")) {
			// Mine
			// TODO
		} else if (args[0].equalsIgnoreCase("-sn")) {
			// Sniper
			// TODO
		}
	}

	/**
	 * Returns a random offset from -.375 to .375
	 *
	 * @return Vector
	 */
	private Vector randomOffset() {
		return new Vector(
			// Subtract 0.5 to get from -.5 to .5, multiply by .75 to confine
			(Math.random() - 0.5) * 0.75,
			(Math.random() - 0.5) * 0.75,
			(Math.random() - 0.5) * 0.75
		);
	}
}
