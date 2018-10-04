package com.zslin.bus.basic.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.basic.model.Attachment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2018/9/12.
 */
public interface IAttachmentDao extends BaseRepository<Attachment, Integer>, JpaSpecificationExecutor<Attachment> {
}
