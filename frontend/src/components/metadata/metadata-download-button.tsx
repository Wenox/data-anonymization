import Button from '@mui/material/Button';
import React, { FC } from 'react';
import { computeHrefDownloadUrl, formatMetadata } from './metadata-download-button.util';
import { TemplateMetadataWithFile } from '../../pages/templates/my-templates';

interface MetadataDownloadButtonProps {
  metadataWithFile?: TemplateMetadataWithFile;
}

const MetadataDownloadButton: FC<MetadataDownloadButtonProps> = ({ metadataWithFile }) => {
  return (
    <Button
      disabled={metadataWithFile?.metadata == null}
      fullWidth
      type="button"
      href={computeHrefDownloadUrl(formatMetadata(metadataWithFile?.metadata))}
      download={`${metadataWithFile?.originalFileName}-metadata.json`}
      color="primary"
      variant="contained"
    >
      Download
    </Button>
  );
};

export default MetadataDownloadButton;
