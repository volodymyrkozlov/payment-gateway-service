package com.volodymyrkozlow.paymentgateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volodymyrkozlow.paymentgateway.dto.TransactionDto;
import com.volodymyrkozlow.paymentgateway.exception.InternalServerErrorException;
import com.volodymyrkozlow.paymentgateway.property.PaymentGatewayProperties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * Service to provide a possibility to publish payment transactions to a file.
 */
@Service
public class FileExternalAuditService implements ExternalAuditService {
    private final File logFile;
    private final ObjectMapper objectMapper;

    public FileExternalAuditService(final PaymentGatewayProperties paymentGatewayProperties,
                                    final ObjectMapper objectMapper) throws IOException {
        this.logFile = createLogFile(paymentGatewayProperties);
        this.objectMapper = objectMapper;
    }

    /**
     * Records submitted payment data to a file.
     *
     * @param transactionDto - must not be null.
     */
    @Override
    public void publishTransaction(final TransactionDto transactionDto) {
        try {
            final var transactionString =
                String.format("%s%s", objectMapper.writeValueAsString(transactionDto), "\r\n");
            Files.writeString(logFile.toPath(), transactionString, StandardOpenOption.APPEND);
        } catch (final IOException e) {
            throw new InternalServerErrorException(e);
        }
    }

    private static File createLogFile(final PaymentGatewayProperties paymentGatewayProperties) throws IOException {
        final var logFile = new File(paymentGatewayProperties.getLogFilePath());
        if (logFile.getParentFile().mkdirs() && !logFile.exists()) {
            logFile.createNewFile();
        }
        return logFile;
    }
}
