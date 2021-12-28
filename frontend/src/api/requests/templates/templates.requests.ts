import { RegisterUserRequest } from '../users/users.types';
import axios from 'axios';
import { ApiResponse } from '../shared.types';

export const postCreateTemplate = (formData: FormData, type: string) => {
  return axios
    .post<string>(`/api/v1/templates?type=${type}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
    .then(function () {
      console.log('SUCCESS!!');
    })
    .catch(function () {
      console.log('FAILURE!!');
    });
};
