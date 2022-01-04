import { TemplateMetadata } from '../../api/requests/templates/templates.types';

export const computeHrefDownloadUrl = (metadata: any) => `data:text/json;charset=utf-8,${metadata}`;

export const formatMetadata = (metadata?: TemplateMetadata) => (metadata ? JSON.stringify(metadata, null, 4) : null);
