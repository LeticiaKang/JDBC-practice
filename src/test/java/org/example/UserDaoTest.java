package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {

    // alt + ins 로 setup메소드 생성
    // 테스트 코드 수행 전 실행할 내용을 작성한다.
    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

        // db_schema.sql 라는 스크립트 파일을 읽어 와서 ClassPathResource에서 읽어와서 populator에 추가해 줌
        populator.addScript(new ClassPathResource("db_schema.sql"));

        // ConnectionManager에서 데이터 소스를 받아오는 것임.
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }


    @Test
    void createTest() throws SQLException {
        UserDao userDao = new UserDao();

        // 데이터테이블이 아디이(userId), 패스워드(password), 이름(name), 이메일(email)로 되어 있기 때문임
        // 유저를 DB에 생성/저장한다.
        userDao.create(new User("wizard", "password", "name", "email"));

        // 유저를 조회한다(findByUserId)
        User user = userDao.findByUserId("wizard");

        // 조회로 찾은 유저 정보가 입력 받은 것고 같은지 확인한다.(isEqualTo)
        assertThat(user).isEqualTo(new User("wizard", "password", "name", "email"));
    }

}
