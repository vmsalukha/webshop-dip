package com.example.webshopdip.services;

import com.example.webshopdip.dtos.*;
import com.example.webshopdip.entities.EvaluationsGoodEntity;
import com.example.webshopdip.entities.GoodsEntity;
import com.example.webshopdip.entities.GoodsInvoicesEntity;
import com.example.webshopdip.entities.GoodsOrdersEntity;
import com.example.webshopdip.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GoodsInvoicesService {

    @Autowired
    private GoodsInvoicesRepository goodsInvoicesRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private SubcategoriesGoodsService subcategoriesGoodsService;
    @Autowired
    private PhotosGoodsService photosGoodsService;
    @Autowired
    private EvaluationsGoodRepository evaluationsGoodRepository;
    @Autowired
    private GoodsOrdersRepository goodsOrdersRepository;
    @Autowired
    private SellersRepository sellersRepository;


//    public GoodsInvoicesDTO createGoodsInvoices(GoodsInvoicesDTO dto) {
//
//        GoodsInvoicesEntity entity = new GoodsInvoicesEntity();
//        entity.setGoods(dto.getGoods());
//        entity.setQuantity(dto.getQuantity());
//        entity.setPrice(dto.getPrice());
//
//        GoodsInvoicesEntity savedEntity = goodsInvoicesRepository.save(entity);
//
//        return entityToDTO(savedEntity);
//    }
//
//    public GoodsInvoicesDTO entityToDTO(GoodsInvoicesEntity entity) {
//
//        GoodsInvoicesDTO dto = new GoodsInvoicesDTO();
//
//        dto.setId(entity.getId());
//        dto.setGoods(entity.getGoods());
//        dto.setQuantity(entity.getQuantity());
//        dto.setPrice(entity.getPrice());
//
//        return dto;
//    }
// ////////////////////////////////2023.09.07///////////////////////////////////////////////////////////////////////////
//    public GoodsInvoicesDTO createGoodsInvoices(GoodsInvoicesDTO dto) {
//        GoodsInvoicesEntity entity = new GoodsInvoicesEntity();
//
//        // Отримати GoodsEntity з Optional<GoodsEntity> і перевірити, чи він присутній
//        Optional<GoodsEntity> goodsEntityOptional = goodsRepository.findById(dto.getGoods().getId());
//
//        if (goodsEntityOptional.isPresent()) {
//            GoodsEntity goodsEntity = goodsEntityOptional.get(); // Отримати GoodsEntity з Optional
//
//            entity.setGoods(goodsEntity);
//            entity.setQuantity(dto.getQuantity());
//            entity.setPrice(dto.getPrice());
//
//            GoodsInvoicesEntity savedEntity = goodsInvoicesRepository.save(entity);
//
//            return entityToDTO(savedEntity);
//        } else {
//            // Обробка ситуації, коли GoodsEntity не знайдено за заданим id
//            // Можна кинути виключення або обробити це інакше, в залежності від бізнес-логіки
//            return null; // або виконати іншу логіку за замовчуванням
//        }
//    }
//
//    public GoodsInvoicesDTO entityToDTO(GoodsInvoicesEntity entity) {
//        GoodsInvoicesDTO dto = new GoodsInvoicesDTO();
//        dto.setId(entity.getId());
//
//        // Перетворюємо GoodsEntity на GoodsDTO
//        GoodsGetAllDTO goodsGetAllDTO = new GoodsGetAllDTO();
//        goodsGetAllDTO.setId(entity.getGoods().getId());
//        // ???Встановлюємо інші властивості GoodsDTO, які вам потрібні
//
//
//        goodsGetAllDTO.setName(entity.getGoods().getName());
//
//        goodsGetAllDTO.setShort_discription(entity.getGoods().getShort_discription());
//
//        goodsGetAllDTO.setSubcategoriesGoodsName(subcategoriesGoodsService.entityToDTO(entity.getGoods().getSubcategoriesGoods()).getName());
//        goodsGetAllDTO.setPhotosGoodsDTOS(photosGoodsService.entityListToDTOS(entity.getGoods().getPhotosGoods()));
//
//
//        dto.setGoods(goodsGetAllDTO);
//        dto.setQuantity(entity.getQuantity());
//        dto.setPrice(entity.getPrice());
//
//        return dto;
//    }
// /////////////////////////////////////////////////////////////////////////////////

    public GoodsInvoicesDTO createGoodsInvoices(GoodsInvoicesToSaveDTO dto) {
        GoodsInvoicesEntity entity = new GoodsInvoicesEntity();

        // Отримати GoodsEntity з Optional<GoodsEntity> і перевірити, чи він присутній
        Optional<GoodsEntity> goodsEntityOptional = goodsRepository.findById(dto.getGoodsId());

        if (goodsEntityOptional.isPresent()) {
            GoodsEntity goodsEntity = goodsEntityOptional.get(); // Отримати GoodsEntity з Optional

            entity.setGoods(goodsEntity);
            entity.setSellers(sellersRepository.findById(dto.getSellersId()).orElse(null));
            entity.setQuantity(dto.getQuantity());
            entity.setPrice(dto.getPrice());
            entity.setDate_invoice(LocalDate.now());
            // Зберегти нову сутність GoodsInvoicesEntity в базі даних
            GoodsInvoicesEntity savedEntity = goodsInvoicesRepository.save(entity);

            // Перетворити збережену сутність на DTO та повернути її
            return entityToDTO(savedEntity);
        } else {
            // Обробка ситуації, коли GoodsEntity не знайдено за заданим id
            // Можна кинути виключення або обробити це інакше, в залежності від бізнес-логіки
            return null; // або виконати іншу логіку за замовчуванням
        }
    }

    public GoodsInvoicesDTO entityToDTO(GoodsInvoicesEntity entity) {
        GoodsInvoicesDTO dto = new GoodsInvoicesDTO();
        dto.setId(entity.getId());

        // Перетворюємо GoodsEntity на GoodsDTO
        GoodsGetAllDTO goodsGetAllDTO = new GoodsGetAllDTO();
        goodsGetAllDTO.setId(entity.getGoods().getId());
        // ??? Встановлюємо інші властивості GoodsDTO, які вам потрібні
        goodsGetAllDTO.setName(entity.getGoods().getName());
        goodsGetAllDTO.setShort_discription(entity.getGoods().getShort_discription());
        goodsGetAllDTO.setSubcategoriesGoodsName(subcategoriesGoodsService.entityToDTO(entity.getGoods().getSubcategoriesGoods()).getName());
        goodsGetAllDTO.setPhotosGoodsDTOS(photosGoodsService.entityListToDTOS(entity.getGoods().getPhotosGoods()));

        dto.setGoods(goodsGetAllDTO);
        dto.setQuantity(entity.getQuantity());
//        System.out.println("GoodsInvoicesDTO entityToDTO - quantity: " + dto.getQuantity());
        dto.setPrice(entity.getPrice());
        dto.setSeller(new SellersDTO(entity.getSellers().getId(), entity.getSellers().getName()));
        List<EvaluationsGoodEntity> evaluationsGoodEntities = evaluationsGoodRepository.findByGoodsId(dto.getGoods().getId());
        Integer summ = 0, num = 0;
        for (EvaluationsGoodEntity evaluationsGood : evaluationsGoodEntities) {
            summ += evaluationsGood.getEvaluation();
            num++;
        }
        if (summ.equals(0) || num.equals(0)) {
            dto.setEvaluation(0);
        } else dto.setEvaluation(summ / num);


        return dto;
    }

    public GoodsInvoicesDTO getOne(Long id) {
        Optional<GoodsInvoicesEntity> optional = goodsInvoicesRepository.findById(id);

        GoodsInvoicesEntity entity = optional.get();
        return entityToDTO(entity);
    }

    public List<GoodsInvoicesDTO> getAll(HttpServletRequest request) {
        Iterable<GoodsInvoicesEntity> goodsInvoicesEntities = goodsInvoicesRepository.findAll(Sort.by(Sort.Order.asc("id")));

        return getListEntitiesToDTOS(request, goodsInvoicesEntities);
    }

    public List<GoodsInvoicesOnCounterDTO> getAllOnCounter (HttpServletRequest request) {
        Iterable<GoodsInvoicesEntity> goodsInvoicesEntities = goodsInvoicesRepository.findAll(Sort.by(Sort.Order.asc("id")));

        return getListDTOSToCounterDTOS(getListEntitiesToDTOS(request, goodsInvoicesEntities)) ;
    }


    public Integer getQuantityGoodTheShop (Long goodsInvoicesId){

        Integer quantity=goodsInvoicesRepository.findById(goodsInvoicesId).orElse(new GoodsInvoicesEntity()).getQuantity();
        List<GoodsOrdersEntity> foundGoods = goodsOrdersRepository.findByGoodsInvoices_Id(goodsInvoicesId);

        Integer quantityInCart = foundGoods.stream().mapToInt(GoodsOrdersEntity::getQuantity).sum();


        return quantity-quantityInCart;
    }

    public List<GoodsInvoicesDTO> getFindByCategoriesGoodsName(HttpServletRequest request, String categoriesGoodsName) {
        List<GoodsInvoicesEntity> goodsInvoicesEntities = goodsInvoicesRepository.findByGoodsSubcategoriesGoodsCategoriesGoodsName(categoriesGoodsName);

        return getListEntitiesToDTOS(request, goodsInvoicesEntities);
    }

    public List<GoodsInvoicesDTO> getListEntitiesToDTOS(HttpServletRequest request, Iterable<GoodsInvoicesEntity> goodsInvoicesEntities) {
        List<GoodsInvoicesDTO> goodsInvoicesDTOS = new ArrayList<>();
        for (GoodsInvoicesEntity goodsInvoicesEntity : goodsInvoicesEntities) {
            GoodsInvoicesDTO dto = entityToDTO(goodsInvoicesEntity);
            goodsInvoicesDTOS.add(dto);
        }

        String currentUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        for (GoodsInvoicesDTO dto : goodsInvoicesDTOS) {
            List<PhotosGoodsDTO> photosDTOList = dto.getGoods().getPhotosGoodsDTOS();

            for (PhotosGoodsDTO photoDTO : photosDTOList) {
                String imagePath = currentUrl + "/images/" + photoDTO.getPath();
                photoDTO.setPath(imagePath);
//                System.out.println("Image path: " + imagePath);
            }
        }
        return goodsInvoicesDTOS;
    }


    public List<GoodsInvoicesOnCounterDTO> getListDTOSToCounterDTOS(Iterable<GoodsInvoicesDTO> goodsInvoicesDTOS) {
        List<GoodsInvoicesOnCounterDTO> goodsInvoicesOnCounterDTOS = new ArrayList<>();
        for (GoodsInvoicesDTO goodInvoiceDTO: goodsInvoicesDTOS){
            GoodsInvoicesOnCounterDTO dto = new GoodsInvoicesOnCounterDTO();
            dto.setId(goodInvoiceDTO.getId());
            dto.setDate_invoice(goodInvoiceDTO.getDate_invoice());
            dto.setGoods(goodInvoiceDTO.getGoods());
            dto.setPrice(goodInvoiceDTO.getPrice());
            dto.setQuantityDisplayed(goodInvoiceDTO.getQuantity());
            dto.setQuantityTheShop(getQuantityGoodTheShop(goodInvoiceDTO.getId()));
//            Integer n1 = goodInvoiceDTO.getQuantity();
//            Integer n2 = getQuantityGoodTheShop(goodInvoiceDTO.getId());
//            Integer num = n1-n2;
//            System.out.println("good ID: " + goodInvoiceDTO.getId() + ": n1: " + n1 + ", n2: " + n2 + ", num: " + num);
            dto.setQuantitySold(dto.getQuantityDisplayed() - dto.getQuantityTheShop());
            dto.setEvaluation(goodInvoiceDTO.getEvaluation());
            dto.setSellerId(goodInvoiceDTO.getSeller().getId());
            goodsInvoicesOnCounterDTOS.add(dto);
        }

        return goodsInvoicesOnCounterDTOS;
    }


    public List<GoodsInvoicesDTO> getByCategoriesId(HttpServletRequest request, Long id) {
        List<GoodsInvoicesEntity> goodsInvoicesEntities = goodsInvoicesRepository.findGoodsByCategoryId(id);

        return getListEntitiesToDTOS(request, goodsInvoicesEntities);
    }

    public List<GoodsInvoicesDTO> getBySubcategoriesId(HttpServletRequest request, Long id) {
//        List<GoodsInvoicesEntity> goodsInvoicesEntities = goodsInvoicesRepository.findGoodsInvoicesEntitiesByGoodsSubcategoriesGoods_Id(id);
        List<GoodsInvoicesEntity> goodsInvoicesEntities = goodsInvoicesRepository.findGoodsBySubcategoryId(id);

        return getListEntitiesToDTOS(request, goodsInvoicesEntities);
    }

    public List<GoodsInvoicesDTO> getByGoodsId(HttpServletRequest request, Long id) {
        List<GoodsInvoicesEntity> goodsInvoicesEntities = goodsInvoicesRepository.findByGoodsId(id);

        return getListEntitiesToDTOS(request, goodsInvoicesEntities);
    }

    public ResponseEntity removeTheBalance(Long goodInvoicesId) {
        GoodsInvoicesEntity goodsInvoices = goodsInvoicesRepository.findById(goodInvoicesId).orElse(null);
        Integer n = goodsInvoices.getQuantity() - getQuantityGoodTheShop(goodInvoicesId);
        goodsInvoices.setQuantity(n);
        goodsInvoicesRepository.save(goodsInvoices);
        return ResponseEntity.ok("New Product Properties have been added successfully");
    }

//    "/getByCategoriesId""/getBySubcategoriesId""/getByGoodsId"

}
