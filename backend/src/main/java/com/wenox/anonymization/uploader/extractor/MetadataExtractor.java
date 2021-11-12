package com.wenox.anonymization.uploader.extractor;

import com.wenox.anonymization.commons.ConnectionDetails;
import com.wenox.anonymization.uploader.extractor.metadata.WorksheetTemplateMetadata;
import java.sql.SQLException;

public interface MetadataExtractor {

  WorksheetTemplateMetadata extractMetadata(ConnectionDetails connectionDetails) throws SQLException;
}
