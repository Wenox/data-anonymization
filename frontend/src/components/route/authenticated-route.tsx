import { Navigate, Outlet } from 'react-router-dom';
import React, { useContext } from 'react';
import AuthContext from '../../context/auth-context';

const AuthenticatedRoute = () => {
  const { me } = useContext(AuthContext);
  return me ? <Outlet /> : <Navigate to="/login" />;
};

export default AuthenticatedRoute;
