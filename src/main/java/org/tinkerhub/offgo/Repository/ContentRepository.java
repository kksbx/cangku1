package org.tinkerhub.offgo.Repository;

import jakarta.persistence.Column;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tinkerhub.offgo.entity.ContentEntity;
import org.tinkerhub.offgo.entity.diary;

@Configuration
public interface ContentReposity extends JpaRepository<ContentEntity, Long> {
    public ContentEntity findById(int id);
}
