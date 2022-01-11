package com.wenox.uploading.repository;

import com.wenox.uploading.domain.template.Template;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends CrudRepository<Template, String> {
}
