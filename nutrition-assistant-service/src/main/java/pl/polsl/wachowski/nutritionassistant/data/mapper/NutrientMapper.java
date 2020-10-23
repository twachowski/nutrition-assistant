package pl.polsl.wachowski.nutritionassistant.data.mapper;


import pl.polsl.wachowski.nutritionassistant.api.nutrients.Nutrient;

public interface NutrientMapper {

    Nutrient get(final Integer id);

}
