package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.event.AE_InteractEvent;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class AE_EffectSound extends AE_EffectParent {
		
	@Override
	public void run(AE_DamageEvent event) {
		final Player player = event.getPlayer();
		String[] args = event.getArgs();
		World world = player.getWorld();
		String soundstring;
		String playsound = "player";
		int x = 1;
		final Location loc = player.getLocation();
		if (args.length == 0 || args[0].isEmpty()) {
			soundstring = null;
		} else if (args.length == 1) {
			soundstring = args[0];
		} else if (args.length == 2) {
			soundstring = args[0];
			if (args[1].equalsIgnoreCase("player") || args[1].equalsIgnoreCase("local"))
				playsound = args[1];
			else
				return;
		} else if (args.length == 3) {
			soundstring = args[0];
			if (args[1].equalsIgnoreCase("player") || args[1].equalsIgnoreCase("local"))
				playsound = args[1];
			else
				return;
			try {
				x = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				return;
			}
		} else {
			soundstring = null;
		}
		if (soundstring == null)
			return;
		soundstring = soundstring.toUpperCase();

		try {
			Sound.valueOf(soundstring);
		} catch (IllegalArgumentException e) {
			return;
		}
		final Sound sound = Sound.valueOf(soundstring);

		if (playsound.equalsIgnoreCase("player")) {
			for (int i = 0; i < x; i++)
				player.playSound(loc, sound, 1F, 1F);
		}

		if (playsound.equalsIgnoreCase("local")) {
			for (int i = 0; i < x; i++)
				world.playSound(loc, sound, 1F, 1F);
			return;
		}
	}

	@Override
	public void run(AE_InteractEvent event) {
		final Player player = event.getPlayer();
		String[] args = event.getArgs();
		World world = player.getWorld();
		String soundstring;
		String playsound = "null";
		int x = 1;
		final Location loc = player.getLocation();
		if (args.length == 0 || args[0].isEmpty()) {
			soundstring = null;
		} else if (args.length == 1) {
			soundstring = args[0];
		} else if (args.length == 2) {
			soundstring = args[0];
			if (args[1].equalsIgnoreCase("local"))
				playsound = args[1];
			else
				return;
		} else if (args.length == 3) {
			soundstring = args[0];
			if (args[1].equalsIgnoreCase("local"))
				playsound = args[1];
			else
				return;
			try {
				x = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				return;
			}
		} else {
			soundstring = null;
		}
		if (soundstring == null)
			return;
		soundstring = soundstring.toUpperCase();

		try {
			Sound.valueOf(soundstring);
		} catch (IllegalArgumentException e) {
			return;
		}
		final Sound sound = Sound.valueOf(soundstring);

		if (playsound.equalsIgnoreCase("null")) {
			return;
		}

		if (playsound.equalsIgnoreCase("local")) {
			for (int i = 0; i < x; i++)
				world.playSound(loc, sound, 1F, 1F);
			return;
		}
	}
}
