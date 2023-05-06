package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Welfare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WelfareRepository extends JpaRepository<Welfare, Long>, WelfareQueryDsl {
}
