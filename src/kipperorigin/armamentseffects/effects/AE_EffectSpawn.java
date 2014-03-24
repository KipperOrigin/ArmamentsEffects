package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_InteractEvent;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Wolf;

public class AE_EffectSpawn extends AE_EffectParent {

	/*
	public void addItems(int difference, String[] args, Entity e) {
		for (int i = difference; i <= args.length; i++) {
			String slot = String.valueOf(args[difference].charAt(0));
			if (slot.equalsIgnoreCase("h"))
				
				
		}
	}
	public void addHelmet(Entity e, ) {
		
	}
	
	public void addBody() {
		
	}
	
	public void addPants() {
		
	}
	
	public void addBoots() {
		
	}
	
	public void addWeapon() {
		
	}
	*/
	
	public void setBabyMob(Entity e) {
		((Ageable)e).setBaby();
		return;
	}

	public void spawnCreeper(EntityType e, Location l, World w, String[] args) {
		Creeper c = (Creeper) w.spawnEntity(l, e);
		if (args.length >= 2) {
			if (args[1].equalsIgnoreCase("electric"))
				c.setPowered(true);
		} else return;
		return;
	}

	public void spawnHorse(EntityType e, Location l, World w, String args[], Player p) {
		Horse h = (Horse) w.spawnEntity(l, e);
		if (args.length >= 2) {
			if (args[1].equalsIgnoreCase("horse"))
				h.setVariant(Horse.Variant.HORSE);
			else if (args[1].equalsIgnoreCase("donkey"))
				h.setVariant(Horse.Variant.DONKEY);
			else if (args[1].equalsIgnoreCase("mule"))
				h.setVariant(Horse.Variant.MULE);
			else if ((args[1].equalsIgnoreCase("skeleton_horse")) || (args[0].equalsIgnoreCase("skeletonhorse")) || (args[0].equalsIgnoreCase("shorse")))
				h.setVariant(Horse.Variant.SKELETON_HORSE);
			else if ((args[1].equalsIgnoreCase("undead_horse")) || (args[0].equalsIgnoreCase("undeadhorse")) || (args[0].equalsIgnoreCase("uhorse")))
				h.setVariant(Horse.Variant.UNDEAD_HORSE);
			else return;
		}
		if (args.length >= 3) {
			if (args[2].equalsIgnoreCase("black"))
				h.setColor(Horse.Color.BLACK);
			else if (args[2].equalsIgnoreCase("brown"))
				h.setColor(Horse.Color.BROWN);
			else if (args[2].equalsIgnoreCase("chestnut"))
				h.setColor(Horse.Color.CHESTNUT);
			else if (args[2].equalsIgnoreCase("creamy"))
				h.setColor(Horse.Color.CREAMY);
			else if ((args[2].equalsIgnoreCase("dark_brown")) || (args[2].equalsIgnoreCase("darkbrown")))
				h.setColor(Horse.Color.DARK_BROWN);
			else if ((args[2].equalsIgnoreCase("gray")) || (args[2].equalsIgnoreCase("grey")))
				h.setColor(Horse.Color.GRAY);
			else if (args[2].equalsIgnoreCase("white"))
				h.setColor(Horse.Color.WHITE);
			else return;
		}
		if (args.length >= 4) {
			if ((args[3].equalsIgnoreCase("wlack_dots")) || (args[3].equalsIgnoreCase("blackdots")))
				h.setStyle(Horse.Style.BLACK_DOTS);
			else if (args[3].equalsIgnoreCase("none"))
				h.setStyle(Horse.Style.NONE);
			else if (args[3].equalsIgnoreCase("white"))
				h.setStyle(Horse.Style.WHITE);
			else if ((args[3].equalsIgnoreCase("white_dots")) || (args[3].equalsIgnoreCase("whitedots")))
				h.setStyle(Horse.Style.WHITE_DOTS);
			else if (args[3].equalsIgnoreCase("whitefield"))
				h.setStyle(Horse.Style.WHITEFIELD);
			else
				h.setStyle(Horse.Style.NONE);
		}
		if (args.length >= 5) {
			if (args[4].equalsIgnoreCase("tamed"))
				h.setOwner(p);
		}
		return;
	}

	public void spawnMagmaCube(EntityType e, Location l, World w, String args[]) {
		MagmaCube m = (MagmaCube) w.spawnEntity(l, e);
		if (args.length >= 2) {
			boolean number = true;
			int i = 1;
			try {
				i = Integer.parseInt(args[1]);
			} catch (NumberFormatException ex) {
				number = false;
			}
			if (number) {
				m.setSize(i);
			} else {
				if (args[2].equalsIgnoreCase("tiny"))
					m.setSize(1);
				else if (args[2].equalsIgnoreCase("small"))
					m.setSize(2);
				else if (args[2].equalsIgnoreCase("normal"))
					m.setSize(4);
				else if (args[2].equalsIgnoreCase("large"))
					m.setSize(6);
				else if (args[2].equalsIgnoreCase("huge"))
					m.setSize(8);
				else if (args[2].equalsIgnoreCase("colossal"))
					m.setSize(16);
				else return;
			}
		}
		return;
	}
	
