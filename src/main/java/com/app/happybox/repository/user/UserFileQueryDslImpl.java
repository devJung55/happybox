package com.app.happybox.repository.user;

import com.app.happybox.entity.file.QUserFile;
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
        Optional<UserFile> userFileInfo = Optional.ofNullable(query.select(userFile)
                .from(userFile)
                .join(userFile.user).fetchJoin()
                .where(userFile.user.id.eq(userId))
                .fetchOne());

        return userFileInfo;
    }
}
