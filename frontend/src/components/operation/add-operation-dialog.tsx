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
  Switch,
  Tooltip,
  Zoom,
} from '@mui/material';
import { Transition } from '../user/password-confirmation-dialog';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { toast } from 'react-toastify';
import {
  putAddColumnShuffleOperation,
  putAddGeneralisationOperation,
  putAddHashingOperation,
  putAddPatternMaskingOperation,
  putAddPerturbationOperation,
  putAddRandomNumberOperation,
  putAddRowShuffleOperation,
  putAddShorteningOperation,
  putAddSuppressionOperation,
  putAddTokenizationOperation,
} from '../../api/requests/column-operations/column-operations.requests';
import { ColumnOperations } from '../../api/requests/table-operations/table-operations.types';
import TextField from '@mui/material/TextField';
import {
  GeneralisationMode,
  HashingMode,
  LetterMode,
  PerturbationMode,
} from '../../api/requests/column-operations/column-operations.types';
import { HelpOutline } from '@mui/icons-material';
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
  const [minValue, setMinValue] = useState<number | null>(null);
  const [maxValue, setMaxValue] = useState<number | null>(null);
  const [intervalSize, setIntervalSize] = useState<number | null>(null);
  const [numberOfDistributions, setNumberOfDistributions] = useState<number | null>(null);
  const [minValueChecked, setMinValueChecked] = useState(false);
  const [maxValueChecked, setMaxValueChecked] = useState(false);
  const [numberOfDistributionsChecked, setNumberOfDistributionsChecked] = useState(false);
  const [fixedValue, setFixedValue] = useState(10);
  const [percentageValue, setPercentageValue] = useState(5);
  const [perturbationMode, setPerturbationMode] = useState<PerturbationMode>(PerturbationMode.FIXED);
  const [hashingMode, setHashingMode] = useState<HashingMode>(HashingMode.SHA3);

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
          <MenuItem value={'Generalisation'}>Generalisation</MenuItem>
          <MenuItem value={'Perturbation'}>Perturbation</MenuItem>
          <MenuItem value={'RandomNumber'}>Random number</MenuItem>
          <MenuItem value={'Hashing'}>Hashing</MenuItem>
          <MenuItem value={'Tokenization'}>Tokenization</MenuItem>
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

        {selectedOperation == 'Generalisation' && (
          <>
            <Grid container spacing={0}>
              <Grid item xs={3}>
                <FormControlLabel
                  control={
                    <Switch
                      checked={minValueChecked}
                      onChange={(e) => setMinValueChecked(e.target.checked)}
                      color="secondary"
                    />
                  }
                  label="Minimum value"
                />
              </Grid>
              <Grid item xs={3}>
                <TextField
                  label="Minimum value"
                  disabled={!minValueChecked}
                  onChange={(e) => setMinValue(Number(e.target.value))}
                  value={minValue || 0}
                  variant="outlined"
                  fullWidth
                  type="number"
                  sx={{ backgroundColor: '#fff' }}
                />
              </Grid>
              <Grid item xs={3} sx={{ pl: 1 }}>
                <FormControlLabel
                  control={
                    <Switch
                      checked={maxValueChecked}
                      onChange={(e) => setMaxValueChecked(e.target.checked)}
                      color="secondary"
                    />
                  }
                  label="Maximum value"
                />
              </Grid>
              <Grid item xs={3}>
                <TextField
                  disabled={!maxValueChecked}
                  label="Maximum value"
                  onChange={(e) => setMaxValue(Number(e.target.value))}
                  value={maxValue || 0}
                  variant="outlined"
                  fullWidth
                  type="number"
                  sx={{ backgroundColor: '#fff' }}
                />
              </Grid>
              <Grid item xs={12} textAlign={'right'} sx={{ mb: 2 }}>
                <Divider sx={{ mt: 2, mb: 2 }} />
                <FormControlLabel
                  labelPlacement="start"
                  control={
                    <Switch
                      checked={numberOfDistributionsChecked}
                      onChange={(e) => setNumberOfDistributionsChecked(e.target.checked)}
                      color="secondary"
                      defaultChecked
                    />
                  }
                  label="Number of distributions mode"
                />
              </Grid>
              <Grid item xs={6} sx={{ pr: 0.5 }}>
                <TextField
                  label="Interval size"
                  disabled={numberOfDistributionsChecked}
                  onChange={(e) => setIntervalSize(Number(e.target.value))}
                  value={intervalSize || 0}
                  variant="outlined"
                  fullWidth
                  type="number"
                  sx={{ backgroundColor: '#fff' }}
                />
              </Grid>
              <Grid item xs={6} sx={{ pl: 0.5 }}>
                <TextField
                  label="Number of distributions"
                  disabled={!numberOfDistributionsChecked}
                  onChange={(e) => setNumberOfDistributions(Number(e.target.value))}
                  value={numberOfDistributions || 0}
                  variant="outlined"
                  fullWidth
                  type="number"
                  sx={{ backgroundColor: '#fff' }}
                />
              </Grid>
            </Grid>
          </>
        )}

        {selectedOperation == 'Perturbation' && (
          <>
            <Grid container spacing={0}>
              <Grid item xs={3}>
                <FormControlLabel
                  control={
                    <Switch
                      checked={minValueChecked}
                      onChange={(e) => setMinValueChecked(e.target.checked)}
                      color="secondary"
                    />
                  }
                  label="Minimum value"
                />
              </Grid>
              <Grid item xs={3}>
                <TextField
                  label="Minimum value"
                  disabled={!minValueChecked}
                  onChange={(e) => setMinValue(Number(e.target.value))}
                  value={minValue || 0}
                  variant="outlined"
                  fullWidth
                  type="number"
                  sx={{ backgroundColor: '#fff' }}
                />
              </Grid>
              <Grid item xs={3} sx={{ pl: 1 }}>
                <FormControlLabel
                  control={
                    <Switch
                      checked={maxValueChecked}
                      onChange={(e) => setMaxValueChecked(e.target.checked)}
                      color="secondary"
                    />
                  }
                  label="Maximum value"
                />
              </Grid>
              <Grid item xs={3}>
                <TextField
                  disabled={!maxValueChecked}
                  label="Maximum value"
                  onChange={(e) => setMaxValue(Number(e.target.value))}
                  value={maxValue || 0}
                  variant="outlined"
                  fullWidth
                  type="number"
                  sx={{ backgroundColor: '#fff' }}
                />
              </Grid>
              <Grid item xs={12} textAlign={'right'} sx={{ mb: 2 }}>
                <Divider sx={{ mt: 2, mb: 2 }} />
                <FormControlLabel
                  labelPlacement="start"
                  control={
                    <Switch
                      checked={perturbationMode === PerturbationMode.PERCENTAGE}
                      onChange={(e) =>
                        setPerturbationMode(e.target.checked ? PerturbationMode.PERCENTAGE : PerturbationMode.FIXED)
                      }
                      color="secondary"
                      defaultChecked
                    />
                  }
                  label="Percentage mode"
                />
              </Grid>
              <Grid item xs={6} sx={{ pr: 0.5 }}>
                <TextField
                  label="Fixed value"
                  disabled={perturbationMode === PerturbationMode.PERCENTAGE}
                  onChange={(e) => setFixedValue(Number(e.target.value))}
                  value={fixedValue || 0}
                  variant="outlined"
                  fullWidth
                  type="number"
                  sx={{ backgroundColor: '#fff' }}
                />
              </Grid>
              <Grid item xs={6} sx={{ pl: 0.5 }}>
                <TextField
                  label="Percentage value"
                  disabled={perturbationMode === PerturbationMode.FIXED}
                  onChange={(e) => setPercentageValue(Number(e.target.value))}
                  value={percentageValue || 0}
                  variant="outlined"
                  fullWidth
                  type="number"
                  sx={{ backgroundColor: '#fff' }}
                />
              </Grid>
            </Grid>
          </>
        )}

        {selectedOperation == 'RandomNumber' && (
          <>
            <Grid container spacing={0}>
              <Grid item xs={6} sx={{ pr: 0.5 }}>
                <TextField
                  label="Minimum value"
                  onChange={(e) => setMinValue(Number(e.target.value))}
                  value={minValue || 0}
                  variant="outlined"
                  fullWidth
                  type="number"
                  sx={{ backgroundColor: '#fff' }}
                />
              </Grid>
              <Grid item xs={6} sx={{ pl: 0.5 }}>
                <TextField
                  label="Maximum value"
                  onChange={(e) => setMaxValue(Number(e.target.value))}
                  value={maxValue || 0}
                  variant="outlined"
                  fullWidth
                  type="number"
                  sx={{ backgroundColor: '#fff' }}
                />
              </Grid>
            </Grid>
          </>
        )}

        {selectedOperation === 'Hashing' && (
          <>
            <h4>Hashing algorithm: {hashingMode === HashingMode.SHA3 ? 'SHA3-256' : 'SHA2-256'}</h4>
            <FormControlLabel
              control={
                <Switch
                  checked={hashingMode == HashingMode.SHA3}
                  onChange={(e) => setHashingMode(e.target.checked ? HashingMode.SHA3 : HashingMode.SHA2)}
                  color="secondary"
                  defaultChecked
                />
              }
              label="SHA3-256"
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
              case 'Generalisation':
                putAddGeneralisationOperation(worksheetId, {
                  tableName: tableName,
                  columnName: columnOperations.column.columnName,
                  columnType: columnOperations.column.type,
                  primaryKeyColumnName: primaryKeyColumnName,
                  primaryKeyColumnType: primaryKeyColumnType,
                  minValue: minValue,
                  maxValue: maxValue,
                  intervalSize: numberOfDistributionsChecked ? null : intervalSize,
                  numberOfDistributions: numberOfDistributionsChecked ? numberOfDistributions : null,
                  generalisationMode: numberOfDistributionsChecked
                    ? GeneralisationMode.DISTRIBUTION
                    : GeneralisationMode.FIXED,
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
              case 'Perturbation':
                putAddPerturbationOperation(worksheetId, {
                  tableName: tableName,
                  columnName: columnOperations.column.columnName,
                  columnType: columnOperations.column.type,
                  primaryKeyColumnName: primaryKeyColumnName,
                  primaryKeyColumnType: primaryKeyColumnType,
                  minValue: minValue,
                  maxValue: maxValue,
                  value: perturbationMode === PerturbationMode.PERCENTAGE ? percentageValue : fixedValue,
                  perturbationMode: perturbationMode,
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
              case 'RandomNumber':
                putAddRandomNumberOperation(worksheetId, {
                  tableName: tableName,
                  columnName: columnOperations.column.columnName,
                  columnType: columnOperations.column.type,
                  primaryKeyColumnName: primaryKeyColumnName,
                  primaryKeyColumnType: primaryKeyColumnType,
                  minValue: minValue || 0,
                  maxValue: maxValue || 100,
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
              case 'Hashing':
                putAddHashingOperation(worksheetId, {
                  tableName: tableName,
                  columnName: columnOperations.column.columnName,
                  columnType: columnOperations.column.type,
                  primaryKeyColumnName: primaryKeyColumnName,
                  primaryKeyColumnType: primaryKeyColumnType,
                  hashingMode: hashingMode,
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
              case 'Tokenization':
                putAddTokenizationOperation(worksheetId, {
                  tableName: tableName,
                  columnName: columnOperations.column.columnName,
                  columnType: columnOperations.column.type,
                  primaryKeyColumnName: primaryKeyColumnName,
                  primaryKeyColumnType: primaryKeyColumnType,
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
