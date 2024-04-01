package org.orgless.carboncellassignment.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orgless.carboncellassignment.service.PublicApiService;
import org.orgless.carboncellassignment.utils.ApiEntry;
import org.orgless.carboncellassignment.utils.RequestInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Authorization")

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PublicApiController {
    private final PublicApiService apiService;

    @GetMapping("/entries")
    public ResponseEntity<List<ApiEntry>> getEntries(@Valid RequestInfoDto infoDto) throws Exception {
        return ResponseEntity.ok(apiService.getEntries(infoDto));
    }
}
