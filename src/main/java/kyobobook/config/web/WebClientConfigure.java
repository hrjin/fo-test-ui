/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * hrjin@kyobobook.com      2021. 11. 24.
 *
 ****************************************************/
package kyobobook.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import io.netty.channel.ChannelOption;
// import io.netty.handler.ssl.SslContextBuilder;
// import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : WebClient.java
 * @Date        : 2021. 11. 24.
 * @author      : hrjin@kyobobook.com
 * @description :
 */
@Configuration
@Slf4j
public class WebClientConfigure {
    
    @Bean
    public WebClient webClient() {
        // in-memory buffer 값이 256KB로 기본설정. 값 늘려줌.
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                                                    .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024*1024*50))
                                                    .build();
        // Request/Response 정보를 상세히 확인
        exchangeStrategies.messageWriters()
            .stream()
            .filter(LoggingCodecSupport.class::isInstance)
            .forEach(writer -> ((LoggingCodecSupport)writer).setEnableLoggingRequestDetails(true));
        
        return WebClient.builder()
                .clientConnector(
                        new ReactorClientHttpConnector(
                                HttpClient
                                    .create()
//                                    .secure(
//                                            // HTTPS 인증서를 검증하지 않고 바로 접속하는 설정
//                                            ThrowingConsumer.unchecked(
//                                                    sslContextSpec -> sslContextSpec.sslContext(
//                                                                SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build()
//                                                                )
//                                                    )
//                                            )
                                    // TCP 연결 시 ConnectionTimeOut , ReadTimeOut , WriteTimeOut 을 적용하는 설정 추가
                                    .tcpConfiguration(
                                                client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 120_000)
                                                                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(180))
                                                                .addHandlerLast(new WriteTimeoutHandler(180))
                                                                )
                                                    )
                                )
                        )
                .exchangeStrategies(exchangeStrategies)
                // Request / Response header를 출력
                .filter(ExchangeFilterFunction.ofRequestProcessor(
                                                    clientRequest -> {
                                                        log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
                                                        clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
                                                        return Mono.just(clientRequest);
                                                    }
                ))
                .filter(ExchangeFilterFunction.ofResponseProcessor(
                    clientResponse -> {
                            clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
                            return Mono.just(clientResponse);
                    }
                ))
                .defaultHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.3")
                .build();
    }
}
