import React, { FC } from 'react';
import TemplateProcessingBase from '../../../components/template/template-processing-base';

const TemplateProcessingSuccess: FC = () => {
  return (
    <TemplateProcessingBase header="Template ready to use" step1={'success'} step2={'success'} step3={'success'} />
  );
};

export default TemplateProcessingSuccess;
