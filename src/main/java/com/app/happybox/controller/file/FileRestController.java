package com.app.happybox.controller.file;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/image/*")
@RequiredArgsConstructor
public class FileRestController {

    //    파일 업로드
    @PostMapping("upload")
    public List<String> suggestUpload(@RequestParam("file") List<MultipartFile> multipartFiles) throws IOException {
        List<String> uuids = new ArrayList<>();
        String path = "C:/upload/" + getPath();
        File file = new File(path);
        if(!file.exists()) {file.mkdirs();}


        for(int i=0; i<multipartFiles.size(); i++){
            uuids.add(UUID.randomUUID().toString());
            multipartFiles.get(i).transferTo(new File(path, uuids.get(i) + "_" + multipartFiles.get(i).getOriginalFilename()));

            InputStream inputStream = new FileInputStream("C:\\upload\\" + getPath() + "\\" + uuids.get(i)+ "_" + multipartFiles.get(i).getOriginalFilename());

            if(multipartFiles.get(i).getContentType().startsWith("image")){
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
