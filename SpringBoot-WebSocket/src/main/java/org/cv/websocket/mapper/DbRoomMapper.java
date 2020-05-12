package org.cv.websocket.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.cv.websocket.entity.DbRoom;

import java.util.List;

/**
 * Created by Ale on 2020/4/11
 */
@Mapper
public interface DbRoomMapper {

    /**
     * 查询所有直播间
     * @return
     */
    @Select("select id,name,remark,coverurl,uid,type from dbroom")
    List<DbRoom> findByAll();
}
