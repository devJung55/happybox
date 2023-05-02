package com.app.happybox.entity.user;

import com.app.happybox.entity.file.FileInfo;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "USER_PROFILE")
@Getter @ToString(exclude = "user") @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @Embedded
    private FileInfo fileInfo;
}
