import {Navigate, Outlet} from "react-router-dom";
import React, {useContext} from "react";
import AuthContext from "../context/auth-context";

const PrivateRoute = () => {
  const { auth } = useContext(AuthContext);
  return auth ? <Outlet/> : <Navigate to="/login"/>;
}

export default PrivateRoute;
