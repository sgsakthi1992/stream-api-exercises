package demo.streamapi;

import demo.streamapi.repos.CustomerRepo;
import demo.streamapi.repos.OrderRepo;
import demo.streamapi.repos.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
public class AppCommandRunner implements CommandLineRunner {

	@Autowired
	private CustomerRepo customerRepos;
	
	@Autowired
	private OrderRepo orderRepos;
	
	@Autowired
	private ProductRepo productRepos;

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		log.info("Customers:");
		customerRepos.findAll()
				.forEach(c -> log.info(c.toString()));

		log.info("Orders:");
		orderRepos.findAll()
				.forEach(o -> log.info(o.toString()));

		log.info("Products:");
		productRepos.findAll()
				.forEach(p -> log.info(p.toString()));
	}

}
