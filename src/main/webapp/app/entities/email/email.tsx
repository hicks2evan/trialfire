import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './email.reducer';
import { IEmail } from 'app/shared/model/email.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmailProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Email = (props: IEmailProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { emailList, match, loading } = props;
  return (
    <div>
      <h2 id="email-heading">
        Emails
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Email
        </Link>
      </h2>
      <div className="table-responsive">
        {emailList && emailList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Sentdate</th>
                <th>Trial</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {emailList.map((email, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${email.id}`} color="link" size="sm">
                      {email.id}
                    </Button>
                  </td>
                  <td>{email.sentdate ? <TextFormat type="date" value={email.sentdate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{email.trial ? <Link to={`trial/${email.trial.id}`}>{email.trial.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${email.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${email.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${email.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Emails found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ email }: IRootState) => ({
  emailList: email.entities,
  loading: email.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Email);
