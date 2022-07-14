package com.godMorning_backend.service;

import com.godMorning_backend.domain.user.User;
import com.godMorning_backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원 탈퇴
    public String withdrawal(User user, User findUser){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //해당 회원의 DB저장 비번과 직접 입력한 비번 비교해서 맞으면 탈퇴 진행
        if(encoder.matches(user.getPassword(), findUser.getPassword())) { //비밀번호 일치할 경우
            user.setId(findUser.getId());
            userRepository.delete(user);
            return "탈퇴 완료 되었습니다.";
        } else { //비밀번호 일치하지 않을 경우
            return "비밀번호가 틀렸습니다.";
        }
    }
}
