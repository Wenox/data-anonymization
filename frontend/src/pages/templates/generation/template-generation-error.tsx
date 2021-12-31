import React, { FC, useEffect } from 'react';
import TemplateGenerationBase from '../../../components/template/template-generation-base';
import { useSearchParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import {
  TemplateGenerationStepStatus,
  TemplateStatus,
} from '../../../components/template/template-generation-base.types';

const getTemplateGenerationErrorFromStatus = (status: TemplateStatus) => {
  switch (status) {
    case TemplateStatus.UPLOAD_FAILURE:
      return (
        <TemplateGenerationBase
          header="Error"
          steps={{
            step1: TemplateGenerationStepStatus.ERROR,
            step2: TemplateGenerationStepStatus.CANCELLED,
            step3: TemplateGenerationStepStatus.CANCELLED,
          }}
        />
      );
    case TemplateStatus.RESTORE_FAILURE:
      return (
        <TemplateGenerationBase
          header="Error"
          steps={{
            step1: TemplateGenerationStepStatus.SUCCESS,
            step2: TemplateGenerationStepStatus.ERROR,
            step3: TemplateGenerationStepStatus.CANCELLED,
          }}
        />
      );
    case TemplateStatus.METADATA_FAILURE:
      return (
        <TemplateGenerationBase
          header="Error"
          steps={{
            step1: TemplateGenerationStepStatus.SUCCESS,
            step2: TemplateGenerationStepStatus.SUCCESS,
            step3: TemplateGenerationStepStatus.ERROR,
          }}
        />
      );
    default:
      return <h1>Unrecognized error</h1>;
  }
};

const getToastErrorMessageFromStatus = (status: TemplateStatus) => {
  switch (status) {
    case TemplateStatus.UPLOAD_FAILURE:
      return 'Failed to generate a new template.\n\nCould not store the dump file on a server.';
    case TemplateStatus.RESTORE_FAILURE:
      return 'Failed to generate a new template.\n\nInvalid or unsupported database dump file provided.\n\nCould not restore database.';
    case TemplateStatus.METADATA_FAILURE:
      return 'Failed to generate a new template.\n\nInvalid or unsupported database dump file provided.\n\nCould not extract database metadata.';
    default:
      return 'Unsupported operation.';
  }
};

const TemplateGenerationError: FC = () => {
  const [searchParams] = useSearchParams();
  const status = (searchParams.get('error_id') ?? '').toUpperCase() as TemplateStatus;

  useEffect(() => {
    toast.error(getToastErrorMessageFromStatus(status), {
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

  return getTemplateGenerationErrorFromStatus(status);
};

export default TemplateGenerationError;
