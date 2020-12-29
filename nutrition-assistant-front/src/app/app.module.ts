import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTabsModule} from '@angular/material/tabs';
import {MatIconModule} from '@angular/material/icon';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {FlexLayoutModule} from '@angular/flex-layout';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RegisterDialogComponent} from './register-dialog/register-dialog.component';
import {DialogWithToolbarComponent} from './dialog-with-toolbar/dialog-with-toolbar.component';
import {LoginDialogComponent} from './login-dialog/login-dialog.component';
import {DiaryScreenComponent} from './diary-screen/diary-screen.component';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatCardModule} from '@angular/material/card';
import {MatTableModule} from '@angular/material/table';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MomentDateModule} from '@angular/material-moment-adapter';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {ActivitiesComponent} from './diary-screen/activities/activities.component';
import {InformationComponent} from './diary-screen/information/information.component';
import {ButtonContainerComponent} from './diary-screen/containers/button-container/button-container.component';
import {DishButtonContainerComponent} from './diary-screen/containers/dish-button-container/dish-button-container.component';
import {ExerciseButtonContainerComponent} from './diary-screen/containers/exercise-button-container/exercise-button-container.component';
import {NoteButtonContainerComponent} from './diary-screen/containers/note-button-container/note-button-container.component';
import {EntriesContainerComponent} from './diary-screen/entries-container/entries-container.component';
import {DatepickerComponent} from './diary-screen/information/datepicker/datepicker.component';
import {GeneralProgressComponent} from './diary-screen/information/general-progress/general-progress.component';
import {NgCircleProgressModule} from 'ng-circle-progress';
import {ElementaryProgressComponent} from './diary-screen/information/elementary-progress/elementary-progress.component';
import {CalorieOverviewComponent} from './diary-screen/information/calorie-overview/calorie-overview.component';
import {CalorieDetailComponent} from './diary-screen/information/calorie-overview/calorie-detail/calorie-detail.component';
import {NutrientDetailsComponent} from './diary-screen/information/nutrients/nutrient-details/nutrient-details.component';
import {GeneralNutrientDetailsComponent} from './diary-screen/information/nutrients/general-nutrient-details/general-nutrient-details.component';
import {CarbohydratesDetailsComponent} from './diary-screen/information/nutrients/carbohydrates-details/carbohydrates-details.component';
import {LipidsDetailsComponent} from './diary-screen/information/nutrients/lipids-details/lipids-details.component';
import {ProteinDetailsComponent} from './diary-screen/information/nutrients/protein-details/protein-details.component';
import {VitaminDetailsComponent} from './diary-screen/information/nutrients/vitamin-details/vitamin-details.component';
import {MineralDetailsComponent} from './diary-screen/information/nutrients/mineral-details/mineral-details.component';
import {ProgressBarComponent} from './diary-screen/information/progress-bar/progress-bar.component';
import {UndefinedProgressBarComponent} from './diary-screen/information/undefined-progress-bar/undefined-progress-bar.component';
import {MatMenuModule} from '@angular/material/menu';
import {MatSelectModule} from '@angular/material/select';
import {BasicToolbarComponent} from './toolbar/basic-toolbar/basic-toolbar.component';
import {LoggedInToolbarComponent} from './toolbar/logged-in-toolbar/logged-in-toolbar.component';
import {NoteEditDialogComponent} from './diary-screen/entry-edit-dialogs/note-edit-dialog/note-edit-dialog.component';
import {FoodEditDialogComponent} from './diary-screen/entry-edit-dialogs/food-edit-dialog/food-edit-dialog.component';
import {ExerciseEditDialogComponent} from './diary-screen/entry-edit-dialogs/exercise-edit-dialog/exercise-edit-dialog.component';
import {FoodSearchDialogComponent} from './diary-screen/activities/food-search-dialog/food-search-dialog.component';
import {ExerciseSearchDialogComponent} from './diary-screen/activities/exercise-search-dialog/exercise-search-dialog.component';
import {NoteAddDialogComponent} from './diary-screen/activities/note-add-dialog/note-add-dialog.component';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatBottomSheetModule} from '@angular/material/bottom-sheet';
import {FoodDetailsSheetComponent} from './diary-screen/activities/food-search-dialog/food-details-sheet/food-details-sheet.component';
import {ProfileScreenComponent} from './profile-screen/profile-screen.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {TargetsSettingsComponent} from './profile-screen/targets-settings/targets-settings.component';
import {BiometricsSettingsComponent} from './profile-screen/biometrics-settings/biometrics-settings.component';
import {MatRadioModule} from '@angular/material/radio';
import {WelcomeScreenComponent} from './welcome-screen/welcome-screen.component';
import {FeatureDescriptionComponent} from './welcome-screen/feature/feature-description/feature-description.component';
import {BiometricsAndGoalsDescriptionComponent} from './welcome-screen/feature/biometrics-and-goals-description/biometrics-and-goals-description.component';
import {FoodsDescriptionComponent} from './welcome-screen/feature/foods-description/foods-description.component';
import {ExerciseDescriptionComponent} from './welcome-screen/feature/exercise-description/exercise-description.component';
import {NoteDescriptionComponent} from './welcome-screen/feature/note-description/note-description.component';
import {NutritionTrackingDescriptionComponent} from './welcome-screen/feature/nutrition-tracking-description/nutrition-tracking-description.component';
import {HttpErrorInterceptor} from './interceptors/http-error-interceptor';
import {RegistrationConfirmComponent} from './registration-confirm/registration-confirm.component';
import {HttpHeaderInterceptor} from './interceptors/http-header-interceptor';
import {MatTooltipModule} from '@angular/material/tooltip';
import {ErrorSnackBarComponent} from './error-snack-bar/error-snack-bar.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterDialogComponent,
    LoginDialogComponent,
    DialogWithToolbarComponent,
    DiaryScreenComponent,
    ActivitiesComponent,
    InformationComponent,
    ButtonContainerComponent,
    DishButtonContainerComponent,
    ExerciseButtonContainerComponent,
    NoteButtonContainerComponent,
    EntriesContainerComponent,
    DatepickerComponent,
    GeneralProgressComponent,
    ElementaryProgressComponent,
    CalorieOverviewComponent,
    CalorieDetailComponent,
    NutrientDetailsComponent,
    GeneralNutrientDetailsComponent,
    CarbohydratesDetailsComponent,
    LipidsDetailsComponent,
    ProteinDetailsComponent,
    VitaminDetailsComponent,
    MineralDetailsComponent,
    ProgressBarComponent,
    UndefinedProgressBarComponent,
    BasicToolbarComponent,
    LoggedInToolbarComponent,
    NoteEditDialogComponent,
    FoodEditDialogComponent,
    ExerciseEditDialogComponent,
    FoodSearchDialogComponent,
    ExerciseSearchDialogComponent,
    NoteAddDialogComponent,
    FoodDetailsSheetComponent,
    ProfileScreenComponent,
    TargetsSettingsComponent,
    BiometricsSettingsComponent,
    WelcomeScreenComponent,
    FeatureDescriptionComponent,
    BiometricsAndGoalsDescriptionComponent,
    FoodsDescriptionComponent,
    ExerciseDescriptionComponent,
    NoteDescriptionComponent,
    NutritionTrackingDescriptionComponent,
    RegistrationConfirmComponent,
    ErrorSnackBarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatCheckboxModule,
    MatTabsModule,
    MatSnackBarModule,
    MatButtonModule,
    MatSlideToggleModule,
    MatIconModule,
    FlexLayoutModule,
    DragDropModule,
    MatCardModule,
    MatTableModule,
    MatDatepickerModule,
    MomentDateModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    NgCircleProgressModule.forRoot({
      radius: 50,
      outerStrokeWidth: 3,
      innerStrokeWidth: 1,
      outerStrokeColor: '#558b2f',
      innerStrokeColor: '#ffab00',
      showSubtitle: false,
      renderOnClick: false,
      titleColor: 'white',
      unitsColor: 'white',
      titleFontSize: '24',
      unitsFontSize: '24',
      space: 6,
      outerStrokeLinecap: 'sting',
      startFromZero: false
    }),
    MatToolbarModule,
    MatMenuModule,
    MatDialogModule,
    MatSelectModule,
    FormsModule,
    MatProgressSpinnerModule,
    MatExpansionModule,
    MatBottomSheetModule,
    MatSidenavModule,
    MatListModule,
    MatRadioModule,
    MatTooltipModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpHeaderInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    RegisterDialogComponent,
    LoginDialogComponent,
    FoodEditDialogComponent,
    ExerciseEditDialogComponent,
    NoteEditDialogComponent,
    FoodSearchDialogComponent,
    ExerciseSearchDialogComponent,
    NoteAddDialogComponent,
    FoodDetailsSheetComponent,
    ErrorSnackBarComponent]
})
export class AppModule {
}
