package com.app.happybox.controller.board;

import com.app.happybox.entity.board.*;
import com.app.happybox.repository.board.DonationBoardRepository;
import com.app.happybox.service.board.DonationBoardService;
import com.app.happybox.service.board.RecipeBoardService;
import com.app.happybox.service.board.ReviewBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user-board/*")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    @Qualifier
    private final ReviewBoardService reviewBoardService;
    @Qualifier
    private final RecipeBoardService recipeBoardService;
    @Qualifier
    private final DonationBoardService donationBoardService;

    @GetMapping("review-board-list")
    public String goReviewList(Model model) {
        model.addAttribute("list", reviewBoardService.getReviewBoards(PageRequest.of(0, 10)));
        return "user-board/review-board-list";
    }

    @GetMapping("review-board-insert")
    public String goReviewWrite() {
        return "user-board/review-board-insert";
    }

    @PostMapping("review-board-insert")
    public void write(ReviewBoard reviewBoard) {
        reviewBoardService.write(reviewBoard);
    }

    //    리뷰 게시판 리스트(최신순)
    @GetMapping("review-board-list/recent")
    @ResponseBody
    public Slice<ReviewBoardDTO> goRecentReviewList(@PageableDefault Pageable pageable) {
        Slice<ReviewBoardDTO> result =
                reviewBoardService
                        .getReviewBoards(PageRequest.of(pageable.getPageNumber() - 1,
                                pageable.getPageSize()));
        return result;
    }

    //    레시피 게시판 리스트 (최신순)
    @GetMapping("recipe-board-list")
    public Slice<RecipeBoardDTO> getRecipeBoardList(int page, int size) {
        return recipeBoardService.getRecipeBoards(PageRequest.of(page, size));
    }

    //    기부 게시판 리스트
    @GetMapping("donate-list")
    public Page<DonationBoardDTO> getDonateBoardList(int page, int size) {
        return donationBoardService.getList(PageRequest.of(page, size));
    }

    //    파일 업로드
    @PostMapping("upload")
    public List<String> suggestUpload(@RequestParam("file") List<MultipartFile> multipartFiles) throws IOException {
        List<String> uuids = new ArrayList<>();
        String path = "C:/upload/" + getPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }


        for (int i = 0; i < multipartFiles.size(); i++) {
            uuids.add(UUID.randomUUID().toString());
            multipartFiles.get(i).transferTo(new File(path, uuids.get(i) + "_" + multipartFiles.get(i).getOriginalFilename()));

            InputStream inputStream = new FileInputStream("C:\\upload\\" + getPath() + "\\" + uuids.get(i) + "_" + multipartFiles.get(i).getOriginalFilename());

            if (multipartFiles.get(i).getContentType().startsWith("image")) {
                FileOutputStream out = new FileOutputStream(new File(path, "t_" + uuids.get(i) + "_" + multipartFiles.get(i).getOriginalFilename()));
                Thumbnailator.createThumbnail(inputStream, out, 400, 400);
                out.close();
            }
        }
        return uuids;
    }


    //    파일 불러오기
    @GetMapping("display")
    public byte[] Display(String fileName) throws Exception {
        try {
            return fileName.contentEquals("null") || fileName.isBlank() ? null : FileCopyUtils.copyToByteArray(new File("C:/upload", fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //    현재 날짜 경로 구하기
    private String getPath() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }


}
