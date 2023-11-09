import { ICharacter } from 'app/shared/model/character.model';

export interface IJob {
  id?: number;
  jobName?: string | null;
  character?: ICharacter | null;
}

export const defaultValue: Readonly<IJob> = {};
