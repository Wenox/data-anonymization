import { getDownloadTemplateDump } from '../api/requests/templates/templates.requests';
import { toast } from 'react-toastify';

export const handleDownloadTemplateDump = (templateId: string, originalFileName: string) => {
  getDownloadTemplateDump(templateId)
    .then((response) => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', originalFileName);
      document.body.appendChild(link);
      link.click();
      toast.success('Successfully downloaded dump file.', {
        position: 'top-right',
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
    })
    .catch((err) => {
      toast.success('Failed to downloaded the dump file.', {
        position: 'top-right',
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
    });
};
