package com.app.happybox.controller.file;

import com.app.happybox.service.board.ReviewBoardService;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/image/*")
@RequiredArgsConstructor
public class FileRestController {

    private static final String ABSOLUTE_PATH = "C:/upload";

    //    파일 업로드
//    @PostMapping("upload")
//    public Map<String, Object> suggestUpload(@RequestParam("file") List<MultipartFile> multipartFiles) throws IOException {
//        Map<String, Object> map = new HashMap<>();
//
//        List<String> uuids = new ArrayList<>();
//        List<String> filePaths = new ArrayList<>();
//        String path = ABSOLUTE_PATH + "/" + getPath();
//        String filePath = "";
//        File file = new File(path);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//
//        for (int i = 0; i < multipartFiles.size(); i++) {
//            uuids.add(UUID.randomUUID().toString());
//            filePath = uuids.get(i) + "_" + multipartFiles.get(i).getOriginalFilename();
//            /* multipartFiles로 가져온 파일을 path, uuid, fileOriginalName 을 File 객체로 만들어 저장 */
//            multipartFiles.get(i).transferTo(new File(path, uuids.get(i) + "_" + multipartFiles.get(i).getOriginalFilename()));
//
//            /* 해당 파일이 이미지인 경우 썸네일도 저장 */
//            if (multipartFiles.get(i).getContentType().startsWith("image")) {
//                FileOutputStream out = new FileOutputStream(new File(path, "t_" + uuids.get(i) + "_" + multipartFiles.get(i).getOriginalFilename()));
//                Thumbnailator.createThumbnail(multipartFiles.get(i).getInputStream(), out, 150, 150);
//                out.close();
//                filePath = "t_" + uuids.get(i) + "_" + multipartFiles.get(i).getOriginalFilename();
//            }
//
//            filePaths.add(getPath() + "/" + filePath);
//        }
////
//        map.put("uuids", uuids);
//        map.put("paths", filePaths);
//        return map;
//    }

    public List<String> upload(@RequestParam("file") List<MultipartFile> multipartFiles) throws IOException {
        /* String 타입의 ArrayList를 만듬*/
        List<String> uuids = new ArrayList<>();
        /* c -> upload 폴더안에 저장해놓은 path를 담음*/
        String path = "C:/upload/" + getPath();
        File file = new File(path);
        if (!file.exists()) {
            /*make Directory 메소드를 사용하여 경로대로 폴더를 생성함*/
            file.mkdirs();
        }
        /*랜덤한 숫자의 Uuid를 만들어서 uuids list에 담아줌*/
        for (int i = 0; i < multipartFiles.size(); i++) {
            uuids.add(UUID.randomUUID().toString());
            /* 각각 파일들에 경로 + uuid + _파일의 실제 이름을 붙여줌*/
            multipartFiles.get(i).transferTo(new File(path, uuids.get(i) + "_" + multipartFiles.get(i).getOriginalFilename()));
            /*파일의 타입이 image라면 썸네일을 파일을 생성해줌*/
            InputStream inputStream = new FileInputStream("C:\\upload\\" + getPath() + "\\" + uuids.get(i)+ "_" + multipartFiles.get(i).getOriginalFilename());

            if (multipartFiles.get(i).getContentType().startsWith("image")) {
                FileOutputStream out = new FileOutputStream(new File(path, "t_" + uuids.get(i) + "_" + multipartFiles.get(i).getOriginalFilename()));
                Thumbnailator.createThumbnail(inputStream, out, 100, 100);
                out.close();
            }
        }
        return uuids;
    }


    //    파일 불러오기
    @GetMapping("display")
    public byte[] Display(String fileName) throws Exception {
        return fileName.contentEquals("null") || fileName.isBlank() ? null : FileCopyUtils.copyToByteArray(new File("C:/upload", fileName));
    }

    //    현재 날짜 경로 구하기
    private String getPath() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
