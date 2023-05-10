package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider, Long>, RiderQueryDsl {
}
