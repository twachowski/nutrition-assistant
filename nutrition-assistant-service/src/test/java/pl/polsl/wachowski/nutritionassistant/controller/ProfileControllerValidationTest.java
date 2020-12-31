package pl.polsl.wachowski.nutritionassistant.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import pl.polsl.wachowski.nutritionassistant.api.targets.HighlightedTargetsRequest;
import pl.polsl.wachowski.nutritionassistant.api.user.UserBiometricsRequest;
import pl.polsl.wachowski.nutritionassistant.api.user.UserBiometricsResponse;
import pl.polsl.wachowski.nutritionassistant.samples.HighlightedTargetsRequestSamples;
import pl.polsl.wachowski.nutritionassistant.samples.HighlightedTargetsResponseSamples;
import pl.polsl.wachowski.nutritionassistant.samples.UserBiometricsSamples;
import pl.polsl.wachowski.nutritionassistant.service.HighlightedTargetsService;
import pl.polsl.wachowski.nutritionassistant.service.ProfileService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.polsl.wachowski.nutritionassistant.api.NutritionAssistantApi.PROFILE_BIOMETRICS_API_SUFFIX;
import static pl.polsl.wachowski.nutritionassistant.api.NutritionAssistantApi.PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX;

@WebMvcTest(value = ProfileController.class,
            excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {
        ProfileController.class,
        ControllerExceptionHandler.class
})
class ProfileControllerValidationTest {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(new JavaTimeModule());

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProfileService profileService;
    @MockBean
    private HighlightedTargetsService highlightedTargetsService;

    @BeforeEach
    void setUp() {
        Mockito.when(profileService.getAuthenticatedUserBiometrics())
                .thenReturn(UserBiometricsSamples.shortFemale());
        Mockito.when(highlightedTargetsService.getAuthenticatedUserHighlightedTargets())
                .thenReturn(HighlightedTargetsResponseSamples.targets());
    }

