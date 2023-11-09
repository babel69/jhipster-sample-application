import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IJob } from 'app/shared/model/job.model';
import { getEntities as getJobs } from 'app/entities/job/job.reducer';
import { ICountry } from 'app/shared/model/country.model';
import { getEntities as getCountries } from 'app/entities/country/country.reducer';
import { IPower } from 'app/shared/model/power.model';
import { getEntities as getPowers } from 'app/entities/power/power.reducer';
import { ICharacter } from 'app/shared/model/character.model';
import { getEntity, updateEntity, createEntity, reset } from './character.reducer';

export const CharacterUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const jobs = useAppSelector(state => state.job.entities);
  const countries = useAppSelector(state => state.country.entities);
  const powers = useAppSelector(state => state.power.entities);
  const characterEntity = useAppSelector(state => state.character.entity);
  const loading = useAppSelector(state => state.character.loading);
  const updating = useAppSelector(state => state.character.updating);
  const updateSuccess = useAppSelector(state => state.character.updateSuccess);

  const handleClose = () => {
    navigate('/character');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getJobs({}));
    dispatch(getCountries({}));
    dispatch(getPowers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.age !== undefined && typeof values.age !== 'number') {
      values.age = Number(values.age);
    }

    const entity = {
      ...characterEntity,
      ...values,
      job: jobs.find(it => it.id.toString() === values.job.toString()),
      country: countries.find(it => it.id.toString() === values.country.toString()),
      power: powers.find(it => it.id.toString() === values.power.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...characterEntity,
          job: characterEntity?.job?.id,
          country: characterEntity?.country?.id,
          power: characterEntity?.power?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.character.home.createOrEditLabel" data-cy="CharacterCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.character.home.createOrEditLabel">Create or edit a Character</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="character-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.character.name')}
                id="character-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.character.surname')}
                id="character-surname"
                name="surname"
                data-cy="surname"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.character.countryName')}
                id="character-countryName"
                name="countryName"
                data-cy="countryName"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.character.jobName')}
                id="character-jobName"
                name="jobName"
                data-cy="jobName"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.character.powerName')}
                id="character-powerName"
                name="powerName"
                data-cy="powerName"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.character.age')}
                id="character-age"
                name="age"
                data-cy="age"
                type="text"
              />
              <ValidatedField
                id="character-job"
                name="job"
                data-cy="job"
                label={translate('jhipsterSampleApplicationApp.character.job')}
                type="select"
              >
                <option value="" key="0" />
                {jobs
                  ? jobs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="character-country"
                name="country"
                data-cy="country"
                label={translate('jhipsterSampleApplicationApp.character.country')}
                type="select"
              >
                <option value="" key="0" />
                {countries
                  ? countries.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="character-power"
                name="power"
                data-cy="power"
                label={translate('jhipsterSampleApplicationApp.character.power')}
                type="select"
              >
                <option value="" key="0" />
                {powers
                  ? powers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/character" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CharacterUpdate;
