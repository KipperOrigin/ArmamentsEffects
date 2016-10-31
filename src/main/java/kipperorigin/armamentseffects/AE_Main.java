package kipperorigin.armamentseffects;

import kipperorigin.armamentseffects.effects.AE_EffectArrow;
import kipperorigin.armamentseffects.effects.AE_EffectCommand;
import kipperorigin.armamentseffects.effects.AE_EffectDisarm;
import kipperorigin.armamentseffects.effects.AE_EffectDrain;
//import kipperorigin.armamentseffects.effects.AE_EffectDurability;
import kipperorigin.armamentseffects.effects.AE_EffectExplode;
import kipperorigin.armamentseffects.effects.AE_EffectFireworkParticle;
//import kipperorigin.armamentseffects.effects.AE_EffectHomingArrow;
import kipperorigin.armamentseffects.effects.AE_EffectInstakill;
import kipperorigin.armamentseffects.effects.AE_EffectKillParticles;
import kipperorigin.armamentseffects.effects.AE_EffectLightning;
import kipperorigin.armamentseffects.effects.AE_EffectManager;
import kipperorigin.armamentseffects.effects.AE_EffectPacketParticles;
import kipperorigin.armamentseffects.effects.AE_EffectParticle;
import kipperorigin.armamentseffects.effects.AE_EffectPiercing;
import kipperorigin.armamentseffects.effects.AE_EffectPotionRightClick;
import kipperorigin.armamentseffects.effects.AE_EffectPotions;
import kipperorigin.armamentseffects.effects.AE_EffectRemoveAilment;
import kipperorigin.armamentseffects.effects.AE_EffectRideable;
import kipperorigin.armamentseffects.effects.AE_EffectShoot;
import kipperorigin.armamentseffects.effects.AE_EffectSound;
import kipperorigin.armamentseffects.effects.AE_EffectSpawn;
import kipperorigin.armamentseffects.effects.AE_EffectStats;
import kipperorigin.armamentseffects.effects.AE_EffectStun;
import kipperorigin.armamentseffects.effects.AE_EffectTeleport;
import kipperorigin.armamentseffects.effects.AE_EffectTnT;
import kipperorigin.armamentseffects.effects.AE_EffectVelocity;
import kipperorigin.armamentseffects.effects.AE_EffectWeb;
import kipperorigin.armamentseffects.resources.AE_Shoot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolManager;

public class AE_Main extends JavaPlugin {

    private final PluginManager pm = Bukkit.getPluginManager();
    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
    	
        final AE_EffectManager listener = new AE_EffectManager(this);
        final AE_CommandExecutor ce = new AE_CommandExecutor(this);
        
        pm.registerEvents(listener, this);
        
        // arrow
        AE_EffectArrow arrow = new AE_EffectArrow(this);
        listener.registerEffect("arrow", arrow);
        listener.registerEffect("fire", arrow);

        // command
        AE_EffectCommand command = new AE_EffectCommand();
        listener.registerEffect("command", command);
        listener.registerEffect("send", command);
        
        // drain
        AE_EffectDrain drain = new AE_EffectDrain(this);
        listener.registerEffect("drain", drain);
        listener.registerEffect("leech", drain);
        
        // disarm
        AE_EffectDisarm disarm = new AE_EffectDisarm();
        listener.registerEffect("disarm", disarm);
        listener.registerEffect("unarm", disarm);
        
        // durability
        // AE_EffectDurability durability = new AE_EffectDurability();
        // listener.registerEffect("durability", durability);

        // explode
        AE_EffectExplode explode = new AE_EffectExplode();
        listener.registerEffect("explode", explode);
        listener.registerEffect("boom", explode);
        
        // firework particle
        AE_EffectFireworkParticle firework = new AE_EffectFireworkParticle(this);
        listener.registerEffect("firework", firework);
        listener.registerEffect("fireworkparticle", firework);
        
