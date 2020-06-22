import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITrial } from 'app/shared/model/trial.model';
import { getEntities as getTrials } from 'app/entities/trial/trial.reducer';
import { getEntity, updateEntity, createEntity, reset } from './email.reducer';
import { IEmail } from 'app/shared/model/email.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmailUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmailUpdate = (props: IEmailUpdateProps) => {
  const [trialId, setTrialId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { emailEntity, trials, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/email');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTrials();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.sentdate = convertDateTimeToServer(values.sentdate);

    if (errors.length === 0) {
      const entity = {
        ...emailEntity,
        ...values,
      };

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
          <h2 id="trialfireApp.email.home.createOrEditLabel">Create or edit a Email</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : emailEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="email-id">ID</Label>
                  <AvInput id="email-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="sentdateLabel" for="email-sentdate">
                  Sentdate
                </Label>
                <AvInput
                  id="email-sentdate"
                  type="datetime-local"
                  className="form-control"
                  name="sentdate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.emailEntity.sentdate)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="email-trial">Trial</Label>
                <AvInput id="email-trial" type="select" className="form-control" name="trial.id">
                  <option value="" key="0" />
                  {trials
                    ? trials.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/email" replace color="info">
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
  trials: storeState.trial.entities,
  emailEntity: storeState.email.entity,
  loading: storeState.email.loading,
  updating: storeState.email.updating,
  updateSuccess: storeState.email.updateSuccess,
});

const mapDispatchToProps = {
  getTrials,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmailUpdate);
