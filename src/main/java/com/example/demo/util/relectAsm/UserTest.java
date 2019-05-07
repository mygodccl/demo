package com.example.demo.util.relectAsm;

import com.esotericsoftware.reflectasm.FieldAccess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTest {
    public int id;
    public String name;

    public static void main(String[] args) {
        FieldAccess fieldAccess = FieldAccess.get(UserTest.class);
        String[] fieldNames = fieldAccess.getFieldNames();
        for (String fieldName : fieldNames) {
            System.out.println(fieldName);
        }
    }
}
