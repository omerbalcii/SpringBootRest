import { IKonu } from "./IKonu"
import { IOgretmen } from "./IOgretmen"

export interface IDersDTO
{
    id: number,
    ogretmen: IOgretmen
    konu : IKonu
}