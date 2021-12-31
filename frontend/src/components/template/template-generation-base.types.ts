export enum TemplateGenerationStepStatus {
  PROGRESS,
  SUCCESS,
  ERROR,
  WAITING,
  CANCELLED,
}

export interface Steps {
  step1: TemplateGenerationStepStatus;
  step2: TemplateGenerationStepStatus;
  step3: TemplateGenerationStepStatus;
}

export enum TemplateStatus {
  NEW = 'NEW',
  UPLOAD_SUCCESS = 'UPLOAD_SUCCESS',
  UPLOAD_FAILURE = 'UPLOAD_FAILURE',
  RESTORE_SUCCESS = 'RESTORE_SUCCESS',
  RESTORE_FAILURE = 'RESTORE_FAILURE',
  METADATA_READY = 'METADATA_READY',
  METADATA_FAILURE = 'METADATA_FAILURE',
}
