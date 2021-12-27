export interface Task {
  taskName: string;
  cronExpression: string;
  description: string;
  scheduled: boolean;
  executable: boolean;
}
