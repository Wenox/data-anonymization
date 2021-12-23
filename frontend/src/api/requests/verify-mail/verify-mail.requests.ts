import axios from 'axios';

export const postVerifyMail = (token: string) => {
  return axios.post<string>(`/api/v1/users/verify-mail?token=${token}`);
};
