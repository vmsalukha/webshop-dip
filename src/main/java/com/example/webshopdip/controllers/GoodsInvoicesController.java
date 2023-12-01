package com.example.webshopdip.controllers;

import com.example.webshopdip.dtos.*;
import com.example.webshopdip.entities.*;
import com.example.webshopdip.repositories.GoodsOrdersRepository;
import com.example.webshopdip.repositories.GoodsRepository;
import com.example.webshopdip.repositories.GoodsInvoicesRepository;
import com.example.webshopdip.services.GoodsService;
import com.example.webshopdip.services.GoodsInvoicesService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/goodsinvoices")
public class GoodsInvoicesController {
    @Autowired
    private GoodsInvoicesService goodsInvoicesService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsInvoicesRepository goodsInvoicesRepository;
    @Autowired
    private GoodsRepository goodsRepository;



    // ////////////////////////2023.09.07/////////////////////////////////////////////////////////////////////
//    @PostMapping
//    public ResponseEntity<GoodsInvoicesDTO> createGoodsInvoices(@RequestBody GoodsInvoicesDTO goodsInvoicesDTO) {
//        System.out.println("goodsInvoicesDTO: " + goodsInvoicesDTO);
//        System.out.println("getPrice: " + goodsInvoicesDTO.getPrice());
//        System.out.println("getGoods: " + goodsInvoicesDTO.getGoods());
//        System.out.println("getQuantity: " + goodsInvoicesDTO.getQuantity());
//
//
//        try {
//            GoodsInvoicesDTO createdDTO = goodsInvoicesService.createGoodsInvoices(goodsInvoicesDTO);
//            return ResponseEntity.ok(createdDTO);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(new GoodsInvoicesDTO()); // or handle the error
//        }
//    }

    @PostMapping
    public ResponseEntity<?> createGoodsInvoices(@RequestBody GoodsInvoicesToSaveDTO goodsInvoicesToSaveDTO) {
//        System.out.println("goodsinvoices->crete->goodsInvoicesDTO: " + goodsInvoicesToSaveDTO);
//        System.out.println("goodsinvoices->crete->getPrice: " + goodsInvoicesToSaveDTO.getPrice());
//        System.out.println("goodsinvoices->crete->getGoods: " + goodsInvoicesToSaveDTO.getGoodsId());
//        System.out.println("goodsinvoices->crete->getQuantity: " + goodsInvoicesToSaveDTO.getQuantity());
//        System.out.println("goodsinvoices->crete->getSellersId: " + goodsInvoicesToSaveDTO.getSellersId());

        if (goodsInvoicesToSaveDTO.getSellersId()==null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Відсутнє значення Id продавця");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (goodsInvoicesToSaveDTO.getQuantity()==null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Відсутнє значення кількості товару");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (goodsInvoicesToSaveDTO.getPrice()==null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Відсутнє значення ціни товару");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Optional<GoodsEntity> goodsEntityOptional = goodsRepository.findById(goodsInvoicesToSaveDTO.getGoodsId());

            if (goodsEntityOptional.isPresent()) {
                GoodsEntity goodsEntity = goodsEntityOptional.get();
                goodsInvoicesToSaveDTO.setGoodsId(goodsService.entityToDTO(goodsEntity).getId());
                GoodsInvoicesDTO createdDTO = goodsInvoicesService.createGoodsInvoices(goodsInvoicesToSaveDTO);
                return ResponseEntity.ok(createdDTO);
            } else {
                return ResponseEntity.badRequest().body(new GoodsInvoicesDTO()); // Обробити випадок, коли товар не знайдено
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new GoodsInvoicesDTO()); // Обробити помилку
        }
    }

