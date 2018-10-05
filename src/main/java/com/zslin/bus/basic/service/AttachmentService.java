package com.zslin.bus.basic.service;

import com.zslin.basic.tools.ConfigTools;
import com.zslin.bus.basic.dao.IAttachmentDao;
import com.zslin.bus.basic.model.Attachment;
import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by zsl on 2018/9/13.
 */
@Service
public class AttachmentService {

    @Autowired
    private ConfigTools configTools;

    @Autowired
    private IAttachmentDao attachmentDao;

    public JsonResult loadOne(String params) {
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            Attachment a = attachmentDao.findOne(id);
            return JsonResult.succ(a);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    /**
     *
     * @param params {id:0, courseId:1, fieldName:pptId}
     * @return
     */
    public JsonResult delete(String params) {
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
//            String fieldName = JsonTools.getJsonParam(params, "fieldName");
            Attachment a = attachmentDao.findOne(id);
            File file = new File(configTools.getUploadPath(false) + a.getUrl());
            if(file!=null && file.exists()) {
                file.delete();
            }
            attachmentDao.delete(a);
            return JsonResult.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("删除失败");
        }
    }

    /**
     *
     * @param params {ids: '1,0,'}
     * @return
     */
    public JsonResult deleteByIds(String params) {
        try {
            String ids = JsonTools.getJsonParam(params, "ids");
            for(String idStr : ids.split(",")) {
                if(idStr!=null && !"".equals(idStr)) {
                    Integer id = Integer.parseInt(idStr.trim());
                    Attachment a = attachmentDao.findOne(id);
                    File file = new File(configTools.getUploadPath(false) + a.getUrl());
                    if(file!=null && file.exists()) {
                        file.delete();
                    }
                    attachmentDao.delete(a);
                }
            }
            return JsonResult.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }
}
