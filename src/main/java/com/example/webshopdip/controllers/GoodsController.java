package com.example.webshopdip.controllers;

import com.example.webshopdip.dtos.*;
import com.example.webshopdip.entities.GoodsEntity;
import com.example.webshopdip.entities.PhotosGoodsEntity;
import com.example.webshopdip.entities.PropertiesGoodsEntity;
import com.example.webshopdip.entities.PropertiesNameGoodsEntity;
import com.example.webshopdip.exceptions.GoodsAlreadyExistException;
import com.example.webshopdip.exceptions.GoodsNotFoundException;
import com.example.webshopdip.repositories.GoodsRepository;
import com.example.webshopdip.repositories.PropertiesGoodsRepository;
import com.example.webshopdip.services.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/goods")
@CrossOrigin
public class GoodsController {
    @Autowired
    private final GoodsService goodsService;
    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private PhotosGoodsService photosGoodsService;
    @Autowired
    private EvaluationsGoodService evaluationsGoodService;
    @Autowired
    private GoodsInvoicesService goodsInvoicesService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @PostMapping
    public ResponseEntity createGood(@RequestBody GoodsDTO goodsDTO) {
        if (goodsDTO.getName().equals(""))
            return ResponseEntity.badRequest().body("Значення назви порожнє");
        else if (goodsDTO.getShort_discription().equals(""))
            return ResponseEntity.badRequest().body("Значення короткого опису порожнє");
        else if (goodsDTO.getSubcategoryId() == null || goodsDTO.getSubcategoryId() == 0)
            return ResponseEntity.badRequest().body("Підкатегорію не вибрано");

//        System.out.println("goodsDTO: " + goodsDTO.getName() + "discription: " + goodsDTO.getShort_discription() + "SubcategoryId: " + goodsDTO.getSubcategoryId());
        try {
            GoodsEntity createdGood = goodsService.createGood(goodsDTO);
//            return ResponseEntity.ok("Новий товар успішно створено");
            return ResponseEntity.ok(createdGood); // Повертає об'єкт створеного товару зі статусом OK
        } catch (GoodsAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
//            return ResponseEntity.badRequest().body("Виникла помилка при створенні товару"); // рекомендовано???
        }
    }


    @PostMapping("/{goodId}/addPropertiesToGood")
    public ResponseEntity addPropertiesToGood(@PathVariable Long goodId, @RequestBody List<Long> propertiesIds) {
        try {
            goodsService.addPropertiesToGood(goodId, propertiesIds);
//            return ResponseEntity.ok("Новий товар успішно створено");
            return ResponseEntity.ok("New Product Properties have been added successfully"); // Повертає об'єкт створеного товару зі статусом OK
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
//            return ResponseEntity.badRequest().body("Виникла помилка при створенні товару"); // рекомендовано???
        }
    }


//    @GetMapping("/getOne")
//    public ResponseEntity getOneGood(@RequestParam Long id) {
//        try {
//            return ResponseEntity.ok(goodService.getGoodsById(id));
//        } catch (GoodsNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Виникла помилка");
//        }
//    }

///////////////////////////////////////////2023.08.16////////////////////////////////////////////
//    @GetMapping("/getOne")
//    public ResponseEntity<?> getOneGood(@RequestParam Long id, HttpServletRequest request) {
//        try {
//            GoodsEntity good = goodService.getGoodsById(id);
//
//            String currentUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//            List<PhotosGoodsEntity> photos = good.getPhotosGoods();
//            for (PhotosGoodsEntity photo : photos) {
//                String imagePath = currentUrl + "/images/" + photo.getPath();
//                photo.setPath(imagePath);
//            }
//
//            return ResponseEntity.ok(good);
//        } catch (GoodsNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Виникла помилка");
//        }
//    }


//    ////////////////////////////////////////2023.08.26////////////////////////////////////////////////
//    @GetMapping("/getOne")
//    public ResponseEntity<?> getOneGood(@RequestParam Long id, HttpServletRequest request) {
//        try {
//            GoodsEntity good = goodService.getGoodsById(id);
//
//            String currentUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//            List<PhotosGoodsEntity> photos = good.getPhotosGoods();
//            for (PhotosGoodsEntity photo : photos) {
//                String imagePath = currentUrl + "/images/" + photo.getPath();
//                photo.setPath(imagePath);
//            }
//
//
//            GoodsGetAllDTO responseDTO = new GoodsGetAllDTO(
//                    good.getId(),
//                    good.getName(),
//                    good.getShort_discription(),
//                    subcategoriesGoodsService.entityToDTO(good.getSubcategoriesGoods()),
//                    photosGoodsService.entityListToDTOS(good.getPhotosGoods())
//            );
//
//            return ResponseEntity.ok(responseDTO);
//        } catch (GoodsNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Виникла помилка");
//        }
//    }

    @GetMapping("/getOne")

