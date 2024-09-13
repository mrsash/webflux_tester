package ru.mrsash.webflux_test;

import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import reactor.test.StepVerifier;

@SpringBootTest
public class WebfluxTesterTest {

    @Autowired
    private WebfluxTester webfluxTester;

    @RepeatedTest(10)
    @DirtiesContext
    public void test1() {
        StepVerifier.create(webfluxTester.differentInnerMono())
                .expectNext("name")
                .verifyComplete();
    }

    @RepeatedTest(10)
    @DirtiesContext
    public void test2() {
        StepVerifier.create(webfluxTester.sameInnerMono())
                .expectNext("name")
                .verifyComplete();
    }

    @RepeatedTest(10)
    @DirtiesContext
    public void test3() {
        StepVerifier.create(webfluxTester.oneLevelMono())
                .expectNext("name")
                .verifyComplete();
    }
}
