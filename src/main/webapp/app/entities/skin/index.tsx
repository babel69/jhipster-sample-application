import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Skin from './skin';
import SkinDetail from './skin-detail';
import SkinUpdate from './skin-update';
import SkinDeleteDialog from './skin-delete-dialog';

const SkinRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Skin />} />
    <Route path="new" element={<SkinUpdate />} />
    <Route path=":id">
      <Route index element={<SkinDetail />} />
      <Route path="edit" element={<SkinUpdate />} />
      <Route path="delete" element={<SkinDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SkinRoutes;
