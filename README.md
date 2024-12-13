![VirtualBiz](/Animation.gif)

> [Required]
> 1. [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) required for all installation methods
> 2. Install Postgres : ```host: 128.199.79.216  username: postgres  pass: 1234 ``` database: ```postgres```
> 3. Install Postgis for local database :
  
 +Mac
     ```$ brew install postgis```

 +Linux
     ```$ sudo apt-get install postgis```

 The Run command :
     ```CREATE EXTENSION postgis;```

### Getting started
## API endpoint


## 1. Auth-controller
#### a. Register to create account:
> method: Post[/api/v1/auths/register](http://128.199.79.216:8080/swagger-ui/index.html#/auths/register)
#### b. Login to view the product and shop
> method: Post[/api/v1/auths/login](http://128.199.79.216:8080/swagger-ui/index.html#/auths/login)
#### c. Verify email by OTPs code:
> method: Put[/api/v1/auths/verify](http://128.199.79.216:8080/swagger-ui/index.html#/auths/verify)
#### d. Resend OTP code to email:
> method: Post[/api/v1/auths/resend](http://128.199.79.216:8080/swagger-ui/index.html#/auths/resend)
#### e: Reset new password after login
> method: Put[/api/v1/auths/reset](http://128.199.79.216:8080/swagger-ui/index.html#/auths/reset)
#### f: Get User role information:
> method: Get[/api/v1/auths](http://128.199.79.216:8080/swagger-ui/index.html#/auths)
#### g: Get user infomation by id:
> method: Get[/api/v1/auths](http://128.199.79.216:8080/swagger-ui/index.html#/auths)
#### h: Reset password when don't know password when login:
> method: Put[/api/v1/auths/forget](http://128.199.79.216:8080/swagger-ui/index.html#/auths/forget)

## 2. user-controller
#### a. Get user information
> method: Get[/api/v1/user](http://128.199.79.216:8080/swagger-ui/index.html#/user)
#### b. Customer Edit profile Infomation
> method: Put[/api/v1/user/edit](http://128.199.79.216:8080/swagger-ui/index.html#/user/edit)

## 3. shop-controller
#### a. Create shop as seller or service provider
> method: Post[/api/v1/shop/create-shop](http://128.199.79.216:8080/swagger-ui/index.html#/shop/create-shop)
#### b. Get all shop for customer page
> method: Get[/api/v1/shop](http://128.199.79.216:8080/swagger-ui/index.html#/shop)
#### c. shop upload shop cover
> method: Put[/api/v1/shop](http://128.199.79.216:8080/swagger-ui/index.html#/shop)
#### d. Get shop By id to view detail for customer
> method: Get[/api/v1/shop/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/shop)
#### e. Delete shop for shop owner
> method: Put[/api/v1/shop/delete-shop](http://128.199.79.216:8080/swagger-ui/index.html#/shop/delete-shop)
#### f. Activate shop for shop owner
> method: Put[/api/v1/shop/activate-shop](http://128.199.79.216:8080/swagger-ui/index.html#/shop/activate-shop)
#### g. Get information shop by shop owner
> method: Get[/api/v1/shop/currentShop](http://128.199.79.216:8080/swagger-ui/index.html#/shop/currentShop)
#### h. Get feedback for seller role on dashboard
> method: Get[/api/v1/shop/feedback/dashboard](http://128.199.79.216:8080/swagger-ui/index.html#/shop/feedback/dashboard)
#### i. Get shop name when customer search
> method: Get[/api/v1/shop/name/{name}](http://128.199.79.216:8080/swagger-ui/index.html#/shop/name)
#### j. Get order on shop seller
> method: Get[/api/v1/shop/orders](http://128.199.79.216:8080/swagger-ui/index.html#/shop/orders)
#### k. Get all popular seller shop
> method: Get[/api/v1/shop/popular/product/shop](http://128.199.79.216:8080/swagger-ui/index.html#/shop/popular/product/shop)
#### l. Get all popular service shop
> method: Get[/api/v1/shop/popular/service/shop](http://128.199.79.216:8080/swagger-ui/index.html#/shop/popular/service/shop)
#### m. Get seller shop rating
> method: Get[/api/v1/shop/rating/product/shop/{shopId}](http://128.199.79.216:8080/swagger-ui/index.html#/shop/rating/product/shop)
#### n. Get service provider shop rating
> method: Get[/api/v1/shop/rating/service/shop/{shopId}](http://128.199.79.216:8080/swagger-ui/index.html#/shop/rating/service/shop)
#### o. Get service dashboard overview
> method: Get[/api/v1/shop/service/provider/overview](http://128.199.79.216:8080/swagger-ui/index.html#/shop/service/provider/overview)
#### p. Update shop post day off
> method: Put[/api/v1/shop/shop/day-off](http://128.199.79.216:8080/swagger-ui/index.html#/shop/shop/day-off)
#### q. Get seller shop overview dashboard
> method: Get[/api/v1/shop/total/dashboard](http://128.199.79.216:8080/swagger-ui/index.html#/shop/total/dashboard)
#### r. Update shop location
> method: Put[/api/v1/shop/update-location](http://128.199.79.216:8080/swagger-ui/index.html#/shop/update-location)
#### s. Update shop description
> method: Put[/api/v1/shop/update-shop-description](http://128.199.79.216:8080/swagger-ui/index.html#/shop/update-shop-description)
#### t. Update shop Infomation
> method: Put[/api/v1/shop/update-shop-info](http://128.199.79.216:8080/swagger-ui/index.html#/shop/update-shop-info)
#### u. Update shop profile
> method: Put[/api/v1/shop/upload-profile](http://128.199.79.216:8080/swagger-ui/index.html#/shop/upload-profile)

## 4. book-controller
#### a. Get all book in action for customer
> method: Get[/api/v1/book](http://128.199.79.216:8080/swagger-ui/index.html#/book)
#### b. Post customer book service
> method: Post[/api/v1/book](http://128.199.79.216:8080/swagger-ui/index.html#/book)
#### c. Get view book by id for customer
> method: Get[/api/v1/book/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/book)
#### d. Update customer cancel book
> method: Put[/api/v1/book/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/book)
#### e. Get service provider shop get accept appointment
> method: Get[/api/v1/book/accept](http://128.199.79.216:8080/swagger-ui/index.html#/book/accept)
#### f. Update accept booking on service provider shop
> method: Put[/api/v1/book/accept/{bookId}](http://128.199.79.216:8080/swagger-ui/index.html#/book/accept)
#### g. Get book request by service id
> method: Get[/api/v1/book/all/request/serviceId/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/book/all/request/serviceId)
#### h. Update book deny
> method: Put[/api/v1/book/deny/{bookId}](http://128.199.79.216:8080/swagger-ui/index.html#/book/deny)
#### i. Get all done process booking service provider shop
> method: Get[/api/v1/book/done](http://128.199.79.216:8080/swagger-ui/index.html#/book/done)
#### j. Get all request process booking service provider shop
> method: Get[/api/v1/book/request](http://128.199.79.216:8080/swagger-ui/index.html#/book/request)

## 5. bookmark-controller
#### a. Post add any bookmark for customer
> method: Post[/api/v1/bookmark](http://128.199.79.216:8080/swagger-ui/index.html#/bookmark)
#### b. Get all bookmark for customer
> method: Get[/api/v1/bookmark/allBookmarks](http://128.199.79.216:8080/swagger-ui/index.html#/bookmark/allBookmarks)
#### c. Get all event bookmark for customer
> method: Get[/api/v1/bookmark/allEventBookmarks](http://128.199.79.216:8080/swagger-ui/index.html#/bookmark/allEventBookmarks)
#### d. Get all product bookmark for customer
> method: Get[/api/v1/bookmark/allProductBookmarks](http://128.199.79.216:8080/swagger-ui/index.html#/bookmark/allProductBookmarks)
#### e. Get all service bookmark for customer
> method: Get[/api/v1/bookmark/allServiceBookmarks](http://128.199.79.216:8080/swagger-ui/index.html#/bookmark/allServiceBookmarks)
#### f. Post add bookmark event
> method: Post[/api/v1/bookmark/event](http://128.199.79.216:8080/swagger-ui/index.html#/bookmark/event)
#### g. Post add bookmark on service
> method: Post[/api/v1/bookmark/service](http://128.199.79.216:8080/swagger-ui/index.html#/bookmark/service)

## 6. import-product-controller
#### a. Get import product for seller shop
> method: Get[/api/v1/importProduct](http://128.199.79.216:8080/swagger-ui/index.html#/importProduct)
#### b. Post import product for seller shop
> method: Post[/api/v1/importProduct](http://128.199.79.216:8080/swagger-ui/index.html#/importProduct)
#### c. Get import product by id for seller shop
> method: Get[/api/v1/importProduct/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/importProduct/{id})
#### d. Update add import product and update seller shop
> method: Put[/api/v1/importProduct/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/importProduct/{id})
#### e. Delete import product for seller shop
> method: Delete[/api/v1/importProduct/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/importProduct/{id})

## 7. event-controller
#### a. Get all event for customer
> method: Get[/api/v1/event](http://128.199.79.216:8080/swagger-ui/index.html#/event)
#### b. Post comment on event for customer
> method: Post[/api/v1/event/comment](http://128.199.79.216:8080/swagger-ui/index.html#/event/comment)
#### c. Post create event for shop
> method: Post[/api/v1/event/create](http://128.199.79.216:8080/swagger-ui/index.html#/event/create)
#### d. Delete event for shop
> method: Delete[/api/v1/event/delete/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/event/delete)
#### e. Get event by id for customer
> method: Get[/api/v1/event/get/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/event/get)
#### f. Get event only order event for shop
> method: Get[/api/v1/event/get/older-event](http://128.199.79.216:8080/swagger-ui/index.html#/event/get/older-event)
#### g. Get event only this week for shop
> method: Get[/api/v1/event/get/this-week](http://128.199.79.216:8080/swagger-ui/index.html#/event/get/this-week)
#### h. Get Event current shop
> method: Get[/api/v1/event/getCurrentShop](http://128.199.79.216:8080/swagger-ui/index.html#/event/getCurrentShop)
#### i. Get event that nearby user for customer
> method: Get[/api/v1/event/nearbyEvent](http://128.199.79.216:8080/swagger-ui/index.html#/event/nearbyEvent)
#### j. Get event about product for cutomer
> method: Get[/api/v1/event/productEvent](http://128.199.79.216:8080/swagger-ui/index.html#/event/productEvent)
#### l. Get event about service for cutomer
> method: Get[/api/v1/event/serviceEvent](http://128.199.79.216:8080/swagger-ui/index.html#/event/serviceEvent)
#### m. Get all event for seller shop
> method: Get[/api/v1/event/sellerType](http://128.199.79.216:8080/swagger-ui/index.html#/event/sellerType)
#### n. Get all event for service shop
> method: Get[/api/v1/event/serviceType](http://128.199.79.216:8080/swagger-ui/index.html#/event/serviceType)
#### o. Get all event by shop Id
> method: Get[/api/v1/event/shop/{shopId}](http://128.199.79.216:8080/swagger-ui/index.html#/event/shop)
#### p. Update event by id for shop
> method: Put[/api/v1/event/update/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/event/update)

## 8. location-controller
#### a. Get all shop that nearby for customer
> method: Get[/api/v1/findNearestLocation/findNearestLocation](http://128.199.79.216:8080/swagger-ui/index.html#/findNearestLocation/findNearestLocation)

## 9. order-controller
#### a. Get all order that is waiting for seller shop
> method: Get[/api/v1/order](http://128.199.79.216:8080/swagger-ui/index.html#/order)
#### b. Post add order for customer
> method: Post[/api/v1/order](http://128.199.79.216:8080/swagger-ui/index.html#/order)
#### c. Get order by id to view detail for customer and seller shop
> method: Get[/api/v1/order/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/order)
#### d. Get all order in action
> method: Get[/api/v1/order/action](http://128.199.79.216:8080/swagger-ui/index.html#/order/action)
#### e. Put cancel order for custor
> method: Get[/api/v1/order/cancel/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/order/cancel)
#### f. Get all history order and book for customer
> method: Get[/api/v1/order/customer/history](http://128.199.79.216:8080/swagger-ui/index.html#/order/customer/history)
#### g. Update accept order for seller shop
> method: Put[/api/v1/order/order/delivery/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/order/order/delivery)
#### h. Update order deny for seller shop
> method: Put[/api/v1/order/order/deny/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/order/order/deny)
#### i. et all history for seller shop
> method: Get[/api/v1/order/seller/history](http://128.199.79.216:8080/swagger-ui/index.html#/order/seller/history)

## 10. product-controller
#### a. Get all product for customer
> method: Get[/api/v1/seller/products](http://128.199.79.216:8080/swagger-ui/index.html#/seller/products)
#### b. Post product for seller shop
> method: Post[/api/v1/seller/products](http://128.199.79.216:8080/swagger-ui/index.html#/seller/products)
#### c. Update product for seller shop
> method: Put[/api/v1/seller/products/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/seller/products)
#### d. Delete product for seller shop
> method: Delete[/api/v1/seller/products/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/seller/products)
#### e. Get product by barcode for seller shop
> method: Get[/api/v1/seller/products/barcode/{barcode}](http://128.199.79.216:8080/swagger-ui/index.html#/seller/products/barcode)
#### f. Get product by current shop
> method: Get[/api/v1/seller/products/currentShop](http://128.199.79.216:8080/swagger-ui/index.html#/seller/products/currentShop)
#### g. Get product by category id for seller shop
> method: Get[/api/v1/seller/products/getByCategoryId/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/seller/products/getByCategoryId)
#### h. Get product by category name for seller shop
> method: Get[/api/v1/seller/products/getByCategoryName/{name}](http://128.199.79.216:8080/swagger-ui/index.html#/seller/products/getByCategoryName)
#### i. Get product by id for customer
> method: Get[/api/v1/seller/products/id/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/seller/products/id)
#### j. Post product by barcode to restock for seller shop
> method: Post[/api/v1/seller/products/import/{barcode}](http://128.199.79.216:8080/swagger-ui/index.html#/seller/products/import)
#### k. Get all product by name for customer when search
> method: Get[/api/v1/seller/products/name/{name}](http://128.199.79.216:8080/swagger-ui/index.html#/seller/products/name)
#### l. Get product by name in shop
> method: Get[/api/v1/seller/products/nameInShop/{name}](http://128.199.79.216:8080/swagger-ui/index.html#/seller/products/nameInShop)
#### m. Get all product by category id for customer
> method: Get[/api/v1/seller/products/product-by-categoryId/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/seller/products/product-by-categoryId)
#### n. Get all product by id in shop for seller shop
> method: Get[/api/v1/seller/products/productInShop/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/seller/products/productInShop)
#### o. Get product by shop Id for customer
> method: Get[/api/v1/seller/products/shop/{shopId}](http://128.199.79.216:8080/swagger-ui/index.html#/seller/products/shop)

## 11. promotion-controller
#### a. Get shop that have promotion for customer
> method: Get[/api/v1/promotion](http://128.199.79.216:8080/swagger-ui/index.html#/promotion)
#### b. Post promotion for seller shop
> method: Post[/api/v1/promotion](http://128.199.79.216:8080/swagger-ui/index.html#/promotion)
#### c. Get promotion by id
> method: Get[/api/v1/promotion/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/promotion)
#### d. Update promotion on seller shop
> method: Put[/api/v1/promotion/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/promotion)
#### e. Delete promotion on seller shop
> method: Delete[/api/v1/promotion/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/promotion/{id})
#### f. Get oldest promotion on seller shop
> method: Get[/api/v1/promotion/get/oldest-promotion](http://128.199.79.216:8080/swagger-ui/index.html#/promotion/get/oldest-promotion)
#### g. Get this week promtion on seller shop
> method: Get[/api/v1/promotion/get/thisweek-promotion](http://128.199.79.216:8080/swagger-ui/index.html#/promotion/get/thisweek-promotion)
#### h. Get all promotion by shop id for customer and seller shop
> method: Get[/api/v1/promotion/shop-id/{shopId}](http://128.199.79.216:8080/swagger-ui/index.html#/promotion/shop-id)

## 12. rate-controller
#### a. Post rate and feedback on book for customer
> method: Post[/api/v1/rate-feedback/booking/{bookId}](http://128.199.79.216:8080/swagger-ui/index.html#/rate-feedback/booking)
#### b. Post rate and feedback on order for customer
> method: Post[/api/v1/rate-feedback/order/{orderId}](http://128.199.79.216:8080/swagger-ui/index.html#/rate-feedback/order)
#### c. Get all order feeback by current shop on seller shop
> method: Get[/api/v1/rate-feedback/seller/currentShop](http://128.199.79.216:8080/swagger-ui/index.html#/rate-feedback/seller/currentShop)
#### d. Get view rating and feedback by id on seller shop
> method: Get[/api/v1/rate-feedback/seller/feedbackId/{feedbackId}](http://128.199.79.216:8080/swagger-ui/index.html#/rate-feedback/seller/feedbackId)
#### e. Get all booking feedback by current shop on service provider
> method: Get[/api/v1/rate-feedback/service/currentShop](http://128.199.79.216:8080/swagger-ui/index.html#/rate-feedback/service/currentShop)
#### f. Get view rating and feedback by id on service provider shop
> method: Get[/api/v1/rate-feedback/service/feedbackId/{feedbackId}](http://128.199.79.216:8080/swagger-ui/index.html#/rate-feedback/service/feedbackId)
#### g. Get feedback by shop id for customer
> method: Get[/api/v1/rate-feedback/shop/{shopId}](http://128.199.79.216:8080/swagger-ui/index.html#/rate-feedback/shop)

## 13. service-controller
#### a. Get all service on customer
> method: Get[/api/v1/service](http://128.199.79.216:8080/swagger-ui/index.html#/service)
#### b. Post new service in service provider shop
> method: Post[/api/v1/service](http://128.199.79.216:8080/swagger-ui/index.html#/service)
#### c. Get service by id to view detail on customer
> method: Get[/api/v1/service/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/service)
#### d. Update service by id on service provider
> method: Put[/api/v1/service/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/service)
#### e. Delete service by id change is_active = true to false on service provider
> method: Delete[/api/v1/service/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/service)
#### f. Get service by id on service provider
> method: Get[/api/v1/service/idInShop/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/service/idInShop)
#### g. Get service by name when user search for customer
> method: Get[/api/v1/service/name/{name}](http://128.199.79.216:8080/swagger-ui/index.html#/service/name)
#### h. Get service in shop by name on service provider
> method: Get[/api/v1/service/nameInShop/{name}](http://128.199.79.216:8080/swagger-ui/index.html#/service/nameInShop)
#### i. Get all service by category id on customer
> method: Get[/api/v1/service/product-by-categoryId/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/service/product-by-categoryId)
#### j. Get service by category id on service provider
> method: Get[/api/v1/service/ServiceByCategoryId/{id}](http://128.199.79.216:8080/swagger-ui/index.html#/service/ServiceByCategoryId)
#### k. Get all service by shop id when view shop on customer
> method: Get[/api/v1/service/serviceShopId/{shopId}](http://128.199.79.216:8080/swagger-ui/index.html#/service/serviceShopId)
#### l. Get all service in service provider shop
> method: Get[/api/v1/service/shopService](http://128.199.79.216:8080/swagger-ui/index.html#/service/shopService)

## 14. service-promotion-controller
#### a. Get all service promotion on customer
> method: Get[/api/v1/serviceprovider](http://128.199.79.216:8080/swagger-ui/index.html#/serviceprovider)
#### b. Post promotion on service on service provider
> method: Post[/api/v1/serviceprovider](http://128.199.79.216:8080/swagger-ui/index.html#/serviceprovider)