    //    @GetMapping
//    public ResponseEntity<Iterable<GoodsInvoicesDTO>> getAll(HttpServletRequest request) {
//        Iterable<GoodsInvoicesEntity> goodsInvoicesEntities = goodsInvoicesRepository.findAll();
//        List<GoodsInvoicesDTO> goodsInvoicesDTOS = new ArrayList<>();
//        for (GoodsInvoicesEntity goodsInvoicesEntity : goodsInvoicesEntities) {
//            GoodsInvoicesDTO dto = goodsInvoicesService.entityToDTO(goodsInvoicesEntity);
//            goodsInvoicesDTOS.add(dto);
//        }
//
//        String currentUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//
//        for (GoodsInvoicesDTO dto : goodsInvoicesDTOS) {
//            List<PhotosGoodsDTO> photosDTOList = dto.getGoods().getPhotosGoodsDTOS();
//
//            for (PhotosGoodsDTO photoDTO : photosDTOList) {
//                String imagePath = currentUrl + "/images/" + photoDTO.getPath();
//                photoDTO.setPath(imagePath);
////                System.out.println("Image path: " + imagePath);
//            }
//        }
//        GoodsInvoicesDTO goodsInvoicesDTOS = goodsInvoicesService.getAll(request);
//
//        return new ResponseEntity<?>(goodsInvoicesDTOS, HttpStatus.OK);
//    }
    @GetMapping
    public ResponseEntity<List<GoodsInvoicesDTO>> getAll(HttpServletRequest request) {
        List<GoodsInvoicesDTO> goodsInvoicesDTOS = goodsInvoicesService.getAll(request);
        for (GoodsInvoicesDTO good: goodsInvoicesDTOS){
            good.setQuantity(goodsInvoicesService.getQuantityGoodTheShop(good.getId()));
        }
        return new ResponseEntity<>(goodsInvoicesDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllOnCounter")
    public ResponseEntity<List<GoodsInvoicesOnCounterDTO>> getAllOnCounter(
            HttpServletRequest request,
            @RequestParam(name = "sellerId", required = false) Long sellerId) {

        System.out.println("getAllOnCounter: sellerId=" + sellerId);

        List<GoodsInvoicesOnCounterDTO> goodsInvoicesOnCounterDTOS = goodsInvoicesService.getAllOnCounter(request);

        List<GoodsInvoicesOnCounterDTO> goodsInvoicesOnCounterBySellerIdDTOS = new ArrayList<>();
        for (GoodsInvoicesOnCounterDTO good: goodsInvoicesOnCounterDTOS){
            if (good.getSellerId().equals(sellerId)){
                goodsInvoicesOnCounterBySellerIdDTOS.add(good);
            }
        }




        return new ResponseEntity<>(goodsInvoicesOnCounterBySellerIdDTOS, HttpStatus.OK);
    }

    public ResponseEntity<Iterable<GoodsInvoicesDTO>> getForCategory(
            HttpServletRequest request,
            @RequestParam(name = "category", required = false) String category
    ) {
        List<GoodsInvoicesDTO> goodsInvoicesDTOS = goodsInvoicesService.getFindByCategoriesGoodsName(request, category);
        return new ResponseEntity<>(goodsInvoicesDTOS, HttpStatus.OK);
    }

    public ResponseEntity<Iterable<GoodsInvoicesDTO>> getForSubcategory(
            HttpServletRequest request,
            @RequestParam(name = "subcategory", required = false) String subcategory
    ) {
        List<GoodsInvoicesDTO> goodsInvoicesDTOS = goodsInvoicesService.getAll(request);
        List<GoodsInvoicesDTO> filteredGoodsInvoicesDTOS = new ArrayList<>();

        for (GoodsInvoicesDTO goodsInvoicesDTO : goodsInvoicesDTOS) {
            if (goodsInvoicesDTO.getGoods().getSubcategoriesGoodsName().equals(subcategory)){
                filteredGoodsInvoicesDTOS.add(goodsInvoicesDTO);

            }
        }
        return new ResponseEntity<>(filteredGoodsInvoicesDTOS, HttpStatus.OK);
    }

    @GetMapping("/getFromFilter")
    public ResponseEntity<Iterable<GoodsInvoicesDTO>> getFromFilter(
            HttpServletRequest request,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "subcategory", required = false) String subcategory
    ) {
//        System.out.println("subcategory: " + subcategory);
//        System.out.println("category: " + category);



        ResponseEntity<Iterable<GoodsInvoicesDTO>> filteredGoodsInvoicesDTOS = null;
        if (subcategory != null) {
//            System.out.println("subcategory if: " + subcategory);
            filteredGoodsInvoicesDTOS = getForSubcategory(request,subcategory);
        } else if (category != null) {
//            System.out.println("category  if: " + category);
            filteredGoodsInvoicesDTOS = getForCategory(request,category);
        } else {
//            System.out.println("getAll if: getAll" );
            return new ResponseEntity<>(goodsInvoicesService.getAll(request), HttpStatus.OK);
        }

        return filteredGoodsInvoicesDTOS;
    }


    @GetMapping("/getOne")
    public ResponseEntity<GoodsInvoicesDTO> getOne(@RequestParam Long id, HttpServletRequest request) {
//        System.out.println("id: " + id);
        try {
            GoodsInvoicesDTO dto = goodsInvoicesService.getOne(id);

            String currentUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

            List<PhotosGoodsDTO> photos = dto.getGoods().getPhotosGoodsDTOS();
            for (PhotosGoodsDTO photo : photos) {
                String imagePath = currentUrl + "/images/" + photo.getPath();
                photo.setPath(imagePath);
            }

            GoodsInvoicesEntity entity = goodsInvoicesRepository.findById(id).orElse(new GoodsInvoicesEntity());

//            System.out.println("PropertiesGoods.size: " + entity.getGoods().getPropertiesGoods().size());
            List<PropertiesGoodsEntity> propertiesGoodsList = entity.getGoods().getPropertiesGoods();
            List<PropertiesGoodsDTO> propertiesDTOList = new ArrayList<>();
            for (PropertiesGoodsEntity propertiesGoods : propertiesGoodsList) {
                PropertiesNameGoodsEntity propertiesNameGoods = propertiesGoods.getPropertiesNameGoods();
                PropertiesGoodsDTO propertiesGoodsDTO = new PropertiesGoodsDTO();
                propertiesGoodsDTO.setId(propertiesGoods.getId());
                propertiesGoodsDTO.setValue(propertiesGoods.getValue());
                propertiesGoodsDTO.setPropertiesName(propertiesNameGoods.getName()); // Додаємо назву властивості
                propertiesGoodsDTO.setType(propertiesNameGoods.getValueType()); // Додаємо тип значення властивості
                propertiesDTOList.add(propertiesGoodsDTO);
            }
            dto.getGoods().setPropertiesGoodsDTOS(propertiesDTOList);
//            System.out.println("getOneGoodsInvoices entityToDTO - dto.getQuantity(): " + dto.getQuantity());
            dto.setQuantity(goodsInvoicesService.getQuantityGoodTheShop(id));
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new GoodsInvoicesDTO()); // or handle the error
        }
    }


