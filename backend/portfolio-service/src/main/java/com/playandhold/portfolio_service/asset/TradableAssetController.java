package com.playandhold.portfolio_service.asset;

import com.playandhold.portfolio_service.asset.dto.CreateTradableAssetRequest;
import com.playandhold.portfolio_service.asset.dto.TradableAssetResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/search")
    public ResponseEntity<List<TradableAssetResponse>> searchTradableAssets(@RequestParam String query) {
        return ResponseEntity.ok(service.searchTradableAssets(query));
    }

}
