package me.ez.jej.client;

// Generated from art/blockbench/table.bbmodel; do not edit by hand.
public final class JuiceTableAnimation {
    public static final float LENGTH = 4F;
    public static final float[] TIMES = {0F, 1.75F, 2.25F, 4F};
    public static final float[] ROTATION = {0.0F, 540.0F, 540.0F, 0.0F};
    public static final float[] SCREW_POSITION = {0.0F, -0.6F, -0.6F, 0.0F};
    public static final float[] RAM_POSITION = {0.0F, -0.6F, -0.6F, 0.0F};
    public static final float[] SCREW_PIVOT = {0.525F, 1.796875F, 0.40625F};
    public static final float[] RAM_PIVOT = {0.53125F, 1.340625F, 0.40625F};
    public static float sample(float[] values, float time) {
        time = Math.max(0, time) % LENGTH;
        for (int i = 1; i < TIMES.length; i++) {
            if (time <= TIMES[i]) return values[i-1] + (values[i] - values[i-1]) * (time - TIMES[i-1]) / (TIMES[i] - TIMES[i-1]);
        }
        return values[values.length-1];
    }
}
