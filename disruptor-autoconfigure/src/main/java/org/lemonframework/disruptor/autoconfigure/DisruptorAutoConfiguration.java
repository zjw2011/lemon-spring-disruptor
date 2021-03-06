/*
 * Copyright 2017-2019 Lemonframework Group Holding Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.lemonframework.disruptor.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lmax.disruptor.dsl.ProducerType;
import org.lemonframework.disruptor.AsyncConsumer;
import org.lemonframework.disruptor.AsyncProducer;

/**
 * boot-parent.
 *
 * @author jiawei
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(DisruptorProperties.class)
public class DisruptorAutoConfiguration {

    private final DisruptorProperties properties;


    public DisruptorAutoConfiguration(DisruptorProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public AsyncProducer userEventPublisher(AsyncConsumer asyncConsumer) {
        return AsyncProducer.builder()
                        .setBufferSize(properties.getBufferSize())
                        .setConsumerCount(properties.getConsumerSize())
                        .setConsumerName(properties.getConsumerName())
                        .setConsumer(asyncConsumer)
                        .setGlobalQueue(properties.isGlobalQueue())
                        .setProducerType(properties.isMulti() ?
                                ProducerType.MULTI : ProducerType.SINGLE)
                        .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public AsyncConsumer asyncConsumer() {
        return data -> {
            throw new RuntimeException("Not Config Asynchronous Conusmer!!");
        };
    }

}
