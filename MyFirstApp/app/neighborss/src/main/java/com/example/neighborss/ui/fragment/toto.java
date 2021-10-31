package com.example.neighborss.ui.fragment;

import android.webkit.URLUtil;

import java.util.function.Predicate;

public class toto {

    void toto(){

        Predicate<String> tata = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return URLUtil.isValidUrl(s);
            }
        };

        tata.test("toto");

        Predicate<String> tata2 = (it) -> URLUtil.isValidUrl(it);

        //tata2.test(image)


    }

}
