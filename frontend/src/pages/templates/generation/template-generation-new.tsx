import React, { FC, useCallback, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { getTemplateStatus } from '../../../api/requests/templates/templates.requests';
import TemplateGenerationBase from '../../../components/template/template-generation-base';
import { TEMPLATE_GENERATION_STEP_TIMEOUT } from '../../../constants/timeouts';
import {
  TemplateGenerationStepStatus,
  TemplateStatus,
} from '../../../components/template/template-generation-base.types';
import { ROUTES } from '../../../constants/routes';

const TemplateGenerationNew: FC = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const id: string = searchParams.get('template_id') ?? '';

  const handleFetch = useCallback(() => {
    getTemplateStatus(id).then((response) =>
      setTimeout(() => {
        switch (response.data as TemplateStatus) {
          case TemplateStatus.NEW:
            handleFetch();
            break;
          case TemplateStatus.UPLOAD_FAILURE:
            navigate(`${ROUTES.TEMPLATES_GENERATING_ERROR}?error_id=upload_failure&template_id=${id}`);
            break;
          case TemplateStatus.RESTORE_FAILURE:
            navigate(`${ROUTES.TEMPLATES_GENERATING_ERROR}?error_id=restore_failure&template_id=${id}`);
            break;
          case TemplateStatus.METADATA_FAILURE:
            navigate(`${ROUTES.TEMPLATES_GENERATING_ERROR}?error_id=metadata_failure&template_id=${id}`);
            break;
          default:
            navigate(`${ROUTES.TEMPLATES_GENERATING_UPLOAD_SUCCESS}?template_id=${id}`);
            break;
        }
      }, TEMPLATE_GENERATION_STEP_TIMEOUT),
    );
  }, [id, navigate]);

  useEffect(() => {
    handleFetch();
  }, [id, handleFetch]);

  return (
    <TemplateGenerationBase
      steps={{
        step1: TemplateGenerationStepStatus.PROGRESS,
        step2: TemplateGenerationStepStatus.WAITING,
        step3: TemplateGenerationStepStatus.WAITING,
      }}
    />
  );
};

export default TemplateGenerationNew;
