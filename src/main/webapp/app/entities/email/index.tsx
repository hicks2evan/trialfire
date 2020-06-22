import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Email from './email';
import EmailDetail from './email-detail';
import EmailUpdate from './email-update';
import EmailDeleteDialog from './email-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmailUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmailUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmailDetail} />
      <ErrorBoundaryRoute path={match.url} component={Email} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmailDeleteDialog} />
  </>
);

export default Routes;
