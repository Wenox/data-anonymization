import React, { FC } from 'react';
import * as yup from 'yup';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import {
  Box,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Slide,
  TextField,
} from '@mui/material';
import { ADMIN_COLOR_DARK } from '../../constants/colors';
import Button from '@mui/material/Button';
import { putRemoveMyAccount } from '../../api/requests/me/me.requests';
import { toast } from 'react-toastify';
import { TransitionProps } from '@mui/material/transitions';
import { useNavigate } from 'react-router-dom';
import { ROUTES } from '../../constants/routes';

export const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement<any, any>;
  },
  ref: React.Ref<unknown>,
) {
  return <Slide direction="up" ref={ref} {...props} />;
});

interface FinalFormInputs {
  password: string;
}

const schemaFinalConfirmation = yup.object().shape({
  password: yup.string().min(4).max(20).required(),
});

const PasswordConfirmationDialog: FC<{ open: boolean; handleClose: () => void }> = ({ open, handleClose }) => {
  const navigate = useNavigate();
  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm<FinalFormInputs>({ resolver: yupResolver(schemaFinalConfirmation) });
  const formSubmitHandler: SubmitHandler<FinalFormInputs> = (data: FinalFormInputs) => {
    putRemoveMyAccount(data)
      .then((response) => {
        if (response.status === 200 && response.data.success) {
          toast.success(response.data.message, {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
          handleClose();
          navigate(ROUTES.LOGOUT);
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
      .catch(() => {
        toast.error('Failed to delete the account.', {
          position: 'top-right',
          autoClose: 3000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        handleClose();
      });
  };
  return (
    <Dialog fullWidth maxWidth="sm" open={open} onClose={handleClose} TransitionComponent={Transition}>
      <DialogTitle sx={{ color: ADMIN_COLOR_DARK }}>Deleting account</DialogTitle>
      <Box component="form" onSubmit={handleSubmit(formSubmitHandler)} noValidate sx={{ mt: 1 }}>
        <DialogContent>
          <DialogContentText>
            <strong>Type in your password to confirm:</strong>
          </DialogContentText>
          <Controller
            name="password"
            control={control}
            defaultValue=""
            render={({ field }) => (
              <TextField
                {...field}
                variant="outlined"
                error={!!errors.password}
                helperText={errors.password ? errors.password?.message : ''}
                margin="normal"
                required
                fullWidth
                name="password"
                label="Password"
                id="password"
                type="password"
              />
            )}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button type="submit">Confirm</Button>
        </DialogActions>
      </Box>
    </Dialog>
  );
};

export default PasswordConfirmationDialog;
