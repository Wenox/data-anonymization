package com.wenox.anonymisation.service;

import com.wenox.anonymisation.domain.Worksheet;
import com.wenox.anonymisation.dto.worksheet.CreateWorksheetRequest;
import com.wenox.anonymisation.repository.ColumnOperationsRepository;
import com.wenox.anonymisation.repository.WorksheetRepository;
import com.wenox.uploading.template.domain.Template;
import com.wenox.uploading.template.repository.TemplateRepository;
import com.wenox.users.domain.User;
import com.wenox.users.service.AuthService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WorksheetService {

  private static final Logger log = LoggerFactory.getLogger(WorksheetService.class);

  private final WorksheetRepository worksheetRepository;
  private final ColumnOperationsRepository columnOperationsRepository;
  private final TemplateRepository templateRepository;
  private final AuthService authService;

  public WorksheetService(WorksheetRepository worksheetRepository,
                          ColumnOperationsRepository columnOperationsRepository,
                          TemplateRepository templateRepository,
                          AuthService authService) {
    this.worksheetRepository = worksheetRepository;
    this.columnOperationsRepository = columnOperationsRepository;
    this.templateRepository = templateRepository;
    this.authService = authService;
  }

  public Worksheet createMyWorksheet(CreateWorksheetRequest dto, Authentication auth) {
    User me = authService.getMe(auth);
    Template template = templateRepository.findById(dto.getTemplateId()).orElseThrow();

    Worksheet worksheet = new Worksheet();
    worksheet.setUser(me);
    worksheet.setTemplate(template);
    worksheetRepository.save(worksheet);

    System.out.println("Created worksheet: " + worksheet);

    return worksheet;
  }

  public List<Worksheet> getAllMyWorksheets(Authentication auth) {
    User me = authService.getMe(auth);
    final var worksheets = worksheetRepository.findAllByUser(me);
    log.info("Retrieving {} worksheets for {}.", worksheets.size(), me.getEmail());
    return worksheets;
  }

  public Worksheet getMyWorksheetSummary(String id, Authentication auth) {
    User me = authService.getMe(auth);
    Worksheet worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      log.warn("{} trying to read worksheet {} that belongs to {}.", me.getEmail(), id, worksheet.getUser().getEmail());
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return worksheet;
  }
}
