import {FavoritoIdentificador} from './FavoritoIdentificador';

export class Favorito {
  constructor(
    public id?: number,
    public servicioId?: number,
    public servicio?: string,
    public alias?: string,
    public identificadores?: FavoritoIdentificador[]
  ) {
  }
}
