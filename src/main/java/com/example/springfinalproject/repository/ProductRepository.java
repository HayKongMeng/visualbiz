package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.Category;
import com.example.springfinalproject.model.Entity.ImportProductWithId;
import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.Entity.ProductForSeller;
import com.example.springfinalproject.model.request.ImportProductRequest;
import com.example.springfinalproject.model.request.ProductRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ProductRepository {

    //get all product
    @Select("""
            SELECT * from product_tb LIMIT #{size} OFFSET ${size}*(#{page}-1)
            """)
    @Results(
            id = "mapper",
            value = {
                    @Result(property = "productId",column = "product_id"),
                    @Result(property = "productName",column = "product_name"),
                    @Result(property = "unitPrice",column = "unit_price"),
                    @Result(property = "productDate",column = "product_date"),
                    @Result(property = "productDescription",column = "product_description"),
                    @Result(property = "productQty",column = "product_qty"),
                    @Result(property = "productImg",column = "product_img"),
                    @Result(property = "expireDate",column = "expired_date"),
                    @Result(property = "isActive",column = "is_active"),
                    @Result(property = "barCode",column = "barcode"),
                    @Result(property = "category",column = "category_id",
                    one = @One(select = "com.example.springfinalproject.repository.CategoryRepository.getCategoryById"))
            }
    )
    List<Product> getAllProduct(Integer page, Integer size);



    //get product by id
    @Select("""
    SELECT p.* from product_tb p
    inner join product_shop_tb ps on p.product_id = ps.product_id
    where p.product_id = #{productId} AND  shop_id = #{shopId};
    """)
    @ResultMap("mapper")
    Product getProductById(Integer productId,Integer shopId);

    //get product by name
    @Select("""
Select * from product_tb p
                  INNER JOIN product_shop_tb pp on p.product_id = pp.product_id
where product_name ILIKE #{productName} || '%' and shop_id = #{shopId} LIMIT #{size} OFFSET ${size}*(#{page}-1)
""")
    @ResultMap("mapper")
    List<Product> getProductByName(String productName, Integer shopId,Integer page,Integer size);

    //get product by category
    @Select("""
SELECT * from product_tb p
         INNER JOIN product_shop_tb ps on p.product_id = ps.product_id
         where category_id = #{categoryId} and shop_id = #{shopId} LIMIT #{size} OFFSET ${size}*(#{page}-1)
""")
    @ResultMap("mapper")
    List<Product> getProductByCategoryId(Integer categoryId,Integer shopId,Integer page,Integer size);

    //get product by category name
    @Select("""
         select * from product_tb p
                  inner join category_tb c on p.category_id = c.category_id
                  INNER JOIN product_shop_tb ps on p.product_id = ps.product_id
                  where category_name ILIKE #{categoryName} || '%' AND shop_id = #{shopIds} LIMIT #{size} OFFSET ${size}*(#{page}-1)
""")
    @ResultMap("mapper")
    List<Product> getProductByCategoryName(String categoryName,Integer shopIds,Integer page,Integer size);

    //post product
//    @Select("""
//            INSERT INTO product_tb(product_name, unit_price, product_description, product_qty, product_img, expired_date,barcode,
//            category_id)
//            values (#{rq.productName},#{rq.unitPrice},#{rq.productDescription},#{rq.productQty},#{rq.productImg},#{rq.expireDate},#{rq.barCode},#{rq.categoryId})
//            RETURNING product_id
//            """)
//    Integer createProduct(@Param("rq") ProductRequest productRequest);

    @Select("""
            INSERT INTO product_tb (product_name, unit_price, product_description, product_qty, product_img, category_id, barcode, expired_date)
            values(#{rq.productName},#{rq.unitPrice},#{rq.productDescription},#{rq.productQty},#{rq.productImg},#{rq.categoryId},#{rq.barCode}, #{rq.expireDate})
            returning product_id
            """)
    Integer createProduct(@Param("rq") ProductRequest productRequest);

    @Select("""
    SELECT shop_id from shop_tb where user_id = #{userId}
    """)
    Integer getShopByUserId(Integer userId);

    @Insert("""
        INSERT INTO product_shop_tb(shop_id,product_id) VALUES (#{shopId},#{productId})
""")
    void saveProductShop(Integer productId, Integer shopId);


    //update product by id
    @Select("""
    UPDATE product_tb SET product_name = #{rq.productName}, unit_price = #{rq.unitPrice},
                          product_description = #{rq.productDescription}, product_qty = #{rq.productQty},
                          product_img = #{rq.productImg}, expired_date = #{rq.expireDate}, category_id = #{rq.categoryId}
                      WHERE product_id = #{productId} RETURNING product_id
""")
    Integer updateProductById(@Param("rq") ProductRequest productRequest, Integer productId);

    //delete product by id
    @Update("""
    UPDATE product_tb SET is_active = false
                      WHERE product_id = #{productId}
""")
    void deleteProduct(Integer productId);


    //get all product by shop id
    @Select("""
    SELECT p.* from product_tb p
    inner join product_shop_tb ps on p.product_id = ps.product_id
    where  shop_id =#{shopId}
    """)
    @ResultMap("mapper")
    List<Product> getProductByShopId(Integer shopId);

    @Select("""
SELECT p.* from product_tb p
    inner join product_shop_tb ps on p.product_id = ps.product_id
where  shop_id =#{shopId} LIMIT #{size} OFFSET ${size}*(#{page}-1)
""")
    @ResultMap("mapper")
    List<Product> getProductByShopIdWithPageSize(Integer shopId,Integer page,Integer size);

    //get all product by category id customer
    @Select("""
SELECT * from product_tb p
INNER JOIN product_shop_tb ps on p.product_id = ps.product_id
where category_id = #{categoryId} LIMIT #{size} OFFSET ${size}*(#{page}-1)
""")
    @ResultMap("mapper")
    List<Product> getProductByCategoryIdForCustomer(Integer categoryId, Integer page, Integer size);

    //get product by name for customer
    @Select("""
SELECT * from product_tb where product_tb.product_name ILIKE #{name} || '%'
""")
    @ResultMap("mapper")
    List<Product> getProductByNameForCustomer(String name);


    //get product by barcode
    @Select("""
SELECT * from product_tb s
         INNER JOIN product_shop_tb ps on s.product_id = ps.product_id
         where barcode = #{barcode} and shop_id = #{shopId}
""")
    @ResultMap("mapper")
    Product getProductByBarcode(String barcode, Integer shopId);

    @Select("""
INSERT INTO import_product_tb(quantity, import_date) VALUES ((#{rq.productQty}),#{rq.importDate}) RETURNING import_id
""")
    Integer importProductByBarcode(@Param("rq") ImportProductRequest importProductRequest, String barcode);

    @Insert("""
INSERT INTO import_product_detail_tb(product_id,import_id) VALUES (#{productId}, #{importId})
""")
    void saveProductImportDetail(Integer productId, Integer importId);

    @Select("""
UPDATE product_tb set product_qty = #{qty} where barcode = #{barcode} RETURNING * ;
""")
    Product updateProductByBarcode( Integer qty, String barcode);

@Select("""
SELECT import_id from import_product_tb where quantity = 10
order by import_date desc limit 1;
""")
    Integer getImportId(Integer qty);
@
    Select("""
    INSERT INTO import_product_tb VALUES (default, #{ip.productQty},#{ip.importDate}) RETURNING *;
""")
@Result( property = "importId" , column = "import_id")
@Result( property = "productQty" , column = "quantity")
@Result( property = "importDate" , column = "import_date")
ImportProductWithId saveImportProuct(@Param("ip") ImportProductRequest importProductRequest);

//get product by id for customer
    @Select("""
SELECT * from product_tb where product_id = #{id}
""")
    @ResultMap("mapper")
    Product getProductByIdForCustomer(Integer id);


    @Select("""
    SELECT distinct pt.product_id,pt.product_name,pt.unit_price,prt.percentage, pt.product_description,
            pt.product_qty,pt.product_img,pt.expired_date,pt.is_active,pt.barcode,pt.category_id
    FROM product_tb pt
    INNER JOIN product_promotion_tb ppt ON pt.product_id = ppt.product_id
    INNER JOIN promotion_tb prt ON ppt.promotion_id = prt.promotion_id
    WHERE prt.shop_id = #{shopId};
 """)
    @Results(
            id = "productMap",
            value = {
                    @Result(property = "productId",column = "product_id"),
                    @Result(property = "productName",column = "product_name"),
                    @Result(property = "unitPrice",column = "unit_price"),
                    @Result(property = "discount",column = "percentage"),
                    @Result(property = "productDescription",column = "product_description"),
                    @Result(property = "productQty",column = "product_qty"),
                    @Result(property = "productImg",column = "product_img"),
                    @Result(property = "expireDate",column = "expired_date"),
                    @Result(property = "isActive",column = "is_active"),
                    @Result(property = "category",column = "category_id",
                            one = @One(select = "com.example.springfinalproject.repository.CategoryRepository.getCategoryById"))
            }
    )
    List<ProductForSeller> getAllProductForSeller(Integer shopId);



    @Select("""
SELECT p.* from product_tb p
                    inner join product_shop_tb ps on p.product_id = ps.product_id
where  shop_id = #{shopId};
""")
    @ResultMap("mapper")
    List<Product> getAllProductByShopId( Integer shopId);



    @Select("""
            SELECT is_active from product_tb where product_id = #{productId}
            """)
    boolean getProductStatus(Integer productId);

    @Update("""
            UPDATE product_tb SET is_active = true
                      WHERE product_id = #{productId}
            """)
    boolean updateProductToTrue(Integer productId);
}
