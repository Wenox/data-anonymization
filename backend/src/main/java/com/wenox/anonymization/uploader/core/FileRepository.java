package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.uploader.core.FileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<FileEntity, Long> {
}
