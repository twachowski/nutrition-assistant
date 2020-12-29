import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {ExerciseSearchResults} from '../model/exercise/exercise-search-results';
import {RoutingService} from './routing.service';

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  constructor(
    private readonly http: HttpClient,
    private readonly routingService: RoutingService) {
  }

  search(exercise: string) {
    const url = this.routingService.getExercisesUrl();
    const queryParam = new HttpParams().set('query', exercise);
    return this.http.get<ExerciseSearchResults>(url, {params: queryParam});
  }

}
