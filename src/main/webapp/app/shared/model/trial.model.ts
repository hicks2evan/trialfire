import { Moment } from 'moment';
import { IUser } from 'app/shared/model/user.model';

export interface ITrial {
  id?: number;
  name?: string;
  status?: string;
  price?: number;
  increasedprice?: number;
  startdate?: Moment;
  enddate?: Moment;
  user?: IUser;
}

export const defaultValue: Readonly<ITrial> = {};
