import React, { FC } from 'react';
import TemplateProcessingBase from '../../../components/template/template-processing-base';
import { useSearchParams } from 'react-router-dom';

const getTemplateProcessingBaseFromErrorId = (errorId: string | null) => {
  if (errorId === 'upload_failure') {
    return <TemplateProcessingBase step1={'error'} step2={'cancelled'} step3={'cancelled'} />;
  } else if (errorId === 'restore_failure') {
    return <TemplateProcessingBase step1={'success'} step2={'error'} step3={'cancelled'} />;
  } else if (errorId === 'metadata_failure') {
    return <TemplateProcessingBase step1={'success'} step2={'success'} step3={'error'} />;
  } else {
    return <h1>No such error</h1>;
  }
};

const TemplateProcessingError: FC = () => {
  const [searchParams] = useSearchParams();
  const errorId = searchParams.get('error_id');

  return getTemplateProcessingBaseFromErrorId(errorId);
};

export default TemplateProcessingError;
