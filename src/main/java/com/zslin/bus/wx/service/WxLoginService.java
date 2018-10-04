package com.zslin.bus.wx.service;

import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.common.tools.RandomTools;
import com.zslin.bus.tools.JsonResult;
import com.zslin.bus.wx.dao.IWxLoginDao;
import com.zslin.bus.wx.model.WxLogin;
import com.zslin.bus.wx.tools.EventTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zsl on 2018/9/25.
 */
@Service
public class WxLoginService {

    @Autowired
    private IWxLoginDao wxLoginDao;

    @Autowired
    private EventTools eventTools;

    /**
     * 生成二维码
     * @param params
     * @return
     */
    public JsonResult buildQr(String params) {
        String random = "lo_"+ RandomTools.genTimeNo(5,5); //lo_开头表示是登陆用
        WxLogin wl = new WxLogin();
        wl.setCreateLong(System.currentTimeMillis()/1000); //秒
        wl.setToken(random);

        String qrStr = eventTools.getTempQr(random);
        System.out.println(qrStr);
        if(qrStr!=null && !"".equals(qrStr)) {
            wl.setTicket(JsonTools.getJsonParam(qrStr, "ticket"));
        }

        wxLoginDao.save(wl); //保存

        return JsonResult.succ(wl);
    }

    /**
     * 通过Token获取对象
     * @param params
     * @return
     */
    public JsonResult loadByToken(String params) {
        String token = JsonTools.getJsonParam(params, "token");
        WxLogin wl = wxLoginDao.findByToken(token);
        if(wl==null) {
            return JsonResult.success("二维码过期，请刷新后重试").set("flag", "0");
        }
        if(wl.getOpenid()==null || "".equals(wl.getOpenid())) {
            return JsonResult.success("请打开微信扫一扫").set("flag", "0");
        }
        //TODO 根据openid获取用户权限，并将用户信息set到下方Result中
        return JsonResult.success("登陆成功").set("flag", "1");
    }
}
