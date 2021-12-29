import axios from 'axios';

export const postCreateTemplate = (
  formData: FormData,
  type: string,
  title: string,
  handleUploadProgress: (progressEvent: any) => void,
) => {
  return axios.post<string>(`/api/v1/templates?type=${type}&title=${title}`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    onUploadProgress: handleUploadProgress,
  });
};