	public void spawnMob(EntityType e, Location l, World world) {
			world.spawnEntity(l, e);
	}
	
	public void spawnOcelot(EntityType e, Location l, World w, String[] args, Player p) {
		Ocelot o = (Ocelot) w.spawnEntity(l, e);
		if (args.length >= 2) {
			if (args[1].equalsIgnoreCase("tamed"))
				o.setOwner(p);
		} else return;
		return;
	}

	public void spawnSheep(EntityType e, Location l, World w, String args[], Player p) {
		Sheep s = (Sheep) w.spawnEntity(l, e);
		if (args.length >= 2) {
			if (args[2].equalsIgnoreCase("black"))
				s.setColor(DyeColor.BLACK);
			else if (args[2].equalsIgnoreCase("blue"))
				s.setColor(DyeColor.BLUE);
			else if (args[2].equalsIgnoreCase("brown"))
				s.setColor(DyeColor.BROWN);
			else if (args[2].equalsIgnoreCase("cyan"))
				s.setColor(DyeColor.CYAN);
			else if ((args[2].equalsIgnoreCase("gray")) || (args[2].equalsIgnoreCase("grey")))
				s.setColor(DyeColor.GRAY);
			else if (args[2].equalsIgnoreCase("green"))
				s.setColor(DyeColor.GREEN);
			else if ((args[2].equalsIgnoreCase("light_blue")) || (args[2].equalsIgnoreCase("lightblue")))
				s.setColor(DyeColor.LIGHT_BLUE);
			else if (args[2].equalsIgnoreCase("lime"))
				s.setColor(DyeColor.LIME);
			else if (args[2].equalsIgnoreCase("magenta"))
				s.setColor(DyeColor.MAGENTA);
			else if (args[2].equalsIgnoreCase("orange"))
				s.setColor(DyeColor.ORANGE);
			else if (args[2].equalsIgnoreCase("pink"))
				s.setColor(DyeColor.PINK);
			else if (args[2].equalsIgnoreCase("purple"))
				s.setColor(DyeColor.PURPLE);
			else if (args[2].equalsIgnoreCase("red"))
				s.setColor(DyeColor.RED);
			else if (args[2].equalsIgnoreCase("silver"))
				s.setColor(DyeColor.SILVER);
			else if (args[2].equalsIgnoreCase("white"))
				s.setColor(DyeColor.WHITE);
			else if (args[2].equalsIgnoreCase("yellow"))
				s.setColor(DyeColor.YELLOW);
			else return;
		return;
		}
	}

	public void spawnSlime(EntityType e, Location l, World w, String args[]) {
		Slime s = (Slime) w.spawnEntity(l, e);
		if (args.length >= 2) {
			boolean number = true;
			int i = 1;
			try {
				i = Integer.parseInt(args[1]);
			} catch (NumberFormatException ex) {
				number = false;
			}
			if (number) {
				s.setSize(i);
			} else {
				if (args[2].equalsIgnoreCase("tiny")) 
					s.setSize(1);
				else if (args[2].equalsIgnoreCase("small"))
					s.setSize(2);
				else if (args[2].equalsIgnoreCase("normal"))
					s.setSize(4);
				else if (args[2].equalsIgnoreCase("large"))
					s.setSize(6);
				else if (args[2].equalsIgnoreCase("huge"))
					s.setSize(8);
				else if (args[2].equalsIgnoreCase("colossal"))
					s.setSize(16);
				else return;
			}
		}
		return;
	}

	public void spawnVillager() {

	}

	public void spawnWolf(EntityType e, Location l, World w, String[] args, Player p) {
		Wolf wolf = (Wolf) w.spawnEntity(l, e);
		if (args.length >= 2) {
			if (args[1].equalsIgnoreCase("tamed"))
				wolf.setOwner(p);
		} else return;
		return;
	}
	
	@Override
	public void run(AE_InteractEvent event) {
		Player player = event.getPlayer();
		Location location = event.getClickedLocation().add(0, 1, 0);
		String[] args = event.getArgs();

		if (args.length == 0)
			return;

		String mob = args[0].toUpperCase();
		EntityType type = EntityType.valueOf(mob);
		World world = location.getWorld();

		if (type == null || !type.isSpawnable())
			return;
		
		if (type == EntityType.CREEPER) {
			this.spawnCreeper(type, location, world, args);
		} else if (type == EntityType.HORSE) {
			this.spawnHorse(type, location, world, args, player);
		} else if (type == EntityType.MAGMA_CUBE) {
			this.spawnMagmaCube(type, location, world, args);
		} else if (type == EntityType.OCELOT) {
			this.spawnOcelot(type, location, world, args, player);
		} else if (type == EntityType.SHEEP) {
			this.spawnSheep(type, location, world, args, player);
		} else if (type == EntityType.SLIME) {
			this.spawnSlime(type, location, world, args);
		} else if (type == EntityType.VILLAGER) {
			this.spawnVillager();
		} else if (type == EntityType.WOLF) {
			this.spawnWolf(type, location, world, args, player);
		} else this.spawnMob(type, location, world);
		
		return;
	}
}
