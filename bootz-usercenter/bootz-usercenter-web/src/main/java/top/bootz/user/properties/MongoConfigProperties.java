package top.bootz.user.properties;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Desc: TODO
 * 
 * @author John
 * @dateTime: 2018年6月10日 下午10:47:07
 */

@Data
@NoArgsConstructor
@Validated
@ConfigurationProperties(prefix = "spring.data.mongodb.custom")
public class MongoConfigProperties {

	@NotBlank
	private String dbname;

	@NotEmpty
	private List<String> hosts;

	@NotEmpty
	private List<Integer> ports;

	private String replicaSet;

	private String username;

	private String password;

	private String database;

	private String authenticationDatabase;

	private int connectionsPerHost;

	private int minConnectionsPerHost;

}
