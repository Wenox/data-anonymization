import { ChangeEvent, FC, useState } from 'react';
import { Box, Button, Container, Divider, Grid, IconButton, LinearProgress, MenuItem, Typography } from '@mui/material';
import { Cancel, Check, Error as ErrorIcon } from '@mui/icons-material';
import { postCreateTemplate } from '../api/requests/templates/templates.requests';
import * as yup from 'yup';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import TextField from '@mui/material/TextField';
import { theme } from '../styles/theme';
import { toast } from 'react-toastify';
import { MAX_FILE_SIZE } from '../constants/file';
import { useNavigate } from 'react-router-dom';
import { FILE_UPLOADER_TIMEOUT, SHOW_TEMPLATE_GENERATION_STEPS_TIMEOUT } from '../constants/timeouts';
import { ROUTES } from '../constants/routes';

interface IFormInputs {
  title: string;
  type: string;
  restoreMode: string;
  description: string;
}

const schema = yup.object().shape({
  title: yup.string().required('Title is required'),
  type: yup.string().required('Type is required'),
  restoreMode: yup.string().required('Restore mode is required'),
  description: yup.string(),
});

export interface FileError {
  display: boolean;
  message: string;
}

const Templates: FC = () => {
  const navigate = useNavigate();
  const [file, setFile] = useState<any>();
  const [fileError, setFileError] = useState<FileError>({ display: false, message: '' });
  const [isFileSelected, setIsFileSelected] = useState(false);
  const [isUploading, setIsUploading] = useState(false);
  const [progress, setProgress] = useState(0);
  const [isSuccess, setIsSuccess] = useState(false);
  const [saveDisabled, setSaveDisabled] = useState(false);

  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm<IFormInputs>({ resolver: yupResolver(schema) });

  const formSubmitHandler: SubmitHandler<IFormInputs> = (data: IFormInputs) => {
    if (file == null) {
      setFileError({ display: true, message: 'You must select a template file!' });
      return;
    } else if (file.size > MAX_FILE_SIZE) {
      setFileError({ display: true, message: 'File must not be larger than 10 MB!' });
      setIsFileSelected(false);
      setFile(null);
      return;
    } else {
      setFileError({ display: false, message: '' });
    }

    const formData = new FormData();
    formData.append('file', file);
    Object.entries(data).forEach(([key, value]) => {
      formData.append(key, value);
    });

    handlePostCreateTemplate(formData);
  };

  const handleFileCancel = () => {
    setIsFileSelected(false);
    setFile(null);
  };

  const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files[0]) {
      setFileError({ display: false, message: '' });
      setFile(event.target.files[0]);
      setIsFileSelected(true);
      setProgress(0);
      setIsSuccess(false);
      setIsUploading(false);
    }
  };

  const handleUploadProgress = (progressEvent?: any) => setProgress((progressEvent.loaded / progressEvent.total) * 100);

  const handlePostCreateTemplate = (formData: FormData) => {
    setIsSuccess(false);
    setIsUploading(true);

    postCreateTemplate(formData, handleUploadProgress)
      .then((response) => {
        if (response.status === 202) {
          setTimeout(() => {
            setFile(null);
            setProgress(0);
            setIsSuccess(true);
            setIsUploading(false);
            setIsFileSelected(false);
            setSaveDisabled(true);
          }, FILE_UPLOADER_TIMEOUT);
          toast.success('Successfully started to generate a new template.', {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
          setTimeout(
            () => navigate(`${ROUTES.TEMPLATES_GENERATING_NEW}?template_id=${response.data}`),
            SHOW_TEMPLATE_GENERATION_STEPS_TIMEOUT,
          );
        }
      })
      .catch((err) =>
        toast.error('Error: ' + err.message, {
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
    <>
      <Container
        maxWidth={'lg'}
        component="main"
        sx={{
          backgroundColor: '#fff',
          border: `1px solid ${theme.palette.primary.main}`,
          boxShadow: `4px 4px 0px ${theme.palette.primary.dark}`,
          borderRadius: '2px',
          pt: 2,
          pb: 3,
        }}
      >
        <Typography color="secondary" variant="h4" sx={{ mb: 2 }}>
          New template
        </Typography>

        <Divider sx={{ mt: 2, mb: 2 }} />
        <h3 style={{ color: `${theme.palette.primary.light}` }}>Description</h3>

        <Box component="form" onSubmit={handleSubmit(formSubmitHandler)} noValidate sx={{ mt: -2 }}>
          <Grid container columnSpacing={2}>
            <Grid item xs={6}>
              <Controller
                name="title"
                control={control}
                defaultValue=""
                render={({ field }) => (
                  <TextField
                    {...field}
                    label="Title"
                    variant="outlined"
                    error={!!errors.title}
                    helperText={errors.title ? errors.title?.message : ''}
                    margin="normal"
                    required
                    fullWidth
                    id="title"
                    name="title"
                    autoComplete="title"
                  />
                )}
              />
            </Grid>
            <Grid item xs={3}>
              <Controller
                name="type"
                control={control}
                defaultValue="PSQL"
                render={({ field }) => (
                  <TextField
                    {...field}
                    label="Type"
                    select
                    variant="outlined"
                    error={!!errors.type}
                    helperText={errors.type ? errors.type?.message : ''}
                    margin="normal"
                    required
                    fullWidth
                    id="type"
                    name="type"
                    autoComplete="type"
                  >
                    <MenuItem key={'PSQL'} value={'PSQL'}>
                      PostgreSQL
                    </MenuItem>
                  </TextField>
                )}
              />
            </Grid>
            <Grid item xs={3}>
              <Controller
                name="restoreMode"
                control={control}
                defaultValue="ARCHIVE"
                render={({ field }) => (
                  <TextField
                    {...field}
                    label="Restore mode"
                    select
                    variant="outlined"
                    error={!!errors.restoreMode}
                    helperText={errors.restoreMode ? errors.restoreMode?.message : ''}
                    margin="normal"
                    required
                    fullWidth
                    id="restoreMode"
                    name="restoreMode"
                    autoComplete="restoreMode"
                  >
                    <MenuItem key={'ARCHIVE'} value={'ARCHIVE'}>
                      Compressed archive
                    </MenuItem>
                    <MenuItem key={'SCRIPT'} value={'SCRIPT'}>
                      Script file
                    </MenuItem>
                  </TextField>
                )}
              />
            </Grid>

            <Grid item xs={12}>
              <Controller
                name="description"
                control={control}
                defaultValue=""
                render={({ field }) => (
                  <TextField
                    {...field}
                    multiline
                    minRows={2}
                    maxRows={2}
                    label="Description"
                    variant="outlined"
                    error={!!errors.description}
                    helperText={errors.description ? errors.description?.message : ''}
                    margin="normal"
                    fullWidth
                    id="description"
                    name="description"
                    autoComplete="description"
                  />
                )}
              />
            </Grid>
          </Grid>

          <Divider sx={{ mt: 2, mb: 2 }} />
          <h3 style={{ color: `${theme.palette.primary.light}` }}>Dump</h3>

          <Box sx={{ mt: 2, height: '90px' }}>
            <Button
              disabled={isFileSelected}
              color="secondary"
              onDragOver={(e: any) => {
                e.preventDefault();
              }}
              onDrop={(e: any) => {
                e.preventDefault();
              }}
              variant="contained"
              component="label"
              sx={{ pl: 6, pr: 6 }}
            >
              Select database file
              <input onChange={handleFileChange} type="file" hidden />
            </Button>
            {file && (
              <Typography color="success" variant="h6" sx={{ fontSize: '80%' }}>
                {file.name} ({Number(file.size / 1024 / 1024).toFixed(2)} MB)
                <span>
                  <IconButton disabled={isUploading} onClick={handleFileCancel} sx={{ ml: 0.5 }}>
                    <Cancel color={isUploading ? 'disabled' : 'error'} fontSize="small" />
                  </IconButton>
                </span>
              </Typography>
            )}

            {isSuccess && (
              <Box color="success.main" display="flex" sx={{ alignItems: 'center' }}>
                <Check color="success" fontSize="medium" />
                <Typography color="success" variant="h6" sx={{ ml: 0.5 }}>
                  Success
                </Typography>
              </Box>
            )}
            {fileError.display && (
              <Box color="error.main" display="flex" sx={{ alignItems: 'center', mt: 1 }}>
                <ErrorIcon color="error" fontSize="medium" />
                <Typography color="success" variant="subtitle2" sx={{ ml: 0.5 }}>
                  {fileError.message}
                </Typography>
              </Box>
            )}
            <Box width="256px">
              {isUploading && (
                <LinearProgress sx={{ height: '8px' }} color="primary" variant="determinate" value={progress} />
              )}
            </Box>
          </Box>
          <Divider sx={{ mt: 2 }} />
          <Button disabled={saveDisabled} color="primary" type="submit" variant="contained" fullWidth sx={{ mt: 3 }}>
            Generate template
          </Button>
        </Box>
      </Container>
    </>
  );
};

export default Templates;
