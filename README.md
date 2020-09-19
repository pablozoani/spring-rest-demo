# spring-rest-demo
Simple REST API

[![CircleCI](https://circleci.com/gh/pablozoani/spring-rest-demo.svg?style=svg)](https://circleci.com/gh/pablozoani/spring-rest-demo)

## File Tree
```
spring-rest-demo
 ├─> .circleci
 │   └── config.yml
 ├── .gitignore
 ├── README.md
 ├── pom.xml
 └─> src
     ├─> main
     │   ├─> java
     │   │   └─> com
     │   │       └─> pablozoani
     │   │           ├── SpringRestDemoApplication.java
     │   │           ├─> api
     │   │           │   └─> v1
     │   │           │       ├─> mapper
     │   │           │       │   ├── CategoryMapper.java
     │   │           │       │   ├── CustomerMapper.java
     │   │           │       │   ├── ProductMapper.java
     │   │           │       │   ├── ProductPhotoMapper.java
     │   │           │       │   └── VendorMapper.java
     │   │           │       └─> model
     │   │           │           ├── CategoryDTO.java
     │   │           │           ├── CustomerDTO.java
     │   │           │           ├── ProductDTO.java
     │   │           │           ├── ProductPhotoDTO.java
     │   │           │           └── VendorDTO.java
     │   │           ├─> bootstrap
     │   │           │   └── Bootstrap.java
     │   │           ├─> controller
     │   │           │   ├── CategoryController.java
     │   │           │   ├── CustomerController.java
     │   │           │   ├── ExceptionHandlerController.java
     │   │           │   ├── ProductController.java
     │   │           │   └── VendorController.java
     │   │           ├─> domain
     │   │           │   ├── Category.java
     │   │           │   ├── Customer.java
     │   │           │   ├── Product.java
     │   │           │   ├── ProductPhoto.java
     │   │           │   └── Vendor.java
     │   │           ├─> exception
     │   │           │   └── ResourceNotFoundException.java
     │   │           ├─> repository
     │   │           │   ├── CategoryRepository.java
     │   │           │   ├── CustomerRepository.java
     │   │           │   ├── ProductPhotoRepository.java
     │   │           │   ├── ProductRepository.java
     │   │           │   └── VendorRepository.java
     │   │           └─> service
     │   │               ├── CategoryService.java
     │   │               ├── CategoryServiceImpl.java
     │   │               ├── CustomerService.java
     │   │               ├── CustomerServiceImpl.java
     │   │               ├── ProductPhotoService.java
     │   │               ├── ProductPhotoServiceImpl.java
     │   │               ├── ProductService.java
     │   │               ├── ProductServiceImpl.java
     │   │               ├── VendorService.java
     │   │               └── VendorServiceImpl.java
     │   └─> resources
     │       ├── application-default.yml
     │       ├── application.yml
     │       └─> bootstrap
     │           └─> images
     │               ├── almonds.jpg
     │               ├── apple.jpg
     │               ├── dried_mix.jpg
     │               ├── oranges.jpg
     │               ├── pineapple.jpg
     │               └── strawberry.jpg
     └─> test
         ├─> java
         │   └─> com
         │       └─> pablozoani
         │           ├─> api
         │           │   └─> v1
         │           │       └─> mapper
         │           │           ├── CategoryMapperTest.java
         │           │           ├── CustomerMapperTest.java
         │           │           ├── ProductMapperTest.java
         │           │           ├── ProductPhotoMapperTest.java
         │           │           └── VendorMapperTest.java
         │           ├─> controller
         │           │   ├── CategoryControllerTest.java
         │           │   ├── CustomerControllerTest.java
         │           │   ├── ProductControllerTest.java
         │           │   └── VendorControllerTest.java
         │           └─> service
         │               ├── CategoryServiceImplTest.java
         │               ├── CustomerServiceImplTest.java
         │               ├── ProductPhotoServiceImplTest.java
         │               ├── ProductServiceImplTest.java
         │               └── VendorServiceImplTest.java
         └─> resources
```