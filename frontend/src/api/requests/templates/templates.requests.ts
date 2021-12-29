import axios from 'axios';

export const postCreateTemplate = (formData: FormData, handleUploadProgress: (progressEvent: any) => void) => {
  return axios.post<string>(`/api/v1/templates`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    onUploadProgress: handleUploadProgress,
  });
};
