import { useQuery } from 'react-query';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { Container, IconButton } from '@mui/material';
import { Check, Close, PlayCircleOutline } from '@mui/icons-material';
import { getTasks, postExecuteTask } from '../api/requests/tasks/tasks.requests';
import { Task } from '../api/requests/tasks/tasks.types';
import { toast } from 'react-toastify';
import { useState } from 'react';
import ConfirmDialog from '../components/task/ConfirmDialog';
import Typography from '@mui/material/Typography';
import { theme } from '../styles/theme';

const Tasks = () => {
  const { data, isLoading, isRefetching } = useQuery('tasks', getTasks);
  const tasks: Task[] = data?.data || [];

  const [open, setOpen] = useState(false);
  const [handleConfirm, setHandleConfirm] = useState<() => void>(() => {});

  const handleOpenDialog = (taskName: string) => {
    setOpen(true);
    setHandleConfirm(() => handleExecuteTask(taskName));
  };

  const handleCloseDialog = () => setOpen(false);

  const handleExecuteTask = (task: string) => {
    postExecuteTask(task)
      .then((response) => {
        if (response.data.success) {
          toast.success(response.data.message, {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
        } else {
          toast.error(response.data.message, {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
        }
      })
      .catch(() =>
        toast.error('Failed to execute the task.', {
          position: 'top-right',
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        }),
      );
  };

  const columns: GridColDef[] = [
    { field: 'taskName', headerName: 'Code name', width: 275 },
    { field: 'description', headerName: 'Description', flex: 1 },
    {
      field: 'scheduled',
      headerName: 'Scheduled',
      width: 130,
      sortable: false,
      filterable: false,
      headerAlign: 'center',
      renderCell: ({ row }) =>
        row.scheduled ? (
          <Check fontSize="large" sx={{ color: '#00cc00', marginLeft: '40px' }} />
        ) : (
          <Close fontSize="large" sx={{ color: 'red', marginLeft: '40px' }} />
        ),
    },
    {
      field: 'executable',
      headerName: 'Executable',
      width: 130,
      sortable: false,
      filterable: false,
      headerAlign: 'center',
      renderCell: ({ row }) =>
        row.executable ? (
          <Check fontSize="large" sx={{ color: '#00cc00', marginLeft: '40px' }} />
        ) : (
          <Close fontSize="large" sx={{ color: 'red', marginLeft: '40px' }} />
        ),
    },
    { field: 'cronExpression', headerName: 'Cron expression', width: 160 },
    {
      field: 'nextScheduledExecution',
      headerName: 'Next scheduled execution',
      width: 200,
      renderCell: ({ row }) =>
        row.nextScheduledExecution || <Close fontSize="large" sx={{ color: 'red', marginLeft: '40px' }} />,
    },
    {
      field: 'actions',
      headerName: 'Execute task',
      width: 130,
      sortable: false,
      filterable: false,
      renderCell: ({ row }) =>
        row.executable ? (
          <div>
            <IconButton onClick={() => handleOpenDialog(row.taskName)}>
              <PlayCircleOutline fontSize="large" sx={{ color: '#00cc00' }} />
            </IconButton>
          </div>
        ) : (
          <div>
            <IconButton disabled>
              <PlayCircleOutline fontSize="large" sx={{ color: 'gray' }} />
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
      {open && <ConfirmDialog open={open} handleCancel={handleCloseDialog} handleConfirm={handleConfirm} />}
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
