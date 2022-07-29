package com.godMorning_backend.repository;

import com.godMorning_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository가 CRUD함수를 이미 가지고 있다
//@Repository라는 어노테이션이 없어도 Ioc가 된다. JpaRepository를 상속했기 때문에.
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);//select * from user where username=? 가 호출되도록 해 주는 함수
    User findByNickname(String email);//select * from user where email = ? 가 호출되도록 해 주는 함수
    boolean existsByNickname(String nickname);//username이 존재하는지 확인해줌

}
