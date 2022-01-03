import Button from '@mui/material/Button';
import React, { FC } from 'react';
import { computeHrefDownloadUrl } from './metadata-download-button.util';

interface MetadataDownloadButtonProps {
  metadata: any;
  buttonText?: string;
}

const MetadataDownloadButton: FC<MetadataDownloadButtonProps> = ({ metadata, buttonText = 'Download' }) => {
  return (
    <Button
      disabled={metadata?.content == null}
      fullWidth
      type="button"
      href={computeHrefDownloadUrl(metadata?.content)}
      download={`${metadata?.fileName}-metadata.json`}
      color="primary"
      variant="contained"
    >
      {buttonText}
    </Button>
  );
};

export default MetadataDownloadButton;
