import { Navigate, Outlet } from 'react-router-dom';
import React, { useContext } from 'react';
import AuthContext from '../context/auth-context';

const PrivateRoute = () => {
  const { me } = useContext(AuthContext);
  return me ? <Outlet /> : <Navigate to="/login" />;
};

export default PrivateRoute;
