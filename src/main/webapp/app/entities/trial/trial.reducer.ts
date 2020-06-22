import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITrial, defaultValue } from 'app/shared/model/trial.model';

export const ACTION_TYPES = {
  FETCH_TRIAL_LIST: 'trial/FETCH_TRIAL_LIST',
  FETCH_TRIAL: 'trial/FETCH_TRIAL',
  CREATE_TRIAL: 'trial/CREATE_TRIAL',
  UPDATE_TRIAL: 'trial/UPDATE_TRIAL',
  DELETE_TRIAL: 'trial/DELETE_TRIAL',
  RESET: 'trial/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITrial>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TrialState = Readonly<typeof initialState>;

// Reducer

export default (state: TrialState = initialState, action): TrialState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TRIAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TRIAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TRIAL):
    case REQUEST(ACTION_TYPES.UPDATE_TRIAL):
    case REQUEST(ACTION_TYPES.DELETE_TRIAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TRIAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TRIAL):
    case FAILURE(ACTION_TYPES.CREATE_TRIAL):
    case FAILURE(ACTION_TYPES.UPDATE_TRIAL):
    case FAILURE(ACTION_TYPES.DELETE_TRIAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TRIAL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TRIAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TRIAL):
    case SUCCESS(ACTION_TYPES.UPDATE_TRIAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TRIAL):
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

const apiUrl = 'api/trials';

// Actions

export const getEntities: ICrudGetAllAction<ITrial> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TRIAL_LIST,
  payload: axios.get<ITrial>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITrial> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TRIAL,
    payload: axios.get<ITrial>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITrial> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TRIAL,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITrial> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TRIAL,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITrial> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TRIAL,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