        // homing arrow
        // AE_EffectHomingArrow ha = new AE_EffectHomingArrow(this);
        // listener.registerEffect("Homing", ha);
        // listener.registerEffect("Seeking", ha);

        // instakill
        AE_EffectInstakill instakill = new AE_EffectInstakill();
        listener.registerEffect("instakill", instakill);
        listener.registerEffect("slay", instakill);

        // instapotion
        AE_EffectPotionRightClick potright = new AE_EffectPotionRightClick();
        listener.registerEffect("instapot", potright);
        listener.registerEffect("poteffect", potright);
        
        // kill particles
        AE_EffectKillParticles killparticles = new AE_EffectKillParticles(this);
        listener.registerEffect("killallparticles", killparticles);
        listener.registerEffect("killparticles", killparticles);
        
        // lightning
        AE_EffectLightning lightning = new AE_EffectLightning(this);
        listener.registerEffect("lightning", lightning);
        listener.registerEffect("thunder", lightning);
        
        // piercing
        AE_EffectPiercing piercing = new AE_EffectPiercing();
        listener.registerEffect("piercing", piercing);
        listener.registerEffect("penetration", piercing);
        
        // potions
        AE_EffectPotions potions = new AE_EffectPotions();
        listener.registerEffect("inflict", potions);
        listener.registerEffect("apply", potions);
        
        // rideable
        AE_EffectRideable rideable = new AE_EffectRideable(this);
        listener.registerEffect("rideable", rideable);
        listener.registerEffect("ride", rideable);

        // shoot
        AE_EffectShoot shoot = new AE_EffectShoot();
        listener.registerEffect("shoot", shoot);
        listener.registerEffect("launch", shoot);
        listener.registerEffect("cast", shoot);

        // sound
        AE_EffectSound sound = new AE_EffectSound();
        listener.registerEffect("playsound", sound);
        listener.registerEffect("play", sound);

        // spawnmob
        AE_EffectSpawn spawn = new AE_EffectSpawn();
        listener.registerEffect("spawn", spawn);
        listener.registerEffect("summon", spawn);
        
        // stats
        AE_EffectStats stats = new AE_EffectStats();
        listener.registerEffect("stats", stats);
        listener.registerEffect("numbers", stats);

        // stun
        AE_EffectStun stun = new AE_EffectStun();
        listener.registerEffect("stun", stun);
        listener.registerEffect("stop", stun);
        
        // tnt
        AE_EffectTnT tnt = new AE_EffectTnT(this);
        listener.registerEffect("tnt", tnt);
        listener.registerEffect("primedtnt", tnt);

        // velocity
        AE_EffectVelocity velocity = new AE_EffectVelocity();
        listener.registerEffect("velocity", velocity);
        listener.registerEffect("speed", velocity);

        // web
        AE_EffectWeb web = new AE_EffectWeb(this);
        listener.registerEffect("web", web);
        listener.registerEffect("trap", web);

        // particle
        AE_EffectParticle particle = new AE_EffectParticle(this);
        listener.registerEffect("particle", particle);
        listener.registerEffect("display", particle);
        
        // packet particles
        AE_EffectPacketParticles packetParticles = new AE_EffectPacketParticles(this);
        listener.registerEffect("packetparticle", packetParticles);
        listener.registerEffect("packetparticles", packetParticles);

        // remove ailment
        AE_EffectRemoveAilment heal = new AE_EffectRemoveAilment();
        listener.registerEffect("heal", heal);
        listener.registerEffect("cure", heal);

        // teleport
        AE_EffectTeleport teleport = new AE_EffectTeleport();
        listener.registerEffect("teleport", teleport);
        listener.registerEffect("move", teleport);
    }
    
	public ProtocolManager getProtocolManager() {
		return protocolManager;
	}

	public void setProtocolManager(ProtocolManager protocolManager) {
		this.protocolManager = protocolManager;
	}

}
