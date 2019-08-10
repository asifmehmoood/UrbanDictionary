package com.asif.urbandictionary;

import com.asif.urbandictionary.module.DictResponse;
import com.asif.urbandictionary.utils.AppConstants;

import org.junit.Test;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {



    @Test public void testAPI() throws Exception {
        URLConnection connection = new URL(AppConstants.BASE_URL+"define?term=wat").openConnection();
        connection.setRequestProperty("x-rapidapi-key", AppConstants.API_KEY);

        InputStream response = connection.getInputStream();

        StringBuffer buffer = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, Charset.defaultCharset()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                buffer.append(line);
            }
        }

        assert buffer.length() > 0;
    }
}