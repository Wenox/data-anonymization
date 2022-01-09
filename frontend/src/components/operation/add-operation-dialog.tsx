import React, { FC, useState } from 'react';
import {
  Checkbox,
  Dialog,
  DialogActions,
  DialogContent,
  Divider,
  FormControlLabel,
  MenuItem,
  Select,
} from '@mui/material';
import { Transition } from '../user/password-confirmation-dialog';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { toast } from 'react-toastify';
import {
  putAddColumnShuffleOperation,
  putAddSuppressionOperation,
} from '../../api/requests/column-operations/column-operations.requests';
import { ColumnOperations } from '../../api/requests/table-operations/table-operations.types';
import TextField from '@mui/material/TextField';

interface AddOperationDialogProps {
  open: boolean;
  handleCancel: () => void;
  handleAddSuccess: () => void;
  columnOperations: ColumnOperations;
  worksheetId: string;
  tableName: string;
  primaryKeyColumnName: string;
  primaryKeyColumnType: string;
}

const AddOperationDialog: FC<AddOperationDialogProps> = ({
  open,
  handleCancel,
  handleAddSuccess,
  columnOperations,
  worksheetId,
  tableName,
  primaryKeyColumnName,
  primaryKeyColumnType,
}) => {
  const [selectedOperation, setSelectedOperation] = useState('Suppression');
  const [withRepetitions, setWithRepetitions] = useState(false);
  const [suppressionToken, setSuppressionToken] = useState('*');

  return (
    <Dialog fullWidth maxWidth="sm" open={open} onClose={handleCancel} TransitionComponent={Transition}>
      <Typography color="secondary" variant="h4" sx={{ mb: 2, mt: 2, pl: 3 }}>
        Select operation
      </Typography>
      <DialogContent>
        <Select fullWidth value={selectedOperation} onChange={(e) => setSelectedOperation(e.target.value)}>
          <MenuItem value={'Suppression'}>Suppression</MenuItem>
          <MenuItem value={'Shuffle'}>Shuffle</MenuItem>
        </Select>

        <Divider sx={{ mt: 2, mb: 2 }} />

        {selectedOperation === 'Suppression' && (
          <TextField
            label="Suppression token"
            onChange={(e) => setSuppressionToken(e.target.value)}
            value={suppressionToken}
            variant="outlined"
            fullWidth
            sx={{ backgroundColor: '#fff' }}
          />
        )}

        {selectedOperation === 'Shuffle' && (
          <FormControlLabel
            control={<Checkbox checked={withRepetitions} onChange={(e) => setWithRepetitions(e.target.checked)} />}
            label="With repetitions"
          />
        )}
      </DialogContent>
      <DialogActions>
        <Button onClick={handleCancel}>Cancel</Button>
        <Button
          color="secondary"
          onClick={() => {
            switch (selectedOperation) {
              case 'Suppression':
                putAddSuppressionOperation(worksheetId, {
                  tableName: tableName,
                  columnName: columnOperations.column.columnName,
                  columnType: columnOperations.column.type,
                  primaryKeyColumnName: primaryKeyColumnName,
                  primaryKeyColumnType: primaryKeyColumnType,
                  suppressionToken: suppressionToken,
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
                      handleAddSuccess();
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
                break;
              case 'Shuffle':
                putAddColumnShuffleOperation(worksheetId, {
                  tableName: tableName,
                  columnName: columnOperations.column.columnName,
                  columnType: columnOperations.column.type,
                  primaryKeyColumnName: primaryKeyColumnName,
                  primaryKeyColumnType: primaryKeyColumnType,
                  withRepetitions: withRepetitions,
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
                      handleAddSuccess();
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
                break;
              default:
                alert('Unsupported operation!');
            }
          }}
        >
          Confirm
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default AddOperationDialog;
