package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.Category;
import com.example.springfinalproject.model.request.CategoryRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryRepository {
    //get all categories
    @Select("""
Select * from category_tb LIMIT #{size} OFFSET ${size} * (#{page}-1)
""")
    @Results(
            id = "mapper",
            value = {
                    @Result(property = "categoryId",column = "category_id"),
                    @Result(property = "categoryName",column = "category_name")
            }
    )
    List<Category> getAllCategories(Integer page,Integer size);

    // get category by id
    @Select("""
SELECT * from category_tb where category_id = #{categoryId}
""")
    @ResultMap("mapper")
    Category getCategoryById(Integer categoryId);

    //create category
    @Select("""
INSERT INTO category_tb(category_name) VALUES (#{rq.categoryName}) RETURNING category_id
""")
    Integer createCategory(@Param("rq") CategoryRequest categoryRequest);

    //delete category by id
    @Delete("""
Delete from category_tb where category_id = #{categoryId}
""")
    @ResultMap("mapper")
    boolean deleteCategoryById(Integer categoryId);

    //get category by name
    @Select("""
SELECT * from category_tb where category_name = #{categoryName}
""")
    @ResultMap("mapper")
    Category getCategoryByName(String categoryName);

    //update category by id
    @Update("""
UPDATE category_tb SET category_name = #{rq.categoryName} WHERE category_id = #{categoryId}
""")
    Integer updateCategoryById(@Param("rq") CategoryRequest categoryRequest, Integer categoryId);

    //get all categories by shop id
    @Select("""
SELECT DISTINCT c.* from  category_tb c
INNER JOIN product_tb p on c.category_id = p.category_id
INNER JOIN product_shop_tb ps on p.product_id = ps.product_id
where shop_id = #{shopId}
""")
    @ResultMap("mapper")
    List<Category> getAllCategoriesInShop(Integer shopId);

    @Select("""
    SELECT  ctb.category_id, ctb.category_name FROM category_tb ctb INNER JOIN public.product_tb pt on ctb.category_id = pt.category_id
    WHERE pt.product_id = #{productId}
""")
    @Result(property = "categoryId" , column = "category_id")
    @Result(property = "categoryName", column = "category_name")
    Category getCategoryByProductId(Integer productId);

    @Select("""
    SELECT  ctb.category_id, ctb.category_name FROM category_tb ctb
    INNER JOIN service_tb st ON ctb.category_id = st.category_id
    WHERE st.service_id =#{serviceId};
 """)
    @Result(property = "categoryId" , column = "category_id")
    @Result(property = "categoryName", column = "category_name")
    Category getCategoryByServiceId(Integer serviceId);

    @Select("""
    SELECT * FROM category_tb WHERE category_id = #{categoryId};
""")
    Category category(Integer categoryId);


}
