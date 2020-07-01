import './footer.scss';

import React from 'react';

import { Col, Row } from 'reactstrap';

const Footer = props => (
  <div className="footer page-content">
    <Row>
      <Col md="12">
      <a href="https://github.com/hicks2evan/trialfire">trialfire on github</a> |
      <a href="https://hicks2evan.github.io/">more on Evan</a>
      </Col>
    </Row>
  </div>
);

export default Footer;
