package ma.formations.multiconnector.service;

import ma.formations.multiconnector.dtos.customer.*;

import java.util.List;

public interface ICustomerService {
    List<CustomerDto> getAllCustomers();
    CustomerDto getCustomByIdentity(String identityRef);
    AddCustomerResponse createCustomer(AddCustomerRequest dto);
    UpdateCustomerResponse updateCustomer(String identityRef, UpdateCustomerRequest dto);
    String deleteCustomerByIdentityRef(String identityRef);
}
