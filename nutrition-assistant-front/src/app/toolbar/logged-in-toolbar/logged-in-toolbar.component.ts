import { Component, OnInit, Input } from '@angular/core';
import { BasicToolbarComponent } from '../basic-toolbar/basic-toolbar.component';
import { Router } from '@angular/router';
import { UserContextService } from 'src/app/services/user-context.service';

@Component({
  selector: 'app-logged-in-toolbar',
  templateUrl: './logged-in-toolbar.component.html',
  styleUrls: ['./logged-in-toolbar.component.css']
})
export class LoggedInToolbarComponent extends BasicToolbarComponent implements OnInit {

  readonly user: string;

  constructor(
    private readonly router: Router,
    private readonly userContextService: UserContextService) {
    super();
    this.user = this.userContextService.getLoggedUser();
  }

  ngOnInit() {
  }

  logout() {
    localStorage.removeItem('jwt');
    this.router.navigate(['home']);
  }

}
