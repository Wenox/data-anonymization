export enum TemplateGenerationStatus {
  PROGRESS,
  SUCCESS,
  ERROR,
  WAITING,
  CANCELLED,
}

export interface Steps {
  step1: TemplateGenerationStatus;
  step2: TemplateGenerationStatus;
  step3: TemplateGenerationStatus;
}
