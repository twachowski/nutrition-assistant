package pl.polsl.wachowski.nutritionassistant.domain.util;

import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.api.units.FoodMassUnit;
import pl.polsl.wachowski.nutritionassistant.api.units.TimeUnit;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.DiaryEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.ExerciseEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.FoodEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.NoteEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class DiaryEntryEntitySamples {

    private DiaryEntryEntitySamples() {
    }

    public static DiaryEntryEntity ofTodayWithAllEntries(final UserEntity user) {
        final DiaryEntryEntity diaryEntry = new DiaryEntryEntity(LocalDate.now(), user);
        diaryEntry.add(new FoodEntryEntity("12345",
                                           NutritionDataProvider.USDA,
                                           FoodMassUnit.GRAM,
                                           BigDecimal.ONE,
                                           (short) 0,
                                           diaryEntry));
        diaryEntry.add(new ExerciseEntryEntity("running",
                                               TimeUnit.HOUR,
                                               BigDecimal.ONE,
                                               (short) 1,
                                               diaryEntry));
        diaryEntry.add(new NoteEntryEntity("content",
                                           (short) 2,
                                           diaryEntry));
        return diaryEntry;
    }

}
