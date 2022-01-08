import React, { FC } from 'react';
import { Transition } from '../user/password-confirmation-dialog';
import Typography from '@mui/material/Typography';
import {
  Box,
  Dialog,
  DialogActions,
  DialogContent,
  FormControl,
  FormControlLabel,
  FormLabel,
  Radio,
  RadioGroup,
  TextField,
} from '@mui/material';
import Button from '@mui/material/Button';
import { toast } from 'react-toastify';
import { postGenerateOutcome } from '../../api/requests/outcomes/outcome.requests';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { DumpMode } from '../../api/requests/outcomes/outcome.types';

interface GenerateOutcomeDialogProps {
  open: boolean;
  worksheetId: string;
  handleClose: () => void;
}

interface IFormInputs {
  worksheetId: string;
  anonymisationScriptName: string;
  dumpMode: DumpMode;
}

const schema = yup.object().shape({
  anonymisationScriptName: yup.string().required('Script name is required'),
});

const GenerateOutcomeDialog: FC<GenerateOutcomeDialogProps> = ({ open, worksheetId, handleClose }) => {
  const [dumpMode, setDumpMode] = React.useState<DumpMode>(DumpMode.SCRIPT_FILE);
  const handleChangeDumpMode = (event: React.ChangeEvent<HTMLInputElement>) => {
    setDumpMode(event.target.value as DumpMode);
  };

  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm<IFormInputs>({ resolver: yupResolver(schema) });

  const formSubmitHandler: SubmitHandler<IFormInputs> = (data: IFormInputs) => {
    postGenerateOutcome({ ...data, worksheetId: worksheetId, dumpMode: dumpMode })
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
            name="anonymisationScriptName"
            control={control}
            defaultValue=""
            render={({ field }) => (
              <TextField
                {...field}
                variant="outlined"
                error={!!errors.anonymisationScriptName}
                helperText={errors.anonymisationScriptName ? errors.anonymisationScriptName?.message : ''}
                margin="normal"
                required
                fullWidth
                name="anonymisationScriptName"
                label="Anonymisation script name"
                id="anonymisationScriptName"
              />
            )}
          />
          <FormLabel component="legend">Dump mode</FormLabel>
          <RadioGroup row value={dumpMode} onChange={handleChangeDumpMode}>
            <FormControlLabel value={DumpMode.SCRIPT_FILE} control={<Radio />} label="Script file" />
            <FormControlLabel value={DumpMode.COMPRESSED_ARCHIVE} control={<Radio />} label="Compressed archive" />
          </RadioGroup>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} fullWidth color="primary" variant="contained">
            Cancel
          </Button>
          <Button color="secondary" type="submit" fullWidth variant="contained">
            Generate
          </Button>
        </DialogActions>
      </Box>
    </Dialog>
  );
};

export default GenerateOutcomeDialog;
