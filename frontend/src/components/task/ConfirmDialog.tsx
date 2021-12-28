import React, { FC, useState } from 'react';
import {
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  TextField,
} from '@mui/material';
import { ADMIN_COLOR_DARK } from '../../constants/colors';
import Button from '@mui/material/Button';
import { Transition } from '../user/password-confirmation-dialog';
import { postExecuteTask } from '../../api/requests/tasks/tasks.requests';
import { toast } from 'react-toastify';

interface ConfirmDialogProps {
  open: boolean;
  handleCancel: () => void;
  taskName: string;
}

const ConfirmDialog: FC<ConfirmDialogProps> = ({ open, handleCancel, taskName }) => {
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
    <Dialog fullWidth maxWidth="sm" open={open} onClose={handleCancel} TransitionComponent={Transition}>
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
      <DialogTitle sx={{ color: ADMIN_COLOR_DARK }}>Confirmation</DialogTitle>
      <DialogContent>
        <DialogContentText>
          <strong>Are you sure to execute this task?</strong>
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleCancel}>Cancel</Button>
        <Button onClick={handleExecuteTask}>Confirm</Button>
      </DialogActions>
    </Dialog>
  );
};

export default ConfirmDialog;
