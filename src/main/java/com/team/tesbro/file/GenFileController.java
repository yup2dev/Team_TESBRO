package com.team.tesbro.file;

import com.team.tesbro.Academy.Academy;
import com.team.tesbro.Academy.AcademyRepository;
import com.team.tesbro.Board.Board;
import com.team.tesbro.Board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class GenFileController {
    private final GenFileService genFileService;
    private final AcademyRepository academyRepository;
    private final BoardRepository boardRepository;

    @RequestMapping("/{id}")
    @ResponseBody
    public ResultData upload(@RequestParam Map<String, Object> param,
                             MultipartRequest multipartRequest,
                             @PathVariable("id") Integer id) {
        // Board / Academy 객체를 id로 조회
        // Board나 Academy 상세 등록폼을 보면 academy/board형태로 파라미터를 받아서 해당하는 곳으로 이동할 수 있도록 추가
        Academy academy = academyRepository.findById(id).orElse(null);
        Board board = boardRepository.findById(id).orElse(null);
        if (academy == null && board == null) {
            return new ResultData("F-1", "존재하지 않는 Academy ID입니다.");
        }

        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        List<Long> fileIds = new ArrayList<>();

        for (String fileInputName : fileMap.keySet()) {
            MultipartFile multipartFile = fileMap.get(fileInputName);

            String[] fileInputNameBits = fileInputName.split("___", 2);

            if (fileInputNameBits.length == 2 && fileInputNameBits[0].equals("file")) {
                fileInputNameBits = fileInputNameBits[1].split("__");

                int fileSize = (int) multipartFile.getSize();

                if (fileSize <= 0) {
                    continue;
                }

                String relTypeCode = fileInputNameBits[0];
                int relId = Integer.parseInt(fileInputNameBits[1]);
                String typeCode = fileInputNameBits[2];
                String type2Code = fileInputNameBits[3];
                int fileNo = Integer.parseInt(fileInputNameBits[4]);
                String originFileName = multipartFile.getOriginalFilename();
                String fileExtTypeCode = Util.getFileExtTypeCodeFromFileName(multipartFile.getOriginalFilename());
                String fileExtType2Code = Util.getFileExtType2CodeFromFileName(multipartFile.getOriginalFilename());
                String fileExt = Util.getFileExtFromFileName(multipartFile.getOriginalFilename()).toLowerCase();

                boolean fileUpdated = false;

                if (relId != 0) {
                    long oldFileId = genFileService.getGenFileId(relTypeCode, relId, typeCode, type2Code, fileNo);

                    if (oldFileId > 0) {
                        genFileService.updateFileOnDisk(multipartFile, oldFileId, originFileName, fileExtTypeCode,
                                fileExtType2Code, fileExt, fileSize);

                        fileUpdated = true;
                    }
                }

                if (fileUpdated == false) {

                }

                // academy라면 해당 매핑테이블에 저장
                if (relTypeCode.equals("academy")) {
                    long fileId = genFileService.saveFileOnDisk(multipartFile, relTypeCode, relId, typeCode, type2Code,
                            fileNo, originFileName, fileExtTypeCode, fileExtType2Code, fileExt, fileSize);

                    fileIds.add(fileId);
                    GenFile genFile = genFileService.getGenFileById(fileId); // 수정된 부분
                    academy.getGenFiles().add(genFile);
                    academyRepository.save(academy);
                    //board라면 해당 매핑테이블에 저장
                } else if (relTypeCode.equals("board")) {
                    long fileId = genFileService.saveFileOnDisk(multipartFile, relTypeCode, relId, typeCode, type2Code,
                            fileNo, originFileName, fileExtTypeCode, fileExtType2Code, fileExt, fileSize);

                    fileIds.add(fileId);
                    GenFile genFile = genFileService.getGenFileById(fileId);
                    board.getGenFiles().add(genFile);
                    boardRepository.save(board);
                }
            }
        }

        int deleteCount = 0;

        for (String inputName : param.keySet()) {
            String[] inputNameBits = inputName.split("__");

            if (inputNameBits[0].equals("deleteFile")) {
                String relTypeCode = inputNameBits[1];
                int relId = Integer.parseInt(inputNameBits[2]);
                String typeCode = inputNameBits[3];
                String type2Code = inputNameBits[4];
                int fileNo = Integer.parseInt(inputNameBits[5]);

                long oldFileId = genFileService.getGenFileId(relTypeCode, relId, typeCode, type2Code, fileNo);

                boolean needToDelete = oldFileId > 0;

                if (needToDelete) {
                    genFileService.deleteFile(oldFileId);
                    deleteCount++;

                    // academies에서 삭제
                    if (relTypeCode.equals("academy")) {
                        academy.getGenFiles().remove(genFileService.getGenFileById(relId));
                    }
                }
            }
        }

        Map<String, Object> rsDataBody = new HashMap<>();

        return new ResultData("S-1", String.format("%d개의 파일을 저장했습니다. %d개의 파일을 삭제했습니다.", fileIds.size(), deleteCount),
                rsDataBody);
    }
}
