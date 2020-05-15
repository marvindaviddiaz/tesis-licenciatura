
export class Cuenta {
  constructor(
    public numero?: number,
    public usuario?: string,
    public tipo?: string,
    public alias?: string,
    public estado?: string,
    public saldoActual?: number,
    public saldoDisponible?: number
  ) {
  }
}
