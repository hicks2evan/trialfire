import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmail, defaultValue } from 'app/shared/model/email.model';

export const ACTION_TYPES = {
  FETCH_EMAIL_LIST: 'email/FETCH_EMAIL_LIST',
  FETCH_EMAIL: 'email/FETCH_EMAIL',
  CREATE_EMAIL: 'email/CREATE_EMAIL',
  UPDATE_EMAIL: 'email/UPDATE_EMAIL',
  DELETE_EMAIL: 'email/DELETE_EMAIL',
  RESET: 'email/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmail>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type EmailState = Readonly<typeof initialState>;

// Reducer

export default (state: EmailState = initialState, action): EmailState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMAIL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMAIL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EMAIL):
    case REQUEST(ACTION_TYPES.UPDATE_EMAIL):
    case REQUEST(ACTION_TYPES.DELETE_EMAIL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EMAIL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMAIL):
    case FAILURE(ACTION_TYPES.CREATE_EMAIL):
    case FAILURE(ACTION_TYPES.UPDATE_EMAIL):
    case FAILURE(ACTION_TYPES.DELETE_EMAIL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMAIL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMAIL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMAIL):
    case SUCCESS(ACTION_TYPES.UPDATE_EMAIL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMAIL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/emails';

// Actions

export const getEntities: ICrudGetAllAction<IEmail> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_EMAIL_LIST,
  payload: axios.get<IEmail>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IEmail> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMAIL,
    payload: axios.get<IEmail>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEmail> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMAIL,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmail> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMAIL,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmail> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMAIL,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
