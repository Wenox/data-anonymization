package com.wenox.anonymization.uploader.core;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface WorksheetTemplateRepository extends CrudRepository<WorksheetTemplate, UUID> {
}
