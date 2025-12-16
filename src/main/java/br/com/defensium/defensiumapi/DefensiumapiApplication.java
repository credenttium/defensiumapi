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

	@Autowired
	private Environment environment;

	private LocalDateTime dataHoraImplantacao = LocalDateTime.now();

	public static void main(String[] args) {
		SpringApplication.run(DefensiumapiApplication.class, args);
	}

	@GetMapping
	public LinkedHashMap<String, String> getInformation() throws UnknownHostException {
		LinkedHashMap<String, String> informacao = new LinkedHashMap<>();
			informacao.put("Aplicação", "DefensiumService");
			informacao.put("Porta", environment.getProperty("local.server.port"));
			informacao.put("Descrição", "Sistema de Gestão de Autenticação e Autorização de Usuários Multisistema");
			informacao.put("Ambiente", "Desenvolvimento");
			informacao.put("Implantação", getRecuperarDataHora());
			informacao.put("Versão", versao);
			informacao.put("Endereço", InetAddress.getLocalHost().getHostAddress());
			informacao.put("Demanda", getDemanda());
		return informacao;
	}

	private String getRecuperarDataHora() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return this.dataHoraImplantacao.format(dateTimeFormatter);
	}

	private String getDemanda() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
		return "DEFENSIUM".concat(LocalDateTime.now().format(dateTimeFormatter)).concat("API");
	}

}
