import React, { FC, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { getTemplateStatus } from '../../../api/requests/templates/templates.requests';
import TemplateProcessingBase from '../../../components/template/template-processing-base';
import { TEMPLATE_PROCESSING_STEP_TIMEOUT } from '../../../constants/timeouts';

const TemplateProcessingNew: FC = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const id: string = searchParams.get('template_id') ?? '';

  console.log('extracted id: ', id);

  const refetch = () => {
    getTemplateStatus(id).then((response) => {
      const status = response.data;
      console.log('status: ', status);
      if (status === 'NEW') {
        setTimeout(() => {
          console.log('refetch in 1000ms because still NEW');
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

  return <TemplateProcessingBase step1={'inprogress'} step2={'waiting'} step3={'waiting'} />;
};

export default TemplateProcessingNew;
