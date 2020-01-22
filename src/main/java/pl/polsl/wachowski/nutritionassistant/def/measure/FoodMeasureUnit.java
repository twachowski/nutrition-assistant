package pl.polsl.wachowski.nutritionassistant.def.measure;

public enum FoodMeasureUnit implements Unit {

    GRAM("g"),
    OUNCE("oz");

    private final String symbol;

    FoodMeasureUnit(final String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

}
