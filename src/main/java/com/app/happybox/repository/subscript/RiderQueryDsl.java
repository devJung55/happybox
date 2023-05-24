package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Rider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RiderQueryDsl {
//    마이페이지 배달원 목록
    public Page<Rider> findAllByWelfareIdWithPaging_QueryDSL(Pageable pageable, Long welfareId);

//    배달원 수
    public Long findRiderCount_QueryDSL(Long welfareId);
}
