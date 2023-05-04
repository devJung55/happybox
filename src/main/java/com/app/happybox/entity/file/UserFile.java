package com.app.happybox.entity.file;

import com.app.happybox.entity.type.UserFileRepresent;
import com.app.happybox.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity @Table(name = "TBL_USER_FILE")
@DiscriminatorValue("USER")
@Getter @ToString(exclude = "user",callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFile extends Files {
    @Enumerated(EnumType.STRING)
    private UserFileRepresent userFileRepresent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
}
