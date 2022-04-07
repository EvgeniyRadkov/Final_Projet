package com.gmail.radzkovevgeni.legal.service;

import com.gmail.radzkovevgeni.legal.service.model.FindCompanyDTO;
import com.gmail.radzkovevgeni.legal.service.model.PaginationDTO;
import com.gmail.radzkovevgeni.legal.service.model.ViewCompanyDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CompaniesService {
    List<ViewCompanyDTO> getList(PaginationDTO getCompanyDTO, Pageable pageable);

    List<ViewCompanyDTO> find(FindCompanyDTO findCompanyDTO);
}