    public ResponseEntity<?> getOneGood(@RequestParam Long id, HttpServletRequest request) {
        try {
            GoodsEntity good = goodsService.getGoodsById(id);

            String currentUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

            List<PhotosGoodsEntity> photos = good.getPhotosGoods();
            for (PhotosGoodsEntity photo : photos) {
                String imagePath = currentUrl + "/images/" + photo.getPath();
                photo.setPath(imagePath);
            }

            List<PropertiesGoodsEntity> propertiesGoodsList = good.getPropertiesGoods();
            List<PropertiesGoodsDTO> propertiesDTOList = new ArrayList<>();
            for (PropertiesGoodsEntity propertiesGoods : propertiesGoodsList) {
                PropertiesNameGoodsEntity propertiesNameGoods = propertiesGoods.getPropertiesNameGoods();

                PropertiesGoodsDTO propertiesGoodsDTO = new PropertiesGoodsDTO();
                propertiesGoodsDTO.setId(propertiesGoods.getId());
//                propertiesGoodsDTO.setType(propertiesGoods.getType());
                propertiesGoodsDTO.setValue(propertiesGoods.getValue());
                propertiesGoodsDTO.setPropertiesName(propertiesNameGoods.getName()); // Додаємо назву властивості
                propertiesGoodsDTO.setType(propertiesNameGoods.getValueType()); // Додаємо тип значення властивості
                propertiesDTOList.add(propertiesGoodsDTO);
            }

//            // Отримання переліку типів значень з ValueTypePropertiesEnum
//            List<ValueTypePropertiesEnum.ValueType> valueTypeList = Arrays.asList(ValueTypePropertiesEnum.ValueType.values());


            GoodsGetAllDTO responseDTO = new GoodsGetAllDTO(
                    good.getId(),
                    good.getName(),
                    good.getShort_discription(),
                    good.getSubcategoriesGoods().getId(),
                    good.getSubcategoriesGoods().getName(),
                    photosGoodsService.entityListToDTOS(good.getPhotosGoods()),
                    propertiesDTOList // Додаємо список властивостей з назвами
//                    valueTypeList // Додаємо перелік типів значень
            );
            responseDTO.getPropertiesGoodsDTOS().sort(Comparator.comparing(PropertiesGoodsDTO::getPropertiesName));
            responseDTO.setEvaluation(evaluationsGoodService.evaluationsByGood(id));
            return ResponseEntity.ok(responseDTO);
        } catch (GoodsNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Виникла помилка");
        }
    }


//    @GetMapping("/getOne")
//    public ResponseEntity<?> getOneGood(@RequestParam Long id, HttpServletRequest request) {
//        try {
//            GoodsEntity good = goodService.getGoodsById(id);
//            System.out.println("subcategory: " + good.getSubcategoriesGoods().getName());
//            String currentUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//            List<PhotosGoodsEntity> photos = good.getPhotosGoods();
//            for (PhotosGoodsEntity photo : photos) {
//                String imagePath = currentUrl + "/images/" + photo.getPath();
//                photo.setPath(imagePath);
//            }
//
//            return ResponseEntity.ok(good);
//        } catch (GoodsNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Виникла помилка");
//        }
//    }


    //    @GetMapping
//    public ResponseEntity<Iterable<GoodsEntity>> getAll() {
//        Iterable<GoodsEntity> goods = goodsRepository.findAll();
//        return new ResponseEntity<>(goods, HttpStatus.OK);


// ///////////////////////////////2023.08.18//////////////////////////////////////////////////////////////////
//    @GetMapping
//    public ResponseEntity<Iterable<GoodsEntity>> getAll(HttpServletRequest request) {
//        Iterable<GoodsEntity> goods = goodsRepository.findAll();
//
//        //String currentUrl = request.getRequestURL().toString(); // Отримуємо поточну URL-адресу
//        String currentUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//        for (GoodsEntity good : goods) {
//            List<PhotosGoodsEntity> photos = good.getPhotosGoods();
//            for (PhotosGoodsEntity photo : photos) {
//                // Додаємо поточну URL-адресу та папку "images" до "path" кожної фотографії
//                String imagePath = currentUrl + "/images/" + photo.getPath();
//                photo.setPath(imagePath);
//            }
//        }
//
//        return new ResponseEntity<>(goods, HttpStatus.OK);
//    }

//    @GetMapping
//    public ResponseEntity<List<GoodsGetAllDTO>> getAll(HttpServletRequest request) {
//        Iterable<GoodsEntity> goodsEntityIterable = goodsRepository.findAll();
//        List<GoodsGetAllDTO> goodsDTOList = new ArrayList<>();
//        goodsEntityIterable.forEach(goodsDTOList::add);
//
//
//        String currentUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//        for (GoodsGetAllDTO dto : goodsList) {
//            List<PhotosGoodsDTO> photosDTOList = dto.getPhotosGoodsDTOS();
//            for (PhotosGoodsDTO photoDTO : photosDTOList) {
//                String imagePath = currentUrl + "/images/" + photoDTO.getPath();
//                photoDTO.setPath(imagePath);
//            }
//        }
//
//        return new ResponseEntity<>(goodsList, HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<List<GoodsGetAllDTO>> getAll(HttpServletRequest request) {
//        Sort sortById = Sort.by(Sort.Order.asc("id"));
//        Iterable<GoodsEntity> goodsEntityIterable = goodsRepository.findAll(sortById);

