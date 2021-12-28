import { ChangeEvent, FC, useState } from 'react';
import { Box, Button, IconButton, LinearProgress, Typography } from '@mui/material';
import axios from 'axios';
import { Cancel, Check } from '@mui/icons-material';

const Templates: FC = () => {
  const [file, setFile] = useState<any>();
  const [isFileSelected, setIsFileSelected] = useState(false);
  const [isUploading, setIsUploading] = useState(false);
  const [progress, setProgress] = useState(0);
  const [isSuccess, setIsSuccess] = useState(false);

  const postCreateTemplate = (formData: FormData, type: string) => {
    setIsSuccess(false);
    setIsUploading(true);
    axios
      .post<string>(`/api/v1/templates?type=${type}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
        onUploadProgress: (progressEvent) => {
          setProgress((progressEvent.loaded / progressEvent.total) * 100);
        },
      })
      .then(function () {
        setTimeout(() => {
          setFile(null);
          setProgress(0);
          setIsSuccess(true);
          setIsUploading(false);
          setIsFileSelected(false);
        }, 500);
      });
  };

  const handleFileCancel = () => {
    setIsFileSelected(false);
    setFile(null);
  };

  const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files[0]) {
      setFile(event.target.files[0]);
      setIsFileSelected(true);
      setProgress(0);
      setIsSuccess(false);
      setIsUploading(false);
    }
  };

  const handleSubmit = async () => {
    const formData = new FormData();
    formData.append('file', file);
    await postCreateTemplate(formData, 'PSQL');
  };

  return (
    <>
      <br />
      <br />
      <br />
      <br />
      <br />

      <Box id="upload-box">
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

        <Box width="180px">
          {isSuccess && (
            <Box color="success.main" display="flex" sx={{ alignItems: 'center' }}>
              <Check color="success" fontSize="medium" />
              <Typography color="success" variant="h6" sx={{ ml: 0.5 }}>
                Success
              </Typography>
            </Box>
          )}
          {isUploading && (
            <LinearProgress sx={{ height: '8px' }} color="secondary" variant="determinate" value={progress} />
          )}
        </Box>
      </Box>
      <Button variant="contained" sx={{ mt: 20 }} onClick={handleSubmit}>
        Fire
      </Button>
    </>
  );
};

export default Templates;
