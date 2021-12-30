import React, { FC, useEffect } from 'react';
import TemplateProcessingBase from '../../../components/template/template-processing-base';
import { useSearchParams } from 'react-router-dom';
import { toast } from 'react-toastify';

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

const getToastMessageFromErrorId = (errorId: string | null) => {
  if (errorId === 'upload_failure') {
    return 'Failed to generate a new template.\n\nCould not store the dump file on a server.';
  } else if (errorId === 'restore_failure') {
    return 'Failed to generate a new template.\n\nInvalid or unsupported database dump file provided.\n\nCould not restore database.';
  } else if (errorId === 'metadata_faiure') {
    return 'Failed to generate a new template.\n\nInvalid or unsupported database dump file provided.\n\nCould not extract database metadata.';
  } else {
    return 'Unsupported operation.';
  }
};

const TemplateProcessingError: FC = () => {
  const [searchParams] = useSearchParams();
  const errorId = searchParams.get('error_id');

  useEffect(() => {
    toast.error(getToastMessageFromErrorId(errorId), {
      position: 'top-right',
      autoClose: 8000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: 'colored',
    });
  });

  return getTemplateProcessingBaseFromErrorId(errorId);
};

export default TemplateProcessingError;
