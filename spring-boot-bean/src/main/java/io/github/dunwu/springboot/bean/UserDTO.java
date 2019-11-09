package io.github.dunwu.springboot.bean;

import lombok.Data;
import lombok.ToString;

/**
 * @author Zhang Peng
 * @since 2018-11-02
 */
@Data
@ToString
public class UserDTO {

	private long id;

	private String name;

	private InfoDTO infoDTO;

}
