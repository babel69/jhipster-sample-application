import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './character.reducer';

export const Character = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const characterList = useAppSelector(state => state.character.entities);
  const loading = useAppSelector(state => state.character.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="character-heading" data-cy="CharacterHeading">
        <Translate contentKey="jhipsterSampleApplicationApp.character.home.title">Characters</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterSampleApplicationApp.character.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/character/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterSampleApplicationApp.character.home.createLabel">Create new Character</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {characterList && characterList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.character.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.character.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('surname')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.character.surname">Surname</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('surname')} />
                </th>
                <th className="hand" onClick={sort('countryName')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.character.countryName">Country Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('countryName')} />
                </th>
                <th className="hand" onClick={sort('jobName')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.character.jobName">Job Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('jobName')} />
                </th>
                <th className="hand" onClick={sort('powerName')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.character.powerName">Power Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('powerName')} />
                </th>
                <th className="hand" onClick={sort('age')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.character.age">Age</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('age')} />
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.character.job">Job</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.character.country">Country</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.character.power">Power</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {characterList.map((character, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/character/${character.id}`} color="link" size="sm">
                      {character.id}
                    </Button>
                  </td>
                  <td>{character.name}</td>
                  <td>{character.surname}</td>
                  <td>{character.countryName}</td>
                  <td>{character.jobName}</td>
                  <td>{character.powerName}</td>
                  <td>{character.age}</td>
                  <td>{character.job ? <Link to={`/job/${character.job.id}`}>{character.job.id}</Link> : ''}</td>
                  <td>{character.country ? <Link to={`/country/${character.country.id}`}>{character.country.id}</Link> : ''}</td>
                  <td>{character.power ? <Link to={`/power/${character.power.id}`}>{character.power.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/character/${character.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/character/${character.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (location.href = `/character/${character.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterSampleApplicationApp.character.home.notFound">No Characters found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Character;
