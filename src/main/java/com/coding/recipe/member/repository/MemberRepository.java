package com.coding.recipe.member.repository;

import com.coding.recipe.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// JpaRepository<전달 받을 엔티티 타입, pk 컬럼 타입>
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    // 이메일로 회원 정보 조회 (select * from member_table where member_email=?)
    Optional<MemberEntity> findByMemberEmail(String memberEmail);


}
