//package com.example.webshopdip.services;
//
//import com.example.webshopdip.dtos.*;
//import com.example.webshopdip.entities.GoodsEntity;
//import com.example.webshopdip.entities.GoodsInvoicesEntity;
//import com.example.webshopdip.entities.GoodsOrdersEntity;
//import com.example.webshopdip.repositories.GoodsInvoicesRepository;
//import com.example.webshopdip.repositories.GoodsOrdersRepository;
//import com.example.webshopdip.repositories.GoodsRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class GoodsOrdersService {
//    @Autowired
//    GoodsOrdersRepository goodsOrdersRepository;
//    @Autowired
//    GoodsInvoicesRepository goodsInvoicesRepository;
//    @Autowired
//    GoodsInvoicesService goodsInvoicesService;
//    @Autowired
//    private SubcategoriesGoodsService subcategoriesGoodsService;
//    @Autowired
//    private PhotosGoodsService photosGoodsService;
//    @Autowired
//    private GoodsRepository goodsRepository;
//
//    public GoodsOrdersDTO createGoodsOrders(GoodsOrdersDTO dto) {
//        GoodsOrdersEntity entity = new GoodsOrdersEntity();
//
//        // Отримати GoodsEntity з Optional<GoodsEntity> і перевірити, чи він присутній
//        Optional<GoodsEntity> goodsEntityOptional = goodsRepository.findById(dto.getGoodsInvoicesDTO().getGoods().getId());
//
//        if (goodsEntityOptional.isPresent()) {
//            GoodsEntity goodsEntity = goodsEntityOptional.get(); // Отримати GoodsEntity з Optional
//
//            GoodsInvoicesEntity entityInvoices = new GoodsInvoicesEntity();
//            entityInvoices.setGoods(goodsEntity);
//            entityInvoices.setQuantity(dto.getQuantity());
//            entityInvoices.setPrice(dto.getPrice());
//
//            entity.setGoodsInvoices(entityInvoices);
//
//            return entityToDTO(entity);
//        } else {
//            return null;
//        }
//    }
//
//    public GoodsOrdersDTO entityToDTO(GoodsOrdersEntity entity) {
//        GoodsOrdersDTO dto = new GoodsOrdersDTO();
//        dto.setId(entity.getId());
//
//        GoodsInvoicesDTO goodsInvoicesDTO = new GoodsInvoicesDTO();
//        goodsInvoicesDTO.setId(entity.getGoodsInvoices().getId());
//
//        // Перетворюємо GoodsEntity на GoodsGetAllDTO
//        GoodsGetAllDTO goodsGetAllDTO = new GoodsGetAllDTO();
//        goodsGetAllDTO.setId(entity.getGoodsInvoices().getGoods().getId());
//        // ??? Встановлюємо інші властивості GoodsDTO, які вам потрібні
//        goodsGetAllDTO.setName(entity.getGoodsInvoices().getGoods().getName());
//        goodsGetAllDTO.setShort_discription(entity.getGoodsInvoices().getGoods().getShort_discription());
//        goodsGetAllDTO.setSubcategoriesGoodsName(subcategoriesGoodsService.entityToDTO(entity.getGoodsInvoices().getGoods().getSubcategoriesGoods()).getName());
//        goodsGetAllDTO.setPhotosGoodsDTOS(photosGoodsService.entityListToDTOS(entity.getGoodsInvoices().getGoods().getPhotosGoods()));
//
//        goodsInvoicesDTO.setGoods(goodsGetAllDTO);
//
//        dto.setGoodsInvoicesDTO(goodsInvoicesDTO);
//        dto.setPrice(entity.getPrice());
//        dto.setQuantity(entity.getQuantity());
//
//        return dto;
//    }
//
//
//    public List<GoodsOrdersDTO> getAll(HttpServletRequest request) {
//        Iterable<GoodsOrdersEntity> goodsOrdersEntities = goodsOrdersRepository.findAll();
//
//        return getListEntitiesToDTOS(request, goodsOrdersEntities);
//    }
//
//    public GoodsOrdersDTO getOne(Long id) {
//        Optional<GoodsOrdersEntity> optional = goodsOrdersRepository.findById(id);
//
//        GoodsOrdersEntity entity = optional.get();
//        return entityToDTO(entity);
//    }
//    public List<GoodsOrdersDTO> getListEntitiesToDTOS(HttpServletRequest request, Iterable<GoodsOrdersEntity> goodsOrdersEntities) {
//        List<GoodsOrdersDTO> goodsOrdersDTOS = new ArrayList<>();
//
//
//        for (GoodsOrdersEntity goodsOrdersEntity : goodsOrdersEntities) {
//            GoodsOrdersDTO dto = entityToDTO(goodsOrdersEntity);
//            goodsOrdersDTOS.add(dto);
//        }
//
//        String currentUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//
//        for (GoodsOrdersDTO dto : goodsOrdersDTOS) {
//            List<PhotosGoodsDTO> photosDTOList = dto.getGoodsInvoicesDTO().getGoods().getPhotosGoodsDTOS();
//
//            for (PhotosGoodsDTO photoDTO : photosDTOList) {
//                String imagePath = currentUrl + "/images/" + photoDTO.getPath();
//                photoDTO.setPath(imagePath);
////                System.out.println("Image path: " + imagePath);
//            }
//        }
//        return goodsOrdersDTOS;
//    }
//    @Transactional
//    public void delete(Long id) {
//        goodsOrdersRepository.deleteById(id);
//    }
//}

