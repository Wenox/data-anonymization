import React, { FC, useEffect } from 'react';
import TemplateGenerationBase from '../../../components/template/template-generation-base';
import { useSearchParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import { TemplateGenerationStatus } from '../../../components/template/template-generation-base.types';

const getTemplateGenerationBaseFromErrorId = (errorId: string | null) => {
  if (errorId === 'upload_failure') {
    return (
      <TemplateGenerationBase
        header="Error"
        steps={{
          step1: TemplateGenerationStatus.ERROR,
          step2: TemplateGenerationStatus.CANCELLED,
          step3: TemplateGenerationStatus.CANCELLED,
        }}
      />
    );
  } else if (errorId === 'restore_failure') {
    return (
      <TemplateGenerationBase
        header="Error"
        steps={{
          step1: TemplateGenerationStatus.SUCCESS,
          step2: TemplateGenerationStatus.ERROR,
          step3: TemplateGenerationStatus.CANCELLED,
        }}
      />
    );
  } else if (errorId === 'metadata_failure') {
    return (
      <TemplateGenerationBase
        header="Error"
        steps={{
          step1: TemplateGenerationStatus.SUCCESS,
          step2: TemplateGenerationStatus.SUCCESS,
          step3: TemplateGenerationStatus.ERROR,
        }}
      />
    );
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

const TemplateGenerationError: FC = () => {
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

  return getTemplateGenerationBaseFromErrorId(errorId);
};

export default TemplateGenerationError;
