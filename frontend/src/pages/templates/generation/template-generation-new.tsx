import React, { FC, useCallback, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { getTemplateStatus } from '../../../api/requests/templates/templates.requests';
import TemplateGenerationBase from '../../../components/template/template-generation-base';
import { TEMPLATE_GENERATION_STEP_TIMEOUT } from '../../../constants/timeouts';
import { TemplateGenerationStatus } from '../../../components/template/template-generation-base.types';
import { ROUTES } from '../../../constants/routes';

const TemplateGenerationNew: FC = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const id: string = searchParams.get('template_id') ?? '';

  const refetch = useCallback(() => {
    getTemplateStatus(id).then((response) => {
      const status = response.data;
      if (status === 'NEW') {
        setTimeout(() => {
          refetch();
        }, TEMPLATE_GENERATION_STEP_TIMEOUT);
      } else if (status === 'UPLOAD_FAILURE') {
        setTimeout(() => {
          navigate(`${ROUTES.TEMPLATES_GENERATING_ERROR}?error_id=upload_failure&template_id=${id}`);
        }, TEMPLATE_GENERATION_STEP_TIMEOUT);
      } else if (status === 'RESTORE_FAILURE') {
        setTimeout(() => {
          navigate(`${ROUTES.TEMPLATES_GENERATING_ERROR}?error_id=restore_failure&template_id=${id}`);
        }, TEMPLATE_GENERATION_STEP_TIMEOUT);
      } else if (status === 'METADATA_FAILURE') {
        setTimeout(() => {
          navigate(`${ROUTES.TEMPLATES_GENERATING_ERROR}?error_id=metadata_failure&template_id=${id}`);
        }, TEMPLATE_GENERATION_STEP_TIMEOUT);
      } else {
        setTimeout(() => {
          navigate(`${ROUTES.TEMPLATES_GENERATING_UPLOAD_SUCCESS}?template_id=${id}`);
        }, TEMPLATE_GENERATION_STEP_TIMEOUT);
      }
    });
  }, [id, navigate]);

  useEffect(() => {
    refetch();
  }, [id, refetch]);

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
