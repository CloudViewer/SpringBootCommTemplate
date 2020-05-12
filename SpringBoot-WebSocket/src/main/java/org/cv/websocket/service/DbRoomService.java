package org.cv.websocket.service;

import org.cv.websocket.entity.DbRoom;

import java.util.List;

/**
 * Created by Ale on 2020/4/11
 */
public interface DbRoomService {

    List<DbRoom> findByAll();
}
