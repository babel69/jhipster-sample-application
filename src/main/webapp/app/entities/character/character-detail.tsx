import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './character.reducer';

export const CharacterDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const characterEntity = useAppSelector(state => state.character.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="characterDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.character.detail.title">Character</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{characterEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterSampleApplicationApp.character.name">Name</Translate>
            </span>
          </dt>
          <dd>{characterEntity.name}</dd>
          <dt>
            <span id="surname">
              <Translate contentKey="jhipsterSampleApplicationApp.character.surname">Surname</Translate>
            </span>
          </dt>
          <dd>{characterEntity.surname}</dd>
          <dt>
            <span id="countryName">
              <Translate contentKey="jhipsterSampleApplicationApp.character.countryName">Country Name</Translate>
            </span>
          </dt>
          <dd>{characterEntity.countryName}</dd>
          <dt>
            <span id="jobName">
              <Translate contentKey="jhipsterSampleApplicationApp.character.jobName">Job Name</Translate>
            </span>
          </dt>
          <dd>{characterEntity.jobName}</dd>
          <dt>
            <span id="powerName">
              <Translate contentKey="jhipsterSampleApplicationApp.character.powerName">Power Name</Translate>
            </span>
          </dt>
          <dd>{characterEntity.powerName}</dd>
          <dt>
            <span id="age">
              <Translate contentKey="jhipsterSampleApplicationApp.character.age">Age</Translate>
            </span>
          </dt>
          <dd>{characterEntity.age}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.character.job">Job</Translate>
          </dt>
          <dd>{characterEntity.job ? characterEntity.job.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.character.country">Country</Translate>
          </dt>
          <dd>{characterEntity.country ? characterEntity.country.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.character.power">Power</Translate>
          </dt>
          <dd>{characterEntity.power ? characterEntity.power.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/character" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/character/${characterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CharacterDetail;
