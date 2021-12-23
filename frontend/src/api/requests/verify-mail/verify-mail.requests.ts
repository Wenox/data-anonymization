import axios from 'axios';

export const postVerifyMail = (token: string) => {
  return axios.post<string>(`/api/v1/users/verify-mail?token=${token}`);
};

export const postVerifyMailSendAgainGivenToken = (token: string) => {
  return axios.post<string>(`/api/v1/users/verify-mail/send-again?token=${token}`);
};

export const postVerifyMailSendAgainGivenEmail = (email: string) => {
  return axios.post<string>(`/api/v1/users/verify-mail/send-again?email=${email}`);
};
