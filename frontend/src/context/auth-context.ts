import {IMe} from "../api/auth";
import React from "react";

export interface IAuthContext {
  auth: IMe | null;
  setAuth: (user: IMe | null) => void;
}

const AuthContext = React.createContext<IAuthContext>({
  auth: null,
  setAuth: (auth: IMe | null) => {}
});

export default AuthContext;