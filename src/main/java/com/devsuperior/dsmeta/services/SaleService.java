package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.ReportMinDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    public Page<ReportMinDTO> findReport(String minDate, String maxDate, String name, Pageable pageable) {
        Map<String, LocalDate> dates = parseDates(minDate, maxDate);
        Page<Sale> result = repository.searchReport(dates.get("minDate"), dates.get("maxDate"), name, pageable);
        return result.map(ReportMinDTO::new);
    }

    public List<SummaryMinDTO> findSummary(String minDate, String maxDate) {
        Map<String, LocalDate> dates = parseDates(minDate, maxDate);
        return repository.searchSummary(dates.get("minDate"), dates.get("maxDate"));
    }

    private Map<String, LocalDate> parseDates(String minDate, String maxDate) {
        LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        LocalDate minDateInLocalDate = today.minusYears(1);
        LocalDate maxDateInLocalDate = today;

        if (!minDate.isEmpty() || !maxDate.isEmpty()) {
            if (!minDate.isEmpty()) {
                minDateInLocalDate = LocalDate.parse(minDate, formatter);
            }
            if (!maxDate.isEmpty()) {
                maxDateInLocalDate = LocalDate.parse(maxDate, formatter);
            }
        }
        return Map.of("minDate", minDateInLocalDate, "maxDate", maxDateInLocalDate);
    }
}
