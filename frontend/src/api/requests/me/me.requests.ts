import axios, { AxiosResponse } from 'axios';
import { EditMyProfileDto, MeResponse, RemoveMyAccountDto } from './me.types';
import { User } from '../users/users.types';
import { ApiResponse } from '../shared.types';

export const getMe = (): Promise<AxiosResponse<MeResponse>> => {
  return axios.get<MeResponse>('/api/v1/me');
};

export const getMyProfile = (): Promise<AxiosResponse<User>> => {
  return axios.get<User>('/api/v1/me/profile');
};

export const putEditMyProfile = (body: EditMyProfileDto): Promise<AxiosResponse<ApiResponse>> => {
  return axios.put<ApiResponse>('/api/v1/me/profile/edit', body);
};

export const putRemoveMyAccount = (body: RemoveMyAccountDto): Promise<AxiosResponse<ApiResponse>> => {
  return axios.put<ApiResponse>('/api/v1/me/remove-account', body);
};

export const putRestoreMyAccount = (token: string): Promise<AxiosResponse<string>> => {
  return axios.put<string>(`/api/v1/me/restore-account?token=${token}`);
};
