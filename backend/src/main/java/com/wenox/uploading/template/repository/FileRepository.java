package com.wenox.uploading.template.repository;

import com.wenox.uploading.template.domain.FileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<FileEntity, Long> {
}
