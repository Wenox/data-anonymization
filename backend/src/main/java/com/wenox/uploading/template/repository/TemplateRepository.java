package com.wenox.uploading.template.repository;

import com.wenox.uploading.template.domain.Template;
import org.springframework.data.repository.CrudRepository;

public interface TemplateRepository extends CrudRepository<Template, String> {
}
