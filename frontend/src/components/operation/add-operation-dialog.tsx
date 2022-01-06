import React, { FC } from 'react';
import { Dialog, DialogActions, DialogContent, DialogTitle, Divider, MenuItem, Select } from '@mui/material';
import { Transition } from '../user/password-confirmation-dialog';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { toast } from 'react-toastify';
import { putAddSuppressionOperation } from '../../api/requests/column-operations/column-operations.requests';
import { ColumnOperations } from '../../api/requests/table-operations/table-operations.types';

interface AddOperationDialogProps {
  open: boolean;
  handleCancel: () => void;
  columnOperations: ColumnOperations;
  worksheetId: string;
  tableName: string;
  primaryKeyColumnName: string;
}

const AddOperationDialog: FC<AddOperationDialogProps> = ({
  open,
  handleCancel,
  columnOperations,
  worksheetId,
  tableName,
  primaryKeyColumnName,
}) => {
  return (
    <Dialog fullWidth maxWidth="sm" open={open} onClose={handleCancel} TransitionComponent={Transition}>
      <Typography color="secondary" variant="h4" sx={{ mb: 2, mt: 2, pl: 3 }}>
        Select operation
      </Typography>
      <DialogContent>
        <Select fullWidth value={'Shuffle'} label="Age">
          <MenuItem value={'Shuffle'}>Shuffle</MenuItem>
        </Select>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleCancel}>Cancel</Button>
        <Button
          color="secondary"
          onClick={() => {
            console.log('column operations: ', columnOperations);
            putAddSuppressionOperation(worksheetId, {
              tableName: tableName,
              columnName: columnOperations.column.columnName,
              primaryKeyColumnName: primaryKeyColumnName,
              suppressionToken: '*',
            })
              .then((response) => {
                if (response.data.success) {
                  toast.success(response.data.message, {
                    position: 'top-right',
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                  });
                  handleCancel();
                } else {
                  toast.error(response.data.message, {
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
                toast.error('Failed to add operation: ' + err.data, {
                  position: 'top-right',
                  autoClose: 5000,
                  hideProgressBar: false,
                  closeOnClick: true,
                  pauseOnHover: true,
                  draggable: true,
                  progress: undefined,
                }),
              );
          }}
        >
          Confirm
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default AddOperationDialog;
