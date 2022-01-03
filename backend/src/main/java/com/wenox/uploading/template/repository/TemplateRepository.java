package com.wenox.uploading.template.repository;

import com.wenox.uploading.template.domain.Template;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends CrudRepository<Template, String> {
}
