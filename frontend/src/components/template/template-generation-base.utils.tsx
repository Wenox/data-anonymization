import { CircularProgress } from '@mui/material';
import { Block, CheckCircleOutline, ErrorOutlineOutlined, HourglassTopOutlined } from '@mui/icons-material';
import React from 'react';
import { TemplateGenerationStatus } from './template-generation-base.types';
import { sxStepIcon, sxStepProgress } from './template-generation-base.styles';

export const getStepIconFromStatus = (status: TemplateGenerationStatus) => {
  if (status === TemplateGenerationStatus.PROGRESS) {
    return <CircularProgress color="secondary" size="6rem" sx={sxStepProgress} />;
  } else if (status === TemplateGenerationStatus.SUCCESS) {
    return <CheckCircleOutline color="success" sx={sxStepIcon} />;
  } else if (status === TemplateGenerationStatus.ERROR) {
    return <ErrorOutlineOutlined color="error" sx={sxStepIcon} />;
  } else if (status === TemplateGenerationStatus.WAITING) {
    return <HourglassTopOutlined color={'disabled'} sx={sxStepIcon} />;
  } else if (status === TemplateGenerationStatus.CANCELLED) {
    return <Block color="secondary" sx={sxStepIcon} />;
  }
};

export const getStepMessageFromStatus = (status: TemplateGenerationStatus) => {
  if (status === TemplateGenerationStatus.PROGRESS) {
    return 'Loading...';
  } else if (status === TemplateGenerationStatus.SUCCESS) {
    return 'Success';
  } else if (status === TemplateGenerationStatus.ERROR) {
    return 'Error';
  } else if (status === TemplateGenerationStatus.WAITING) {
    return 'Waiting';
  } else if (status === TemplateGenerationStatus.CANCELLED) {
    return 'Cancelled';
  }
};
