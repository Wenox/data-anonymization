import React from "react";
import {MeResponse} from "../api/requests/me/me.types";

export interface IAuthContext {
  me: MeResponse | null;
  setMe: (user: MeResponse | null) => void;
}

const AuthContext = React.createContext<IAuthContext>({
  me: null,
  setMe: (me: MeResponse | null) => {}
});

export default AuthContext;