    @Test
    @DisplayName("Should return user biometrics")
    void shouldReturnUserBiometrics() throws Exception {
        //given
        final String response = asJson(new UserBiometricsResponse(UserBiometricsSamples.shortFemale()));

        //when

        //then
        mvc.perform(get(PROFILE_BIOMETRICS_API_SUFFIX))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    @DisplayName("Should fail validation when trying to send null user biometrics request")
    void shouldFailValidationWhenTryingToSendNullUserBiometricsRequest() throws Exception {
        //given

        //when

        //then
        mvc.perform(put(PROFILE_BIOMETRICS_API_SUFFIX)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to update user biometrics with null biometrics")
    void shouldFailValidationWhenTryingToUpdateUserBiometricsWithNullBiometrics() throws Exception {
        //given
        final UserBiometricsRequest request = new UserBiometricsRequest(null);

        //when

        //then
        mvc.perform(put(PROFILE_BIOMETRICS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("User biometrics must not be null"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update user biometrics with null date of birth")
    void shouldFailValidationWhenTryingToUpdateUserBiometricsWithNullDateOfBirth() throws Exception {
        //given
        final UserBiometricsRequest request = new UserBiometricsRequest(UserBiometricsSamples.withNullDateOfBirth());

        //when

        //then
        mvc.perform(put(PROFILE_BIOMETRICS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Date of birth must not be null"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update user biometrics with null sex")
    void shouldFailValidationWhenTryingToUpdateUserBiometricsWithNullSex() throws Exception {
        //given
        final UserBiometricsRequest request = new UserBiometricsRequest(UserBiometricsSamples.withNullSex());

        //when

        //then
        mvc.perform(put(PROFILE_BIOMETRICS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Sex must not be null"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update user biometrics with null height")
    void shouldFailValidationWhenTryingToUpdateUserBiometricsWithNullHeight() throws Exception {
        //given
        final UserBiometricsRequest request = new UserBiometricsRequest(UserBiometricsSamples.withNullHeight());

        //when

        //then
        mvc.perform(put(PROFILE_BIOMETRICS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Height must not be null"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update user biometrics with negative height")
    void shouldFailValidationWhenTryingToUpdateUserBiometricsWithNegativeHeight() throws Exception {
        //given
        final UserBiometricsRequest request = new UserBiometricsRequest(UserBiometricsSamples.withNegativeHeight());

        //when

        //then
        mvc.perform(put(PROFILE_BIOMETRICS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Height must be positive"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update user biometrics with zero height")
    void shouldFailValidationWhenTryingToUpdateUserBiometricsWithZeroHeight() throws Exception {
        //given
        final UserBiometricsRequest request = new UserBiometricsRequest(UserBiometricsSamples.withZeroHeight());

        //when

        //then
        mvc.perform(put(PROFILE_BIOMETRICS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Height must be positive"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update user biometrics with null weight")
    void shouldFailValidationWhenTryingToUpdateUserBiometricsWithNullWeight() throws Exception {
        //given
        final UserBiometricsRequest request = new UserBiometricsRequest(UserBiometricsSamples.withNullWeight());

        //when

        //then
        mvc.perform(put(PROFILE_BIOMETRICS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Weight must not be null"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update user biometrics with negative weight")
    void shouldFailValidationWhenTryingToUpdateUserBiometricsWithNegativeWeight() throws Exception {
        //given
        final UserBiometricsRequest request = new UserBiometricsRequest(UserBiometricsSamples.withNegativeWeight());

        //when

        //then
        mvc.perform(put(PROFILE_BIOMETRICS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Weight must be positive"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update user biometrics with zero weight")
    void shouldFailValidationWhenTryingToUpdateUserBiometricsWithZeroWeight() throws Exception {
        //given
        final UserBiometricsRequest request = new UserBiometricsRequest(UserBiometricsSamples.withZeroWeight());

        //when

        //then
        mvc.perform(put(PROFILE_BIOMETRICS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Weight must be positive"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update user biometrics with null activity level")
    void shouldFailValidationWhenTryingToUpdateUserBiometricsWithNullActivityLevel() throws Exception {
        //given
        final UserBiometricsRequest request = new UserBiometricsRequest(UserBiometricsSamples.withNullActivityLevel());

        //when

        //then
        mvc.perform(put(PROFILE_BIOMETRICS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Activity level must not be null"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update user biometrics with null calorie goal")
    void shouldFailValidationWhenTryingToUpdateUserBiometricsWithNullCalorieGoal() throws Exception {
        //given
        final UserBiometricsRequest request = new UserBiometricsRequest(UserBiometricsSamples.withNullCalorieGoal());

        //when

        //then
        mvc.perform(put(PROFILE_BIOMETRICS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Calorie goal must not be null"));
    }

    @Test
    @DisplayName("Should pass validation when trying to update user biometrics")
    void shouldPassValidationWhenTryingToUpdateUserBiometrics() throws Exception {
        //given
        final UserBiometricsRequest request = new UserBiometricsRequest(UserBiometricsSamples.shortFemale());

        //when

        //then
        mvc.perform(put(PROFILE_BIOMETRICS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return highlighted targets")
    void shouldReturnHighlightedTargets() throws Exception {
        //given
        final String response = asJson(HighlightedTargetsResponseSamples.targets());

        //when

        //then
        mvc.perform(get(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    @DisplayName("Should fail validation when trying to send null highlighted targets request")
    void shouldFailValidationWhenTryingToSendNullHighlightedTargetsRequest() throws Exception {
        //given

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with null target 1")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithNullTarget1() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withNullTarget1();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 1 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with empty target 1")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithEmptyTarget1() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withEmptyTarget1();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 1 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with blank target 1")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithBlankTarget1() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withBlankTarget1();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 1 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with null target 2")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithNullTarget2() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withNullTarget2();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 2 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with empty target 2")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithEmptyTarget2() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withEmptyTarget2();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 2 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with blank target 2")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithBlankTarget2() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withBlankTarget2();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 2 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with null target 3")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithNullTarget3() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withNullTarget3();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 3 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with empty target 3")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithEmptyTarget3() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withEmptyTarget3();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 3 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with blank target 3")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithBlankTarget3() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withBlankTarget3();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 3 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with null target 4")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithNullTarget4() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withNullTarget4();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 4 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with empty target 4")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithEmptyTarget4() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withEmptyTarget4();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 4 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with blank target 4")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithBlankTarget4() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withBlankTarget4();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 4 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with null target 5")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithNullTarget5() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withNullTarget5();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 5 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with empty target 5")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithEmptyTarget5() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withEmptyTarget5();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 5 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with blank target 5")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithBlankTarget5() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withBlankTarget5();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 5 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with null target 6")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithNullTarget6() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withNullTarget6();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 6 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with empty target 6")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithEmptyTarget6() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withEmptyTarget6();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 6 must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to update highlighted targets with blank target 6")
    void shouldFailValidationWhenTryingToUpdateHighlightedTargetsWithBlankTarget6() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withBlankTarget6();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Target 6 must not be blank"));
    }

    @Test
    @DisplayName("Should pass validation when trying to update highlighted targets")
    void shouldPassValidationWhenTryingToUpdateHighlightedTargets() throws Exception {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.request();

        //when

        //then
        mvc.perform(put(PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX)
                            .content(asJson(request))
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