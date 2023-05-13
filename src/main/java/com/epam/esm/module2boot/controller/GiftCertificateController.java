package com.epam.esm.module2boot.controller;

import com.epam.esm.module2boot.converter.GetAllCertParamsToQueryMapConverter;
import com.epam.esm.module2boot.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.dto.GiftCertificateUpdateDTO;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/GiftCertificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;

    }

    @PostMapping
    public ResponseEntity<GiftCertificateDTO> createGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateInDTO)
            throws DataBaseConstrainException {
        GiftCertificateDTO giftCertificateOutDto =
                giftCertificateService.createGiftCertificate(giftCertificateInDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(giftCertificateOutDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDTO> getGiftCertificateById(@PathVariable int id) {
        GiftCertificateDTO giftCertificateDTO = giftCertificateService.getGiftCertificateDTOById(id);
        return ResponseEntity.ok(giftCertificateDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificateDTO>
    updateGiftCertificate(@PathVariable int id,
                          @RequestParam MultiValueMap<String, String> queryParams) {

        GiftCertificateUpdateDTO giftCertificateUpdateDTO = new GiftCertificateUpdateDTO();
        giftCertificateUpdateDTO.setFields(queryParams.toSingleValueMap());

        giftCertificateService.updateGiftCertificate(id, giftCertificateUpdateDTO);

        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiftCertificate(@PathVariable int id) {
        giftCertificateService.deleteGiftCertificateById(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity<Page<GiftCertificateDTO>> getAllGiftCertificates(
            @RequestParam MultiValueMap<String, String> queryParams,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "25") int size) {

        GetAllCertParamsToQueryMapConverter converter =
                new GetAllCertParamsToQueryMapConverter(queryParams);

        Page<GiftCertificateDTO> giftCertificates =
                giftCertificateService.getGiftCertificatesBy(converter.getGiftCertQueryMap(),
                        PageRequest.of(page, size, converter.getSort()));

        if (!giftCertificates.isEmpty()) {
            return ResponseEntity.ok(giftCertificates);
        } else {
            throw new NotFoundException("Objects with this params not found");
        }
    }
}
