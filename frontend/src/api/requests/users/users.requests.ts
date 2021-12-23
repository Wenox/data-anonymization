import axios from "axios";
import {FullUserResponse, RegisterUserRequest} from "./users.types";
import {ApiResponse} from "../shared.types";

export const postRegisterUser = (dto: RegisterUserRequest) => {
  return axios.post<ApiResponse>('/api/v1/users/register', dto);
}

export const getUsers = () => {
  return axios.get<FullUserResponse[]>(`/api/v1/users`);
}

export const putBlockUser = (id: string) => {
  return axios.put<ApiResponse>(`api/v1/users/${id}/block`)
}

export const putUnblockUser = (id: string) => {
  return axios.put<ApiResponse>(`api/v1/users/${id}/unblock`)
}

export const putForceUserRemoval = (id: string) => {
  return axios.put<ApiResponse>(`api/v1/users/${id}/force-removal`)
}
