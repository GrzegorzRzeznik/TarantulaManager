package rzeznik.grzegorz.exotic_farm.animal.spider.molt;

import lombok.Getter;
import lombok.Setter;
import rzeznik.grzegorz.exotic_farm.animal.spider.Spider;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
public class Molt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private LocalDate date;
    @ManyToOne
    private Spider spider;

    public Molt() {
    }

    public Molt(LocalDate date, Spider spider) {
        this.date = date;
        this.spider = spider;
    }

    public Molt(Integer id, LocalDate date) {
        this.id = id;
        this.date = date;
    }

    public MoltDTO toDTO() {
        return new MoltDTO(id, date);
    }

    public static Molt applyDTO(MoltDTO moltDTO) {
        return new Molt(moltDTO.getId(), moltDTO.getDate());

    }
}

