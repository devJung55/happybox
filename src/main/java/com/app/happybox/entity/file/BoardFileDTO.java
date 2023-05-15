package com.app.happybox.entity.file;

import com.app.happybox.entity.board.Board;
import com.app.happybox.type.FileRepresent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class BoardFileDTO {
    private Long id;

    private String filePath;
    private String fileUuid;
    private String fileOrgName;

    private FileRepresent fileRepresent;

    private Board board;

    @Builder
    public BoardFileDTO(Long id, String filePath, String fileUuid, String fileOrgName, FileRepresent fileRepresent, Board board) {
        this.id = id;
        this.filePath = filePath;
        this.fileUuid = fileUuid;
        this.fileOrgName = fileOrgName;
        this.fileRepresent = fileRepresent;
        this.board = board;
    }


    public void setFileRepresent(FileRepresent fileRepresent) {
        this.fileRepresent = fileRepresent;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
