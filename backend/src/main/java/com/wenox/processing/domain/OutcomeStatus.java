package com.wenox.processing.domain;

public enum OutcomeStatus {
  GENERATION_STARTED,

  MIRROR_READY,
  MIRROR_FAILURE,

  SCRIPT_CREATION_SUCCESS,
  SCRIPT_CREATION_FAILURE,

  SCRIPT_POPULATION_SUCCESS,
  SCRIPT_POPULATION_FAILURE,

  SCRIPT_EXECUTION_SUCCESS,
  SCRIPT_EXECUTION_FAILURE,

  DATABASE_DUMP_SUCCESS,
  DATABASE_DUMP_FAILURE,
}
