import {Component, Injectable, OnInit} from '@angular/core';
import {SecurityService} from '../security/security.service';


@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
@Injectable()
export class MenuComponent implements OnInit {

  constructor(private securityService: SecurityService) {}

  ngOnInit() {

  }

  public logout() {
    this.securityService.logout();
  }

}
