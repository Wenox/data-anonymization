import {Navigate, Outlet} from "react-router-dom";
import React from "react";

const PrivateRoute = () => {
  const auth = localStorage.getItem('logged_user') !== null;
  return auth ? <Outlet/> : <Navigate to="/login"/>;
}

export default PrivateRoute;
