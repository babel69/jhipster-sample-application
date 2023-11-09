import { ICharacter } from 'app/shared/model/character.model';

export interface IPower {
  id?: number;
  name?: string | null;
  character?: ICharacter | null;
}

export const defaultValue: Readonly<IPower> = {};
