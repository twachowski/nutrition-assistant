package pl.polsl.wachowski.nutritionassistant.def.activity;

public enum ActivityLevel {
    SEDENTARY(1.2f),
    LIGHT(1.375f),
    MODERATE(1.55f),
    HIGH(1.725f),
    EXTRA(1.9f);

    private final float modifier;

    ActivityLevel(final float modifier) {
        this.modifier = modifier;
    }

    public float getModifier() {
        return modifier;
    }

}
