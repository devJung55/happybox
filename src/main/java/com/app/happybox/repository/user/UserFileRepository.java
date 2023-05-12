package com.app.happybox.repository.user;

import com.app.happybox.entity.file.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFileRepository extends JpaRepository<UserFile, Long>, UserFileQueryDsl {
}
