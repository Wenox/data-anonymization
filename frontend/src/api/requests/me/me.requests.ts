import axios, { AxiosResponse } from 'axios';
import { MeResponse } from './me.types';
import { FullUserResponse, User } from '../users/users.types';

export const getMe = (): Promise<AxiosResponse<MeResponse>> => {
  return axios.get<MeResponse>('/api/v1/me');
};

export const getMyProfile = (): Promise<AxiosResponse<User>> => {
  return axios.get<User>('/api/v1/me/profile');
};
