import { Operation } from '../constants/anonymisation-validation-matrix';

export const getColorForOperation = (operation: Operation) => {
  switch (operation) {
    case Operation.COLUMN_SHUFFLE:
      return '#d6c100';
    case Operation.SUPPRESSION:
      return '#d90016';
    case Operation.SUBSTITUTION:
      return '#db6600';
    case Operation.HASHING:
      return '#a8db00';
    case Operation.TOKENIZATION:
      return '#5ebd00';
    case Operation.SHORTENING:
      return '#00bd84';
    case Operation.GENERALISATION:
      return '#0084bd';
    case Operation.PERTURBATION:
      return '#9400bd';
    case Operation.RANDOM_NUMBER:
      return '#bd009a';
    case Operation.ROW_SHUFFLE:
      return '#750031';
    case Operation.PATTERN_MASKING:
      return '#067a37';
  }
};
