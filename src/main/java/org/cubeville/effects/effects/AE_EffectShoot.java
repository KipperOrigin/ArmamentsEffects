package org.cubeville.effects.effects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Item;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.cubeville.effects.event.ProjectileEvent;

@SuppressWarnings("deprecation")
public class AE_EffectShoot extends AE_EffectParent {

    @Override
    public void run(ProjectileEvent event) {
        Player player = event.getPlayer();
        Projectile projectile = event.getProjectile();
        String args[] = event.getArgs();
        Location location = event.getProjectile().getLocation();
        World world = location.getWorld();
        if (projectile instanceof Projectile) {
            event.cancel();
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("snowball"))
                    player.launchProjectile(Snowball.class).setVelocity(projectile.getVelocity());
                else if (args[0].equalsIgnoreCase("fireball"))
                    player.launchProjectile(Fireball.class).setVelocity(projectile.getVelocity());
                else if (args[0].equalsIgnoreCase("fireshot"))
                    player.launchProjectile(SmallFireball.class).setVelocity(projectile.getVelocity());
                else if (args[0].equalsIgnoreCase("fireblast"))
                    player.launchProjectile(LargeFireball.class).setVelocity(projectile.getVelocity());
                /* else if (args[0].equalsIgnoreCase("arrow"))
                    player.launchProjectile(Arrow.class).setVelocity(projectile.getVelocity()); BORKED */
                else if (args[0].equalsIgnoreCase("withershot"))
                    player.launchProjectile(WitherSkull.class).setVelocity(projectile.getVelocity());
                else if (args[0].equalsIgnoreCase("egg"))
                    player.launchProjectile(Egg.class).setVelocity(projectile.getVelocity());
                else if (args[0].equalsIgnoreCase("fish"))
                    player.launchProjectile(Fish.class).setVelocity(projectile.getVelocity());
                else if (args[0].equalsIgnoreCase("xpbottle"))
                    player.launchProjectile(ThrownExpBottle.class).setVelocity(projectile.getVelocity());
                else if (args[0].equalsIgnoreCase("charged_creeper")) {
                    Creeper creeper = (Creeper) world.spawnEntity(location, EntityType.CREEPER);
                    creeper.setPowered(true);
                    creeper.setVelocity(projectile.getVelocity());
                }
            } else if (args.length > 1 && args.length <= 4) {
            	if (args[0].equals("Dropped_Item") || args[0].equalsIgnoreCase("item")) {
            		ItemStack i;
            		if (Material.valueOf(args[1].toUpperCase()) == null)
            			return;
            		try {
            			i = new ItemStack(Material.valueOf(args[1].toUpperCase()));
            		} catch (IllegalArgumentException e) {
            			player.sendMessage("Invalid Item Type; Type used = " + args[1]);
            			return;
            		}

            		Material mat1 = i.getType();
            		
            		if (args.length == 3) {
            			ItemMeta itemMeta = i.getItemMeta();
            			itemMeta.setDisplayName(args[2]);
            			i.setItemMeta(itemMeta);
            		}
            		Item item = player.getWorld().dropItem(location, i);
            		
            		//TEMPORARY SELUTION
            		if(item.getItemStack().getType() == Material.STONE) {
            			if (mat1.equals(Material.STONE));
            			else {	
            				player.sendMessage("INVALID ITEM, PLEASE USE AN ACTUAL ITEM");
            				item.remove();
            				return;
            			}
            		}

            		item.setVelocity(projectile.getVelocity());
            		if (args.length == 4 && args[3].equalsIgnoreCase("nope"))
            			item.setPickupDelay(10000000);
            	} else if (args[0].equalsIgnoreCase("Falling_Block") || args[0].equalsIgnoreCase("block")) {
            		Material m;
            		byte b = 0;
            		
            		try {
            			m = Material.valueOf(args[1].toUpperCase());
            		} catch (IllegalArgumentException e) {
            			player.sendMessage("Please use a valid block");
            			return;
            		}
            		
            		if (!m.isBlock()) {
            			player.sendMessage("Please use a valid block");
            			return;
            		}
            		
            		if (args.length == 3)
            			try {
            				b = Byte.valueOf(args[2]);
            			} catch (NumberFormatException e) {
            				player.sendMessage("Please use a valid number");
            			}
            		
            		FallingBlock fb = player.getWorld().spawnFallingBlock(location, m, b);
            		fb.setVelocity(projectile.getVelocity());
            		fb.setDropItem(false);
            		if (args.length == 4 && args[3].equalsIgnoreCase("Damage"))
            			fb.setHurtEntities(true);
            	} else if (args[0].equalsIgnoreCase("spawn") || args[0].equalsIgnoreCase("mob")) {
                     try {
                         EntityType.valueOf(args[1].toUpperCase());
                     } catch  (IllegalArgumentException e) {
                         return;
                     }
                     
                     EntityType type = EntityType.valueOf(args[1].toUpperCase());

                     if (type == null || !type.isSpawnable())
                         return;
                     
                     Entity entity = world.spawnEntity(location, type);
                     entity.setVelocity(projectile.getVelocity());
                 } 
            }  
        }
    }
}
