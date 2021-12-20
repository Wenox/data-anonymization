import axios from "axios";
import {FullUserResponse, RegisterUserRequest} from "./users.types";
import {ApiResponse} from "../shared.types";

export const postRegisterUser = (dto: RegisterUserRequest) => {
  return axios.post<ApiResponse>('/api/v1/auth/register', dto);
}

export const getUsers = () => {
  return axios.get<FullUserResponse[]>(`/api/v1/users`);
}
