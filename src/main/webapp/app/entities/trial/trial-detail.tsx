import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './trial.reducer';
import { ITrial } from 'app/shared/model/trial.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITrialDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TrialDetail = (props: ITrialDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { trialEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Trial [<b>{trialEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{trialEntity.name}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{trialEntity.status}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{trialEntity.price}</dd>
          <dt>
            <span id="user">User</span>
          </dt>
          <dd>{trialEntity.user}</dd>
          <dt>
            <span id="increasedprice">Increasedprice</span>
          </dt>
          <dd>{trialEntity.increasedprice}</dd>
          <dt>
            <span id="startdate">Startdate</span>
          </dt>
          <dd>
            <TextFormat value={trialEntity.startdate} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="enddate">Enddate</span>
          </dt>
          <dd>
            <TextFormat value={trialEntity.enddate} type="date" format={APP_DATE_FORMAT} />
          </dd>
        </dl>
        <Button tag={Link} to="/trial" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/trial/${trialEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ trial }: IRootState) => ({
  trialEntity: trial.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TrialDetail);
