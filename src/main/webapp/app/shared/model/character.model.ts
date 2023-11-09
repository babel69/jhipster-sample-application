import { IJob } from 'app/shared/model/job.model';
import { ICountry } from 'app/shared/model/country.model';
import { IPower } from 'app/shared/model/power.model';
import { ISkin } from 'app/shared/model/skin.model';

export interface ICharacter {
  id?: number;
  name?: string | null;
  surname?: string | null;
  countryName?: string | null;
  jobName?: string | null;
  powerName?: string | null;
  age?: number | null;
  job?: IJob | null;
  country?: ICountry | null;
  power?: IPower | null;
  skin?: ISkin | null;
}

export const defaultValue: Readonly<ICharacter> = {};
