package org.practice.shop.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class FileService {
    // 파일 등록하기
    public String uploadFile(String uploadPath, String originalFile, byte[] fileData) throws Exception {
        // uuid 생성 - 파일이름이 중복되지 않도록 파일명을 랜덤으로 만들어준다
        UUID uuid = UUID.randomUUID();
        // 확장자 ??.jpg -> .jpg만 변수값에 할당
        String extentsion = originalFile.substring(originalFile.lastIndexOf("."));
        // 새로운 파일 명 uuid는 객체이므로 .toString으로 문자값을 받아오자
        String saveFileName = uuid.toString() + extentsion;
        // 경로와 파일명 지정해주기
        String fileUploadFullUrl = uploadPath + "/" + saveFileName;
        // 파일 생성 - 예외 처리 진행
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return saveFileName;
    }
    // 파일 삭제하기
    public void deleteFile(String filepath) {
        // 파일 객체 생성
        File deleteFile = new File(filepath);
        if(deleteFile.exists()) {
            deleteFile.delete();
        }
    }
}
