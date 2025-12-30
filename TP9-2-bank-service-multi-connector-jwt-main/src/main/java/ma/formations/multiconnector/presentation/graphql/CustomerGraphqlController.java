package ma.formations.multiconnector.presentation.graphql;

import lombok.RequiredArgsConstructor;
import ma.formations.multiconnector.dtos.customer.*;
import ma.formations.multiconnector.service.ICustomerService;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerGraphqlController {

    private final ICustomerService customerService;

    @QueryMapping
    public List<CustomerDto> customers() {
        return customerService.getAllCustomers();
    }

    @QueryMapping
    public CustomerDto customerByIdentity(@Argument String identityRef) {
        return customerService.getCustomByIdentity(identityRef);
    }

    @MutationMapping
    public AddCustomerResponse createCustomer(@Argument("dto") AddCustomerRequest dto) {
        return customerService.createCustomer(dto);
    }

    @MutationMapping
    public UpdateCustomerResponse updateCustomer(@Argument String identityRef, @Argument("dto") UpdateCustomerRequest dto) {
        return customerService.updateCustomer(identityRef, dto);
    }

    @MutationMapping
    public String deleteCustomer(@Argument String identityRef) {
        return customerService.deleteCustomerByIdentityRef(identityRef);
    }
}
