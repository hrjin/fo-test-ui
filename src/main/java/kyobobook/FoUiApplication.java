package kyobobook;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.ResponseEntity;

// import kyobobook.common.Constants;

@SpringBootApplication
public class FoUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoUiApplication.class, args);
	}

    @Bean
    public CommandLineRunner getTokenKeyRunner() {
        return new CommandLineRunner() {
            
            @Override
            public void run(String... args) throws Exception {
                
                // Nothing Todo..
                
                // String url = authUrl + authkeyPath;
                // HttpHeaders httpHeaders = new HttpHeaders();
                // ResponseEntity<?> responseEntity = restTemplateUtil.send(url, HttpMethod.GET, httpHeaders, null, ResponseMessage.class);
                
                // ResponseMessage responseMessage = (ResponseMessage) responseEntity.getBody();
                // System.setProperty(Constants.PROPS_PUBLIC_KEY_NAME, responseMessage.getData().toString());
                // Constants.PUBLIC_KEY = responseMessage.getData().toString();
            }
        };
    }
}
