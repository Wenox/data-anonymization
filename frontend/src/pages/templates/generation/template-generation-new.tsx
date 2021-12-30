import React, { FC, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { getTemplateStatus } from '../../../api/requests/templates/templates.requests';
import TemplateGenerationBase from '../../../components/template/template-generation-base';
import { TEMPLATE_PROCESSING_STEP_TIMEOUT } from '../../../constants/timeouts';
import { TemplateGenerationStatus } from '../../../components/template/template-generation-base.types';

const TemplateGenerationNew: FC = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const id: string = searchParams.get('template_id') ?? '';

  const refetch = () => {
    getTemplateStatus(id).then((response) => {
      const status = response.data;
      if (status === 'NEW') {
        setTimeout(() => {
          refetch();
        }, TEMPLATE_PROCESSING_STEP_TIMEOUT);
      } else if (status === 'UPLOAD_FAILURE') {
        setTimeout(() => {
          navigate(`/templates/processing/error?error_id=upload_failure&template_id=${id}`);
        }, TEMPLATE_PROCESSING_STEP_TIMEOUT);
      } else if (status === 'RESTORE_FAILURE') {
        setTimeout(() => {
          navigate(`/templates/processing/error?error_id=restore_failure&template_id=${id}`);
        }, TEMPLATE_PROCESSING_STEP_TIMEOUT);
      } else if (status === 'METADATA_FAILURE') {
        setTimeout(() => {
          navigate(`/templates/processing/error?error_id=metadata_failure&template_id=${id}`);
        }, TEMPLATE_PROCESSING_STEP_TIMEOUT);
      } else {
        setTimeout(() => {
          navigate(`/templates/processing/upload-success?template_id=${id}`);
        }, TEMPLATE_PROCESSING_STEP_TIMEOUT);
      }
    });
  };

  useEffect(() => {
    refetch();
  }, [id]);

  return (
    <TemplateGenerationBase
      steps={{
        step1: TemplateGenerationStatus.PROGRESS,
        step2: TemplateGenerationStatus.WAITING,
        step3: TemplateGenerationStatus.WAITING,
      }}
    />
  );
};

export default TemplateGenerationNew;
