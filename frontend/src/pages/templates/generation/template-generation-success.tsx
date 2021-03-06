import React, { FC, useEffect } from 'react';
import TemplateGenerationBase from '../../../components/template/template-generation-base';
import { toast } from 'react-toastify';
import { TemplateGenerationStepStatus } from '../../../components/template/template-generation-base.types';

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
      header="Template ready to produce worksheets"
      steps={{
        step1: TemplateGenerationStepStatus.SUCCESS,
        step2: TemplateGenerationStepStatus.SUCCESS,
        step3: TemplateGenerationStepStatus.SUCCESS,
      }}
    />
  );
};

export default TemplateGenerationSuccess;
