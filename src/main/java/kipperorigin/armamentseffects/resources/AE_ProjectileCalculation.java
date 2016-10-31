package kipperorigin.armamentseffects.resources;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class AE_ProjectileCalculation {
	/**
	 * Returns a random offset for shotgun effect
	 *
	 * @return Vector
	 */
	public Vector randomOffset() {
		return new Vector(
			// Subtract 0.5 to get from -.5 to .5, multiply by .75 to confine
			(Math.random() - 0.5) * 0.75,
			Math.random() * 0.5,
			1
		);
	}
	
	public Vector randomOffsetTight() {
		return new Vector(
			(Math.random() - 0.5) * .5,
			Math.random() * 0.25,
			1.5
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
	public Vector transform(Location loc, Vector vec) {
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
