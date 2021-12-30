import React, { FC, useEffect } from 'react';
import TemplateGenerationBase from '../../../components/template/template-generation-base';
import { toast } from 'react-toastify';
import { TemplateGenerationStatus } from '../../../components/template/template-generation-base.types';

const TemplateGenerationSuccess: FC = () => {
  useEffect(() => {
    toast.success('The template is ready to produce new worksheets.', {
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
    <TemplateGenerationBase
      header="Template ready to use"
      steps={{
        step1: TemplateGenerationStatus.SUCCESS,
        step2: TemplateGenerationStatus.SUCCESS,
        step3: TemplateGenerationStatus.SUCCESS,
      }}
    />
  );
};

export default TemplateGenerationSuccess;
