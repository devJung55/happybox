package com.app.happybox.repository.notice;

import com.app.happybox.entity.customer.Notice;
import com.app.happybox.entity.customer.NoticeSearch;
import com.app.happybox.entity.customer.QNotice;
import com.querydsl.core.types.dsl.BooleanExpression;
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
    public Page<Notice> findNoticeListWithPaging_QueryDSL(Pageable pageable, NoticeSearch noticeSearch) {
        BooleanExpression noticeTitleContains = noticeSearch.getNoticeTitle() == null ? null : notice.noticeTitle.contains(noticeSearch.getNoticeTitle());
        BooleanExpression noticeContentContains = noticeSearch.getNoticeContent() == null ? null : notice.noticeContent.contains(noticeSearch.getNoticeContent());

        List<Notice> noticePage = query.select(notice)
                .from(notice)
                .where(noticeTitleContains, noticeContentContains)
                .orderBy(notice.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long count = query.select(notice.count()).from(notice).fetchOne();

        return new PageImpl<>(noticePage, pageable, count);
    }

    @Override
    public Optional<Notice> findNoticeDetailById_QueryDSL(Long id) {
        return Optional.ofNullable(
                query.select(notice)
                .from(notice)
                .join(notice.noticeFiles)
                .fetchJoin()
                .where(notice.id.eq(id))
                .fetchOne());
    }

    @Override
    public Page<Notice> findNoticeListDescWithPaging_QueryDSL(Pageable pageable) {
        List<Notice> noticeList = query.select(notice)
                .from(notice)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(notice.id.count()).from(notice).fetchOne();

        return new PageImpl<>(noticeList, pageable, count);
    }
}
