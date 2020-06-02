import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {ConfirmacionData} from './confirmacion-data';

@Component({
  selector: 'app-confirmacion-pago-dialog',
  templateUrl: './confirmacion-pago-dialog.html',
  styleUrls: ['./confirmacion-pago-dialog.css']
})
export class ConfirmacionPagoDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<ConfirmacionPagoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ConfirmacionData) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
