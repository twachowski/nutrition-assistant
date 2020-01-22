package pl.polsl.wachowski.nutritionassistant.def.measure;

public enum TimeUnit implements Unit {

    MINUTE("min"),
    HOUR("h");

    private final String symbol;

    TimeUnit(final String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

}
