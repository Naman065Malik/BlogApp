package NM.SpringBoot.BlogApp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import NM.SpringBoot.BlogApp.Utils.JWT;

@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner {

	@Autowired
	private JWT jwt;
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Application Started");
		Map	<String, Object> claims = Map.of("key1", "value1", "key2", "value2", "role", "admin");
		String token = jwt.generateToken("Naman3147", 10000000, claims);
		System.out.println(token);
		System.out.println(jwt.isTokenExpired(token));
		System.out.println(jwt.extractAllClaims(token));
		System.out.println(jwt.getUsername(token));
		System.out.println(jwt.getRole(token));
		System.out.println(jwt.validateToken(token, "Naman3147"));
	}

}
