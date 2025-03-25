import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.ComputerRepairRequestRepository;
import repository.ComputerRepairedFormRepository;
import repository.jdbc.ComputerRepairRequestJdbcRepository;
import repository.jdbc.ComputerRepairedFormJdbcRepository;
import services.ComputerRepairServices;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class RepairShopConfig {

    @Bean
    public Properties jdbcProperties() {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            throw new RuntimeException("Cannot load database configuration: " + e.getMessage(), e);
        }
        return props;
    }

    @Bean
    public ComputerRepairRequestRepository requestsRepository() {
        return new ComputerRepairRequestJdbcRepository(jdbcProperties());
    }

    @Bean
    public ComputerRepairedFormRepository formsRepository() {
        return new ComputerRepairedFormJdbcRepository(jdbcProperties());
    }

    @Bean
    public ComputerRepairServices computerRepairServices() {
        return new ComputerRepairServices(requestsRepository(), formsRepository());
    }
}