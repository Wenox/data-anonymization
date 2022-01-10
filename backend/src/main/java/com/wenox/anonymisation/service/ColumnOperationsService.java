package com.wenox.anonymisation.service;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.ColumnShuffle;
import com.wenox.anonymisation.domain.Generalisation;
import com.wenox.anonymisation.domain.Hashing;
import com.wenox.anonymisation.domain.PatternMasking;
import com.wenox.anonymisation.domain.Perturbation;
import com.wenox.anonymisation.domain.RandomNumber;
import com.wenox.anonymisation.domain.RowShuffle;
import com.wenox.anonymisation.domain.Shortening;
import com.wenox.anonymisation.domain.Suppression;
import com.wenox.anonymisation.domain.Tokenization;
import com.wenox.anonymisation.dto.columnoperations.AddColumnShuffleRequest;
import com.wenox.anonymisation.dto.columnoperations.AddGeneralisationRequest;
import com.wenox.anonymisation.dto.columnoperations.AddHashingRequest;
import com.wenox.anonymisation.dto.columnoperations.AddPatternMaskingRequest;
import com.wenox.anonymisation.dto.columnoperations.AddPerturbationRequest;
import com.wenox.anonymisation.dto.columnoperations.AddRandomNumberRequest;
import com.wenox.anonymisation.dto.columnoperations.AddRowShuffleRequest;
import com.wenox.anonymisation.dto.columnoperations.AddShorteningRequest;
import com.wenox.anonymisation.dto.columnoperations.AddSuppressionRequest;
import com.wenox.anonymisation.dto.columnoperations.AddTokenizationRequest;
import com.wenox.anonymisation.repository.ColumnOperationsRepository;
import com.wenox.anonymisation.repository.ColumnShuffleRepository;
import com.wenox.anonymisation.repository.GeneralisationRepository;
import com.wenox.anonymisation.repository.HashingRepository;
import com.wenox.anonymisation.repository.PatternMaskingRepository;
import com.wenox.anonymisation.repository.PerturbationRepository;
import com.wenox.anonymisation.repository.RandomNumberRepository;
import com.wenox.anonymisation.repository.RowShuffleRepository;
import com.wenox.anonymisation.repository.ShorteningRepository;
import com.wenox.anonymisation.repository.SuppressionRepository;
import com.wenox.anonymisation.repository.TokenizationRepository;
import com.wenox.anonymisation.repository.WorksheetRepository;
import com.wenox.users.dto.ApiResponse;
import com.wenox.users.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ColumnOperationsService {

  private final AuthService authService;
  private final WorksheetRepository worksheetRepository;
  private final ColumnOperationsRepository columnOperationsRepository;
  private final SuppressionRepository suppressionRepository;
  private final ColumnShuffleRepository columnShuffleRepository;
  private final RowShuffleRepository rowShuffleRepository;
  private final PatternMaskingRepository patternMaskingRepository;
  private final ShorteningRepository shorteningRepository;
  private final GeneralisationRepository generalisationRepository;
  private final PerturbationRepository perturbationRepository;
  private final RandomNumberRepository randomNumberRepository;
  private final HashingRepository hashingRepository;
  private final TokenizationRepository tokenizationRepository;

  public ColumnOperationsService(AuthService authService,
                                 WorksheetRepository worksheetRepository,
                                 ColumnOperationsRepository columnOperationsRepository,
                                 SuppressionRepository suppressionRepository,
                                 ColumnShuffleRepository columnShuffleRepository,
                                 RowShuffleRepository rowShuffleRepository,
                                 PatternMaskingRepository patternMaskingRepository,
                                 ShorteningRepository shorteningRepository,
                                 GeneralisationRepository generalisationRepository,
                                 PerturbationRepository perturbationRepository,
                                 RandomNumberRepository randomNumberRepository,
                                 HashingRepository hashingRepository,
                                 TokenizationRepository tokenizationRepository) {
    this.authService = authService;
    this.worksheetRepository = worksheetRepository;
    this.columnOperationsRepository = columnOperationsRepository;
    this.suppressionRepository = suppressionRepository;
    this.columnShuffleRepository = columnShuffleRepository;
    this.rowShuffleRepository = rowShuffleRepository;
    this.patternMaskingRepository = patternMaskingRepository;
    this.shorteningRepository = shorteningRepository;
    this.generalisationRepository = generalisationRepository;
    this.perturbationRepository = perturbationRepository;
    this.randomNumberRepository = randomNumberRepository;
    this.hashingRepository = hashingRepository;
    this.tokenizationRepository = tokenizationRepository;
  }

  @Transactional
  public ApiResponse addSuppressionOperationForColumn(String id, AddSuppressionRequest dto, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }

    var columnOperations =
        columnOperationsRepository.findOperationsForColumn(worksheet, dto.getTableName(), dto.getColumnName())
            .orElseGet(
                () -> {
                  var newColumnOperations = new ColumnOperations();
                  newColumnOperations.setWorksheet(worksheet);
                  newColumnOperations.setTableName(dto.getTableName());
                  newColumnOperations.setColumnName(dto.getColumnName());
                  newColumnOperations.setColumnType(dto.getColumnType());
                  newColumnOperations.setPrimaryKeyColumnName(dto.getPrimaryKeyColumnName());
                  newColumnOperations.setPrimaryKeyColumnType(dto.getPrimaryKeyColumnType());
                  return newColumnOperations;
                }
            );

    if (columnOperations.getSuppression() != null) {
      return ApiResponse.ofError("This column already uses suppression.");
    }

    Suppression suppression = new Suppression();
    suppression.setSuppressionToken(dto.getSuppressionToken());
    columnOperations.setSuppression(suppression);
    columnOperationsRepository.save(columnOperations);
    suppressionRepository.save(suppression);

    return ApiResponse.ofSuccess("Suppression added successfully.");
  }

  @Transactional
  public ApiResponse addColumnShuffleOperationForColumn(String id, AddColumnShuffleRequest dto, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }

    var columnOperations =
        columnOperationsRepository.findOperationsForColumn(worksheet, dto.getTableName(), dto.getColumnName())
            .orElseGet(
                () -> {
                  var newColumnOperations = new ColumnOperations();
                  newColumnOperations.setWorksheet(worksheet);
                  newColumnOperations.setTableName(dto.getTableName());
                  newColumnOperations.setColumnName(dto.getColumnName());
                  newColumnOperations.setColumnType(dto.getColumnType());
                  newColumnOperations.setPrimaryKeyColumnName(dto.getPrimaryKeyColumnName());
                  newColumnOperations.setPrimaryKeyColumnType(dto.getPrimaryKeyColumnType());
                  return newColumnOperations;
                }
            );

    if (columnOperations.getColumnShuffle() != null) {
      return ApiResponse.ofError("This column already uses column shuffle.");
    }

    if (columnOperations.getSuppression() != null) {
      return ApiResponse.ofError("This column uses suppression and therefore cannot use column shuffle.");
    }

    ColumnShuffle columnShuffle = new ColumnShuffle();
    columnShuffle.setWithRepetitions(dto.isWithRepetitions());
    columnOperations.setColumnShuffle(columnShuffle);
    columnOperationsRepository.save(columnOperations);
    columnShuffleRepository.save(columnShuffle);

    return ApiResponse.ofSuccess("Column shuffle added successfully.");
  }

  @Transactional
  public ApiResponse addRowShuffleOperationForColumn(String id, AddRowShuffleRequest dto, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }

    var columnOperations =
        columnOperationsRepository.findOperationsForColumn(worksheet, dto.getTableName(), dto.getColumnName())
            .orElseGet(
                () -> {
                  var newColumnOperations = new ColumnOperations();
                  newColumnOperations.setWorksheet(worksheet);
                  newColumnOperations.setTableName(dto.getTableName());
                  newColumnOperations.setColumnName(dto.getColumnName());
                  newColumnOperations.setColumnType(dto.getColumnType());
                  newColumnOperations.setPrimaryKeyColumnName(dto.getPrimaryKeyColumnName());
                  newColumnOperations.setPrimaryKeyColumnType(dto.getPrimaryKeyColumnType());
                  return newColumnOperations;
                }
            );

    if (columnOperations.getRowShuffle() != null) {
      return ApiResponse.ofError("This column already uses row shuffle.");
    }

    if (columnOperations.getSuppression() != null) {
      return ApiResponse.ofError("This column uses suppression and therefore cannot use row shuffle.");
    }

    if (columnOperations.getPatternMasking() != null) {
      return ApiResponse.ofError("This column uses pattern masking and therefore cannot use row shuffle.");
    }

    RowShuffle rowShuffle = new RowShuffle();
    rowShuffle.setLetterMode(dto.getLetterMode());
    rowShuffle.setWithRepetitions(dto.isWithRepetitions());
    columnOperations.setRowShuffle(rowShuffle);
    columnOperationsRepository.save(columnOperations);
    rowShuffleRepository.save(rowShuffle);

    return ApiResponse.ofSuccess("Row shuffle added successfully.");
  }

  @Transactional
  public ApiResponse addPatternMaskingOperationForColumn(String id, AddPatternMaskingRequest dto, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }

    var columnOperations =
        columnOperationsRepository.findOperationsForColumn(worksheet, dto.getTableName(), dto.getColumnName())
            .orElseGet(
                () -> {
                  var newColumnOperations = new ColumnOperations();
                  newColumnOperations.setWorksheet(worksheet);
                  newColumnOperations.setTableName(dto.getTableName());
                  newColumnOperations.setColumnName(dto.getColumnName());
                  newColumnOperations.setColumnType(dto.getColumnType());
                  newColumnOperations.setPrimaryKeyColumnName(dto.getPrimaryKeyColumnName());
                  newColumnOperations.setPrimaryKeyColumnType(dto.getPrimaryKeyColumnType());
                  return newColumnOperations;
                }
            );

    if (columnOperations.getPatternMasking() != null) {
      return ApiResponse.ofError("This column already uses pattern masking.");
    }

    if (columnOperations.getSuppression() != null) {
      return ApiResponse.ofError("This column uses suppression and therefore cannot use pattern masking.");
    }

    if (columnOperations.getRowShuffle() != null) {
      return ApiResponse.ofError("This column uses row shuffle and therefore cannot use pattern masking.");
    }

    PatternMasking patternMasking = new PatternMasking();
    patternMasking.setPattern(dto.getPattern());
    patternMasking.setMaskingCharacter(dto.getMaskingCharacter());
    patternMasking.setDiscardExcessiveCharacters(dto.isDiscardExcessiveCharacters());
    columnOperations.setPatternMasking(patternMasking);
    columnOperationsRepository.save(columnOperations);
    patternMaskingRepository.save(patternMasking);

    return ApiResponse.ofSuccess("Pattern masking added successfully.");
  }

  @Transactional
  public ApiResponse addShorteningOperationForColumn(String id, AddShorteningRequest dto, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }

    var columnOperations =
        columnOperationsRepository.findOperationsForColumn(worksheet, dto.getTableName(), dto.getColumnName())
            .orElseGet(
                () -> {
                  var newColumnOperations = new ColumnOperations();
                  newColumnOperations.setWorksheet(worksheet);
                  newColumnOperations.setTableName(dto.getTableName());
                  newColumnOperations.setColumnName(dto.getColumnName());
                  newColumnOperations.setColumnType(dto.getColumnType());
                  newColumnOperations.setPrimaryKeyColumnName(dto.getPrimaryKeyColumnName());
                  newColumnOperations.setPrimaryKeyColumnType(dto.getPrimaryKeyColumnType());
                  return newColumnOperations;
                }
            );

    if (columnOperations.getShortening() != null) {
      return ApiResponse.ofError("This column already uses shortening.");
    }

    Shortening shortening = new Shortening();
    shortening.setLength(dto.getLength());
    shortening.setEndsWithPeriod(dto.isEndsWithPeriod());
    columnOperations.setShortening(shortening);
    columnOperationsRepository.save(columnOperations);
    shorteningRepository.save(shortening);

    return ApiResponse.ofSuccess("Shortening added successfully.");
  }

  @Transactional
  public ApiResponse addGeneralisationOperationForColumn(String id, AddGeneralisationRequest dto, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }

    var columnOperations =
        columnOperationsRepository.findOperationsForColumn(worksheet, dto.getTableName(), dto.getColumnName())
            .orElseGet(
                () -> {
                  var newColumnOperations = new ColumnOperations();
                  newColumnOperations.setWorksheet(worksheet);
                  newColumnOperations.setTableName(dto.getTableName());
                  newColumnOperations.setColumnName(dto.getColumnName());
                  newColumnOperations.setColumnType(dto.getColumnType());
                  newColumnOperations.setPrimaryKeyColumnName(dto.getPrimaryKeyColumnName());
                  newColumnOperations.setPrimaryKeyColumnType(dto.getPrimaryKeyColumnType());
                  return newColumnOperations;
                }
            );

    if (columnOperations.getGeneralisation() != null) {
      return ApiResponse.ofError("This column already uses generalisation.");
    }

    Generalisation generalisation = new Generalisation();
    generalisation.setGeneralisationMode(dto.getGeneralisationMode());
    generalisation.setMinValue(dto.getMinValue());
    generalisation.setMaxValue(dto.getMaxValue());
    generalisation.setIntervalSize(dto.getIntervalSize());
    generalisation.setNumberOfDistributions(dto.getNumberOfDistributions());
    columnOperations.setGeneralisation(generalisation);
    columnOperationsRepository.save(columnOperations);
    generalisationRepository.save(generalisation);

    return ApiResponse.ofSuccess("Generalisation added successfully.");
  }

  @Transactional
  public ApiResponse addPerturbationOperationForColumn(String id, AddPerturbationRequest dto, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }

    var columnOperations =
        columnOperationsRepository.findOperationsForColumn(worksheet, dto.getTableName(), dto.getColumnName())
            .orElseGet(
                () -> {
                  var newColumnOperations = new ColumnOperations();
                  newColumnOperations.setWorksheet(worksheet);
                  newColumnOperations.setTableName(dto.getTableName());
                  newColumnOperations.setColumnName(dto.getColumnName());
                  newColumnOperations.setColumnType(dto.getColumnType());
                  newColumnOperations.setPrimaryKeyColumnName(dto.getPrimaryKeyColumnName());
                  newColumnOperations.setPrimaryKeyColumnType(dto.getPrimaryKeyColumnType());
                  return newColumnOperations;
                }
            );

    if (columnOperations.getPerturbation() != null) {
      return ApiResponse.ofError("This column already uses perturbation.");
    }

    Perturbation perturbation = new Perturbation();
    perturbation.setPerturbationMode(dto.getPerturbationMode());
    perturbation.setValue(dto.getValue());
    perturbation.setMinValue(dto.getMinValue());
    perturbation.setMaxValue(dto.getMaxValue());
    columnOperations.setPerturbation(perturbation);
    columnOperationsRepository.save(columnOperations);
    perturbationRepository.save(perturbation);

    return ApiResponse.ofSuccess("Perturbation added successfully.");
  }

  @Transactional
  public ApiResponse addRandomNumberOperationForColumn(String id, AddRandomNumberRequest dto, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }

    var columnOperations =
        columnOperationsRepository.findOperationsForColumn(worksheet, dto.getTableName(), dto.getColumnName())
            .orElseGet(
                () -> {
                  var newColumnOperations = new ColumnOperations();
                  newColumnOperations.setWorksheet(worksheet);
                  newColumnOperations.setTableName(dto.getTableName());
                  newColumnOperations.setColumnName(dto.getColumnName());
                  newColumnOperations.setColumnType(dto.getColumnType());
                  newColumnOperations.setPrimaryKeyColumnName(dto.getPrimaryKeyColumnName());
                  newColumnOperations.setPrimaryKeyColumnType(dto.getPrimaryKeyColumnType());
                  return newColumnOperations;
                }
            );

    if (columnOperations.getRandomNumber() != null) {
      return ApiResponse.ofError("This column already uses random number operation.");
    }

    RandomNumber randomNumber = new RandomNumber();
    randomNumber.setMinValue(dto.getMinValue());
    randomNumber.setMaxValue(dto.getMaxValue());
    columnOperations.setRandomNumber(randomNumber);
    columnOperationsRepository.save(columnOperations);
    randomNumberRepository.save(randomNumber);

    return ApiResponse.ofSuccess("Random number operation added successfully.");
  }

  @Transactional
  public ApiResponse addHashingOperationForColumn(String id, AddHashingRequest dto, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }

    var columnOperations =
        columnOperationsRepository.findOperationsForColumn(worksheet, dto.getTableName(), dto.getColumnName())
            .orElseGet(
                () -> {
                  var newColumnOperations = new ColumnOperations();
                  newColumnOperations.setWorksheet(worksheet);
                  newColumnOperations.setTableName(dto.getTableName());
                  newColumnOperations.setColumnName(dto.getColumnName());
                  newColumnOperations.setColumnType(dto.getColumnType());
                  newColumnOperations.setPrimaryKeyColumnName(dto.getPrimaryKeyColumnName());
                  newColumnOperations.setPrimaryKeyColumnType(dto.getPrimaryKeyColumnType());
                  return newColumnOperations;
                }
            );

    if (columnOperations.getHashing() != null) {
      return ApiResponse.ofError("This column already uses hashing operation.");
    }

    Hashing hashing = new Hashing();
    hashing.setHashingMode(dto.getHashingMode());
    columnOperations.setHashing(hashing);
    columnOperationsRepository.save(columnOperations);
    hashingRepository.save(hashing);

    return ApiResponse.ofSuccess("Hashing operation added successfully.");
  }

  @Transactional
  public ApiResponse addTokenizationOperationForColumn(String id, AddTokenizationRequest dto, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }

    var columnOperations =
        columnOperationsRepository.findOperationsForColumn(worksheet, dto.getTableName(), dto.getColumnName())
            .orElseGet(
                () -> {
                  var newColumnOperations = new ColumnOperations();
                  newColumnOperations.setWorksheet(worksheet);
                  newColumnOperations.setTableName(dto.getTableName());
                  newColumnOperations.setColumnName(dto.getColumnName());
                  newColumnOperations.setColumnType(dto.getColumnType());
                  newColumnOperations.setPrimaryKeyColumnName(dto.getPrimaryKeyColumnName());
                  newColumnOperations.setPrimaryKeyColumnType(dto.getPrimaryKeyColumnType());
                  return newColumnOperations;
                }
            );

    if (columnOperations.getTokenization() != null) {
      return ApiResponse.ofError("This column already uses tokenization operation.");
    }

    Tokenization tokenization = new Tokenization();
    tokenization.setStartingValue(dto.getStartingValue());
    tokenization.setStep(dto.getStep());
    columnOperations.setTokenization(tokenization);
    columnOperationsRepository.save(columnOperations);
    tokenizationRepository.save(tokenization);

    return ApiResponse.ofSuccess("Tokenization operation added successfully.");
  }


}
