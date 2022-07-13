package rzeznik.grzegorz.exotic_farm.animal.spider.molt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import rzeznik.grzegorz.exotic_farm.animal.spider.SpiderDTO;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MoltDTO {

    private Integer id;
    private LocalDate date;
    private SpiderDTO spiderDTO;

    public MoltDTO(Integer id, LocalDate date) {
        this.id = id;
        this.date = date;
    }

    public MoltDTO(LocalDate date) {
        this.date = date;
    }


}
