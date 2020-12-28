package pl.polsl.wachowski.nutritionassistant.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import pl.polsl.wachowski.fdc.client.FdcClient;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.EditedFoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.food.Food;
import pl.polsl.wachowski.nutritionassistant.api.food.FoodBasicData;
import pl.polsl.wachowski.nutritionassistant.api.food.NutrientDetails;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.Nutrient;
import pl.polsl.wachowski.nutritionassistant.api.units.FoodMassUnit;
import pl.polsl.wachowski.nutritionassistant.client.MockFdcClient;
import pl.polsl.wachowski.nutritionassistant.client.MockNutritionixClient;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.DiaryEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.FoodEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.repository.DiaryRepository;
import pl.polsl.wachowski.nutritionassistant.domain.repository.FoodRepository;
import pl.polsl.wachowski.nutritionassistant.domain.repository.UserRepository;
import pl.polsl.wachowski.nutritionassistant.exception.entry.EntryNotFoundException;
import pl.polsl.wachowski.nutritionassistant.util.FoodServiceSamples;
import pl.polsl.wachowski.nutritionassistant.util.NutrientHelper;
import pl.polsl.wachowski.nutritionassistant.util.UserEntitySamples;
import pl.polsl.wachowski.nutritionix.client.NutritionixClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@ContextConfiguration(classes = {UserRepository.class, DiaryRepository.class, FoodRepository.class})
@EnableJpaRepositories(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.repository")
@EntityScan(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.db")
class FoodServiceTest {

    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;
    private final FoodService foodService;

    @Autowired
    FoodServiceTest(final UserRepository userRepository,
                    final DiaryRepository diaryRepository,
                    final FoodRepository foodRepository) {
        this.userRepository = userRepository;
        this.diaryRepository = diaryRepository;
        final FdcClient fdcClient = new MockFdcClient();
        final NutritionixClient nutritionixClient = new MockNutritionixClient();
        this.foodService = FoodServiceSamples.foodService(foodRepository,
                                                          fdcClient,
                                                          nutritionixClient);
    }

    @Test
    @DisplayName("Should return set of foods")
    void shouldReturnSetOfFoods() {
        //given
        final String query = "potato";

        //when
        final Set<FoodBasicData> foods = foodService.searchFoods(query);

        //then
        assertNotNull(foods);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when null provider is given")
    void shouldThrowIllegalStateExceptionWhenNullProviderIsGiven() {
        //given
        final String foodId = "12345";
        final NutritionDataProvider provider = null;
        final Executable executable = () -> foodService.getFood(foodId, provider);

        //when

        //then
        final IllegalStateException exception = assertThrows(IllegalStateException.class, executable);
        assertTrue(exception.getMessage().startsWith("Provider"));
    }

    @Test
    @DisplayName("Should return food containing all mandatory nutrients")
    void shouldReturnFoodContainingAllMandatoryNutrients() {
        //given
        final String foodId = "12345";
        final NutritionDataProvider provider = NutritionDataProvider.USDA;

        //when
        final Food food = foodService.getFood(foodId, provider);
        final Set<Nutrient> nutrients = food.getNutrients()
                .stream()
                .map(NutrientDetails::getNutrient)
                .collect(Collectors.toSet());

        //then
        assertTrue(nutrients.containsAll(NutrientHelper.MANDATORY_NUTRIENTS));
    }

    @Test
    @DisplayName("Should throw EntryNotFoundException when no food entry has given position")
    void shouldThrowEntryNotFoundExceptionWhenNoFoodEntryHasGivenPosition() {
        //given
        final List<FoodEntryEntity> foodEntries = Collections.emptyList();
        final short entryPosition = 0;
        final EditedFoodEntry editedFoodEntry = new EditedFoodEntry(FoodMassUnit.OUNCE, BigDecimal.TEN);
        final Executable executable = () -> foodService.editFoodEntry(foodEntries,
                                                                      entryPosition,
                                                                      editedFoodEntry);

        //when

        //then
        assertThrows(EntryNotFoundException.class, executable);
    }

    @Test
    @DisplayName("Should edit food entry")
    void shouldEditFoodEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final DiaryEntryEntity diaryEntry = new DiaryEntryEntity(diaryDate, user);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        final short foodEntryPosition = 0;
        final FoodEntryEntity oldFoodEntry = new FoodEntryEntity("12345",
                                                                 NutritionDataProvider.USDA,
                                                                 FoodMassUnit.GRAM,
                                                                 BigDecimal.ONE,
                                                                 foodEntryPosition,
                                                                 diaryEntry);
        diaryEntry.add(oldFoodEntry);
        final EditedFoodEntry editedFoodEntry = new EditedFoodEntry(FoodMassUnit.OUNCE, BigDecimal.TEN);

        //when
        userRepository.save(user);
        final FoodMassUnit oldFoodEntryMassUnit = oldFoodEntry.getUnit();
        final BigDecimal oldFoodEntryAmount = oldFoodEntry.getAmount();
        foodService.editFoodEntry(diaryEntry.getFoodEntries(),
                                  foodEntryPosition,
                                  editedFoodEntry);
        final FoodEntryEntity newFoodEntry = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate)
                .getFoodEntries()
                .iterator()
                .next();

        //then
        assertNotEquals(editedFoodEntry.getMassUnit(), oldFoodEntryMassUnit);
        assertNotEquals(editedFoodEntry.getAmount(), oldFoodEntryAmount);
        assertEquals(editedFoodEntry.getMassUnit(), newFoodEntry.getUnit());
        assertEquals(editedFoodEntry.getAmount(), newFoodEntry.getAmount());
    }

}