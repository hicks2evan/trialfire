import { Moment } from 'moment';
import { ITrial } from 'app/shared/model/trial.model';

export interface IEmail {
  id?: number;
  sentdate?: string;
  trial?: ITrial;
}

export const defaultValue: Readonly<IEmail> = {};
