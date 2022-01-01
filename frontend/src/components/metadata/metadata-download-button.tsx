import Button from '@mui/material/Button';
import React, { FC } from 'react';
import { computeHrefDownloadUrl } from './metadata-download-button.util';

interface MetadataDownloadButtonProps {
  metadata: any;
}

const MetadataDownloadButton: FC<MetadataDownloadButtonProps> = ({ metadata }) => {
  return (
    <Button
      sx={{ ml: 0.5 }}
      fullWidth
      type="button"
      href={computeHrefDownloadUrl(metadata.content)}
      download={`${metadata.fileName}-metadata.json`}
      color="primary"
      variant="contained"
    >
      Download JSON
    </Button>
  );
};

export default MetadataDownloadButton;
