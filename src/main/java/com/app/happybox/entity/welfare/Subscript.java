package com.app.happybox.entity.welfare;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.user.Welfare;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity @Table(name = "TBL_SUBSCRIPT")
@Getter @ToString(exclude = "welfare") @NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate @DynamicInsert
public class Subscript extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @NotNull
    private String subscriptName;

    @ColumnDefault(value = "0")
    private Integer subscriptPrice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Welfare welfare;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subscript")
    private List<SubscriptUser> subscriptUsers;
}
