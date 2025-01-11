package demo.SpringBootWithAWS.web.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class HelloResponseDtoTest {

    @Test
    public void lombokTest() {
        //given
        String name = "songhae";
        int amount = 1;

        //when
        HelloResponseDto helloResponseDto = new HelloResponseDto(name, amount);

        //then
        assertThat(helloResponseDto.getName()).isEqualTo(name);
        assertThat(helloResponseDto.getYear()).isEqualTo(amount);
    }
}
