import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './trial.reducer';
import { ITrial } from 'app/shared/model/trial.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITrialUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TrialUpdate = (props: ITrialUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { trialEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/trial');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.startdate = convertDateTimeToServer(values.startdate);
    values.enddate = convertDateTimeToServer(values.enddate);

    if (errors.length === 0) {
      const entity = {
        ...trialEntity,
        ...values
      };
      entity.user = users[values.user];

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="trialfireApp.trial.home.createOrEditLabel">Create or edit a Trial</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : trialEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="trial-id">ID</Label>
                  <AvInput id="trial-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="trial-name">
                  Name
                </Label>
                <AvField id="trial-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="trial-status">
                  Status
                </Label>
                <AvField id="trial-status" type="text" name="status" />
              </AvGroup>
              <AvGroup>
                <Label id="priceLabel" for="trial-price">
                  Price
                </Label>
                <AvField id="trial-price" type="string" className="form-control" name="price" />
              </AvGroup>
              <AvGroup>
                <Label id="increasedpriceLabel" for="trial-increasedprice">
                  Increasedprice
                </Label>
                <AvField id="trial-increasedprice" type="string" className="form-control" name="increasedprice" />
              </AvGroup>
              <AvGroup>
                <Label id="startdateLabel" for="trial-startdate">
                  Startdate
                </Label>
                <AvInput
                  id="trial-startdate"
                  type="datetime-local"
                  className="form-control"
                  name="startdate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.trialEntity.startdate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="enddateLabel" for="trial-enddate">
                  Enddate
                </Label>
                <AvInput
                  id="trial-enddate"
                  type="datetime-local"
                  className="form-control"
                  name="enddate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.trialEntity.enddate)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="trial-user">User</Label>
                <AvInput id="trial-user" type="select" className="form-control" name="user">
                  <option value="" key="0" />
                  {users
                    ? users.map((otherEntity, index) => (
                        <option value={index} key={otherEntity.id}>
                          {otherEntity.login}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/trial" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  trialEntity: storeState.trial.entity,
  loading: storeState.trial.loading,
  updating: storeState.trial.updating,
  updateSuccess: storeState.trial.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TrialUpdate);
