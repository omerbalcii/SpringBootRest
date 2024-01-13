import { IDersDTO } from "./IDersDTO";
import { IOgrenci } from "./IOgrenci";

export interface IDersOgrenciDTO {
    id: number;
    ders: IDersDTO;
    ogrenci: IOgrenci;
    devamsizlik: number;
    note: number;
  }
  