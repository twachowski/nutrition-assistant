import { Injectable } from '@angular/core';
import { ActivityLevel } from '../model/user/activity-level.enum';

@Injectable({
  providedIn: 'root'
})
export class ActivityService {

  private readonly activityLevelModifierMap = new Map<ActivityLevel, number>([
    [ActivityLevel.NONE, 1],
    [ActivityLevel.SEDENTARY, 1.2],
    [ActivityLevel.LIGHT, 1.375],
    [ActivityLevel.MODERATE, 1.55],
    [ActivityLevel.HIGH, 1.725],
    [ActivityLevel.EXTRA, 1.9]
  ]);

  constructor() { }

  getModifier(activityLevel: ActivityLevel) {
    return this.activityLevelModifierMap.get(activityLevel);
  }

}
