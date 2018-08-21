/*
 * Copyright 2016 http://github.com/hs-web
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sun.web.controller;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 提供默认支持的查询条件类型,用于动态指定查询条件
 *
 * @author
 * @since 1.0
 */
public enum TermType {

    /**
     * ==
     *
     * @since 1.0
     */
    eq("eq"),
    /**
     * !=
     *
     * @since 1.0
     */
    not("not"),
    /**
     * like
     *
     * @since 1.0
     */
    like("like"),
    /**
     * not like
     *
     * @since 1.0
     */
    nlike("nlike"),
    /**
     * >
     *
     * @since 1.0
     */
    gt("gt"),
    /**
     * <
     *
     * @since 1.0
     */
    lt("lt"),
    /**
     * >=
     *
     * @since 1.0
     */
    gte("gte"),
    /**
     * <=
     *
     * @since 1.0
     */
    lte("lte"),
    /**
     * in
     *
     * @since 1.0
     */
    in("in"),
    /**
     * notin
     *
     * @since 1.0
     */
    nin("nin"),
    /**
     * =''
     *
     * @since 1.0
     */
    empty("empty"),
    /**
     * !=''
     *
     * @since 1.0
     */
    nempty("nempty"),
    /**
     * is null
     *
     * @since 1.0
     */
    isnull("isnull"),
    /**
     * not null
     *
     * @since 1.0
     */
    notnull("notnull"),
    /**
     * between
     *
     * @since 1.0
     */
    btw("btw"),
    /**
     * not between
     *
     * @since 1.0
     */
    nbtw("nbtw");

    private static final Map<String, TermType> cache = Maps.newHashMap();

    static {
        if (values() != null) {
            for (TermType operator : values()) {
                cache.put(operator.getValue(), operator);
            }
        }
    }

    private String value;

    TermType(String value) {
        this.value = value;
    }

    public static TermType parse(String operation) {
        return cache.get(operation);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }


}
