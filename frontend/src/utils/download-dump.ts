import { getDownloadTemplateDump } from '../api/requests/templates/templates.requests';
import { toast } from 'react-toastify';
import { getDownloadAnonymisationScript, getDownloadOutcomeDump } from '../api/requests/outcomes/outcome.requests';

export const handleDownloadTemplateDump = (templateId: string, originalFileName: string) => {
  getDownloadTemplateDump(templateId)
    .then((response) => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', originalFileName);
      document.body.appendChild(link);
      link.click();
      toast.success('Successfully downloaded template dump file.', {
        position: 'top-right',
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
    })
    .catch(() => {
      toast.error('Failed to download the template dump file.', {
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

export const handleDownloadOutcomeDump = (outcomeId: string, originalFileName: string) => {
  getDownloadOutcomeDump(outcomeId)
    .then((response) => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', originalFileName);
      document.body.appendChild(link);
      link.click();
      toast.success('Successfully downloaded outcome dump file.', {
        position: 'top-right',
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
    })
    .catch(() => {
      toast.error('Failed to download the outcome dump file.', {
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

export const handleDownloadAnonymisationScript = (outcomeId: string, originalFileName: string) => {
  getDownloadAnonymisationScript(outcomeId)
    .then((response) => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', originalFileName);
      document.body.appendChild(link);
      link.click();
      toast.success('Successfully downloaded anonymisation script file.', {
        position: 'top-right',
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
    })
    .catch(() => {
      toast.error('Failed to download the anonymisation script file.', {
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

export const isDownloadDisabled = (outcomeStatus: string) =>
  [
    'DATABASE_DUMP_FAILURE',
    'SCRIPT_EXECUTION_FAILURE',
    'SCRIPT_POPULATION_FAILURE',
    'SCRIPT_POPULATION_FAILURE',
    'MIRROR_FAILURE',
  ].includes(outcomeStatus);
