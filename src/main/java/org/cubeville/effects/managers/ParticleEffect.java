package org.cubeville.effects.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

// import org.bukkit.craftbukkit.v1_18_R1.entity.CraftLivingEntity;

import org.bukkit.block.Block;
import org.bukkit.block.data.type.GlassPane;
import org.bukkit.block.data.type.Slab;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import org.cubeville.effects.pluginhook.PluginHookManager;

@SerializableAs("ParticleEffect")
public class ParticleEffect extends EffectWithLocation
{
    private static List<Material> noCollisionBlocks = Arrays.asList(Material.CHAIN, Material.IRON_BARS, Material.LIGHT_BLUE_CARPET, Material.LIGHT_GRAY_CARPET, Material.LIME_CARPET, Material.MAGENTA_CARPET, Material.ORANGE_CARPET, Material.PINK_CARPET, Material.PURPLE_CARPET, Material.RED_CARPET, Material.WHITE_CARPET, Material.YELLOW_CARPET, Material.GREEN_CARPET, Material.GRAY_CARPET, Material.BROWN_CARPET, Material.BLACK_CARPET, Material.CYAN_CARPET , Material.TWISTING_VINES);
    
    private List<ParticleEffectComponent> components;
    private int stepsLoop;
    private int repeatCount; // 0 = indefinitely, not recommended though
    private int repeatOffset;
    
    public ParticleEffect(String name) {
        setName(name);
        stepsLoop = 1;
        repeatCount = 1;
    }

    public ParticleEffect(Map<String, Object> config) {
        setName((String) config.get("name"));
        stepsLoop = (int) config.get("stepsLoop");
        repeatCount = (int) config.get("repeatCount");
        repeatOffset = (int) config.get("repeatOffset");
        components = (List<ParticleEffectComponent>) config.get("components");
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = getSerializationBase();
        ret.put("stepsLoop", stepsLoop);
        ret.put("repeatCount", repeatCount);
        ret.put("repeatOffset", repeatOffset);
        ret.put("components", components);
        return ret;
    }

    public int getStepsTotal() {
        return repeatCount * repeatOffset + stepsLoop;
    }

    public boolean hasStep(int Step) {
        if(repeatCount == 0) return true;
        return Step < getStepsTotal();
    }

    public void play(Location location) {
        play(0, new StaticParticleEffectLocationCalculator(location), null);
    }

