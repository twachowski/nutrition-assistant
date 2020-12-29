import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {FoodSearchItem} from '../model/food/food-search-item';
import {RoutingService} from './routing.service';
import {FoodSearchItemResponse} from '../model/food/food-search-item-response';
import {FoodResponse} from '../model/food/food-response';

@Injectable({
  providedIn: 'root'
})
export class FoodService {

  constructor(
    private readonly http: HttpClient,
    private readonly routingService: RoutingService) {
  }

  search(foodName: string) {
    const url = this.routingService.getFoodsUrl();
    const queryParam = new HttpParams().set('query', foodName);
    return this.http.get<FoodSearchItemResponse>(url, {params: queryParam});
  }

  getDetails(foodItem: FoodSearchItem) {
    const url = this.routingService.getFoodUrl(foodItem.id);
    const providerParam = new HttpParams().set('provider', foodItem.provider);
    return this.http.get<FoodResponse>(url, {params: providerParam});
  }

}