package com.example.webshopdip.services;

import com.example.webshopdip.controllers.OrdersListsController;
import com.example.webshopdip.dtos.*;
import com.example.webshopdip.entities.*;
import com.example.webshopdip.exceptions.GoodsNotFoundException;
import com.example.webshopdip.exceptions.GoodsOrdersNotFoundException;
import com.example.webshopdip.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GoodsOrdersService {
    @Autowired
    GoodsOrdersRepository goodsOrdersRepository;
    @Autowired
    GoodsInvoicesRepository goodsInvoicesRepository;
    @Autowired
    GoodsInvoicesService goodsInvoicesService;
    @Autowired
    private OrdersListsController ordersListsController;
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private OrdersListsRepository ordersListsRepository;
    @Autowired
    private OrdersListsService ordersListsService;
    @Autowired
    private GoodsService goodsService;

    /**
     * public ResponseEntity<GoodsOrdersToSaveDTO> createGoodsOrders(@RequestBody Map<String, Object> request) {
     * Long customers_Id = (Long) request.get("customers_Id");
     * Long goodsInvoices_Id = (Long) request.get("goodsInvoices_Id");
     * Integer quantity = (Integer) request.get("quantity");
     * Float price = (Float) request.get("price");
     * goodsInvoicesRepository.findById(GoodsOrdersToSaveDTO.getGoodsInvoicesDTO().getId()).orElse(null)
     */
    //    public GoodsOrdersToSaveDTO createGoodsOrders(GoodsOrdersToSaveDTO dto) {
//        GoodsOrdersEntity entity = new GoodsOrdersEntity();
//
//        // Отримати GoodsEntity з Optional<GoodsEntity> і перевірити, чи він присутній
//        Optional<GoodsEntity> goodsEntityOptional = goodsRepository.findById(dto.getGoodsInvoicesDTO().getGoods().getId());
//        //перевіряємо чи є в ордер ліст запис без номера щодо данного покупця.
//        //якщо є то додаємо до того ід запису наш перелік товарів в кошик
//        //якщо немає то створюємо цей запис і додаємо перелік товарів
//        if (goodsEntityOptional.isPresent()) {
//            GoodsEntity goodsEntity = goodsEntityOptional.get(); // Отримати GoodsEntity з Optional
//
//            GoodsInvoicesEntity entityInvoices = new GoodsInvoicesEntity();
//            entityInvoices.setGoods(goodsEntity);
//            entityInvoices.setQuantity(dto.getQuantity());
//            entityInvoices.setPrice(dto.getPrice());
//
//            entity.setGoodsInvoices(entityInvoices);
//
//            return entityToDTO(entity);
//        } else {
//            return null;
//        }
    //   }
    //public GoodsOrdersToSaveDTO createGoodsOrder(Long customersId, Long goodsInvoicesId, Integer quantity, Float price) {
    // Тут додайте логіку створення запису GoodsOrdersEntity
    // перевірка на існування відповідного замовлення і товару, створення нового замовлення якщо не існує
    // і додавання запису GoodsOrdersEntity
    // Після цього перетворіть отриманий об'єкт в DTO і поверніть його.
//}
    //public GoodsOrdersToSaveDTO createGoodsOrders(Long customersId, Long goodsInvoicesId) throws GoodsOrdersNotFoundException {
    public GoodsOrdersDTO createGoodsOrders(GoodsOrdersToSaveDTO goodsOrdersToSaveDTO) throws GoodsOrdersNotFoundException {
 /*
          {
    "goodsInvoicesId":4,   "ordersListsId":4,  "price": 100.0,   "quantity": 2
}
         */
        GoodsOrdersEntity goodsOrdersEntity = new GoodsOrdersEntity();
//        Optional<GoodsOrdersToSaveDTO> optional1 = goodsOrdersRepository.findById(goodsOrdersToSaveDTO.getId());

        goodsOrdersEntity.setGoodsInvoices(goodsInvoicesRepository.findById(goodsOrdersToSaveDTO.getGoodsInvoicesId()).orElse(null));
        goodsOrdersEntity.setOrdersLists(ordersListsRepository.findById(goodsOrdersToSaveDTO.getOrdersListsId()).orElse(null));
        goodsOrdersEntity.setPrice(goodsOrdersToSaveDTO.getPrice());
        goodsOrdersEntity.setQuantity(goodsOrdersToSaveDTO.getQuantity());
        //перевіряємо чи є в ордер ліст запис без номера щодо данного покупця.
        //якщо є то додаємо до того ід запису наш перелік товарів в кошик
        //якщо немає то створюємо цей запис і додаємо перелік товарів
        goodsOrdersRepository.save(goodsOrdersEntity);
        return entityToDTO(goodsOrdersEntity);


    }


    public GoodsOrdersDTO entityToDTO(GoodsOrdersEntity entity) {//21.10 00:40
        GoodsOrdersDTO dto = new GoodsOrdersDTO();
        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        dto.setQuantity(entity.getQuantity());

        //dto.setGoodsInvoicesDTO(goodsInvoicesService.entityToDTO(entity.getGoodsInvoices()));//22.10 22:50
        //dto.setOrdersListsDTO(entity.getOrdersLists());

        GoodsInvoicesEntity goodsInvoicesEntity = entity.getGoodsInvoices();
        GoodsInvoicesDTO goodsInvoicesDTO = goodsInvoicesService.entityToDTO(goodsInvoicesEntity);
        dto.setGoodsInvoicesDTO(goodsInvoicesDTO);

        OrdersListsEntity ordersListsEntity = entity.getOrdersLists();
        OrdersListsDTO ordersListsDTO = ordersListsService.entityToDTO(ordersListsEntity);
        dto.setOrdersListsDTO(ordersListsDTO);

        return dto;
    }

//    id
//    ordersListsDTO
//    goodsDTO
//    price
//    quantity


    public GoodsOrdersWithGoodDTO entityToDTOWithGood(GoodsOrdersEntity entity, HttpServletRequest request) throws GoodsNotFoundException {//2023/11/08

        GoodsOrdersWithGoodDTO goodsOrdersWithGoodDTO = new GoodsOrdersWithGoodDTO();
        goodsOrdersWithGoodDTO.setId(entity.getId());
        goodsOrdersWithGoodDTO.setOrdersListsId(ordersListsService.entityToDTO(entity.getOrdersLists()).getId());
        goodsOrdersWithGoodDTO.setGoodsGetAllDTO(goodsService.entityToDTO(goodsService.getGoodsById(entity.getGoodsInvoices().getGoods().getId())));

        goodsOrdersWithGoodDTO.setPrice(entity.getPrice());
        goodsOrdersWithGoodDTO.setQuantity(entity.getQuantity());

        goodsOrdersWithGoodDTO.getGoodsGetAllDTO().setPhotosGoodsDTOS(goodsService.changePathPhoto(goodsOrdersWithGoodDTO.getGoodsGetAllDTO().getPhotosGoodsDTOS(), request));

        return goodsOrdersWithGoodDTO;
    }

//
//public List<GoodsOrdersWithGoodDTO> getCartByCustomerId(Long id) {  //2023/11/08
////    public ResponseEntity<?> getCartByCustomerId(Long id) {  //2023/11/08
//    try {
////        Iterable<SellersEntity> sellersEntities = sellersRepository.findAll(Sort.by(Sort.Order.asc("id")));
////
////        Iterable<SellersDTO> sellersDTOs = StreamSupport.stream(sellersEntities.spliterator(), false)
////                .map(this::entityToDTO)
////                .collect(Collectors.toList());
//
//
//        List<OrdersListsEntity> ordersListsEntities = ordersListsRepository.findByCustomers_IdAndNumberIsNull(id);
//        Long orderlistId = ordersListsService.entityToDTO(ordersListsEntities.get(0)).getId();
//        List<GoodsOrdersEntity> goodsOrdersEntities = goodsOrdersRepository.findByOrdersLists_Id(orderlistId);
////        return ResponseEntity.ok(goodsOrdersEntities);
//        List<GoodsOrdersWithGoodDTO> goodsOrdersWithGoodDTOList = new ArrayList<>();
//        for (GoodsOrdersEntity goodsOrdersEntity: goodsOrdersEntities){
//            goodsOrdersWithGoodDTOList.add(entityToDTOWithGood(goodsOrdersEntity));
//        }
//
//
//
//        return goodsOrdersWithGoodDTOList;
////        return new ResponseEntity<>(goodsOrdersWithGoodDTOList, HttpStatus.OK);
//    } catch (Exception e) {
//        // Обробка помилки
////        return new ResponseEntity<>("Помилка у виконанні запиту", HttpStatus.INTERNAL_SERVER_ERROR);
//        e.printStackTrace();
//    }
//    return goodsOrdersWithGoodDTOList;
//}


    public List<GoodsOrdersWithGoodDTO> getCartByCustomerId(Long id, HttpServletRequest request) { //2023/11/08
        List<GoodsOrdersWithGoodDTO> goodsOrdersWithGoodDTOList = new ArrayList<>();

        try {
            List<OrdersListsEntity> ordersListsEntities = ordersListsRepository.findByCustomers_IdAndNumberIsNull(id);

            if (ordersListsEntities.isEmpty()) {
                // Якщо список порожній, можливо, потрібно взяти додаткові заходи.
                // Повернути порожній список або зробити щось інше, залежить від ваших вимог.
                return goodsOrdersWithGoodDTOList;
            }

            Long orderlistId = ordersListsService.entityToDTO(ordersListsEntities.get(0)).getId();
            List<GoodsOrdersEntity> goodsOrdersEntities = goodsOrdersRepository.findByOrdersLists_Id(orderlistId);

            for (GoodsOrdersEntity goodsOrdersEntity : goodsOrdersEntities) {
                GoodsOrdersWithGoodDTO newGoodsOrdersWithGoodDTO = entityToDTOWithGood(goodsOrdersEntity, request);
                Optional<GoodsInvoicesEntity> goodsInvoicesEntity = goodsInvoicesRepository.findById(goodsOrdersEntity.getGoodsInvoices().getId());
                Integer num = goodsInvoicesService.getQuantityGoodTheShop(goodsInvoicesEntity.get().getId());
//                System.out.println("GoodsOrdersService->QuantityInShop: " + num);
                newGoodsOrdersWithGoodDTO.setQuantityInShop(num);
                goodsOrdersWithGoodDTOList.add(newGoodsOrdersWithGoodDTO);
            }
        } catch (Exception e) {
            // Позначте помилку і поверніть її
            goodsOrdersWithGoodDTOList = null;
        }


        return goodsOrdersWithGoodDTOList;
    }


    public List<GoodsOrdersWithGoodDTO> getShopingByCustomerId(Long id, HttpServletRequest request) {
        List<GoodsOrdersWithGoodDTO> goodsOrdersWithGoodDTOList = new ArrayList<>();

        try {
            List<OrdersListsEntity> ordersListsEntities = ordersListsRepository.findByCustomers_IdAndNumberIsNotNull(id);

            if (ordersListsEntities.isEmpty()) {
                // Якщо список порожній, можливо, потрібно взяти додаткові заходи.
                // Повернути порожній список або зробити щось інше, залежить від ваших вимог.

                return goodsOrdersWithGoodDTOList;
            }

            Long orderlistId = ordersListsService.entityToDTO(ordersListsEntities.get(0)).getId();
            List<GoodsOrdersEntity> goodsOrdersEntities = goodsOrdersRepository.findByOrdersLists_Id(orderlistId);

            for (GoodsOrdersEntity goodsOrdersEntity : goodsOrdersEntities) {
                GoodsOrdersWithGoodDTO newGoodsOrdersWithGoodDTO = entityToDTOWithGood(goodsOrdersEntity, request);
                Optional<GoodsInvoicesEntity> goodsInvoicesEntity = goodsInvoicesRepository.findById(goodsOrdersEntity.getGoodsInvoices().getId());
                Integer num = goodsInvoicesService.getQuantityGoodTheShop(goodsInvoicesEntity.get().getId());
//                System.out.println("GoodsOrdersService->QuantityInShop: " + num);
                newGoodsOrdersWithGoodDTO.setQuantityInShop(num);
                goodsOrdersWithGoodDTOList.add(newGoodsOrdersWithGoodDTO);
            }
        } catch (Exception e) {
            // Позначте помилку і поверніть її
            goodsOrdersWithGoodDTOList = null;
        }


        return goodsOrdersWithGoodDTOList;
    }


    public List<GoodsOrdersDTO> getAll(HttpServletRequest request) {
        List<GoodsOrdersEntity> goodsOrdersEntities = goodsOrdersRepository.findAll();

        return getListEntitiesToDTOS(request, goodsOrdersEntities);
    }

    public GoodsOrdersDTO getOne(Long id) {
        GoodsOrdersEntity goodsOrdersEntity = goodsOrdersRepository.findById(id).orElse(null);
        GoodsInvoicesDTO goodsInvoicesDTO = goodsInvoicesService.getOne(goodsOrdersEntity.getGoodsInvoices().getId());
        GoodsOrdersDTO GoodsOrdersDTO = entityToDTO(goodsOrdersEntity);
        GoodsOrdersDTO.setGoodsInvoicesDTO(goodsInvoicesDTO);
        return GoodsOrdersDTO;
    }

    public List<GoodsOrdersDTO> getListEntitiesToDTOS(HttpServletRequest request, Iterable<GoodsOrdersEntity> goodsOrdersEntities) {
        List<GoodsOrdersDTO> GoodsOrdersDTOS = new ArrayList<>();


        for (GoodsOrdersEntity goodsOrdersEntity : goodsOrdersEntities) {
            GoodsOrdersDTO dto = entityToDTO(goodsOrdersEntity);
            GoodsOrdersDTOS.add(dto);
        }

        String currentUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        for (GoodsOrdersDTO goodsOrdersDTO : GoodsOrdersDTOS) {
            GoodsInvoicesDTO goodsInvoicesDTO = goodsOrdersDTO.getGoodsInvoicesDTO();
            if (goodsInvoicesDTO != null) {
                List<PhotosGoodsDTO> photosDTOList = goodsInvoicesDTO.getGoods().getPhotosGoodsDTOS();

                for (PhotosGoodsDTO photoDTO : photosDTOList) {
                    String imagePath = currentUrl + "/images/" + photoDTO.getPath();
                    photoDTO.setPath(imagePath);
                }
            }
        }
        return GoodsOrdersDTOS;
    }

    @Transactional
    public ResponseEntity<String> editQuantityGoodTheCart(List<GoodsOrdersEditQuantityDTO> goodsOrdersEditQuantityDTOS) {
        try {
            for (GoodsOrdersEditQuantityDTO goodsOrdersEditQuantityDTO : goodsOrdersEditQuantityDTOS) {
                Optional<GoodsOrdersEntity> optionalGoodsOrders = goodsOrdersRepository.findById(goodsOrdersEditQuantityDTO.getId());

                if (optionalGoodsOrders.isPresent()) {
                    GoodsOrdersEntity goodsOrders = optionalGoodsOrders.get();
                    goodsOrders.setQuantity(goodsOrdersEditQuantityDTO.getQuantity());
                    goodsOrdersRepository.save(goodsOrders);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GoodsOrders not found with id: " + goodsOrdersEditQuantityDTO.getId());
                }
            }

            return ResponseEntity.ok("Кількість товару успішно змінено");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Transactional
    public void delete(Long id) {//перевірено 21.10 15:30 - працює
//        System.out.println("GoodsOrdersService->delete id: " + id);
        goodsOrdersRepository.deleteById(id);
    }

    //ВІДОБРАЖЕННЯ ТОВАРІВ КОНКРЕТНОГО customer
//    public  Iterable<GoodsOrdersEntity> getAllByCustomer(Long customerId) {
//        Iterable<GoodsOrdersEntity> goodsOrdersEntities = goodsOrdersRepository.findAll();
//        List<GoodsOrdersEntity> goodsOrdersByCustomer=new ArrayList<>();
//        for (GoodsOrdersEntity goodsOrdersEntity:goodsOrdersEntities){
//            if (goodsOrdersEntity.getOrdersLists().getCustomers().getId().equals(customerId)) {
//                goodsOrdersByCustomer.add(goodsOrdersEntity);
//            }
//
//        }
//        return goodsOrdersByCustomer;
//    }
    //22.10 23:30
    public Iterable<GoodsOrdersDTO> convertToDTO(Iterable<GoodsOrdersEntity> goodsOrdersEntities) {
        List<GoodsOrdersDTO> GoodsOrdersDTOs = new ArrayList<>();

        for (GoodsOrdersEntity entity : goodsOrdersEntities) {
            GoodsOrdersDTO dto = entityToDTO(entity);
            GoodsOrdersDTOs.add(dto);
        }

        return GoodsOrdersDTOs;
    }

    //22.10 23:30
    public Iterable<GoodsOrdersEntity> getAllByCustomer(Long customerId) {
        Iterable<GoodsOrdersEntity> goodsOrdersEntities = goodsOrdersRepository.findAll();
        List<GoodsOrdersEntity> goodsOrdersByCustomer = new ArrayList<>();

        for (GoodsOrdersEntity goodsOrdersEntity : goodsOrdersEntities) {
            if (goodsOrdersEntity.getOrdersLists().getCustomers().getId().equals(customerId)) {
                goodsOrdersByCustomer.add(goodsOrdersEntity);
            }
        }

        return goodsOrdersByCustomer;
    }


}
