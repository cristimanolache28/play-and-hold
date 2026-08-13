package com.playandhold.portfolio_service.asset;

import com.playandhold.portfolio_service.asset.dto.CreateTradableAssetRequest;
import com.playandhold.portfolio_service.asset.dto.TradableAssetResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tradable-assets")
@RequiredArgsConstructor
public class TradableAssetController {

    private final TradableAssetService service;

    @PostMapping()
    public ResponseEntity<TradableAssetResponse> createTradableAsset(@Valid @RequestBody CreateTradableAssetRequest request) {
        TradableAssetResponse assetResponse = service.createTradableAsset(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assetResponse);
    }

}
