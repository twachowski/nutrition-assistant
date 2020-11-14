package pl.polsl.wachowski.nutritionassistant.domain.db.entry;

public interface Sortable {

    Short getPosition();

    void setPosition(final Short position);

    void moveUp();

}
