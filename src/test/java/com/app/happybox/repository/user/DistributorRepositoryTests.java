package com.app.happybox.repository.user;

import com.app.happybox.entity.type.Gender;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.Distributor;
import com.app.happybox.entity.user.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class DistributorRepositoryTests {
    @Autowired
    private DistributorRepository distributorRepository;

    @Test
    public void saveTest(){
        // given
        Address address = new Address("99999", "경남 거창", "정표네 사과농장");
        Distributor distributor = new Distributor("kjp1234",
                "1234",
                address,
                "kjp1234@gmail.com",
                "01043214321",
                "김정표"
        );

        // when

        // then
        distributorRepository.save(distributor);
    }
}