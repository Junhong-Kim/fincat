package com.wagoowagoo.fincat.common;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    abstract protected Object controller();

    @BeforeEach
    private void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller()) // 테스트할 Controller
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true)) // UTF-8 인코딩
                .alwaysDo(print()) // 항상 테스트 결과 콘솔에 출력
                .alwaysExpect(MockMvcResultMatchers.status().isOk()) // 항상 예측되는 결과 (200 OK)
                .build();
    }
}
