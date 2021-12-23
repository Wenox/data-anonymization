import axios from 'axios';
import { ResetPasswordRequest } from './reset-password.types';

export const postRequestResetPassword = (email: string) => {
  return axios.post<void>(`/api/v1/reset-password/request-reset?email=${email}`);
};

export const getShowChangePasswordForm = (token: string) => {
  return axios.get<string>(`/api/v1/reset-password/show-change-password-form?token=${token}`);
};

export const postChangePassword = (dto: ResetPasswordRequest) => {
  return axios.post<string>(`/api/v1/reset-password/change-password`, dto);
};
