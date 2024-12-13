package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.ImportProduct;
import com.example.springfinalproject.model.request.ImportProductRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ImportProductRepository {
    @Select("SELECT * FROM import_product_tb ")
    @Results(
            id = "mapper",
            value = {
                    @Result(property = "importId", column = "import_id"),
                    @Result(property = "importDate", column = "import_date")
            }
    )
    List<ImportProduct> getAllImportProduct();

    @Select("""
    SELECT * FROM import_product_tb WHERE import_id = #{importId}""")
    @ResultMap("mapper")
    ImportProduct getImportByProductId(Integer importId);

    @Select("""
    SELECT * FROM product_tb WHERE product_id = #{productId}
""")
    Integer getProductToImport(Integer productId);


    @Select("UPDATE import_product_tb SET quantity = #{rq.quantity}, import_date = #{rq.importDate} " +
            "WHERE import_id = #{id} ")
    Integer updateImportProduct(@Param("rq") ImportProductRequest importProductRequest, Integer id);

    @Delete("DELETE FROM import_product_tb WHERE import_id = #{id} ")
    boolean deleteImportProduct(Integer id);


    @Select("INSERT INTO import_product_tb (quantity, import_date) " +
            "VALUES (#{rq.quantity}, #{rq.importDate}) " +
            "RETURNING import_id ")
    Integer addNewImportProduct(@Param("rq") ImportProductRequest importProductRequest);

    @Insert("""
        INSERT INTO import_product_detail_tb(import_id, product_id) VALUES (#{importId}, #{productId})
""")
    void saveImportProduct(Integer productId, Integer importId);

    @Select("INSERT INTO product_tb (product_qty) VALUES (#{rq.product_qty}) " +
            "RETURNING * ")
    void getProductQty(@Param("rq") ImportProductRequest importProductRequest);
}
