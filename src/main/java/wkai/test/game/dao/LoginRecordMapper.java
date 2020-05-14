package wkai.test.game.dao;

import org.apache.ibatis.annotations.Mapper;
import wkai.test.game.entity.LoginRecord;

@Mapper
public interface LoginRecordMapper {
    int insert(LoginRecord record);

    int insertSelective(LoginRecord record);

}