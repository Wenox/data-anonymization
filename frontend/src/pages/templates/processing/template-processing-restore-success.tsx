import React, { FC, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { getTemplateStatus } from '../../../api/requests/templates/templates.requests';
import TemplateProcessingBase from '../../../components/template/template-processing-base';

const TemplateProcessingRestoreSuccess: FC = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const id: string = searchParams.get('template_id') ?? '';

  const refetch = () => {
    getTemplateStatus(id).then((response) => {
      const status = response.data;
      console.log('status: ', status);
      if (status === 'RESTORE_SUCCESS') {
        setTimeout(() => {
          console.log('refetch in 1000ms because still RESTORE_SUCCESS');
          refetch();
        }, 1000);
      } else if (status === 'UPLOAD_FAILURE') {
        setTimeout(() => {
          navigate(`/templates/processing/error?error_id=upload_failure&template_id=${id}`);
        }, 1000);
      } else if (status === 'RESTORE_FAILURE') {
        setTimeout(() => {
          navigate(`/templates/processing/error?error_id=restore_failure&template_id=${id}`);
        }, 1000);
      } else if (status === 'METADATA_FAILURE') {
        setTimeout(() => {
          navigate(`/templates/processing/error?error_id=metadata_failure&template_id=${id}`);
        }, 1000);
      } else {
        setTimeout(() => {
          navigate(`/templates/processing/success?template_id=${id}`);
        }, 1000);
      }
    });
  };

  useEffect(() => {
    refetch();
  }, [id]);

  return <TemplateProcessingBase step1={'success'} step2={'success'} step3={'inprogress'} />;
};

export default TemplateProcessingRestoreSuccess;
