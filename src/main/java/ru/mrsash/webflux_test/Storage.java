package ru.mrsash.webflux_test;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
public class Storage {

    private final List<String> someList = new ArrayList<>();

    public Mono<String> get() {
        return Mono.fromFuture(CompletableFuture.completedFuture(someList))
                .flatMapMany(Flux::fromIterable)
                .filter(Objects::nonNull)
                .take(1)
                .singleOrEmpty();
    }

    public Mono<Void> add(String s) {
        return Mono.fromFuture(CompletableFuture.runAsync(() -> someList.add(s)));
    }

}
