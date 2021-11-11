package com.wenox.anonymization.uploader.extractor;

import com.wenox.anonymization.commons.ConnectionDetails;
import java.sql.SQLException;

public interface MetadataExtractor {

  DatabaseMetadataExtractor.WorksheetTemplateMetadata extractMetadata(ConnectionDetails connectionDetails) throws
      SQLException;
}
