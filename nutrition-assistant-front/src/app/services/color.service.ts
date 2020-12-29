import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ColorService {

  private readonly colorMap = new Map<number, string>();
  private readonly lastIndex: number;

  constructor() {
    const coeff1 = 255 / 50;
    const coeff2 = 127 / 50;
    let r = 255;
    let g = 0;
    for (let i = 0; i < 50; i++) {
      this.colorMap.set(i, this.rgb(r, Math.round(g)));
      g += coeff1;
    }
    for (let i = 50; i <= 100; i++) {
      this.colorMap.set(i, this.rgb(Math.round(r), Math.round(g)));
      r -= coeff1;
      g -= coeff2;
    }
    this.lastIndex = this.colorMap.size - 1;
  }

  private rgb(r: number, g: number) {
    return `rgb(${r},${g},0)`;
  }

  get(index: number) {
    if (index < 0)
      index = 0;
    else if (index > this.lastIndex)
      index = this.lastIndex;
    return this.colorMap.get(index);
  }

}
