package com.wenox.processing.service.operations;

import com.wenox.processing.service.operations.columnshuffle.ColumnShuffleFacade;
import com.wenox.processing.service.operations.generalisation.GeneralisationFacade;
import com.wenox.processing.service.operations.hashing.HashingFacade;
import com.wenox.processing.service.operations.patternmasking.PatternMaskingFacade;
import com.wenox.processing.service.operations.perturbation.PerturbationFacade;
import com.wenox.processing.service.operations.randomnumber.RandomNumberFacade;
import com.wenox.processing.service.operations.rowshuffle.RowShuffleFacade;
import com.wenox.processing.service.operations.shortening.ShorteningFacade;
import com.wenox.processing.service.operations.substitution.SubstitutionFacade;
import com.wenox.processing.service.operations.suppression.SuppressionFacade;
import com.wenox.processing.service.operations.tokenization.TokenizationFacade;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AnonymisationFacadeFactory implements FacadeFactory<AnonymisationOperation, AnonymisationFacade> {

  private final Map<AnonymisationOperation, AnonymisationFacade> facades = new HashMap<>();

  public AnonymisationFacadeFactory() {
    facades.putIfAbsent(AnonymisationOperation.COLUMN_SHUFFLE, new ColumnShuffleFacade());
    facades.putIfAbsent(AnonymisationOperation.GENERALISATION, new GeneralisationFacade());
    facades.putIfAbsent(AnonymisationOperation.HASHING, new HashingFacade());
    facades.putIfAbsent(AnonymisationOperation.PATTERN_MASKING, new PatternMaskingFacade());
    facades.putIfAbsent(AnonymisationOperation.PERTURBATION, new PerturbationFacade());
    facades.putIfAbsent(AnonymisationOperation.RANDOM_NUMBER, new RandomNumberFacade());
    facades.putIfAbsent(AnonymisationOperation.ROW_SHUFFLE, new RowShuffleFacade());
    facades.putIfAbsent(AnonymisationOperation.SHORTENING, new ShorteningFacade());
    facades.putIfAbsent(AnonymisationOperation.SUBSTITUTION, new SubstitutionFacade());
    facades.putIfAbsent(AnonymisationOperation.SUPPRESSION, new SuppressionFacade());
    facades.putIfAbsent(AnonymisationOperation.TOKENIZATION, new TokenizationFacade());
  }

  @Override
  public AnonymisationFacade getFacade(AnonymisationOperation operation) {
    return facades.get(operation);
  }
}
