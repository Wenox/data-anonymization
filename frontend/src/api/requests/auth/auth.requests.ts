import axios from "axios";
import {LoginUserRequest} from "./auth.types";
import {REFRESH_TOKEN} from "../../../constants/auth";
import {refresher} from "../../axios.config";

export const postLogin = (dto: LoginUserRequest) => {
  return axios.post('/api/v1/auth/login', dto);
}

export const postRefreshToken = () => {
  return refresher.post('/api/v1/auth/refresh-token', {}, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(REFRESH_TOKEN)}`
    }
  })
}
