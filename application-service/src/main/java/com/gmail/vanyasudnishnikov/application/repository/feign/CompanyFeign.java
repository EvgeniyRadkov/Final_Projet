package com.gmail.vanyasudnishnikov.application.repository.feign;

import com.gmail.vanyasudnishnikov.application.service.model.ViewCompanyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "company", url = "http://localhost:8070/api/legals")
public interface CompanyFeign {

    @GetMapping
    Optional<ViewCompanyDTO> getCompanyById(@RequestParam("LegalId") Long companyId);

    @GetMapping
    Optional<ViewCompanyDTO> getCompanyByName(@RequestParam("companyName") String companyName);
}