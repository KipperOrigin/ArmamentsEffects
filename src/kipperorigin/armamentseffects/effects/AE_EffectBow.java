package kipperorigin.armamentseffects.effects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import kipperorigin.armamentseffects.resources.AE_RemoveItem;

public class AE_EffectBow extends AE_EffectParent {
	
	private AE_Main plugin;

	public AE_EffectBow(AE_Main plugin) {
		this.plugin = plugin;
	}

	AE_RemoveItem AE_RI = new AE_RemoveItem(plugin);
	
	@Override
	public void run(final AE_ProjectileEvent event) {
		/* -t Trishot
		 * -sg Shotgun
		 * -sn Sniper
		 * -b Burst (x3)
		 */
		
		final String args[] = event.getArgs();
		Player player = event.getPlayer();
		Location loc = player.getLocation();
		int multiply = 1;
		loc = loc.add(0, 1, 0);
		
		if (args.length < 1 || args.length > 2)
			return;

		if (args.length >= 2) {
			try {
				multiply = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				return;
			}
		}

		if (args.length >= 3) {
			return;
		}
		
		List<Vector> shots = new ArrayList<Vector>();
		
		if (args[0].equalsIgnoreCase("s")) {
			// Single shot
			shots.add(transform(loc, new Vector(0, 0.3, 1)));
		} else if (args[0].equalsIgnoreCase("t")) {
			// Trishot
			// Shot 1 slightly upward
			shots.add(transform(loc, new Vector(0, 0.4, 1)));
			// Shot 2 slightly to the left
			shots.add(transform(loc, new Vector(0.25, 0.1, 1)));
			// Shot 3 slightly to the right
			shots.add(transform(loc, new Vector(-0.25, 0.1, 1)));
		} else if (args[0].equalsIgnoreCase("sg")) {
			// Shotgun
			for (int i = 0; i < 8; i++) {
				shots.add(transform(loc, randomOffset()));
			}
		} else if (args[0].equalsIgnoreCase("mr")) {
			// Mortar
			shots.add(transform(loc, new Vector(0, 2, 1)));
		} else if (args[0].equalsIgnoreCase("mn")) {
			// Mine
			// TODO
		} else if (args[0].equalsIgnoreCase("sn")) {
			// Sniper
			shots.add(transform(loc, new Vector(0, 0, 1)));
			multiply *= 5;
		} else if (args[0].equalsIgnoreCase("b")) {
			// Burst
			// TODO
		}

		if (!shots.isEmpty()) {
			for (Vector shot : shots) {
				shot.normalize().multiply(multiply);
			}

			AE_RI.removeItem(event.getPlayer());
		}
	}

	/**
	 * Returns a random offset for shotgun effect
	 *
	 * @return Vector
	 */
	private Vector randomOffset() {
		return new Vector(
			// Subtract 0.5 to get from -.5 to .5, multiply by .75 to confine
			(Math.random() - 0.5) * 0.75,
			Math.random() * 0.5,
			1
		);
	}

	/**
	 * Transforms a vector based on the way the player is facing
	 *
	 * Code borrowed from https://github.com/Bukkit/Bukkit/pull/1012
	 *
	 * X = left
	 * Y = up
	 * Z = forward
	 *
	 * @param loc Player's location
	 * @param vec Vector
	 * @return
	 */
	private Vector transform(Location loc, Vector vec) {
		final double yaw = Math.toRadians(loc.getYaw());
        final double pitch = Math.toRadians(loc.getPitch());

        final double cosYaw = Math.cos(yaw);
        final double sinYaw = Math.sin(yaw);
        final double cosPitch = Math.cos(pitch);
        final double sinPitch = Math.sin(pitch);

        final Vector left = new Vector(
                cosYaw,
                0,
                sinYaw
        );

        final Vector up = new Vector(
                -sinYaw * sinPitch,
                cosPitch,
                cosYaw * sinPitch
        );

        final Vector forward = new Vector(
                -sinYaw * cosPitch,
                -sinPitch,
                cosYaw * cosPitch
        );

        return left.multiply(vec.getX()).add(up.multiply(vec.getY())).add(forward.multiply(vec.getZ()));
    }
}