    public boolean play(int step, ParticleEffectLocationCalculator locationCalculator, Player player) {
        if(!hasStep(step)) return false;

        int localStep = step % getStepsLoop();

        boolean blockCollisionDetected = false;
        boolean entityCollisionDetected = false;
        
        for(ParticleEffectComponent component: components) {
            if(component.isActive(localStep)) {
                boolean blockCollisionCheck = component.getBlockCollisionCheck();
                boolean entityCollisionCheck = component.getEntityCollisionCheck();

                Location location = locationCalculator.getLocationForStep(step - component.getLocationOffset(localStep));
                
                for(Vector vec: component.getModifiedCoordinates(localStep)) {
                    Location nloc = location.clone();
                    Vector nvec = vec.clone();
                    if(component.getDirectionalCoordinates()) {
                        nvec = transform(location.getYaw(), location.getPitch(), nvec);
                    }
                    nloc.add(nvec);

                    if(blockCollisionCheck) {
                        Block block = location.getWorld().getBlockAt(nloc);
                        if(block.getType() != Material.AIR) {
                            if(!noCollisionBlocks.contains(block.getType())) {
                                boolean collision = false;
                                if(block.getBlockData() instanceof Slab) {
                                    Slab slab = (Slab) block.getBlockData();
                                    if(slab.getType() == Slab.Type.DOUBLE) collision = true;
                                    else {
                                        double f = nloc.getY() - Math.floor(nloc.getY());
                                        if(slab.getType() == Slab.Type.BOTTOM) {
                                            if(f < 0.5) collision = true;
                                        }
                                        else {
                                            if(f >= 0.5) collision = true;
                                        }
                                    }
                                }
                                else if(block.getBlockData() instanceof GlassPane) {
                                    if(((GlassPane) block.getBlockData()).getFaces().size() > 0)
                                        collision = true;
                                }
                                else {
                                    collision = true;
                                }
                                if(collision) {
                                    if(player != null && blockCollisionDetected == false) PluginHookManager.onBlockCollisionEvent(player, block);
                                    blockCollisionDetected = true;
                                    continue;
                                }
                            }
                        }
                    }

                    if(entityCollisionCheck) {
                        List<Player> entities = nloc.getWorld().getPlayers(); // TODO: Not going to deal with bats' hitboxes for now, can be added later
                        // TODO: Also not going to deal with flying / swimming / ..., for now only standing and sneaking is supported, until I find manage to get hitboxes from minecraft itself
                        for(Player entity: entities) {
                            //((CraftLivingEntity) entity).getHandle().getBoundingBoxForCulling();
                            if(entity == player) continue;
                            if(entity.hasMetadata("vanished")) continue;
                            Vector emin = entity.getLocation().toVector();
                            emin.subtract(new Vector(0.5f, 0.0f, 0.5f));
                            Vector emax = entity.getLocation().toVector();
                            emax.add(new Vector(0.5f, entity.isSneaking() ? 1.5f : 1.9f, 0.5f));
                            if(nloc.toVector().isInAABB(emin, emax)) {
                                if(player != null && entityCollisionDetected == false) PluginHookManager.onEntityCollisionEvent(player, entity);
                                entityCollisionDetected = true;
                                break;
                            }
                        }
                        if(entityCollisionDetected) continue;
                    }
                    
                    double speed = (double) component.getSpeed().getValue(step);

                    if(component.getParticle() == Particle.REDSTONE || component.getParticle() == Particle.DUST_COLOR_TRANSITION) {
                        int red = (int) (Math.round(component.getColourRed().getValue(step) * 255));
                        if(red < 0) red = 0;
                        if(red > 255) red = 255;
                        int green = (int) (Math.round(component.getColourGreen().getValue(step) * 255));
                        if(green < 0) green = 0;
                        if(green > 255) green = 255;
                        int blue = (int) (Math.round(component.getColourBlue().getValue(step) * 255));
                        if(blue < 0) blue = 0;
                        if(blue > 255) blue = 255;
                        float size = (float) component.getSize().getValue(step);

                        Particle.DustOptions dustoptions;
                        if(component.getParticle() == Particle.DUST_COLOR_TRANSITION) {
                            int tored = (int) (Math.round(component.getColourToRed().getValue(step) * 255));
                            if(tored < 0) tored = 0;
                            if(tored > 255) tored = 255;
                            int togreen = (int) (Math.round(component.getColourToGreen().getValue(step) * 255));
                            if(togreen < 0) togreen = 0;
                            if(togreen > 255) togreen = 255;
                            int toblue = (int) (Math.round(component.getColourToBlue().getValue(step) * 255));
                            if(toblue < 0) toblue = 0;
                            if(toblue > 255) toblue = 255;
                            dustoptions = new Particle.DustTransition(Color.fromRGB(red, green, blue), Color.fromRGB(tored, togreen, toblue), size);
                        }
                        else 
                            dustoptions = new Particle.DustOptions(Color.fromRGB(red, green, blue), size);

                        nloc.getWorld().spawnParticle(component.getParticle(), nloc.getX(), nloc.getY(), nloc.getZ(), (int)(component.getCount().getValue(step)),
                                                      component.getSpreadX().getValue(step), component.getSpreadY().getValue(step), component.getSpreadZ().getValue(step),
                                                      speed, dustoptions);
                    }
                    else if(component.getParticle() == Particle.ITEM_CRACK) {
                        nloc.getWorld().spawnParticle(component.getParticle(), nloc.getX(), nloc.getY(), nloc.getZ(), (int)(component.getCount().getValue(step)),
                                                      component.getSpreadX().getValue(step), component.getSpreadY().getValue(step), component.getSpreadZ().getValue(step),
                                                      speed, new ItemStack(component.getMaterial()));
                    }
                    else if(component.getParticle() == Particle.BLOCK_DUST || component.getParticle() == Particle.BLOCK_CRACK || component.getParticle() == Particle.FALLING_DUST) {
                        nloc.getWorld().spawnParticle(component.getParticle(), nloc.getX(), nloc.getY(), nloc.getZ(), (int)(component.getCount().getValue(step)),
                                                      component.getSpreadX().getValue(step), component.getSpreadY().getValue(step), component.getSpreadZ().getValue(step),
                                                      speed, component.getMaterial().createBlockData());
                    }
                    else {
                        nloc.getWorld().spawnParticle(component.getParticle(), nloc.getX(), nloc.getY(), nloc.getZ(), (int)(component.getCount().getValue(step)),
                                                      component.getSpreadX().getValue(step), component.getSpreadY().getValue(step), component.getSpreadZ().getValue(step), speed);
                    }
                }
            }
        }

        if(blockCollisionDetected || entityCollisionDetected) return false;
        return hasStep(step + 1);
    }

