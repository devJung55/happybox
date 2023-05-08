package com.app.happybox.repository.notice;

import com.app.happybox.entity.customer.Notice;
import com.app.happybox.entity.customer.QNotice;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.customer.QNotice.notice;

@RequiredArgsConstructor
public class NoticeQueryDslImpl implements NoticeQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Page<Notice> findNoticeListWithPaging_QueryDSL(Pageable pageable) {
        List<Notice> noticePage = query.select(notice)
                .from(notice)
                .orderBy(notice.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(noticePage);
    }

    @Override
    public Optional<Notice> findNoticeDetailById_QueryDSL(Long id) {
        return Optional.ofNullable(
                query.select(notice)
                .from(notice)
                .join(notice.noticeFile)
                .fetchJoin()
                .where(notice.id.eq(id))
                .fetchOne());
    }
}
