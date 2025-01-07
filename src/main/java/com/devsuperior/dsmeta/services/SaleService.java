package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.ReportMinDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    public Page<ReportMinDTO> findReport(String minDate, String maxDate, String name, Pageable pageable) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        LocalDate minDateInLocalDate = today.minusYears(1);
        LocalDate maxDateInLocalDate = today;
        if (!minDate.isEmpty() || !maxDate.isEmpty()) {
            minDateInLocalDate = LocalDate.parse(minDate, dtf);
            maxDateInLocalDate = LocalDate.parse(maxDate, dtf);
        }
        Page<Sale> result = repository.findReport(minDateInLocalDate,maxDateInLocalDate,name,pageable);
        return result.map(ReportMinDTO::new);
    }
}