    private List<Player> getNearbyPlayers(Location location)
    {
        List<Player> ret = new ArrayList<>();
        List<Player> playersInWorld = location.getWorld().getPlayers();
        for(Player player: playersInWorld) {
            if(player.getLocation().distance(location) < 50) { // TODO: Does that value make sense? Should it be configurable?
                ret.add(player);
            }
        }
        return ret;
    }

    private static Vector transform(double yaw, double pitch, Vector vec) {
        final double ryaw = Math.toRadians(yaw);
        final double rpitch = -Math.toRadians(pitch);
        
        final double cosYaw = Math.cos(ryaw);
        final double sinYaw = Math.sin(ryaw);
        final double cosPitch = Math.cos(rpitch);
        final double sinPitch = Math.sin(rpitch);
        
        final Vector left = new Vector(cosYaw, 0, sinYaw);
        final Vector up = new Vector(-sinYaw * -sinPitch, cosPitch, cosYaw * -sinPitch);
        final Vector forward = new Vector(-sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
        
        return left.multiply(vec.getX())
            .add(up.multiply(vec.getY()))
            .add(forward.multiply(vec.getZ()));
    }

    public List<String> getInfo(boolean detailed) {
        List<String> ret = getInfoBase();
        ret.add("Length: " + stepsLoop);
        ret.add("Repeat: " + repeatCount);

        int count = 0;
        for(ParticleEffectComponent component: components) {
            ret.add("§e* Component: " + (++count) + "§r");
            ret.addAll(component.getInfo(detailed));
        }
        return ret;
    }

    public String getType() {
        return "Particle";
    }
    
    public final int getStepsLoop() {
        return this.stepsLoop;
    }

    public final void setStepsLoop(final int argStepsLoop) {
        this.stepsLoop = argStepsLoop;
    }

    public final int getRepeatCount() {
        return this.repeatCount;
    }

    public final void setRepeatCount(final int argRepeatCount) {
        this.repeatCount = argRepeatCount;
    }

    public final List<ParticleEffectComponent> getComponents() {
        if(components == null) components = new ArrayList<>();
        return components;
    }

    public final void addComponent(ParticleEffectComponent component) {
        if(components == null) components = new ArrayList<>();
        components.add(component);
    }

    public final void removeComponent(int index) {
        index--;
        if(index >= components.size() || index < 0) {
            throw new RuntimeException("No component " + index + "!");
        }
        components.remove(index);
    }
    
    public final void setRepeatOffset(final int argRepeatOffset) {
        repeatOffset = argRepeatOffset;
    }

    public final int getRepeatOffset() {
        return repeatOffset;
    }
}
