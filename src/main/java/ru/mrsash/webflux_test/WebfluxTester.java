package ru.mrsash.webflux_test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebfluxTester {

    private final Storage storage;

    public Mono<String> differentInnerMono() {
        return startCallable()
                .flatMap(stringMono -> storage.get()
                        .flatMap(s -> Mono.error(new IllegalArgumentException()))
                        .thenReturn(stringMono)
                )
                .flatMap(stringMono -> storage.add("test")
                        .thenReturn(stringMono)
                );
    }

    public Mono<String> sameInnerMono() {
        return startCallable()
                .flatMap(stringMono -> storage.get()
                        .flatMap(s -> Mono.error(new IllegalArgumentException()))
                        .switchIfEmpty(storage.add("test"))
                        .thenReturn(stringMono)
                );
    }

    public Mono<String> oneLevelMono() {
        return startCallable()
                .then(storage.get())
                .flatMap(s -> Mono.error(new IllegalArgumentException()))
                .switchIfEmpty(Mono.empty())
                .flatMap(o -> storage.add("test"))
                .thenReturn("name");
    }

    private Mono<String> startCallable() {
        return Mono.fromCallable(() -> "name");
    }
}
