package com.zslin;

import com.zslin.basic.tools.ConfigTools;
import com.zslin.bus.wx.tools.ExchangeTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * Created by zsl on 2018/7/5.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("zsl")
public class NormalTest {

    @Autowired
    private ConfigTools configTools;

    @Autowired
    private ExchangeTools exchangeTools;

    @Test
    public void test01() {
        String result = exchangeTools.saveHeadImg(configTools.getUploadPath("headimg/",true)+ UUID.randomUUID().toString(), "http://thirdwx.qlogo.cn/mmopen/72VK6XRwaSbQloGXe4AuhWyTNjV4ibibAwRvCsulYGCrZNBssuZGwjUCD96j6QnPkoK5HAZfDWiaJZklaO5Gk9AKoqialSibu9MRe/132");
        String res = result.replace(configTools.getUploadPath(false), "\\");
        System.out.println(result);
        System.out.println(res);
    }
}
