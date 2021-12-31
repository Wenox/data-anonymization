import { CircularProgress } from '@mui/material';
import { Block, CheckCircleOutline, ErrorOutlineOutlined, HourglassTopOutlined } from '@mui/icons-material';
import React from 'react';
import { TemplateGenerationStepStatus } from './template-generation-base.types';
import { sxStepIcon, sxStepProgress } from './template-generation-base.styles';

export const getStepIconFromStepStatus = (status: TemplateGenerationStepStatus) => {
  switch (status) {
    case TemplateGenerationStepStatus.PROGRESS:
      return <CircularProgress color="secondary" size="6rem" sx={sxStepProgress} />;
    case TemplateGenerationStepStatus.SUCCESS:
      return <CheckCircleOutline color="success" sx={sxStepIcon} />;
    case TemplateGenerationStepStatus.ERROR:
      return <ErrorOutlineOutlined color="error" sx={sxStepIcon} />;
    case TemplateGenerationStepStatus.WAITING:
      return <HourglassTopOutlined color={'disabled'} sx={sxStepIcon} />;
    case TemplateGenerationStepStatus.CANCELLED:
      return <Block color="secondary" sx={sxStepIcon} />;
  }
};

export const getStepMessageFromStepStatus = (status: TemplateGenerationStepStatus) => {
  switch (status) {
    case TemplateGenerationStepStatus.PROGRESS:
      return 'Loading...';
    case TemplateGenerationStepStatus.SUCCESS:
      return 'Success';
    case TemplateGenerationStepStatus.ERROR:
      return 'Error';
    case TemplateGenerationStepStatus.WAITING:
      return 'Waiting';
    case TemplateGenerationStepStatus.CANCELLED:
      return 'Cancelled';
  }
};
