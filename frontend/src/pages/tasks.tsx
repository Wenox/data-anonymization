import { useQuery } from 'react-query';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { Container, IconButton } from '@mui/material';
import { Check, Close, PlayCircleOutline } from '@mui/icons-material';
import { getTasks } from '../api/requests/tasks/tasks.requests';
import { Task } from '../api/requests/tasks/tasks.types';
import { useState } from 'react';
import TaskConfirmDialog from '../components/task/TaskConfirmDialog';
import Typography from '@mui/material/Typography';
import { theme } from '../styles/theme';
import { centeredColumn } from '../styles/data-table';

const Tasks = () => {
  const { data, isLoading, isRefetching } = useQuery('tasks', getTasks);
  const tasks: Task[] = data?.data || [];

  const [open, setOpen] = useState(false);
  const [taskName, setTaskName] = useState('');

  const handleOpenDialog = (taskName: string) => {
    setOpen(true);
    setTaskName(taskName);
  };

  const handleCloseDialog = () => {
    setOpen(false);
  };

  const columns: GridColDef[] = [
    { field: 'taskName', headerName: 'Code name', width: 275, ...centeredColumn() },
    { field: 'description', headerName: 'Description', flex: 1, headerClassName: 'data-grid-header' },
    {
      field: 'scheduled',
      headerName: 'Scheduled',
      width: 130,
      sortable: false,
      filterable: false,
      ...centeredColumn(),
      renderCell: ({ row }) =>
        row.scheduled ? (
          <Check fontSize="large" sx={{ color: '#00ad17' }} />
        ) : (
          <Close fontSize="large" sx={{ color: '#e00000' }} />
        ),
    },
    {
      field: 'executable',
      headerName: 'Executable',
      width: 130,
      sortable: false,
      filterable: false,
      ...centeredColumn(),
      renderCell: ({ row }) =>
        row.executable ? (
          <Check fontSize="large" sx={{ color: '#00ad17' }} />
        ) : (
          <Close fontSize="large" sx={{ color: '#e00000' }} />
        ),
    },
    { field: 'cronExpression', headerName: 'Cron expression', width: 160, ...centeredColumn() },
    {
      field: 'nextScheduledExecution',
      headerName: 'Next scheduled execution',
      width: 240,
      ...centeredColumn(),
      renderCell: ({ row }) => row.nextScheduledExecution || <Close fontSize="large" sx={{ color: '#e00000' }} />,
    },
    {
      field: 'actions',
      headerName: 'Execute task',
      width: 130,
      sortable: false,
      filterable: false,
      ...centeredColumn(),
      renderCell: ({ row }) =>
        row.executable ? (
          <div>
            <IconButton onClick={() => handleOpenDialog(row.taskName)}>
              <PlayCircleOutline fontSize="large" sx={{ color: '#00ad17' }} />
            </IconButton>
          </div>
        ) : (
          <div>
            <IconButton disabled>
              <PlayCircleOutline fontSize="large" sx={{ color: 'disabled' }} />
            </IconButton>
          </div>
        ),
    },
  ];

  return (
    <Container
      maxWidth={false}
      component="main"
      sx={{
        backgroundColor: '#fff',
        border: `1px solid ${theme.palette.primary.main}`,
        boxShadow: `4px 4px 0px ${theme.palette.primary.dark}`,
        pt: 2,
        pb: 3,
        borderRadius: '2px',
      }}
    >
      <Typography color="secondary" variant="h2" sx={{ mb: 2 }}>
        Tasks
      </Typography>
      {open && <TaskConfirmDialog open={open} handleCancel={handleCloseDialog} taskName={taskName} />}
      <DataGrid
        autoHeight
        columns={columns}
        rows={tasks.map((task) => ({
          ...task,
          id: task.taskName,
        }))}
        loading={isLoading || isRefetching}
      />
    </Container>
  );
};

export default Tasks;
