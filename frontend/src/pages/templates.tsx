import { ChangeEvent, FC, useState } from 'react';
import { Box, Button, LinearProgress, Typography } from '@mui/material';
import axios from 'axios';
import { Check } from '@mui/icons-material';

const Templates: FC = () => {
  const [file, setFile] = useState<any>();
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
          setProgress(0);
          setIsSuccess(true);
          setIsUploading(false);
        }, 500);
      });
  };

  const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
    console.log('handleFileChange: ', event);
    if (event.target.files && event.target.files[0]) {
      setFile(event.target.files[0]);
      setProgress(0);
      setIsSuccess(false);
      setIsUploading(false);
    }
  };

  const handleSubmit = async () => {
    console.log('file: ', file);
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

      <Box>
        <Button
          onDragOver={(e: any) => {
            e.preventDefault();
          }}
          onDrop={(e: any) => {
            e.preventDefault();
          }}
          variant="contained"
          component="label"
        >
          {isUploading ? 'Uploading...' : 'Select file'}
          <input onChange={handleFileChange} type="file" hidden />
        </Button>
        {file && (
          <h4>
            {file.name} ({Number(file.size / 1024 / 1024).toFixed(2)} MB)
          </h4>
        )}

        <Box maxWidth={'xs'} marginY={3}>
          {isSuccess && (
            <Box color="success.main" display="flex">
              <Check color="success" />
              <Typography>Upload success</Typography>
            </Box>
          )}
          {isUploading && <LinearProgress color="secondary" variant="determinate" value={progress} />}
        </Box>
      </Box>
      <Button onClick={handleSubmit}>Upload</Button>
    </>
  );
};

export default Templates;
