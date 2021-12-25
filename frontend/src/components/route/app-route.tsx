import { Route } from 'react-router-dom';
import { RouteDescription } from '../../constants/routes';
import AuthenticatedRoute from './authenticated-route';
import React from 'react';
import Navigation from '../navigation/navigation';

export const getAppRoute = ({ path, element, authenticated, menu }: RouteDescription) => {
  const Element = element;
  if (authenticated) {
    return (
      <Route key={path} path={path} element={<AuthenticatedRoute />}>
        <Route
          key={path}
          path={path}
          element={
            menu ? (
              <Navigation>
                <Element />
              </Navigation>
            ) : (
              <Element />
            )
          }
        />
      </Route>
    );
  } else {
    return (
      <Route
        key={path}
        path={path}
        element={
          menu ? (
            <Navigation>
              <Element />
            </Navigation>
          ) : (
            <Element />
          )
        }
      />
    );
  }
};
