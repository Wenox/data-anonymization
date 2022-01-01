import { TemplateStatus } from '../../../components/template/template-generation-base.types';
import { FileType } from '../shared.types';

export interface MyTemplate {
  id: string;
  originalFileName: string;
  status: TemplateStatus;
  type: FileType;
  title: string;
  description: string;
  createdDate: string;
  metadata: any;
}
