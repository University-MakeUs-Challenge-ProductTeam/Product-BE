package umc.product.domain.semester.service;

import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;

public interface SemesterPartService {
    public SemesterPart saveSemesterPart(Member member, Part part, Semester semester);
}
