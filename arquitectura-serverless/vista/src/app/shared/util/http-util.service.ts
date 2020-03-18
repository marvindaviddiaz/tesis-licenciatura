import {HttpErrorResponse} from '@angular/common/http';

export class HttpUtilService {

  public static handleError(error: HttpErrorResponse): string {
    console.error(error);
    if (0 === error.status) {
      // TODO check if '0' is the correct status
      // window.location.href = '/';
      return 'Ha ocurrido un error con la conexión';
    } else {
      const ex = error.error;
      if (ex.message) {
        return ex.message;
      } else if (ex.content) {
        if (ex.content.Message) {
          return ex.content.Message;
        } else {
          return ex.content;
        }
      } else {
        return ex.error;
      }
    }
  }

}
