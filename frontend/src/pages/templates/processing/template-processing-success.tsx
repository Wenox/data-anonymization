import React, { FC, useEffect } from 'react';
import TemplateProcessingBase from '../../../components/template/template-processing-base';
import { toast } from 'react-toastify';

const TemplateProcessingSuccess: FC = () => {
  useEffect(() => {
    toast.success('Template ready to use. You can produce a new worksheet with now.', {
      position: 'top-right',
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
    });
  });
  return (
    <TemplateProcessingBase header="Template ready to use" step1={'success'} step2={'success'} step3={'success'} />
  );
};

export default TemplateProcessingSuccess;
