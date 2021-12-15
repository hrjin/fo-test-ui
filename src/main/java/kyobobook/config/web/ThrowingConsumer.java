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

import java.util.function.Consumer;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : ThrowingConsumer.java
 * @Date        : 2021. 11. 24.
 * @author      : hrjin@kyobobook.com
 * @description : Stream 처리에서 Exception 처리를 위한 Util
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Throwable> {
    void accept(T t) throws E;

    static <T, E extends Throwable> Consumer<T> unchecked(ThrowingConsumer<T, E> f) {
        return t -> {
            try {
                f.accept(t);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

    static <T, E extends Throwable> Consumer<T> unchecked(ThrowingConsumer<T, E> f, Consumer<Throwable> c) {
        return t -> {
            try {
                f.accept(t);
            } catch (Throwable e) {
                c.accept(e);
            }
        };
    }
}
