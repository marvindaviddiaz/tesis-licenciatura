import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-control-messages',
  templateUrl: './control-messages.component.html',
  styleUrls: ['./control-messages.component.css']
})
export class ControlMessagesComponent  {

  @Input() control: FormControl;
  @Input() message: String = 'Formato Inválido';

  constructor() { }

}
