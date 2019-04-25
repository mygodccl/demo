package com.example.demo.util.blackList;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractBlackList implements BlackList {

    private static Set<String> contextBlackList = new HashSet<>();

    private static final byte[] FLAGS = new byte[256];

    private static final byte NEED_ESCAPE = 0x01;

    private static final byte NEED_ADD_DOT = 0x02;

    static {
        FLAGS['.'] = NEED_ESCAPE;

        FLAGS['?'] = NEED_ADD_DOT;
        FLAGS['+'] = NEED_ADD_DOT;
        FLAGS['*'] = NEED_ADD_DOT;

        contextBlackList.add("com.+");
        contextBlackList.add("org.+");
        contextBlackList.add("+.FILTERED");
        contextBlackList.add("javax.+");
        contextBlackList.add("__spring_+");
        contextBlackList.add("reactJsRootPath");
        contextBlackList.add("controllerBegin");
        contextBlackList.add("languageLocale");
        contextBlackList.add("headerStyle");
        contextBlackList.add("currencyLocale");
        contextBlackList.add("i18nRootPath");
        contextBlackList.add("log_trace_reqId");
        contextBlackList.add("currencyCode");
    }

    AbstractBlackList() {
        contextBlackList = convert();
    }


    private Set<String> convert() {
        return contextBlackList.stream().map(this::convertToRegular).collect(Collectors.toSet());
    }

    private String convertToRegular(String input) {
        StringBuilder buffer = new StringBuilder();
        char[] bytes = input.toCharArray();
        for (char b : bytes) {
            switch (FLAGS[b]) {
                case NEED_ESCAPE:
                    buffer.append("\\");
                    break;
                case NEED_ADD_DOT:
                    buffer.append(".");
                    break;
                default:
                    break;
            }
            buffer.append(b);
        }
        return buffer.toString();
    }

    @Override
    public boolean match(Object target) {
        for (String reg : contextBlackList) {
            if (Optional.ofNullable(target).map(Object::toString).orElse("").matches(reg)) {
                return true;
            }
        }
        return false;
    }

}
