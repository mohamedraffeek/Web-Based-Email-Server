package com.CSED.email;

import com.CSED.email.dataAccessObject.Data;
import com.CSED.email.dataAccessObject.IData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class EmailApplication {

	private static final Data data = Data.getInstance();


	public static void main(String[] args) throws IOException {
		data.loadData();
		SpringApplication.run(EmailApplication.class, args);
	}

}
