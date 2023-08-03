package com.coding.recipe.member.service;

import com.coding.recipe.member.dto.MemberDTO;
import com.coding.recipe.member.entity.MemberEntity;
import com.coding.recipe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * repository의 save 메서드 호출 (조건. entity 객체를 넘겨줘야 함)
     * 1. dto -> entity 반환
     * 2. repository의 save 메서드 호출
     */
    @Transactional
    public MemberEntity save(MemberDTO memberDTO) {
        Optional<MemberEntity> memberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());

        if (memberEmail.isPresent()) {
            return null;
        } else {
            MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
            return memberRepository.save(memberEntity);
        }
    }

    /**
     * 1. 회원이 입력한 이메일로 DB에서 조회
     * 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 확인
     */
    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());

        // 조회 결과가 있는 경우
        if (byMemberEmail.isPresent()) {
            MemberEntity memberEntity = byMemberEmail.get();
            // 비밀번호 일치
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                // entity -> dto 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();

        for (MemberEntity memberEntity : memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }

        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberId = memberRepository.findById(id);
        if (optionalMemberId.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberId.get());
        } else {
            return null;
        }
    }

    public MemberDTO updateFrom(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    @Transactional
    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    @Transactional
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    // 이메일 중복 확인 메서드
    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        // email 중복 확인
        if (byMemberEmail.isPresent()) {
            return "null";
        } else {
            return "ok";
        }
    }
}
