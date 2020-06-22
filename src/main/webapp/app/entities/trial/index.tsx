import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Trial from './trial';
import TrialDetail from './trial-detail';
import TrialUpdate from './trial-update';
import TrialDeleteDialog from './trial-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TrialDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TrialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TrialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TrialDetail} />
      <ErrorBoundaryRoute path={match.url} component={Trial} />
    </Switch>
  </>
);

export default Routes;
