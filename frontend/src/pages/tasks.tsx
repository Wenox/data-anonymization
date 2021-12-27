import { useQuery } from 'react-query';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import '../styles/users.scss';
import { IconButton } from '@mui/material';
import { Check, Close, PlayCircleOutline } from '@mui/icons-material';
import { getTasks, postExecuteTask } from '../api/requests/tasks/tasks.requests';
import { Task } from '../api/requests/tasks/tasks.types';
import { toast } from 'react-toastify';

const Tasks = () => {
  const { data, isLoading, isRefetching } = useQuery('tasks', getTasks);
  const tasks: Task[] = data?.data || [];

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
            <IconButton onClick={() => handleExecuteTask(row.taskName)}>
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
    <>
      <div id="tasks">
        <h1>Tasks</h1>
        <DataGrid
          autoHeight
          columns={columns}
          rows={tasks.map((task) => ({
            ...task,
            id: task.taskName,
          }))}
          loading={isLoading || isRefetching}
        />
      </div>
    </>
  );
};

export default Tasks;
