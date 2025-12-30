package ma.formations.multiconnector.service.impl;

import lombok.RequiredArgsConstructor;
import ma.formations.multiconnector.dao.CustomerRepository;
import ma.formations.multiconnector.dao.UserRepository;
import ma.formations.multiconnector.domain.Customer;
import ma.formations.multiconnector.domain.User;
import ma.formations.multiconnector.dtos.customer.*;
import ma.formations.multiconnector.service.ICustomerService;
import ma.formations.multiconnector.service.exception.BusinessException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public CustomerDto getCustomByIdentity(String identityRef) {
        Customer customer = customerRepository.findByIdentityRef(identityRef)
                .orElseThrow(() -> new BusinessException("Customer not found with identityRef=" + identityRef));
        return toDto(customer);
    }

    @Override
    public AddCustomerResponse createCustomer(AddCustomerRequest dto) {
        if (customerRepository.existsByIdentityRef(dto.getIdentityRef())) {
            throw new BusinessException("identityRef already exists: " + dto.getIdentityRef());
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException("username already exists: " + dto.getUsername());
        }

        User user = User.builder()
                .username(dto.getUsername())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .build();

        Customer customer = Customer.builder()
                .identityRef(dto.getIdentityRef())
                .user(user)
                .build();

        Customer saved = customerRepository.save(customer);

        return AddCustomerResponse.builder()
                .message("Customer created")
                .customer(toDto(saved))
                .build();
    }

    @Override
    public UpdateCustomerResponse updateCustomer(String identityRef, UpdateCustomerRequest dto) {
        Customer customer = customerRepository.findByIdentityRef(identityRef)
                .orElseThrow(() -> new BusinessException("Customer not found with identityRef=" + identityRef));

        // username unique check
        if (!customer.getUser().getUsername().equals(dto.getUsername())
                && userRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException("username already exists: " + dto.getUsername());
        }

        customer.getUser().setUsername(dto.getUsername());
        customer.getUser().setFirstname(dto.getFirstname());
        customer.getUser().setLastname(dto.getLastname());

        Customer saved = customerRepository.save(customer);

        return UpdateCustomerResponse.builder()
                .message("Customer updated")
                .customer(toDto(saved))
                .build();
    }

    @Override
    public String deleteCustomerByIdentityRef(String identityRef) {
        Customer customer = customerRepository.findByIdentityRef(identityRef)
                .orElseThrow(() -> new BusinessException("Customer not found with identityRef=" + identityRef));
        customerRepository.delete(customer);
        return "Customer with identity " + identityRef + " is removed";
    }

    private CustomerDto toDto(Customer c) {
        return CustomerDto.builder()
                .id(c.getId())
                .identityRef(c.getIdentityRef())
                .username(c.getUser().getUsername())
                .firstname(c.getUser().getFirstname())
                .lastname(c.getUser().getLastname())
                .build();
    }
}
