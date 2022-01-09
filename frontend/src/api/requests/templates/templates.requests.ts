import axios from 'axios';
import { MyTemplate } from './templates.types';

export const postCreateTemplate = (formData: FormData, handleUploadProgress: (progressEvent: any) => void) => {
  return axios.post<string>(`/api/v1/templates`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    onUploadProgress: handleUploadProgress,
  });
};

export const getTemplateStatus = (id: string) => {
  return axios.get<string>(`/api/v1/templates/${id}/status`);
};

export const getAllMyTemplates = () => {
  return axios.get<MyTemplate[]>('/api/v1/templates/me');
};

export const getDownloadTemplateDump = (id: string) => {
  return axios.get<any>(`/api/v1/templates/${id}/dump/download`, {
    responseType: 'blob',
  });
};
