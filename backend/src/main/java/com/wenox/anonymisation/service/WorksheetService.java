package com.wenox.anonymisation.service;

import com.wenox.anonymisation.domain.Worksheet;
import com.wenox.anonymisation.dto.CreateWorksheetRequest;
import com.wenox.anonymisation.repository.OperationRepository;
import com.wenox.anonymisation.repository.WorksheetRepository;
import com.wenox.uploading.template.domain.Template;
import com.wenox.uploading.template.repository.TemplateRepository;
import com.wenox.users.domain.User;
import com.wenox.users.service.AuthService;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class WorksheetService {

  private final WorksheetRepository worksheetRepository;
  private final OperationRepository operationRepository;
  private final TemplateRepository templateRepository;
  private final AuthService authService;

  public WorksheetService(WorksheetRepository worksheetRepository,
                          OperationRepository operationRepository,
                          TemplateRepository templateRepository,
                          AuthService authService) {
    this.worksheetRepository = worksheetRepository;
    this.operationRepository = operationRepository;
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

    return worksheet;
  }

  public List<Worksheet> getAllMyWorksheets() {
    return null;
  }
}
