package com.volodymyrkozlow.paymentgateway.dto.error;

import java.util.List;

/**
 * Data transfer object for response error.
 */
public record TransactionDeclinedErrorDto(Boolean approved,
                                          List<TransactionDeclinedErrorDetailsDto> errors) {
}
