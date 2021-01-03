import { Component, OnInit, Input, AfterViewInit, OnChanges, SimpleChanges } from '@angular/core';
import {
  trigger,
  state,
  style,
  animate,
  transition
} from '@angular/animations';
import { ColorService } from 'src/app/services/color.service';

interface StateInfo {
  value: number;
  color: string;
}

const STATE1 = 'state1';
const STATE2 = 'state2';

@Component({
  selector: 'app-progress-bar',
  templateUrl: './progress-bar.component.html',
  styleUrls: ['./progress-bar.component.css'],
  animations: [
    trigger('animation', [
      state(STATE1, style({
        width: '{{value1}}%',
        fill: '{{color1}}'
      }), { params: { value1: '0', color1: 'red' } }),
      state(STATE2, style({
        width: '{{value2}}%',
        fill: '{{color2}}'
      }), { params: { value2: '0', color2: 'green' } }),
      transition(`${STATE1} <=> ${STATE2}`, [
        animate('1s ease-out')
      ])
    ])
  ]
})
export class ProgressBarComponent implements OnInit, AfterViewInit, OnChanges {

  currentState = STATE1;
  state1Info: StateInfo = { value: 0, color: 'red' };
  state2Info: StateInfo = { value: 100, color: 'green' };

  @Input() value = 0;

  constructor(private readonly colorService: ColorService) {
  }

  ngOnInit() {
  }

  ngAfterViewInit() {
    setTimeout(() => this.changeState());
  }

  ngOnChanges(changes: SimpleChanges) {
    this.value = Number.parseInt(changes.value.currentValue, 10);
    if (!changes.value.isFirstChange()) {
      this.changeState();
    }
  }

  changeState() {
    const isState2 = this.currentState === STATE2;
    const targetState = isState2 ? this.state1Info : this.state2Info;
    targetState.value = this.value;
    targetState.color = this.colorService.get(this.value);
    this.currentState = isState2 ? STATE1 : STATE2;
  }

}
