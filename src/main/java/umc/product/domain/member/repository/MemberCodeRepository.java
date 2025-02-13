package umc.product.domain.member.repository;


import org.springframework.data.repository.CrudRepository;
import umc.product.domain.member.entity.MemberCode;

public interface MemberCodeRepository extends CrudRepository<MemberCode, String>
{
}

