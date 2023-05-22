package com.app.happybox.entity.user;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

/**
 * 추후 Redis DB에 적용
 * */
@Entity @Table(name = "TBL_USER_RANDOM_KEY")
@Getter @ToString(exclude = "user") @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRandomKey {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @NotNull @Setter
    private String ranKey;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public String getTmpPassword() {
        char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String pwd = "";

        /* 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합 */
        int idx = 0;
        for(int i = 0; i < 10; i++){
            idx = (int) (charSet.length * Math.random());
            pwd += charSet[idx];
        }

        return pwd;
    }

    public UserRandomKey(Long id, String ranKey, User user) {
        this.id = id;
        this.ranKey = ranKey;
        this.user = user;
    }

    public UserRandomKey(User user) {
        this.id = getId();
        this.ranKey = getTmpPassword();
        this.user = user;
    }
}
