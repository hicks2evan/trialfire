import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './email.reducer';
import { IEmail } from 'app/shared/model/email.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmailDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmailDetail = (props: IEmailDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { emailEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Email [<b>{emailEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="sentdate">Sentdate</span>
          </dt>
          <dd>{emailEntity.sentdate ? <TextFormat value={emailEntity.sentdate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Trial</dt>
          <dd>{emailEntity.trial ? emailEntity.trial.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/email" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/email/${emailEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ email }: IRootState) => ({
  emailEntity: email.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmailDetail);
