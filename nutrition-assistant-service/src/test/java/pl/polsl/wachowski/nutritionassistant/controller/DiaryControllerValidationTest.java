package pl.polsl.wachowski.nutritionassistant.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pl.polsl.wachowski.nutritionassistant.api.diary.DiaryEntriesResponse;
import pl.polsl.wachowski.nutritionassistant.facade.DiaryFacade;
import pl.polsl.wachowski.nutritionassistant.samples.EntryEditRequestSamples;
import pl.polsl.wachowski.nutritionassistant.samples.EntryMoveRequestSamples;
import pl.polsl.wachowski.nutritionassistant.samples.NewEntryRequestSamples;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.polsl.wachowski.nutritionassistant.api.NutritionAssistantApi.*;

@WebMvcTest(value = DiaryController.class,
            excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {
        DiaryController.class,
        ControllerExceptionHandler.class
})
class DiaryControllerValidationTest {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    @Autowired
    private MockMvc mvc;
    @MockBean
    private DiaryFacade diaryFacade;

    @BeforeEach
    void setUp() {
        Mockito.when(diaryFacade.getEntries(LocalDate.now()))
                .thenReturn(DiaryEntriesResponse.EMPTY_INSTANCE);
    }

    @Test
    @DisplayName("Should return 404 status code when trying to search entries with null diary date path parameter")
    void shouldReturn404StatusCodeWhenTryingToSearchEntriesWithNullDiaryDatePathParameter() throws Exception {
        //given
        final LocalDate diaryDate = null;

        //when

        //then
        mvc.perform(get(DIARY_DAY_API_SUFFIX, diaryDate))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should fail validation when trying to search entries with invalid diary date path parameter")
    void shouldFailValidationWhenTryingToSearchEntriesWithInvalidDiaryDatePathParameter() throws Exception {
        //given
        final String diaryDate = "2020.12.30";

        //when

        //then
        mvc.perform(get(DIARY_DAY_API_SUFFIX, diaryDate))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return set diary entries")
    void shouldReturnDiaryEntries() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();

        //when

        //then
        mvc.perform(get(DIARY_DAY_API_SUFFIX, diaryDate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("foodEntries").isArray())
                .andExpect(jsonPath("exerciseEntries").isArray())
                .andExpect(jsonPath("noteEntries").isArray());
    }

    @Test
    @DisplayName("Should fail validation when trying to add entry with invalid diary date path parameter")
    void shouldFailValidationWhenTryingToAddEntryWithInvalidDiaryDatePathParameter() throws Exception {
        //given
        final String diaryDate = "2020;12;30";
        final String request = asJson(NewEntryRequestSamples.validFoodRequest());

        //when

        //then
        mvc.perform(post(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to send null new entry request")
    void shouldFailValidationWhenTryingToSendNullNewEntryRequest() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();

        //when

        //then
        mvc.perform(post(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to send new entry request with null entry type")
    void shouldFailValidationWhenTryingToSendNewEntryRequestWithNullEntryType() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String request = asJson(NewEntryRequestSamples.withAllNullFields());

        //when

        //then
        mvc.perform(post(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Entry type must not be null"));
    }

    @Test
    @DisplayName("Should fail validation when trying to add null food")
    void shouldFailValidationWhenTryingToAddNullFood() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String request = asJson(NewEntryRequestSamples.nullFoodRequest());

        //when

        //then
        mvc.perform(post(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Food entry must not be null in new food entry request"));
    }

    @Test
    @DisplayName("Should fail validation when trying to add null exercise")
    void shouldFailValidationWhenTryingToAddNullExercise() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String request = asJson(NewEntryRequestSamples.nullExerciseRequest());

        //when

        //then
        mvc.perform(post(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Exercise entry must not be null in new exercise entry request"));
    }

    @Test
    @DisplayName("Should fail validation when trying to add null note")
    void shouldFailValidationWhenTryingToAddNullNote() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String request = asJson(NewEntryRequestSamples.nullNoteRequest());

        //when

        //then
        mvc.perform(post(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Note entry must not be null in new note entry request"));
    }

    @Test
    @DisplayName("Should pass validation when trying to add food")
    void shouldPassValidationWhenTryingToAddFood() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String request = asJson(NewEntryRequestSamples.validFoodRequest());

        //when

        //then
        mvc.perform(post(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should pass validation when trying to add exercise")
    void shouldPassValidationWhenTryingToAddExercise() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String request = asJson(NewEntryRequestSamples.validExerciseRequest());

        //when

        //then
        mvc.perform(post(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should pass validation when trying to add note")
    void shouldPassValidationWhenTryingToAddNote() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String request = asJson(NewEntryRequestSamples.validNoteRequest());

        //when

        //then
        mvc.perform(post(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should fail validation when trying to edit entry with invalid diary date path parameter")
    void shouldFailValidationWhenTryingToEditEntryWithInvalidDiaryDatePathParameter() throws Exception {
        //given
        final String diaryDate = "2020;12;30";
        final short entryPosition = 0;
        final String request = asJson(EntryEditRequestSamples.validFoodRequest());

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRY_POSITION_API_SUFFIX, diaryDate, entryPosition)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to edit entry with invalid entry position path parameter")
    void shouldFailValidationWhenTryingToEditEntryWithInvalidEntryPositionPathParameter() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String entryPosition = "zero";
        final String request = asJson(EntryEditRequestSamples.validFoodRequest());

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRY_POSITION_API_SUFFIX, diaryDate, entryPosition)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to send null entry edit request")
    void shouldFailValidationWhenTryingToSendNullEntryEditRequest() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final Short entryPosition = 0;

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRY_POSITION_API_SUFFIX, diaryDate, entryPosition)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to send entry edit request with null entry type")
    void shouldFailValidationWhenTryingToSendEntryEditRequestWithNullEntryType() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final Short entryPosition = 0;
        final String request = asJson(EntryEditRequestSamples.withAllNullFields());

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRY_POSITION_API_SUFFIX, diaryDate, entryPosition)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Entry type must not be null"));
    }

    @Test
    @DisplayName("Should fail validation when trying to send food entry edit request with null food entry")
    void shouldFailValidationWhenTryingToSendFoodEntryEditRequestWithNullFoodEntry() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final Short entryPosition = 0;
        final String request = asJson(EntryEditRequestSamples.nullFoodRequest());

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRY_POSITION_API_SUFFIX, diaryDate, entryPosition)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Edited food entry must not be null in food entry edit request"));
    }

    @Test
    @DisplayName("Should fail validation when trying to send exercise entry edit request with null exercise entry")
    void shouldFailValidationWhenTryingToSendExerciseEntryEditRequestWithNullExerciseEntry() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final Short entryPosition = 0;
        final String request = asJson(EntryEditRequestSamples.nullExerciseRequest());

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRY_POSITION_API_SUFFIX, diaryDate, entryPosition)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Edited exercise entry must not be null in exercise entry edit request"));
    }

    @Test
    @DisplayName("Should fail validation when trying to send note entry edit request with null note entry")
    void shouldFailValidationWhenTryingToSendNoteEntryEditRequestWithNullNoteEntry() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final Short entryPosition = 0;
        final String request = asJson(EntryEditRequestSamples.nullNoteRequest());

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRY_POSITION_API_SUFFIX, diaryDate, entryPosition)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Edited note entry must not be null in note entry edit request"));
    }

    @Test
    @DisplayName("Should pass validation when trying to edit food")
    void shouldPassValidationWhenTryingToEditFood() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final Short entryPosition = 0;
        final String request = asJson(EntryEditRequestSamples.validFoodRequest());

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRY_POSITION_API_SUFFIX, diaryDate, entryPosition)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should pass validation when trying to edit exercise")
    void shouldPassValidationWhenTryingToEditExercise() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final Short entryPosition = 0;
        final String request = asJson(EntryEditRequestSamples.validExerciseRequest());

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRY_POSITION_API_SUFFIX, diaryDate, entryPosition)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should pass validation when trying to edit note")
    void shouldPassValidationWhenTryingToEditNote() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final Short entryPosition = 0;
        final String request = asJson(EntryEditRequestSamples.validNoteRequest());

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRY_POSITION_API_SUFFIX, diaryDate, entryPosition)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should fail validation when trying to delete entry with invalid diary date path parameter")
    void shouldFailValidationWhenTryingToDeleteEntryWithInvalidDiaryDatePathParameter() throws Exception {
        //given
        final String diaryDate = "2020;12;30";
        final Short entryPosition = 0;

        //when

        //then
        mvc.perform(delete(DIARY_DAY_ENTRY_POSITION_API_SUFFIX, diaryDate, entryPosition))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to delete entry with invalid entry position path parameter")
    void shouldFailValidationWhenTryingToDeleteEntryWithInvalidEntryPositionPathParameter() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String entryPosition = "zero";

        //when

        //then
        mvc.perform(delete(DIARY_DAY_ENTRY_POSITION_API_SUFFIX, diaryDate, entryPosition))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should pass validation when trying to delete entry")
    void shouldPassValidationWhenTryingToDeleteEntry() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final Short entryPosition = 0;

        //when

        //then
        mvc.perform(delete(DIARY_DAY_ENTRY_POSITION_API_SUFFIX, diaryDate, entryPosition))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should fail validation when trying to move entry with invalid diary date path parameter")
    void shouldFailValidationWhenTryingToMoveEntryWithInvalidDiaryDatePathParameter() throws Exception {
        //given
        final String diaryDate = "2020;12;30";
        final String request = asJson(EntryMoveRequestSamples.validWithSamePositions());

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to send null entry move request")
    void shouldFailValidationWhenTryingToSendNullEntryMoveRequest() throws Exception {
        //given
        final String diaryDate = "2020;12;30";

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to move entry with null previous position")
    void shouldFailValidationWhenTryingToMoveEntryWithNullPreviousPosition() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String request = asJson(EntryMoveRequestSamples.withNullPreviousPosition());

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Previous position must not be null"));
    }

    @Test
    @DisplayName("Should fail validation when trying to move entry with negative previous position")
    void shouldFailValidationWhenTryingToMoveEntryWithNegativePreviousPosition() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String request = asJson(EntryMoveRequestSamples.withNegativePreviousPosition());

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Previous position must not be negative"));
    }

    @Test
    @DisplayName("Should fail validation when trying to move entry with null current position")
    void shouldFailValidationWhenTryingToMoveEntryWithNullCurrentPosition() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String request = asJson(EntryMoveRequestSamples.withNullCurrentPosition());

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Current position must not be null"));
    }

    @Test
    @DisplayName("Should fail validation when trying to move entry with negative current position")
    void shouldFailValidationWhenTryingToMoveEntryWithNegativeCurrentPosition() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String request = asJson(EntryMoveRequestSamples.withNegativeCurrentPosition());

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Current position must not be negative"));
    }

    @Test
    @DisplayName("Should pass validation when trying to move entry with different positions")
    void shouldPassValidationWhenTryingToMoveEntryWithDifferentPositions() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String request = asJson(EntryMoveRequestSamples.validWithDifferentPositions());

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should pass validation when trying to move entry with same positions")
    void shouldPassValidationWhenTryingToMoveEntryWithSamePositions() throws Exception {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String request = asJson(EntryMoveRequestSamples.validWithSamePositions());

        //when

        //then
        mvc.perform(patch(DIARY_DAY_ENTRIES_API_SUFFIX, diaryDate)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private static String asJson(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}