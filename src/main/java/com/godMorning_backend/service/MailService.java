package com.godMorning_backend.service;

import com.godMorning_backend.domain.user.User;
import com.godMorning_backend.dto.MailDto;
import com.godMorning_backend.repository.JDBCUserRepository;
import com.godMorning_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@Service
@RequiredArgsConstructor

public class MailService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final JDBCUserRepository jdbcUserRepository;
    public MailDto createMailAndChangePw(String email){
        String tempPw = getTempPw();
        MailDto mailDto = new MailDto();


        mailDto.setAddress(email);
        mailDto.setTitle("갓모닝 임시 비밀번호 안내 이메일 입니다.");
        mailDto.setMessage("안녕하세요. 갓모닝 임시 비밀번호 관련 안내 이메일입니다." + "회원님의 임시 비밀번호는 "
        + tempPw + " 입니다." + "로그인 후에 비밀번호를 변경해주세요.");

        updatePw(tempPw, email);


        return mailDto;
    }

    public void updatePw(String temp, String email){
        String tempPw = temp;
        Long id = userRepository.findByEmail(email).getId();
        jdbcUserRepository.setPassword(tempPw, id);

    }



    public String getTempPw(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        int idx = 0;
        for (int i=0; i<10; i++){
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    public void mailSend(MailDto mailDto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        message.setFrom("dinamic1016@naver.com");
        mailSender.send(message);
    }

    public void updatePw(Long id, String password){

    }



}
