package com.multi.repo;

import com.multi.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<FileEntity,Long> {
}
