package org.cubeville.effects.effects;

import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;
import org.cubeville.effects.event.ProjectileEvent;

public class AE_EffectVelocity extends AE_EffectParent {

    @Override
    public void run(ProjectileEvent event) {
        Projectile projectile = event.getProjectile();
        String[] args = event.getArgs();

        int vec;
        if (args.length == 0)
            return;

        try {
            vec = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            return;
        }

        Vector v = projectile.getVelocity();
        Vector z = v.multiply(new Vector(vec, (vec / 2), vec));
        projectile.setVelocity(z);
        return;
    }
}
