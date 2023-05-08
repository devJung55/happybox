package com.app.happybox.entity.file;

import com.app.happybox.entity.board.Board;
import com.app.happybox.entity.type.FileRepresent;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "TBL_BOARD_FILE")
@Getter @ToString(exclude = "board") @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardFile extends Files {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    //    파일이 대표 파일인지 여부
    @Enumerated(EnumType.STRING)
    private FileRepresent fileRepresent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Board board;

    public BoardFile(String filePath, String fileUuid, String fileOrgName, FileRepresent fileRepresent) {
        super(filePath, fileUuid, fileOrgName);
        this.fileRepresent = fileRepresent;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
