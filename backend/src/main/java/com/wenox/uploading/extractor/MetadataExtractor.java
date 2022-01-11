package com.wenox.uploading.extractor;

import com.wenox.infrastructure.service.DatabaseConnection;
import com.wenox.uploading.extractor.domain.metadata.TemplateMetadata;
import java.sql.SQLException;

public interface MetadataExtractor {

  TemplateMetadata extractMetadata(DatabaseConnection databaseConnection) throws SQLException;
}
