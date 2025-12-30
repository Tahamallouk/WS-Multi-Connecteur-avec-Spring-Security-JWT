package ma.formations.multiconnector.service.impl;

import lombok.AllArgsConstructor;
import ma.formations.multiconnector.dao.PermissionRepository;
import ma.formations.multiconnector.dao.RoleRepository;
import ma.formations.multiconnector.dao.UserRepository;
import ma.formations.multiconnector.domain.Permission;
import ma.formations.multiconnector.domain.Role;
import ma.formations.multiconnector.domain.User;
import ma.formations.multiconnector.dtos.user.PermissionVo;
import ma.formations.multiconnector.dtos.user.RoleVo;
import ma.formations.multiconnector.dtos.user.UserVo;
import ma.formations.multiconnector.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements IUserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final PermissionRepository permissionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        UserVo userVo = modelMapper.map(user, UserVo.class);

        // Flatten permissions -> as "authorities" too (comme dans le poly)
        List<RoleVo> permissionAuthorities = new ArrayList<>();
        userVo.getAuthorities().forEach(
                roleVo -> roleVo.getAuthorities().forEach(
                        permissionVo -> permissionAuthorities.add(
                                RoleVo.builder().authority(permissionVo.getAuthority()).build()
                        )
                )
        );
        userVo.getAuthorities().addAll(permissionAuthorities);

        return userVo;
    }

    @Override
    public void save(UserVo userVo) {
        User user = modelMapper.map(userVo, User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // garder uniquement les vrais roles ROLE_*
        user.setAuthorities(
                user.getAuthorities().stream()
                        .filter(r -> r.getAuthority() != null && r.getAuthority().startsWith("ROLE_"))
                        .map(r -> roleRepository.findByAuthority(r.getAuthority())
                                .orElseThrow(() -> new RuntimeException("Role not found: " + r.getAuthority())))
                        .collect(Collectors.toList())
        );

        userRepository.save(user);
    }

    @Override
    public void save(RoleVo roleVo) {
        Role role = modelMapper.map(roleVo, Role.class);

        role.setAuthorities(
                role.getAuthorities().stream()
                        .map(p -> permissionRepository.findByAuthority(p.getAuthority())
                                .orElseThrow(() -> new RuntimeException("Permission not found: " + p.getAuthority())))
                        .collect(Collectors.toList())
        );

        roleRepository.save(role);
    }

    @Override
    public void save(PermissionVo vo) {
        permissionRepository.save(modelMapper.map(vo, Permission.class));
    }

    @Override
    public RoleVo getRoleByName(String authority) {
        return modelMapper.map(
                roleRepository.findByAuthority(authority)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + authority)),
                RoleVo.class
        );
    }

    @Override
    public PermissionVo getPermissionByName(String authority) {
        return modelMapper.map(
                permissionRepository.findByAuthority(authority)
                        .orElseThrow(() -> new RuntimeException("Permission not found: " + authority)),
                PermissionVo.class
        );
    }
}
