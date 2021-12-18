import axios from "axios";

export const resetPassword = (email: string) => {
  return axios.post(`/api/v1/request-reset-password?email=${email}`);
}

export const showChangePassword = (token: string) => {
  return axios.get(`/api/v1/show-change-password?token=${token}`);
}

export interface ResetPasswordDto {
  newPassword: string;
  token: string;
}

export const changePassword = (body: ResetPasswordDto) => {
  return axios.post(`/api/v1/change-password`, body);
}
