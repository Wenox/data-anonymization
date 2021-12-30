import { CircularProgress } from '@mui/material';
import { Block, CheckCircleOutline, ErrorOutlineOutlined, HourglassTopOutlined } from '@mui/icons-material';
import React from 'react';
import { TemplateGenerationStatus } from './template-generation-base.types';
import { sxStepIcon, sxStepProgress } from './template-generation-base.styles';

export const getStepIconFromStatus = (status: TemplateGenerationStatus) => {
  switch (status) {
    case TemplateGenerationStatus.PROGRESS:
      return <CircularProgress color="secondary" size="6rem" sx={sxStepProgress} />;
    case TemplateGenerationStatus.SUCCESS:
      return <CheckCircleOutline color="success" sx={sxStepIcon} />;
    case TemplateGenerationStatus.ERROR:
      return <ErrorOutlineOutlined color="error" sx={sxStepIcon} />;
    case TemplateGenerationStatus.WAITING:
      return <HourglassTopOutlined color={'disabled'} sx={sxStepIcon} />;
    case TemplateGenerationStatus.CANCELLED:
      return <Block color="secondary" sx={sxStepIcon} />;
  }
};

export const getStepMessageFromStatus = (status: TemplateGenerationStatus) => {
  switch (status) {
    case TemplateGenerationStatus.PROGRESS:
      return 'Loading...';
    case TemplateGenerationStatus.SUCCESS:
      return 'Success';
    case TemplateGenerationStatus.ERROR:
      return 'Error';
    case TemplateGenerationStatus.WAITING:
      return 'Waiting';
    case TemplateGenerationStatus.CANCELLED:
      return 'Cancelled';
  }
};
