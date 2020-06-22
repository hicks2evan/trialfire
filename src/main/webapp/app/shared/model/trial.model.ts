import { Moment } from 'moment';

export interface ITrial {
  id?: number;
  name?: string;
  status?: string;
  price?: number;
  user?: number;
  increasedprice?: number;
  startdate?: Moment;
  enddate?: Moment;
}

export const defaultValue: Readonly<ITrial> = {};
