package com.example.springfinalproject.repository;


import com.example.springfinalproject.model.Entity.ShopType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShopTypeRepository {
    @Select("SELECT * FROM shop_type_tb")
    @Results(
            id = "mapper",
            value = {
                    @Result(property = "shopTypeId", column = "shop_type_id"),
                    @Result(property = "shopType", column = "shop_type")
            }
    )
    List<ShopType> findAllShopType();

    @Select("SELECT * FROM shop_type_tb WHERE shop_type_id = #{shopTypeId}")
    @ResultMap("mapper")
    ShopType findShopTypeById(int shopTypeId);
}
