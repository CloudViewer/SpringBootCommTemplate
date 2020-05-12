package org.cv.websocket.service.impl;

import org.cv.websocket.entity.DbRoom;
import org.cv.websocket.mapper.DbRoomMapper;
import org.cv.websocket.service.DbRoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Ale on 2020/4/11
 */
@Service
public class DbRoomServiceImpl implements DbRoomService {

    @Resource
    private DbRoomMapper mapper;
    @Override
    public List<DbRoom> findByAll() {
        return mapper.findByAll();
    }
}
