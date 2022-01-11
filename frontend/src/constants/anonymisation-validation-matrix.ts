export enum Operation {
  COLUMN_SHUFFLE = 'ColumnShuffle',
  GENERALISATION = 'Generalisation',
  HASHING = 'Hashing',
  PATTERN_MASKING = 'PatternMasking',
  PERTURBATION = 'Perturbation',
  RANDOM_NUMBER = 'RandomNumber',
  ROW_SHUFFLE = 'RowShuffle',
  SHORTENING = 'Shortening',
  SUBSTITUTION = 'Substitution',
  SUPPRESSION = 'Suppression',
  TOKENIZATION = 'Tokenization',
}

export enum Type {
  INTEGER = '4',
  TEXT = '12',
}

interface AnonymisationValidationRule {
  operation: Operation;
  allowedTypes: Type[];
  compatibleOperations: Operation[];
}

export const validationMatrix: AnonymisationValidationRule[] = [
  {
    operation: Operation.SUPPRESSION,
    allowedTypes: [Type.INTEGER, Type.TEXT],
    compatibleOperations: [],
  },
  {
    operation: Operation.COLUMN_SHUFFLE,
    allowedTypes: [Type.INTEGER, Type.TEXT],
    compatibleOperations: [
      Operation.GENERALISATION,
      Operation.HASHING,
      Operation.PATTERN_MASKING,
      Operation.PERTURBATION,
      Operation.RANDOM_NUMBER,
      Operation.ROW_SHUFFLE,
      Operation.SHORTENING,
      Operation.SHORTENING,
      Operation.SUBSTITUTION,
      Operation.TOKENIZATION,
    ],
  },
  {
    operation: Operation.GENERALISATION,
    allowedTypes: [Type.INTEGER],
    compatibleOperations: [Operation.COLUMN_SHUFFLE],
  },
  {
    operation: Operation.HASHING,
    allowedTypes: [Type.INTEGER, Type.TEXT],
    compatibleOperations: [Operation.COLUMN_SHUFFLE],
  },
  {
    operation: Operation.PATTERN_MASKING,
    allowedTypes: [Type.INTEGER, Type.TEXT],
    compatibleOperations: [Operation.COLUMN_SHUFFLE, Operation.PERTURBATION, Operation.RANDOM_NUMBER],
  },
  {
    operation: Operation.PERTURBATION,
    allowedTypes: [Type.INTEGER],
    compatibleOperations: [Operation.COLUMN_SHUFFLE, Operation.PATTERN_MASKING],
  },
  {
    operation: Operation.RANDOM_NUMBER,
    allowedTypes: [Type.INTEGER, Type.TEXT],
    compatibleOperations: [Operation.COLUMN_SHUFFLE, Operation.PATTERN_MASKING],
  },
  {
    operation: Operation.ROW_SHUFFLE,
    allowedTypes: [Type.TEXT],
    compatibleOperations: [Operation.COLUMN_SHUFFLE],
  },
  {
    operation: Operation.SHORTENING,
    allowedTypes: [Type.TEXT],
    compatibleOperations: [Operation.COLUMN_SHUFFLE],
  },
  {
    operation: Operation.SUBSTITUTION,
    allowedTypes: [Type.INTEGER, Type.TEXT],
    compatibleOperations: [Operation.COLUMN_SHUFFLE],
  },
  {
    operation: Operation.TOKENIZATION,
    allowedTypes: [Type.INTEGER, Type.TEXT],
    compatibleOperations: [Operation.COLUMN_SHUFFLE],
  },
];
