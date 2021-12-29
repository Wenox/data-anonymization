import { ChangeEvent, FC, useState } from 'react';
import { Box, Button, Container, IconButton, LinearProgress, Typography } from '@mui/material';
import { Cancel, Check, Error } from '@mui/icons-material';
import { postCreateTemplate } from '../api/requests/templates/templates.requests';
import * as yup from 'yup';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import TextField from '@mui/material/TextField';
import { theme } from '../styles/theme';
import { toast } from 'react-toastify';

interface IFormInputs {
  title: string;
}

const schema = yup.object().shape({
  title: yup.string().required('Title is required'),
});

interface FileError {
  display: boolean;
  message: string;
}

const Templates: FC = () => {
  const [file, setFile] = useState<any>();
  const [fileError, setFileError] = useState<FileError>({ display: false, message: '' });
  const [isFileSelected, setIsFileSelected] = useState(false);
  const [isUploading, setIsUploading] = useState(false);
  const [progress, setProgress] = useState(0);
  const [isSuccess, setIsSuccess] = useState(false);

  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm<IFormInputs>({ resolver: yupResolver(schema) });

  const formSubmitHandler: SubmitHandler<IFormInputs> = (data: IFormInputs) => {
    if (file == null) {
      setFileError({ display: true, message: 'You must select a template file!' });
      return;
    } else if (file.size > 1024 * 1024 * 10) {
      setFileError({ display: true, message: 'File must not be larger than 10 MB!' });
      setIsFileSelected(false);
      setFile(null);
      return;
    } else {
      setFileError({ display: false, message: '' });
    }

    const formData = new FormData();
    formData.append('file', file);

    handlePostCreateTemplate(formData, 'PSQL');
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

  const handlePostCreateTemplate = (formData: FormData, type: string) => {
    setIsSuccess(false);
    setIsUploading(true);

    postCreateTemplate(formData, type, 'title', handleUploadProgress)
      .then((response) => {
        if (response.status === 202) {
          setTimeout(() => {
            setFile(null);
            setProgress(0);
            setIsSuccess(true);
            setIsUploading(false);
            setIsFileSelected(false);
          }, 500);
          toast.success('Success.', {
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
        maxWidth={false}
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
        <Typography color="secondary" variant="h2" sx={{ mb: 2 }}>
          New template
        </Typography>

        <Box component="form" onSubmit={handleSubmit(formSubmitHandler)} noValidate sx={{ mt: 1 }}>
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

          <Box sx={{ height: '120px' }}>
            <Button
              disabled={isFileSelected}
              color="primary"
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
              Select file
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
                <Error color="error" fontSize="medium" />
                <Typography color="success" variant="subtitle2" sx={{ ml: 0.5 }}>
                  {fileError.message}
                </Typography>
              </Box>
            )}
            <Box width="180px">
              {isUploading && (
                <LinearProgress sx={{ height: '8px' }} color="primary" variant="determinate" value={progress} />
              )}
            </Box>
          </Box>
          <Button color="secondary" type="submit" variant="contained" sx={{ mt: 3, mb: 2 }}>
            Save
          </Button>
        </Box>
      </Container>
    </>
  );
};

export default Templates;
