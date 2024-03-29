package org.cubeville.effects.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("ParticleEffectTimelineEntry")
public class ParticleEffectTimelineEntry implements ConfigurationSerializable
{
    int stepStart;
    int stepRepeat;
    int stepCount;
    int locationOffset;
    
    public ParticleEffectTimelineEntry(int stepStart, int stepRepeat, int stepCount, int locationOffset) {
	this.stepStart = stepStart;
	this.stepRepeat = stepRepeat;
	this.stepCount = stepCount;
        this.locationOffset = locationOffset;
    }

    public ParticleEffectTimelineEntry(Map<String, Object> config) {
	stepStart = (int) config.get("stepStart");
	stepRepeat = (int) config.get("stepRepeat");
	stepCount = (int) config.get("stepCount");
        if(config.get("locationOffset") != null)
            locationOffset = (int) config.get("locationOffset");
        else
            locationOffset = 0;
    }

    public Map<String, Object> serialize() {
	Map<String, Object> ret = new HashMap<>();
	ret.put("stepStart", stepStart);
	ret.put("stepRepeat", stepRepeat);
	ret.put("stepCount", stepCount);
        ret.put("locationOffset", locationOffset);
	return ret;
    }
    
    public final int getStepStart() {
	return this.stepStart;
    }

    public final void setStepStart(final int argStepStart) {
	this.stepStart = argStepStart;
    }

    public final int getStepRepeat() {
	return this.stepRepeat;
    }

    public final void setStepRepeat(final int argStepRepeat) {
	this.stepRepeat = argStepRepeat;
    }

    public final int getStepCount() {
	return this.stepCount;
    }

    public final void setStepCount(final int argStepCount) {
	this.stepCount = argStepCount;
    }

    public final int getLocationOffset() {
        return this.locationOffset;
    }

    public final void setLocationOffset(final int locationOffset) {
        this.locationOffset = locationOffset;
    }
}
