import React, { FC, useState } from 'react';
import { CircularProgress, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import Button from '@mui/material/Button';
import { Transition } from '../user/password-confirmation-dialog';
import { postExecuteTask } from '../../api/requests/tasks/tasks.requests';
import { toast } from 'react-toastify';

interface TaskConfirmDialogProps {
  open: boolean;
  handleCancel: () => void;
  taskName: string;
}

const TaskConfirmDialog: FC<TaskConfirmDialogProps> = ({ open, handleCancel, taskName }) => {
  const [isLoading, setIsLoading] = useState(false);
  const handleExecuteTask = () => {
    setIsLoading(true);
    postExecuteTask(taskName)
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
      )
      .finally(() => {
        setIsLoading(false);
        handleCancel();
      });
  };
  return (
    <Dialog fullWidth maxWidth="xs" open={open} onClose={handleCancel} TransitionComponent={Transition}>
      {isLoading && (
        <CircularProgress
          style={{
            position: 'absolute',
            left: '50%',
            top: '50%',
            transform: 'translate(-50%, -50%)',
          }}
        />
      )}
      <DialogTitle>Confirmation</DialogTitle>
      <DialogContent>
        <DialogContentText>
          <strong>Are you sure to execute this task?</strong>
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleCancel}>Cancel</Button>
        <Button color="secondary" onClick={handleExecuteTask}>
          Confirm
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default TaskConfirmDialog;
