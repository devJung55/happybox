package com.app.happybox.entity.board;

import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.user.WelfareDTO;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@Builder
public class DonationBoardDTO {

//    DonationBoard 기본 컬럼
    private String boardTitle;
    private String boardContent;
//    연관된 Entity의 DTO(Welfare)
    private WelfareDTO welfareDTO;

//    DontationFileDTO 작성할 곳

}
