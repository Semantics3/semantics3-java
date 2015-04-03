package com.semantics3.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by abishek on 03/04/15.
 */
public class TestUtils {
    public static Properties getConfig(String fileName) throws IOException {
        Properties property = new Properties();
        InputStream input = TestUtils.class.getClassLoader().getResourceAsStream(fileName);
        if(input==null){
            System.out.println("Sorry, unable to find " + fileName);
        }
        property.load(input);
        return property;
    }
}
