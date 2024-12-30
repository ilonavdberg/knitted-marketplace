package com.knitted.marketplace.utils.validation;

import java.util.Map;

public record ValidationResult(
        boolean isValid,
        Map<String, String> errors
) {
}
