package pl.polsl.wachowski.nutritionassistant.def.measure;

public enum EnergyUnit implements Unit {

    KCAL("kcal"),
    KJ("kJ");

    private final String symbol;

    EnergyUnit(final String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

}
