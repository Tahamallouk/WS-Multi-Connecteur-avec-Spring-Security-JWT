package ma.formations.multiconnector.presentation.graphql;

import lombok.RequiredArgsConstructor;
import ma.formations.multiconnector.dtos.transaction.*;
import ma.formations.multiconnector.service.ITransactionService;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TransactionGraphqlController {

    private final ITransactionService transactionService;

    @MutationMapping
    public AddWirerTransferResponse addWirerTransfer(@Argument("dto") AddWirerTransferRequest dto) {
        return transactionService.wiredTransfer(dto);
    }
}
