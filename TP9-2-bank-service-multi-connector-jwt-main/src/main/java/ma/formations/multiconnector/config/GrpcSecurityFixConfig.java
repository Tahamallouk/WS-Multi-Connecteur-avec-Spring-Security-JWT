package ma.formations.multiconnector.config;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

/**
 * Fix required by GrpcServerSecurityAutoConfiguration:
 * provides a GrpcAuthenticationReader bean so the server can start.
 * TP9 focuses on HTTP JWT, not gRPC security.
 */
@Configuration
public class GrpcSecurityFixConfig {

    @Bean
    public GrpcAuthenticationReader grpcAuthenticationReader() {
        return new GrpcAuthenticationReader() {
            @Override
            public Authentication readAuthentication(ServerCall<?, ?> call, Metadata headers) {
                // No auth for gRPC (return null => unauthenticated)
                return null;
            }
        };
    }
}
