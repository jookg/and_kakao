package com.hanbit.kakaotalk;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by hb1030-pc on 2017-09-29.
 */

public class Service {
    public static interface iPredicate{
        public void  execute();
    }
    public static interface iPost{
        public void excute(Object o);
    }
    public static interface iList{
        public ArrayList<?> excute();
    }
    public static interface iGet{
        public Object excute(Object o);
    }
    public static interface iUpdate{
        public void excute(Object o);
    }
    public static interface iDelete{
        public void excute(Object o);
    }
}
