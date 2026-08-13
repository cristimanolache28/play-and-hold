package com.playandhold.portfolio_service.asset;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tradable-asset")
@RequiredArgsConstructor
public class TradableAssetController {

    private final TradableAssetService service;



}
