package br.com.defensium.defensiumapi;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping({ "/", "" })
public class DefensiumapiApplication {

	@Value("${app.version}")
	private String versao;

	@Value("${app.description}")
	private String descricao;

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(DefensiumapiApplication.class, args);
	}

	@GetMapping
	public LinkedHashMap<String, String> getInformation() throws UnknownHostException {
		LinkedHashMap<String, String> informacao = new LinkedHashMap<>();
		informacao.put("Aplicação", "DefensiumService");
		informacao.put("Porta", environment.getProperty("local.server.port"));
		informacao.put("Descrição", descricao);
		informacao.put("Ambiente", "Desenvolvimento");
		informacao.put("Implantação", getRecuperarDataHora());
		informacao.put("Versão", versao);
		informacao.put("Endereço", InetAddress.getLocalHost().getHostAddress());
		return informacao;
	}

	private String getRecuperarDataHora() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return LocalDateTime.now().format(dateTimeFormatter);
	}

}
