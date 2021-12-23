import axios, { AxiosResponse } from 'axios';
import { MeResponse } from './me.types';

export const getMe = (): Promise<AxiosResponse<MeResponse>> => {
  return axios.get<MeResponse>('/api/v1/me');
};
