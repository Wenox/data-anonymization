import React, { FC, useCallback, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { getTemplateStatus } from '../../../api/requests/templates/templates.requests';
import TemplateGenerationBase from '../../../components/template/template-generation-base';
import { TEMPLATE_GENERATION_STEP_TIMEOUT } from '../../../constants/timeouts';
import { TemplateGenerationStatus } from '../../../components/template/template-generation-base.types';
import { ROUTES } from '../../../constants/routes';

const TemplateGenerationRestoreSuccess: FC = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const id: string = searchParams.get('template_id') ?? '';

  const handleNextState = (status: string) =>
    setTimeout(() => {
      switch (status) {
        case 'RESTORE_SUCCESS':
          handleFetch();
          break;
        case 'UPLOAD_FAILURE':
          navigate(`${ROUTES.TEMPLATES_GENERATING_ERROR}?error_id=upload_failure&template_id=${id}`);
          break;
        case 'RESTORE_FAILURE':
          navigate(`${ROUTES.TEMPLATES_GENERATING_ERROR}?error_id=restore_failure&template_id=${id}`);
          break;
        case 'METADATA_FAILURE':
          navigate(`${ROUTES.TEMPLATES_GENERATING_ERROR}?error_id=metadata_failure&template_id=${id}`);
          break;
        default:
          navigate(`${ROUTES.TEMPLATES_GENERATING_SUCCESS}?template_id=${id}`);
          break;
      }
    }, TEMPLATE_GENERATION_STEP_TIMEOUT);

  const handleFetch = useCallback(() => {
    getTemplateStatus(id).then((response) => {
      const status = response.data;
      handleNextState(status);
    });
  }, [id, navigate]);

  useEffect(() => {
    handleFetch();
  }, [id, handleFetch]);

  return (
    <TemplateGenerationBase
      steps={{
        step1: TemplateGenerationStatus.SUCCESS,
        step2: TemplateGenerationStatus.SUCCESS,
        step3: TemplateGenerationStatus.PROGRESS,
      }}
    />
  );
};

export default TemplateGenerationRestoreSuccess;
