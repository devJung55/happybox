package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Distributor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistributorRepository extends JpaRepository<Distributor, Long>, DistributorQueryDsl {
}
