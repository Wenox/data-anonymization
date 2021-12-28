import React, { FC } from 'react';
import { Box, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField } from '@mui/material';
import { ADMIN_COLOR_DARK } from '../../constants/colors';
import { Controller } from 'react-hook-form';
import Button from '@mui/material/Button';
import { Transition } from '../user/password-confirmation-dialog';

interface ConfirmDialogProps {
  open: boolean;
  handleCancel: () => void;
  handleConfirm: () => void;
}

const ConfirmDialog: FC<ConfirmDialogProps> = ({ open, handleCancel, handleConfirm }) => {
  return (
    <Dialog fullWidth maxWidth="sm" open={open} onClose={handleCancel} TransitionComponent={Transition}>
      <DialogTitle sx={{ color: ADMIN_COLOR_DARK }}>Confirmation</DialogTitle>
      <DialogContent>
        <DialogContentText>
          <strong>Are you sure to execute this task?</strong>
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleCancel}>Cancel</Button>
        <Button onClick={handleConfirm}>Confirm</Button>
      </DialogActions>
    </Dialog>
  );
};

export default ConfirmDialog;
