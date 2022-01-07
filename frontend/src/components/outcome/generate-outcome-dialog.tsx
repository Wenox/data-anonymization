import React, { FC } from 'react';
import { Transition } from '../user/password-confirmation-dialog';
import Typography from '@mui/material/Typography';
import { Box, Dialog, DialogActions, DialogContent, TextField } from '@mui/material';
import Button from '@mui/material/Button';
import { toast } from 'react-toastify';
import { postGenerateOutcome } from '../../api/requests/outcomes/outcome.requests';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';

interface GenerateOutcomeDialogProps {
  open: boolean;
  worksheetId: string;
  handleClose: () => void;
}

interface IFormInputs {
  worksheetId: string;
  scriptName: string;
}

const schema = yup.object().shape({
  scriptName: yup.string().required('Script name is required'),
});

const GenerateOutcomeDialog: FC<GenerateOutcomeDialogProps> = ({ open, worksheetId, handleClose }) => {
  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm<IFormInputs>({ resolver: yupResolver(schema) });

  const formSubmitHandler: SubmitHandler<IFormInputs> = (data: IFormInputs) => {
    postGenerateOutcome({ ...data, worksheetId: worksheetId })
      .then((response) => {
        if (response.status === 202) {
          toast.success('Successfully started to generate a new outcome.', {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
          handleClose();
        } else {
          toast.error('Failed to generate a new outcome.', {
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
      .catch((err) =>
        toast.error('Failed to generate a new outcome.', {
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

  return (
    <Dialog fullWidth maxWidth="sm" open={open} onClose={handleClose} TransitionComponent={Transition}>
      <Box component="form" onSubmit={handleSubmit(formSubmitHandler)} noValidate>
        <Typography color="secondary" variant="h4" sx={{ mt: 2, pl: 3 }}>
          Generate outcome
        </Typography>
        <DialogContent>
          <p>By confirming you will start to generate a new outcome. This process may take a while.</p>
          <Controller
            name="scriptName"
            control={control}
            defaultValue=""
            render={({ field }) => (
              <TextField
                {...field}
                variant="outlined"
                error={!!errors.scriptName}
                helperText={errors.scriptName ? errors.scriptName?.message : ''}
                margin="normal"
                required
                fullWidth
                name="scriptName"
                label="Script name"
                id="scriptName"
              />
            )}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} fullWidth color="primary" variant="contained">
            Cancel
          </Button>
          <Button color="secondary" type="submit" fullWidth variant="contained">
            Confirm
          </Button>
        </DialogActions>
      </Box>
    </Dialog>
  );
};

export default GenerateOutcomeDialog;
