package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;

public interface MemberQueryDsl {
//    회원정보수정
    public void setMemberInfoById_QueryDSL(Member member);

//    회원탈퇴
    public void setMemberStatusById_QueryDSL(Member member);
}
