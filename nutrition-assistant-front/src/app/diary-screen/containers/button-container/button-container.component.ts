import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfigService } from 'src/app/services/dialog-config.service';

@Component({
  selector: 'app-button-container',
  templateUrl: './button-container.component.html',
  styleUrls: ['./button-container.component.css']
})
export class ButtonContainerComponent implements OnInit {

  @Input() readonly label: string;
  @Input() readonly icon: string;

  @Output() private readonly openDialogEvent = new EventEmitter<void>();

  constructor(
    protected readonly dialog: MatDialog,
    protected readonly dialogConfigService: DialogConfigService) {
  }

  ngOnInit() {
  }

  emitOpenDialogEvent() {
    this.openDialogEvent.emit();
  }

}
