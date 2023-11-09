import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Character from './character';
import Country from './country';
import Job from './job';
import Power from './power';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="character/*" element={<Character />} />
        <Route path="country/*" element={<Country />} />
        <Route path="job/*" element={<Job />} />
        <Route path="power/*" element={<Power />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