    @GetMapping("/getByCategoriesId")
    public ResponseEntity<Iterable<GoodsInvoicesDTO>> getByCategoriesId(
            HttpServletRequest request,
            @RequestParam(name = "id", required = false) Long id
    ) {
        List<GoodsInvoicesDTO> goodsInvoicesDTOS = goodsInvoicesService.getByCategoriesId(request, id);
        for (GoodsInvoicesDTO good: goodsInvoicesDTOS){
            good.setQuantity(goodsInvoicesService.getQuantityGoodTheShop(good.getId()));
        }
        return new ResponseEntity<>(goodsInvoicesDTOS, HttpStatus.OK);
    }

    @GetMapping("/getBySubcategoriesId")
    public ResponseEntity<Iterable<GoodsInvoicesDTO>> getBySubcategoriesId(
            HttpServletRequest request,
            @RequestParam(name = "id", required = false) Long id
    ) {
        List<GoodsInvoicesDTO> goodsInvoicesDTOS = goodsInvoicesService.getBySubcategoriesId(request, id);
        for (GoodsInvoicesDTO good: goodsInvoicesDTOS){
            good.setQuantity(goodsInvoicesService.getQuantityGoodTheShop(good.getId()));
        }
        return new ResponseEntity<>(goodsInvoicesDTOS, HttpStatus.OK);
    }

