package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by davidhe on 01/02/18.
 */
@Slf4j
public class TextUtils {

    private static final String[] numberType = { "int", "number", "float", "numeric", "float", "double", "decimal", "smallint", "int unsigned" };

    public static boolean isDecimalValue(String str) {
        String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
        return str.matches(regex);
    }

    public static boolean isDateValue(String str) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            sdf1.parse(str);
        } catch (Exception e) {
            try {
                sdf2.parse(str);
            } catch (Exception e2) {
                return false;
            }
        }
        return true;
    }

    public static String intText(String value) {
        if (StringUtils.isBlank(value)) {
            try {
                value = Integer.valueOf(Double.valueOf(value).intValue()).toString();
            } catch (Exception e) {// can't convert to int
            }
        }
        return value;
    }

    public static String setValueIfBlank(String str, String defaultStr) {
        return StringUtils.trimToNull(str) == null ? defaultStr : str;
    }

    public static String getReplaceString(String sqlStr) {
        if (StringUtils.isNotBlank(sqlStr)) {
            sqlStr = sqlStr.replaceAll("\'", "\'\'");
        }
        return sqlStr;
    }

    public static int parseInt(String str, int defaultValue) {
        if (StringUtils.isEmpty(str)) {
            log.warn("Input string is empty, returns [" + defaultValue + "].");
            return defaultValue;
        }
        String tmp = str.trim();
        try {
            return Integer.parseInt(tmp);
        } catch (NumberFormatException nfe) {
            log.warn("Can NOT parse to int [" + str + "], return [" + defaultValue + "].");
            return defaultValue;
        }
    }

    public static Integer parseInt(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        // decode() handles 0xAABD and 0777 (hex and octal) as well.
        return Integer.decode(str);
    }

    public static String boolText(Boolean boo) {
        if (boo == null) {
            return "N";
        }
        return boo ? "Y" : "N";
    }

    public static Boolean parseBool(String str) {
        if ("true".equalsIgnoreCase(str)) {
            return Boolean.TRUE;
        } else if ("false".equalsIgnoreCase(str)) {
            return Boolean.FALSE;
        } else if ("yes".equalsIgnoreCase(str)) {
            return Boolean.TRUE;
        } else if ("no".equalsIgnoreCase(str)) {
            return Boolean.FALSE;
        } else if ("y".equalsIgnoreCase(str)) {
            return Boolean.TRUE;
        } else if ("n".equalsIgnoreCase(str)) {
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    public static Boolean parseBool(Integer str) {
        if (str == null) {
            return Boolean.FALSE;
        }
        if (str > 0) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public static boolean isCharType(String dataType) {
        return "char".equals(dataType) || "text".equals(dataType) || "datetime".equals(dataType) || "date".equals(dataType) || "longtext".equals(dataType)
                        || "varchar".equals(dataType);
    }

    public static boolean isNumberValue(String str) {
        String regex = "^(-?[0-9]\\d*\\.?\\d*)|(-?[0])$";
        return str.matches(regex);
    }

    public static String safeToString(Object str, String orElse) {
        return Optional.ofNullable(str).map(Object::toString).orElse(orElse);
    }

    public static String trimToNull(Object str) {
        String tmp = str == null ? "" : str.toString();
        tmp = StringUtils.trimToNull(tmp);
        return tmp;
    }

    public static List<String> toList(String str, String spliter) {
        List<String> rtn = new ArrayList<>();
        if (str == null || StringUtils.isBlank(str)) {
            return rtn;
        }
        String[] it = str.split(spliter);
        for (String s : it) {
            if (StringUtils.isNotBlank(s)) {
                rtn.add(StringUtils.trimToEmpty(s));
            }
        }
        return rtn;
    }

    public static String camel2underline(String camel) {
        char[] cs = camel.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : cs) {
            if (Character.isUpperCase(c)) {
                stringBuilder.append("_").append(Character.toLowerCase(c));
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    public static String underline2camel(String underline) {
        String[] tmpStr = underline.split("_");
        StringBuilder stringBuilder = new StringBuilder(tmpStr[0]);
        for (int i = 1; i < tmpStr.length; i++) {
            char[] cs = tmpStr[i].toCharArray();
            cs[0] = Character.toUpperCase(cs[0]);
            stringBuilder.append(cs);
        }
        return stringBuilder.toString();
    }

    public static boolean isNumber(String dataType) {
        Assert.notNull(dataType, "dataType can not be null!");
        return ArrayUtils.contains(numberType, dataType.toLowerCase());
    }
}
