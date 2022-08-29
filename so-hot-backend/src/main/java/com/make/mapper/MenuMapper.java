package com.make.mapper;

import com.make.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author make
 * @since 2022-08-24
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(int userId);

}
