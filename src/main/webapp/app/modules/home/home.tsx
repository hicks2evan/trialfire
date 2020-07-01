import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';

export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const { account } = props;

  return (
    <Row>
      <Col md="9">
        <h1>Welcome to trialfire.</h1>
        <h2>This is a work in progress. Eventually it should be able to do these things:</h2>
        <ul>
          <li>Let a user create an account</li>
          <li>Let a user login</li>
          <li>Let a user provide gmail credentials and access to gmail</li>
          <li>Let a user view and edit promitional trial periods</li>
          <li>Scrape users gmails for new promotional trials</li>
          <li>Alert users via email if trials are about to expire</li>
          <li>Let admin users do admin stuff</li>
        </ul>
      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
