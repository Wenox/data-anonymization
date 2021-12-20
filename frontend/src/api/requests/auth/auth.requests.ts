import axios from "axios";
import {LoginUserRequest} from "./auth.types";

export const postLogin = (dto: LoginUserRequest) => {
  return axios.post('/api/v1/auth/login', dto);
}