        Iterable<GoodsEntity> goodsEntityIterable = goodsRepository.findAll(Sort.by(Sort.Order.asc("id")));
        List<GoodsGetAllDTO> goodsDTOList = new ArrayList<>();

        for (GoodsEntity entity : goodsEntityIterable) {
            GoodsGetAllDTO dto = goodsService.entityToDTO(entity);
            goodsDTOList.add(dto);
        }

        String currentUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        for (GoodsGetAllDTO dto : goodsDTOList) {
            List<PhotosGoodsDTO> photosDTOList = dto.getPhotosGoodsDTOS();

            for (PhotosGoodsDTO photoDTO : photosDTOList) {
                String imagePath = currentUrl + "/images/" + photoDTO.getPath();
                photoDTO.setPath(imagePath);

//                System.out.println("Image path: " + imagePath);
            }
        }

        return new ResponseEntity<>(goodsDTOList, HttpStatus.OK);
    }

//    @GetMapping("/getByCategoriesId")
//    public ResponseEntity<Iterable<GoodsForCatalogDTO>> getByCategoriesId(@RequestParam Long id) {
//        System.out.println("getByCategoriesId id: " + id);
//        try {
//            List<GoodsForCatalogDTO> dtos = goodsService.getByCategoriesId(id);
//            return ResponseEntity.ok(dtos);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(new ArrayList<>() {
//            }); // or handle the error
//        }
//    }

    @GetMapping("/getBySubcategoriesId")
    public ResponseEntity<Iterable<GoodsGetAllDTO>> getBySubcategoriesId(@RequestParam Long id) {
//        System.out.println("id: " + id);
        try {
            List<GoodsGetAllDTO> dtos = goodsService.getBySubcategoriesId(id);
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ArrayList<>() {
            }); // or handle the error
        }
    }

    //    @DeleteMapping("/{id}")
//    public ResponseEntity deleteGood(@PathVariable Long id) {
//        try {
//            // Видалення фотографій товару за його ідентифікатором
//            goodService.deletePhotosByGoodsId(id);
//            return ResponseEntity.ok(goodService.delete(id));
//        } catch (Exception e) {
////            return ResponseEntity.badRequest().body("Виникла помилка");
//            return ResponseEntity.ok(e.getMessage());
//        }
//    }

    @PatchMapping("/editGoodName")
    public ResponseEntity editGoodName(@RequestBody GoodsDTO goodsDTO) throws GoodsAlreadyExistException {

        List<GoodsEntity> goodsEntityList = goodsRepository.findAll();
        List<GoodsEntity> goodsEntities = new ArrayList<>();
        for (GoodsEntity goods : goodsEntityList) {
            if (goods.getName().equals(goodsDTO.getName()) && !goods.getId().equals(goodsDTO.getId())) {
                throw new GoodsAlreadyExistException("The product already exists");
            }
        }

        try {
            goodsService.editGoodName(goodsDTO.getId(), goodsDTO.getName());

            return ResponseEntity.ok("Назву Товару успішно змінено");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PatchMapping("/editGoodDescription")
    public ResponseEntity editGoodDescription(@RequestBody GoodsDTO goodsDTO) {
        System.out.println("goodsDTO Id         : " + goodsDTO.getId());
        System.out.println("goodsDTO description: " + goodsDTO.getShort_discription());
        try {
            goodsService.editGoodDescription(goodsDTO.getId(), goodsDTO.getShort_discription());

            return ResponseEntity.ok("Опис Товару успішно змінено");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PatchMapping("/editGoodSubcategory")
    public ResponseEntity editGoodSubcategory(@RequestBody GoodsDTO goodsDTO) {
        System.out.println("goodsDTO Id         : " + goodsDTO.getId());
        System.out.println("goodsDTO subcategoryId: " + goodsDTO.getSubcategoryId());
        try {
            goodsService.editGoodSubcategory(goodsDTO.getId(), goodsDTO.getSubcategoryId());

            return ResponseEntity.ok("Опис Товару успішно змінено");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteGood(HttpServletRequest request, @PathVariable Long id) {
//        System.out.println("GoodsController: delete Good ");
        List<GoodsInvoicesDTO> goodsInvoicesDTOList = goodsInvoicesService.getByGoodsId(request, id);
//        System.out.println("GoodsController->goodsInvoicesDTOList: " + goodsInvoicesDTOList.size());

        if (goodsInvoicesDTOList.size() > 0 ) {
//            System.out.println("GoodsController: delete Good - if");

            Map<String, String> response = new HashMap<>();
            response.put("message", "Товар виставили в магазин. Видалення заборонено");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
//        // Видалення фотографій товару за його ідентифікатором
//        photosGoodsRepository.deleteByGoods_Id(id);

            // Видалення самого товару
            goodsService.delete(id);

            return ResponseEntity.ok("Товар успішно видалено");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

}
