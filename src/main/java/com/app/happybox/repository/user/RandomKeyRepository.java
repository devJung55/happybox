package com.app.happybox.repository.user;

import com.app.happybox.entity.user.UserRandomKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RandomKeyRepository extends JpaRepository<UserRandomKey, Long>, RandomKeyQueryDsl {
}
