export interface CreateWorksheet {
  templateId: string;
}

export interface WorksheetCreated {
  id: string;
  templateId: string;
  userId: string;
}
