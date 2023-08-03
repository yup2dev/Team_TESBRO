package com.team.tesbro.academy;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcademyForm {
    @NotEmpty(message = "학원이름은 필수항목입니다.")
    private String academyName;
    @NotEmpty(message = "대표자명은 필수항목입니다.")
    private String ceoName;
    @NotEmpty(message = "연락처 기재는 필수항목입니다.")
    private String academyTel;
    @NotEmpty(message = "소개글은 필수항목입니다.")
    private String introduction;
    @NotEmpty(message = "로고 입력은 필수항목입니다.")
    private String imgLogo;
    @NotEmpty(message = "주소입력은 필수항목입니다.")
    private String academyAddress;
    @NotEmpty(message = "사업자번호는 필수항목입니다.")
    private long corNum;
}



