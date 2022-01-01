import React, { FC } from 'react';
import { Transition } from '../user/password-confirmation-dialog';
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import Button from '@mui/material/Button';
import MetadataDownloadButton from './metadata-download-button';
import { theme } from '../../styles/theme';

interface MetadataDialogProps {
  metadata: any;
  open: boolean;
  handleClose: () => void;
}

const MetadataDialog: FC<MetadataDialogProps> = ({ metadata, open, handleClose }) => {
  return (
    <Dialog fullWidth maxWidth="md" open={open} onClose={handleClose} TransitionComponent={Transition}>
      <DialogTitle color="primary">Metadata for {metadata.fileName}</DialogTitle>
      <DialogContent
        sx={{ backgroundColor: `${theme.palette.primary.dark}`, color: `${theme.palette.secondary.light}` }}
      >
        <div>
          <pre>{metadata.content}</pre>
        </div>
      </DialogContent>
      <DialogActions>
        <Button fullWidth color="primary" variant="outlined" onClick={handleClose} sx={{ mr: 0.5 }}>
          Close
        </Button>
        <MetadataDownloadButton metadata={metadata} />
      </DialogActions>
    </Dialog>
  );
};

export default MetadataDialog;
