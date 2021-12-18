import axios from "axios";

export const resetPassword = (email: string) => {
  return axios.post(`/api/v1/request-reset-password?email=${email}`);
}

export const showChangePassword = (token: string) => {
  return axios.get(`/api/v1/show-change-password?token=${token}`)
}
