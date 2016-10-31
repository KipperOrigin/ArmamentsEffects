package kipperorigin.armamentseffects.effects;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.Particle;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import kipperorigin.armamentseffects.resources.AE_EntityGetter;

public class AE_EffectPacketParticles extends AE_EffectParent implements Listener {

    int running;

    private AE_Main plugin;

    public AE_EffectPacketParticles(AE_Main plugin) {

        this.plugin = plugin;
    }
    
    private ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    private AE_EntityGetter eg = new AE_EntityGetter();

	@SuppressWarnings("deprecation")
	@Override
    public void run(final AE_ProjectileEvent event) {		
		final Player player = event.getPlayer();
		final Projectile projectile = event.getProjectile();
		String[] args = event.getArgs();
		int timer = 1;
		long delay = 0;
		final PacketContainer particlePacket = protocolManager.createPacket(PacketType.Play.Server.WORLD_PARTICLES);
		int[] il = {0,0};
    	float xF = 0;
    	float yF = 0;
    	float zF = 0;
    	float[] f = {0,0,0,0,0,0};
    	
		if (event.getRawEvent().isCancelled())
			return;
		
		if (args.length == 0 || args.length > 12)
			return;
		
		if (args.length >= 1) {
			try {
				particlePacket.getParticles().write(0, Particle.valueOf(args[0].toUpperCase()));
			} catch (IllegalArgumentException e) {
				player.sendMessage("ARGUMENT 1 INVALID PARTICLE");
				return;
			}
			if (args[0].equalsIgnoreCase("Block_Crack") || args[0].equalsIgnoreCase("Block_Dust") || args[0].equalsIgnoreCase("Item_Crack")) {
				if (args.length >= 9) {
					String[] parts = args[8].split(";");
					if (parts.length > 2)
						return;
					else {
						try {
							il[0] = Material.valueOf(parts[0].toUpperCase()).getId();
						} catch (IllegalArgumentException e) {
							player.sendMessage("ARGUMENT 9 MUST BE A MATERIAL");
							return;
						}
						if (parts.length == 2)
							try {
								player.sendMessage(parts[1]);
							} catch (NumberFormatException e) {
								player.sendMessage("ARGUMENT 9 DATA VALUE MUST BE AN INTEGER");
							}
					}
					
					try {
						particlePacket.getIntegerArrays().write(0, il);
					} catch (IllegalArgumentException e) {
						player.sendMessage("UNKOWN ERROR PLEASE REPORT");
						return;
					}
				} else {
					player.sendMessage("You must provide a material in argument 9 for this particle!");
					return;
				}
			}
		}		
		
		if (args.length >= 2 && !args[1].equalsIgnoreCase("x"))
			try {
				particlePacket.getIntegers().write(0, Particle.valueOf(args[1].toUpperCase()).getId());
			} catch (IllegalArgumentException e) {
				player.sendMessage("ARGUMENT 2 IS AN INVALID PARTICLE SPREAD");
				return;
			}
		/* if (args.length >= 3 && !args[2].equalsIgnoreCase("x"))
			try {
				xF = Float.valueOf(args[2]);
			} catch (NumberFormatException e) {
				player.sendMessage("ARGUMENT 3 MUST BE X OR A NUMBER");
			}
		if (args.length >= 4 && !args[3].equalsIgnoreCase("x"))
			try {
				yF = Float.valueOf(args[3]);
			} catch (NumberFormatException e) {
				player.sendMessage("ARGUMENT 4 MUST BE X OR A NUMBER");
			}
		if (args.length >= 5 && !args[4].equalsIgnoreCase("x"))
			try {
				zF = Float.valueOf(args[4]);
			} catch (NumberFormatException e) {
				player.sendMessage("ARGUMENT 5 MUST BE X OR A NUMBER");
			}
		if (args.length >= 6 && !args[5].equalsIgnoreCase("x"))
			try {
				particlePacket.getFloat().write(3, Float.valueOf(args[5]));
			} catch (NumberFormatException e) {
				player.sendMessage("ARGUMENT 6 MUST BE X OR A NUMBER");
				return;
			}
		if (args.length >= 7 && !args[6].equalsIgnoreCase("x"))
			try {
				particlePacket.getFloat().write(4, Float.valueOf(args[6]));
			} catch (NumberFormatException e) {
				player.sendMessage("ARGUMENT 7 MUST BE X OR A NUMBER");
				return;
			}
		if (args.length >= 8 && !args[7].equalsIgnoreCase("x"))
			try {
				particlePacket.getFloat().write(5, Float.valueOf(args[7]));
			} catch (NumberFormatException e) {
				player.sendMessage("ARGUMENT 8 MUST BE X OR A NUMBER");
				return;
			}
			*/
		
		int i = 3;
		while (i <= 8) {
			if (args.length >= i && !args[i-1].equalsIgnoreCase("x"))
				try {
					f[i-3] = Float.valueOf(args[i-1]);
				} catch (NumberFormatException e) {
					player.sendMessage("Argument " + (i) + " must be X or a number.");
					return;
				}
			else break;
			if (i >= 6)
				particlePacket.getFloat().write(i-3, f[i-1]);
		}
		
		final float[] ff = f;
		
		if (args.length >= 10)
			if (args[9].equalsIgnoreCase("x"))
				timer = 1;
			else
				try {
					timer = Integer.valueOf(args[9]);
				} catch (NumberFormatException e) {
					player.sendMessage("ARGUMENT 10 MUST BE AN INTEGER");
					return;
				}
		if (args.length >= 11)
			if (args[10].equalsIgnoreCase("x"))
				delay = 0;
			else
				try {
					delay = Long.valueOf(args[10]);
				} catch (NumberFormatException e) {
					player.sendMessage("ARGUMENT 11 MUST BE AN INTEGER");
					return;
				}
		
		final float fXF = xF;
		final float fYF = yF;
		final float fZF = zF;
				
		try {
    		protocolManager.sendServerPacket(player, particlePacket);
    	} catch (InvocationTargetException e) {
    		throw new RuntimeException("Cannot send packet " + particlePacket, e);
    	}
		
        final int taskId = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
            	
            	int i = 0;
            	List<Player> players = eg.getPlayersInRadius(50, projectile);
            	
        		particlePacket.getFloat()
        			.write(0, (float) projectile.getLocation().getX() + ff[0])
        			.write(1, (float) projectile.getLocation().getY() + ff[1])
        			.write(2, (float) projectile.getLocation().getZ() + ff[2])
        			.write(6, 1F);
            	
            	if (players != null) {
            		if (players.size() != 0 && !players.isEmpty()) {
            			try {
                    		for (int x = 0; x < players.size(); x++)
                    			protocolManager.sendServerPacket(players.get(x), particlePacket);
                    	} catch (InvocationTargetException e) {
                    		throw new RuntimeException("Cannot send packet " + particlePacket, e);
                    	}
            		}
            	}
            	
                if (projectile.getLocation().getY() <= 0) {
                	while(projectile.hasMetadata("Data " + String.valueOf(i))) {
                		Bukkit.getScheduler().cancelTask(projectile.getMetadata("Data " + String.valueOf(i)).get(0).asInt());
                		i++;
                	}
                	projectile.eject();
                	projectile.remove();
        		}
            }
        }, delay, timer).getTaskId();
        MetadataValue x = new FixedMetadataValue(plugin, taskId);

        if (args.length == 12 && args[11].equalsIgnoreCase("permanent")) {
    		event.getPlayer().sendMessage("taskId = " + String.valueOf(taskId));
        } else {
			int ix = 0;
			
			while(projectile.hasMetadata("Data " + String.valueOf(i))) 
				ix++;
			
			projectile.setMetadata("Data " + String.valueOf(ix), x);
            if (event.getRawEvent().isCancelled() && projectile.hasMetadata("Data"))
                Bukkit.getScheduler().cancelTask(projectile.getMetadata("Data").get(0).asInt());
        }
	}
}

