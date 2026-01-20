package br.com.sonda.aeronave.exception;

import java.time.Instant;

public record StandardFieldsException(
                Instant timestamp,
                Integer status,
                String error,
                String path
        ) {
}
