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
        NoticeSearch search = doSearch(noticeSearch);

        BooleanExpression noticeTitleContains = search.getNoticeTitle() == null ? null : notice.noticeTitle.contains(search.getNoticeTitle());
        BooleanExpression noticeContentContains = search.getNoticeContent() == null ? null : notice.noticeContent.contains(search.getNoticeContent());
        BooleanExpression noticeWholeContains = search.getNoticeWhole() == null ? null : notice.noticeTitle.contains(search.getNoticeWhole()).or(notice.noticeContent.contains(noticeSearch.getNoticeWhole()));

        List<Notice> noticePage = query.select(notice)
                .from(notice)
                .where(noticeTitleContains, noticeContentContains, noticeWholeContains)
                .orderBy(notice.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long count = query.select(notice.count()).from(notice).where(noticeTitleContains, noticeContentContains, noticeWholeContains).fetchOne();

        return new PageImpl<>(noticePage, pageable, count);
    }

    //    검색 조건 분기처리
    private NoticeSearch doSearch(NoticeSearch noticeSearch) {
        if(noticeSearch != null) {
            switch(noticeSearch.getSearchType()){
                case "제목" : noticeSearch.setNoticeTitle(noticeSearch.getKeyword());
                    break;
                case "내용" : noticeSearch.setNoticeContent(noticeSearch.getKeyword());
                    break;
                case "전체" : noticeSearch.setNoticeWhole(noticeSearch.getKeyword());
                    break;
            }
        }
        return noticeSearch;
    }

    @Override
    public Optional<Notice> findNoticeDetailById_QueryDSL(Long id) {
        return Optional.ofNullable(
                query.select(notice)
                .from(notice)
                .where(notice.id.eq(id))
                .fetchOne());
    }

    @Override
    public Page<Notice> findNoticeListDescWithPaging_QueryDSL(Pageable pageable) {
        List<Notice> noticeList = query.select(notice)
                .from(notice)
                .orderBy(notice.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(notice.id.count()).from(notice).fetchOne();

        return new PageImpl<>(noticeList, pageable, count);
    }
}