    @GetMapping("/getByGoodsId")
    public ResponseEntity<Iterable<GoodsInvoicesDTO>> getByGoodsId(
            HttpServletRequest request,
            @RequestParam(name = "id", required = false) Long id
    ) {
        List<GoodsInvoicesDTO> goodsInvoicesDTOS = goodsInvoicesService.getByGoodsId(request, id);
        for (GoodsInvoicesDTO good: goodsInvoicesDTOS){
            good.setQuantity(goodsInvoicesService.getQuantityGoodTheShop(good.getId()));
        }
        return new ResponseEntity<>(goodsInvoicesDTOS, HttpStatus.OK);
    }

//    @GetMapping("/getByCategoriesIdOnCounter")
//    public ResponseEntity<Iterable<GoodsInvoicesOnCounterDTO>> getByCategoriesIdOnCounter(
//            HttpServletRequest request, @RequestParam(name = "id", required = false) Long id
//    ) {
//        System.out.println("getByCategoriesIdOnCounter good " );
//
//        ResponseEntity<Iterable<GoodsInvoicesDTO>> good = getByCategoriesId(request, id);
//        for (GoodsInvoicesDTO g: good.getBody()){
//            System.out.println("getByCategoriesIdOnCounter good: " + g.getGoods().getName());
//        }
////        goodsInvoicesService.getListDTOSToCounterDTOS(good.getBody());
//        return new ResponseEntity<>(goodsInvoicesService.getListDTOSToCounterDTOS(good.getBody()), HttpStatus.OK);
//    }

    @GetMapping("/getByCategoriesIdOnCounter")
    public ResponseEntity<Iterable<GoodsInvoicesOnCounterDTO>> getByCategoriesIdOnCounter(
            HttpServletRequest request,
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "sellerId", required = false) Long sellerId
    ) {
        System.out.println("getByCategoriesIdOnCounter: id=" +id + " sellerId=" + sellerId);
        ResponseEntity<Iterable<GoodsInvoicesDTO>> goods = getByCategoriesId(request, id);
        List<GoodsInvoicesDTO> goodsInvoicesDTOS = new ArrayList<>();
        for (GoodsInvoicesDTO good: goods.getBody()){
            if (good.getSeller().getId().equals(sellerId)){
                goodsInvoicesDTOS.add(good);
            }
        }
        Iterable<GoodsInvoicesOnCounterDTO> result = goodsInvoicesService.getListDTOSToCounterDTOS(goodsInvoicesDTOS);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/getBySubcategoriesIdOnCounter")
    public ResponseEntity<Iterable<GoodsInvoicesOnCounterDTO>> getBySubcategoriesIdOnCounter(
            HttpServletRequest request,
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "sellerId", required = false) Long sellerId
    ) {
//        System.out.println("getBySubcategoriesIdOnCounter good " );

        ResponseEntity<Iterable<GoodsInvoicesDTO>> goods = getBySubcategoriesId(request, id);
        List<GoodsInvoicesDTO> goodsInvoicesDTOS = new ArrayList<>();
        for (GoodsInvoicesDTO good: goods.getBody()){
            if (good.getSeller().getId().equals(sellerId)){
                goodsInvoicesDTOS.add(good);
            }
        }
        Iterable<GoodsInvoicesOnCounterDTO> result = goodsInvoicesService.getListDTOSToCounterDTOS(goodsInvoicesDTOS);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getByGoodsIdOnCounter")
    public ResponseEntity<Iterable<GoodsInvoicesOnCounterDTO>> getByGoodsIdOnCounter(
            HttpServletRequest request,
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "sellerId", required = false) Long sellerId
    ) {
//        System.out.println("getByGoodsIdOnCounter good " );

        ResponseEntity<Iterable<GoodsInvoicesDTO>> goods = getByGoodsId(request, id);
        List<GoodsInvoicesDTO> goodsInvoicesDTOS = new ArrayList<>();
        for (GoodsInvoicesDTO good: goods.getBody()){
            if (good.getSeller().getId().equals(sellerId)){
                goodsInvoicesDTOS.add(good);
            }
        }
        Iterable<GoodsInvoicesOnCounterDTO> result = goodsInvoicesService.getListDTOSToCounterDTOS(goodsInvoicesDTOS);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/removeTheBalance")
    public ResponseEntity<?> removeTheBalance(@RequestBody Map<String, Object> requestBody) {
        Long goodInvoicesId = Long.parseLong(requestBody.get("id").toString());
        goodsInvoicesService.removeTheBalance(goodInvoicesId);

        return ResponseEntity.ok("Залишок товару знято з прилавку");
    }

}
