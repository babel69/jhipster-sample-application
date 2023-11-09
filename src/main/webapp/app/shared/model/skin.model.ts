import { ICharacter } from 'app/shared/model/character.model';

export interface ISkin {
  id?: number;
  color?: string | null;
  character?: ICharacter | null;
}

export const defaultValue: Readonly<ISkin> = {};
