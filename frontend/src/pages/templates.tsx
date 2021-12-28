import { FC } from 'react';
import { FileWithPath, useDropzone } from 'react-dropzone';
import { Button } from '@mui/material';
import { postCreateTemplate } from '../api/requests/templates/templates.requests';

const Templates: FC = () => {
  const { acceptedFiles, getRootProps, getInputProps } = useDropzone();

  const files = acceptedFiles.map((file: FileWithPath) => {
    console.log('the file: ', file);
    return (
      <li key={file.path}>
        {file.path} - {file.size} bytes
      </li>
    );
  });

  return (
    <>
      <h1>Templates</h1>
      <section className="container">
        <div {...getRootProps({ className: 'dropzone' })}>
          <input {...getInputProps()} />
          <p>Drag n drop some files here, or click to select files</p>
        </div>
        <aside>
          <h4>Files</h4>
          <ul>{files}</ul>
        </aside>
      </section>

      <Button
        onClick={() => {
          console.log('Trying to upload...');
          const formData = new FormData();
          console.log('final accepted files: ', acceptedFiles);
          formData.append('file', acceptedFiles[0]);
          console.log('final form data: ', formData);
          postCreateTemplate(formData, 'PSQL');
        }}
      >
        Upload
      </Button>
    </>
  );
};

export default Templates;
