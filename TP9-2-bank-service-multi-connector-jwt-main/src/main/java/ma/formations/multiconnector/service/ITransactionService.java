package ma.formations.multiconnector.service;

import ma.formations.multiconnector.dtos.transaction.*;

import java.util.List;

public interface ITransactionService {
    AddWirerTransferResponse wiredTransfer(AddWirerTransferRequest dto);
    List<TransactionDto> getTransactions(GetTransactionListRequest dto);
}
