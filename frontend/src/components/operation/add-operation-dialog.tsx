import React, { FC, useState } from 'react';
import {
  Checkbox,
  Dialog,
  DialogActions,
  DialogContent,
  Divider,
  FormControlLabel,
  FormLabel,
  Grid,
  MenuItem,
  Radio,
  RadioGroup,
  Select,
  Tooltip,
  Zoom,
} from '@mui/material';
import { Transition } from '../user/password-confirmation-dialog';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { toast } from 'react-toastify';
import {
  putAddColumnShuffleOperation,
  putAddPatternMaskingOperation,
  putAddRowShuffleOperation,
  putAddShorteningOperation,
  putAddSuppressionOperation,
} from '../../api/requests/column-operations/column-operations.requests';
import { ColumnOperations } from '../../api/requests/table-operations/table-operations.types';
import TextField from '@mui/material/TextField';
import { LetterMode } from '../../api/requests/column-operations/column-operations.types';
import { HelpOutline, LockOutlined } from '@mui/icons-material';
import { theme } from '../../styles/theme';

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
  const [letterMode, setLetterMode] = useState<LetterMode>(LetterMode.RETAIN_CASE);
  const [pattern, setPattern] = useState('');
  const [maskingCharacter, setMaskingCharacter] = useState('#');
  const [discardExcessiveCharacters, setDiscardExcessiveCharacters] = useState(false);
  const [length, setLength] = useState<number>(4);
  const [endsWithPeriod, setEndsWithPeriod] = useState(false);

  return (
    <Dialog fullWidth maxWidth="sm" open={open} onClose={handleCancel} TransitionComponent={Transition}>
      <Typography color="secondary" variant="h4" sx={{ mb: 2, mt: 2, pl: 3 }}>
        Select operation
      </Typography>
      <DialogContent>
        <Select fullWidth value={selectedOperation} onChange={(e) => setSelectedOperation(e.target.value)}>
          <MenuItem value={'Suppression'}>Suppression</MenuItem>
          <MenuItem value={'ColumnShuffle'}>Column shuffle</MenuItem>
          <MenuItem value={'RowShuffle'}>Row shuffle</MenuItem>
          <MenuItem value={'PatternMasking'}>Pattern masking</MenuItem>
          <MenuItem value={'Shortening'}>Shortening</MenuItem>
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

        {['ColumnShuffle', 'RowShuffle'].includes(selectedOperation) && (
          <FormControlLabel
            control={<Checkbox checked={withRepetitions} onChange={(e) => setWithRepetitions(e.target.checked)} />}
            label="With repetitions"
          />
        )}

        {selectedOperation === 'RowShuffle' && (
          <>
            <FormLabel component="legend">Letter mode</FormLabel>
            <RadioGroup row value={letterMode} onChange={(e) => setLetterMode(e.target.value as LetterMode)}>
              <FormControlLabel value={LetterMode.RETAIN_CASE} control={<Radio />} label="Retain case" />
              <FormControlLabel value={LetterMode.TO_LOWERCASE} control={<Radio />} label="To lowercase" />
              <FormControlLabel value={LetterMode.TO_UPPERCASE} control={<Radio />} label="To uppercase" />
            </RadioGroup>
          </>
        )}

        {selectedOperation == 'PatternMasking' && (
          <>
            <Grid container spacing={0} alignItems={'center'}>
              <Grid item xs={11}>
                <TextField
                  label="Pattern"
                  onChange={(e) => setPattern(e.target.value)}
                  value={pattern}
                  variant="outlined"
                  fullWidth
                  sx={{ backgroundColor: '#fff' }}
                />
              </Grid>
              <Grid item xs={1} textAlign={'right'}>
                <Tooltip
                  TransitionComponent={Zoom}
                  title={
                    <div>
                      Build pattern using the following characters:
                      <p>
                        <strong>O</strong> – retains the original character
                      </p>
                      <p>
                        <strong>X</strong> – masks with the provided masking character
                      </p>
                      <p>
                        <strong>N</strong> – random digit [0-9]
                      </p>
                      <p>
                        <strong>L</strong> – random lowercase letter [a-z]
                      </p>
                      <p>
                        <strong>U</strong> – random uppercase letter [A-Z]
                      </p>
                      <p>
                        <strong>A</strong> – random alphabetic letter [a-zA-Z]
                      </p>
                      <p>
                        <strong>C</strong> – random alphanumeric letter [a-zA-Z0-9]
                      </p>
                    </div>
                  }
                  placement={'top'}
                >
                  <HelpOutline fontSize="large" sx={{ color: `${theme.palette.secondary.main}` }} />
                </Tooltip>
              </Grid>
              <Grid xs={6}>
                <TextField
                  label="Masking character"
                  onChange={(e) => setMaskingCharacter(e.target.value)}
                  value={maskingCharacter}
                  variant="outlined"
                  fullWidth
                  sx={{ mt: 2, backgroundColor: '#fff' }}
                  inputProps={{ maxLength: 1 }}
                />
              </Grid>
              <Grid item xs={6} sx={{ pl: 2, pt: 2 }}>
                <FormControlLabel
                  control={
                    <Checkbox
                      checked={discardExcessiveCharacters}
                      onChange={(e) => setDiscardExcessiveCharacters(e.target.checked)}
                    />
                  }
                  label="Discard excessive characters"
                />
              </Grid>
            </Grid>
          </>
        )}

        {selectedOperation == 'Shortening' && (
          <>
            <TextField
              label="Length"
              onChange={(e) => setLength(Number(e.target.value))}
              value={length}
              variant="outlined"
              fullWidth
              type="number"
              InputProps={{ inputProps: { min: 1 } }}
              sx={{ backgroundColor: '#fff' }}
            />
            <FormControlLabel
              control={<Checkbox checked={endsWithPeriod} onChange={(e) => setEndsWithPeriod(e.target.checked)} />}
              label="End abbreviation with a period"
            />
          </>
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
              case 'ColumnShuffle':
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
              case 'RowShuffle':
                putAddRowShuffleOperation(worksheetId, {
                  tableName: tableName,
                  columnName: columnOperations.column.columnName,
                  columnType: columnOperations.column.type,
                  primaryKeyColumnName: primaryKeyColumnName,
                  primaryKeyColumnType: primaryKeyColumnType,
                  withRepetitions: withRepetitions,
                  letterMode: letterMode,
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
              case 'PatternMasking':
                putAddPatternMaskingOperation(worksheetId, {
                  tableName: tableName,
                  columnName: columnOperations.column.columnName,
                  columnType: columnOperations.column.type,
                  primaryKeyColumnName: primaryKeyColumnName,
                  primaryKeyColumnType: primaryKeyColumnType,
                  pattern: pattern,
                  maskingCharacter: maskingCharacter,
                  discardExcessiveCharacters: discardExcessiveCharacters,
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
              case 'Shortening':
                putAddShorteningOperation(worksheetId, {
                  tableName: tableName,
                  columnName: columnOperations.column.columnName,
                  columnType: columnOperations.column.type,
                  primaryKeyColumnName: primaryKeyColumnName,
                  primaryKeyColumnType: primaryKeyColumnType,
                  length: length,
                  endsWithPeriod: endsWithPeriod,
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
