import { ICharacter } from 'app/shared/model/character.model';

export interface ICountry {
  id?: number;
  name?: string | null;
  countryNumber?: number | null;
  character?: ICharacter | null;
}

export const defaultValue: Readonly<ICountry> = {};
