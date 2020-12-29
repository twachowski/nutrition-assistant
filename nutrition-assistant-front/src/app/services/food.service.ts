import { Injectable } from '@angular/core';
import { FoodSearchRequest } from '../model/food/food-search-request';
import { HttpClient } from '@angular/common/http';
import { FoodSearchItem } from '../model/food/food-search-item';
import { FoodDetailsRequest } from '../model/food/food-details-request';
import { FoodDetailsResponse } from '../model/food/food-details-response';
import { RoutingService } from './routing.service';

@Injectable({
  providedIn: 'root'
})
export class FoodService {

  constructor(
    private readonly http: HttpClient,
    private readonly routingService: RoutingService) { }

  search(foodName: string) {
    const url = this.routingService.getFoodSearchUrl();
    const request: FoodSearchRequest = {
      query: foodName
    };
    const body = JSON.stringify(request);
    return this.http.post<FoodSearchItem[]>(url, body);
  }

  getDetails(foodItem: FoodSearchItem) {
    const url = this.routingService.getFoodDetailsUrl();
    const request: FoodDetailsRequest = {
      externalId: foodItem.externalId,
      provider: foodItem.provider
    };
    const body = JSON.stringify(request);
    return this.http.post<FoodDetailsResponse>(url, body);
  }

}
