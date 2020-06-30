import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './trial.reducer';
import { ITrial } from 'app/shared/model/trial.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITrialProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Trial = (props: ITrialProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { trialList, match, loading } = props;
  return (
    <div>
      <h2 id="trial-heading">
        Trials
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Trial
        </Link>
      </h2>
      <div className="table-responsive">
        {trialList && trialList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Status</th>
                <th>Price</th>
                <th>Increasedprice</th>
                <th>Startdate</th>
                <th>Enddate</th>
                <th>User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {trialList.map((trial, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${trial.id}`} color="link" size="sm">
                      {trial.id}
                    </Button>
                  </td>
                  <td>{trial.name}</td>
                  <td>{trial.status}</td>
                  <td>{trial.price}</td>
                  <td>{trial.increasedprice}</td>
                  <td>
                    <TextFormat type="date" value={trial.startdate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={trial.enddate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{trial.user ? trial.user.login : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${trial.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${trial.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${trial.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Trials found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ trial }: IRootState) => ({
  trialList: trial.entities,
  loading: trial.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Trial);
