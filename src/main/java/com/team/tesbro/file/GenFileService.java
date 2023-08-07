package com.team.tesbro.file;

import com.team.tesbro.academy.Academy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GenFileService {
    public static String GEN_FILE_DIR_PATH;
    @Value("${custom.genFileDirPath}")
    public void setGenFileDirPath(String genFileDirPath) {
        GEN_FILE_DIR_PATH = genFileDirPath;
    }

    private final GenFileRepository genFileRepository;

    public void updateFileOnDisk(MultipartFile multipartFile, long fileId, String originFileName, String fileExtTypeCode, String fileExtType2Code, String fileExt, int fileSize) {
    }

    private long saveFile(String relTypeCode, int relId, String typeCode, String type2Code, int fileNo, String fileDir,
                          String originFileName, String fileExtTypeCode, String fileExtType2Code, String fileExt, int fileSize, Academy academy) {

        GenFile genFile = GenFile
                .builder()
                .relTypeCode(relTypeCode)
                .relId(relId)
                .typeCode(typeCode)
                .type2Code(type2Code)
                .fileNo(fileNo)
                .fileDir(fileDir)
                .originFileName(originFileName)
                .fileExtTypeCode(fileExtTypeCode)
                .fileExtType2Code(fileExtType2Code)
                .fileExt(fileExt)
                .fileSize(fileSize)
                .build();

        genFile.setAcademy(academy);
        genFileRepository.save(genFile);

        return genFile.getId();
    }

    public Long saveFileOnDisk(MultipartFile multipartFile, String relTypeCode, int relId, String typeCode, String type2Code, int fileNo, String originFileName, String fileExtTypeCode, String fileExtType2Code, String fileExt, int fileSize, Academy academy) {
        // 새 파일이 저장될 폴더명 생성(연_월)
        String fileDir = Util.getNowYearMonthDateStr();

        // DB에 파일정보 등록(먼저 수행하는 이유는 파일을 생성할 때 파일명이 file 테이블의 주키 이기 때문에)
        long fileId = saveFile(relTypeCode, relId, typeCode, type2Code, fileNo, fileDir, originFileName, fileExtTypeCode,
                fileExtType2Code, fileExt, fileSize, academy);

        // 새 파일이 저장될 폴더(io파일) 객체 생성
        String targetDirPath = GEN_FILE_DIR_PATH + "/" + relTypeCode + "/" + fileDir;
        File targetDir = new File(targetDirPath);

        // 새 파일이 저장될 폴더가 존재하지 않는다면 생성
        if (targetDir.exists() == false) {
            targetDir.mkdirs();
        }

        String targetFileName = fileId + "." + fileExt;
        String targetFilePath = targetDirPath + "/" + targetFileName;

        System.out.println("targetFilePath : " + targetFilePath);

        // 파일 생성(업로드된 파일을 지정된 경로롤 옮김)
        try {
            multipartFile.transferTo(new File(targetFilePath));
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return fileId;
    }

    public long getGenFileId(String relTypeCode, long relId, String typeCode, String type2Code, int fileNo) {
        GenFile genFile = genFileRepository.findByRelTypeCodeAndRelIdAndTypeCodeAndType2CodeAndFileNo(relTypeCode, relId, typeCode, type2Code, fileNo);

        if (genFile == null) {
            return -1;
        }

        return genFile.getId();
    }

    public void deleteFile(long id) {
        // 기존 파일 정보 불러오기
        GenFile oldGenFile = getGenFileById(id);

        // 기존 파일이 디스크에 저장되어 있다면 삭제
        String filePath = oldGenFile.getFilePath();

        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        genFileRepository.delete(oldGenFile);
    }

    public GenFile getGenFileById(long id) {
        return genFileRepository.findById(id).orElse(null);
    }
}