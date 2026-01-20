package br.com.sonda.aeronave.exception;

import java.time.Instant;

public record CustomFieldsException
        (
                Instant timestamp,
                Integer status,
                String path,
                String field,
                String message
        ) {
}
