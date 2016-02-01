package kipperorigin.armamentseffects;

import kipperorigin.armamentseffects.effects.AE_EffectCommand;
import kipperorigin.armamentseffects.effects.AE_EffectDisarm;
import kipperorigin.armamentseffects.effects.AE_EffectDrain;
import kipperorigin.armamentseffects.effects.AE_EffectDurability;
import kipperorigin.armamentseffects.effects.AE_EffectExplode;
import kipperorigin.armamentseffects.effects.AE_EffectFireworkParticle;
import kipperorigin.armamentseffects.effects.AE_EffectInstakill;
import kipperorigin.armamentseffects.effects.AE_EffectKillParticles;
import kipperorigin.armamentseffects.effects.AE_EffectLightning;
import kipperorigin.armamentseffects.effects.AE_EffectManager;
import kipperorigin.armamentseffects.effects.AE_EffectParticle;
import kipperorigin.armamentseffects.effects.AE_EffectPotionRightClick;
import kipperorigin.armamentseffects.effects.AE_EffectPotions;
import kipperorigin.armamentseffects.effects.AE_EffectRemoveAilment;
import kipperorigin.armamentseffects.effects.AE_EffectRideable;
import kipperorigin.armamentseffects.effects.AE_EffectShoot;
import kipperorigin.armamentseffects.effects.AE_EffectSound;
import kipperorigin.armamentseffects.effects.AE_EffectSpawn;
import kipperorigin.armamentseffects.effects.AE_EffectStun;
import kipperorigin.armamentseffects.effects.AE_EffectTeleport;
import kipperorigin.armamentseffects.effects.AE_EffectTnT;
import kipperorigin.armamentseffects.effects.AE_EffectUnbreakable;
import kipperorigin.armamentseffects.effects.AE_EffectVelocity;
import kipperorigin.armamentseffects.effects.AE_EffectWeb;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AE_Main extends JavaPlugin {

	private final PluginManager pm = Bukkit.getPluginManager();

	@Override
	public void onEnable() {
		
		final AE_EffectManager listener = new AE_EffectManager(this);
		
		pm.registerEvents(listener, this);

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
		AE_EffectDurability durability = new AE_EffectDurability();
		listener.registerEffect("durability", durability);

		// explode
		AE_EffectExplode explode = new AE_EffectExplode();
		listener.registerEffect("explode", explode);
		listener.registerEffect("boom", explode);
		
		// firework particle
		AE_EffectFireworkParticle firework = new AE_EffectFireworkParticle(this);
		listener.registerEffect("firework", firework);
		listener.registerEffect("fireworkparticle", firework);

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
		listener.registerEffect("killparticle", killparticles);
		listener.registerEffect("killparticles", killparticles);
		
		// lightning
		AE_EffectLightning lightning = new AE_EffectLightning(this);
		listener.registerEffect("lightning", lightning);
		listener.registerEffect("thunder", lightning);

		// potions
		AE_EffectPotions potions = new AE_EffectPotions();
		listener.registerEffect("inflict", potions);
		listener.registerEffect("apply", potions);
		
		// rideable
		AE_EffectRideable rideable = new AE_EffectRideable();
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

		// stun
		AE_EffectStun stun = new AE_EffectStun();
		listener.registerEffect("stun", stun);
		listener.registerEffect("stop", stun);
		
		// tnt
		AE_EffectTnT tnt = new AE_EffectTnT(this);
		listener.registerEffect("tnt", tnt);
		listener.registerEffect("primedtnt", tnt);
		
		// unbreakable
		AE_EffectUnbreakable unbreakable = new AE_EffectUnbreakable();
		listener.registerEffect("unbreakable", unbreakable);
		listener.registerEffect("unbreaking", unbreakable);

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

		// remove ailment
		AE_EffectRemoveAilment heal = new AE_EffectRemoveAilment();
		listener.registerEffect("heal", heal);
		listener.registerEffect("cure", heal);

		// teleport
		AE_EffectTeleport teleport = new AE_EffectTeleport();
		listener.registerEffect("teleport", teleport);
		listener.registerEffect("move", teleport);
	}
}
