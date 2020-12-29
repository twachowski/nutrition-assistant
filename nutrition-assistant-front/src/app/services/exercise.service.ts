import { Injectable } from '@angular/core';
import { ExerciseSearchRequest } from '../model/exercise/exercise-search-request';
import { HttpClient } from '@angular/common/http';
import { ExerciseSearchResults } from '../model/exercise/exercise-search-results';
import { RoutingService } from './routing.service';

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  constructor(
    private readonly http: HttpClient,
    private readonly routingService: RoutingService) { }

  search(exercise: string) {
    const url = this.routingService.getExerciseSearchUrl();
    const request: ExerciseSearchRequest = {
      query: exercise
    };
    const body = JSON.stringify(request);
    return this.http.post<ExerciseSearchResults>(url, body);
  }

}
