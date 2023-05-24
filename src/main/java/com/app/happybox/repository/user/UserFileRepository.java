package com.app.happybox.repository.user;

import com.app.happybox.entity.file.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserFileRepository extends JpaRepository<UserFile, Long>, UserFileQueryDsl {
}
