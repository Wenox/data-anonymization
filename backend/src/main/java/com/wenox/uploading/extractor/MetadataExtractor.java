package com.wenox.uploading.extractor;

import com.wenox.infrastructure.service.ConnectionDetails;
import com.wenox.uploading.extractor.domain.metadata.TemplateMetadata;
import java.sql.SQLException;

public interface MetadataExtractor {

  TemplateMetadata extractMetadata(ConnectionDetails connectionDetails) throws SQLException;
}
