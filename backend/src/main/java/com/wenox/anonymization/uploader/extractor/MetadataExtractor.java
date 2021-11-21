package com.wenox.anonymization.uploader.extractor;

import com.wenox.anonymization.core.service.ConnectionDetails;
import com.wenox.anonymization.uploader.extractor.metadata.TemplateMetadata;
import java.sql.SQLException;

public interface MetadataExtractor {

  TemplateMetadata extractMetadata(ConnectionDetails connectionDetails) throws SQLException;
}
