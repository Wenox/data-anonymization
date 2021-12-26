import axios, { AxiosResponse } from 'axios';
import { EditMyProfileDto, MeResponse } from './me.types';
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
