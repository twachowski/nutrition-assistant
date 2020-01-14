package pl.polsl.wachowski.nutritionassistant.def.measure;

public enum MassUnit implements Unit {

    MICROGRAM("µg"),
    MILLIGRAM("mg"),
    GRAM("g"),
    IU("IU");

    private final String symbol;

    MassUnit(final String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

}
