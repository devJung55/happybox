package com.app.happybox.repository.user;

import com.app.happybox.entity.file.UserFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.app.happybox.entity.file.QUserFile.userFile;

@RequiredArgsConstructor
public class UserFileQueryDslImpl implements UserFileQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Optional<UserFile> findById_QueryDSL(Long userId) {
        UserFile userFileInfo = query.select(userFile)
                .from(userFile)
                .join(userFile.user).fetchJoin()
                .where(userFile.user.id.eq(userId))
                .fetchOne();

        return Optional.ofNullable(userFileInfo);
    }

    @Override
    public void deleteByUserId(Long userId) {
        query.delete(userFile)
                .where(userFile.user.id.eq(userId))
                .execute();
    }
}
