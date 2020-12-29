import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RegisterDialogComponent } from '../register-dialog/register-dialog.component';
import { LoginDialogComponent } from '../login-dialog/login-dialog.component';
import { DialogConfigService } from '../services/dialog-config.service';
import { Router } from '@angular/router';
import { UserContextService } from '../services/user-context.service';

@Component({
  selector: 'app-welcome-screen',
  templateUrl: './welcome-screen.component.html',
  styleUrls: ['./welcome-screen.component.css']
})
export class WelcomeScreenComponent implements OnInit {

  constructor(
    private readonly dialog: MatDialog,
    private readonly dialogConfigService: DialogConfigService,
    private readonly router: Router,
    private readonly userContextService: UserContextService) { }

  ngOnInit() {
  }

  openRegisterDialog() {
    const dialog = this.dialog.open(RegisterDialogComponent, this.dialogConfigService.getRegisterDialogConfig());
  }

  openLoginDialog() {
    const dialog = this.dialog.open(LoginDialogComponent, this.dialogConfigService.getLoginDialogConfig());
    dialog.afterClosed().subscribe(result => {
      if (result) {
        this.userContextService.setLoggedUser(result);
        this.router.navigate(['diary']);
      }
    });
  }

}
