import { FC } from 'react';
import { Box, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField } from '@mui/material';
import Button from '@mui/material/Button';
import { ADMIN_COLOR_DARK } from '../../constants/colors';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { Transition } from './password-confirmation-dialog';

interface InitialFormInputs {
  confirmation: string;
}

const schemaInitialConfirmation = yup.object().shape({
  confirmation: yup.string().oneOf(['DELETE'], 'Type in DELETE to confirm'),
});

const RemoveMyAccountDialog: FC<{ open: boolean; handleConfirm: () => void; handleClose: () => void }> = ({
  open,
  handleConfirm,
  handleClose,
}) => {
  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm<InitialFormInputs>({ resolver: yupResolver(schemaInitialConfirmation) });
  const formSubmitHandler: SubmitHandler<InitialFormInputs> = (data: InitialFormInputs) => {
    handleConfirm();
  };
  return (
    <Dialog fullWidth maxWidth="sm" open={open} onClose={handleClose} TransitionComponent={Transition}>
      <DialogTitle sx={{ color: ADMIN_COLOR_DARK }}>Deleting account</DialogTitle>
      <Box component="form" onSubmit={handleSubmit(formSubmitHandler)} noValidate sx={{ mt: 1 }}>
        <DialogContent>
          <DialogContentText>
            By confirming you acknowledge that you want to delete your account. You will no longer be to access the
            account and the account will be automatically removed in the nearest future.
            <br />
            <br />
            You will be able to undo this action only for a limited time. An e-mail with a link to go to in case you
            changed your mind will be sent to your address.
            <br />
            <br />
            You will be logged out immediately following the confirmation.
            <br />
            <br />
            <strong>
              Type in <i>DELETE</i> to confirm:
            </strong>
          </DialogContentText>
          <Controller
            name="confirmation"
            control={control}
            defaultValue=""
            render={({ field }) => (
              <TextField
                {...field}
                variant="outlined"
                error={!!errors.confirmation}
                helperText={errors.confirmation ? errors.confirmation?.message : ''}
                margin="normal"
                required
                fullWidth
                name="confirmation"
                label="Confirmation"
                id="confirmation"
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

export default RemoveMyAccountDialog;
